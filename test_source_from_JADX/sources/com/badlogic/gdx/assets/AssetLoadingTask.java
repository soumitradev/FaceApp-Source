package com.badlogic.gdx.assets;

import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

class AssetLoadingTask implements AsyncTask<Void> {
    volatile Object asset = null;
    final AssetDescriptor assetDesc;
    volatile boolean asyncDone = false;
    volatile boolean cancel = false;
    volatile Array<AssetDescriptor> dependencies;
    volatile boolean dependenciesLoaded = false;
    volatile AsyncResult<Void> depsFuture = null;
    final AsyncExecutor executor;
    volatile AsyncResult<Void> loadFuture = null;
    final AssetLoader loader;
    AssetManager manager;
    final long startTime;
    int ticks = 0;

    public AssetLoadingTask(AssetManager manager, AssetDescriptor assetDesc, AssetLoader loader, AsyncExecutor threadPool) {
        this.manager = manager;
        this.assetDesc = assetDesc;
        this.loader = loader;
        this.executor = threadPool;
        this.startTime = manager.log.getLevel() == 3 ? TimeUtils.nanoTime() : 0;
    }

    public Void call() throws Exception {
        AsynchronousAssetLoader asyncLoader = this.loader;
        if (this.dependenciesLoaded) {
            asyncLoader.loadAsync(this.manager, this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
        } else {
            this.dependencies = asyncLoader.getDependencies(this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
            if (this.dependencies != null) {
                this.manager.injectDependencies(this.assetDesc.fileName, this.dependencies);
            } else {
                asyncLoader.loadAsync(this.manager, this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
                this.asyncDone = true;
            }
        }
        return null;
    }

    public boolean update() {
        this.ticks++;
        if (this.loader instanceof SynchronousAssetLoader) {
            handleSyncLoader();
        } else {
            handleAsyncLoader();
        }
        if (this.asset != null) {
            return true;
        }
        return false;
    }

    private void handleSyncLoader() {
        SynchronousAssetLoader syncLoader = this.loader;
        if (this.dependenciesLoaded) {
            this.asset = syncLoader.load(this.manager, this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
        } else {
            this.dependenciesLoaded = true;
            this.dependencies = syncLoader.getDependencies(this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
            if (this.dependencies == null) {
                this.asset = syncLoader.load(this.manager, this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
                return;
            }
            this.manager.injectDependencies(this.assetDesc.fileName, this.dependencies);
        }
    }

    private void handleAsyncLoader() {
        StringBuilder stringBuilder;
        AsynchronousAssetLoader asyncLoader = this.loader;
        if (this.dependenciesLoaded) {
            if (this.loadFuture == null && !this.asyncDone) {
                this.loadFuture = this.executor.submit(this);
            } else if (this.asyncDone) {
                this.asset = asyncLoader.loadSync(this.manager, this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
            } else if (this.loadFuture.isDone()) {
                try {
                    this.loadFuture.get();
                    this.asset = asyncLoader.loadSync(this.manager, this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
                } catch (Exception e) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't load asset: ");
                    stringBuilder.append(this.assetDesc.fileName);
                    throw new GdxRuntimeException(stringBuilder.toString(), e);
                }
            }
        } else if (this.depsFuture == null) {
            this.depsFuture = this.executor.submit(this);
        } else if (this.depsFuture.isDone()) {
            try {
                this.depsFuture.get();
                this.dependenciesLoaded = true;
                if (this.asyncDone) {
                    this.asset = asyncLoader.loadSync(this.manager, this.assetDesc.fileName, resolve(this.loader, this.assetDesc), this.assetDesc.params);
                }
            } catch (Exception e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't load dependencies of asset: ");
                stringBuilder.append(this.assetDesc.fileName);
                throw new GdxRuntimeException(stringBuilder.toString(), e2);
            }
        }
    }

    private FileHandle resolve(AssetLoader loader, AssetDescriptor assetDesc) {
        if (assetDesc.file == null) {
            assetDesc.file = loader.resolve(assetDesc.fileName);
        }
        return assetDesc.file;
    }

    public Object getAsset() {
        return this.asset;
    }
}
