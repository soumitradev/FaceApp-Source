package com.badlogic.gdx.assets;

import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonRegionLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.ThreadUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import java.util.Iterator;
import java.util.Stack;

public class AssetManager implements Disposable {
    final ObjectMap<String, Array<String>> assetDependencies;
    final ObjectMap<String, Class> assetTypes;
    final ObjectMap<Class, ObjectMap<String, RefCountedContainer>> assets;
    final AsyncExecutor executor;
    final ObjectSet<String> injected;
    AssetErrorListener listener;
    final Array<AssetDescriptor> loadQueue;
    int loaded;
    final ObjectMap<Class, ObjectMap<String, AssetLoader>> loaders;
    Logger log;
    final Stack<AssetLoadingTask> tasks;
    int toLoad;

    public AssetManager() {
        this(new InternalFileHandleResolver());
    }

    public AssetManager(FileHandleResolver resolver) {
        this.assets = new ObjectMap();
        this.assetTypes = new ObjectMap();
        this.assetDependencies = new ObjectMap();
        this.injected = new ObjectSet();
        this.loaders = new ObjectMap();
        this.loadQueue = new Array();
        this.tasks = new Stack();
        this.listener = null;
        this.loaded = 0;
        this.toLoad = 0;
        this.log = new Logger("AssetManager", 0);
        setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
        setLoader(Music.class, new MusicLoader(resolver));
        setLoader(Pixmap.class, new PixmapLoader(resolver));
        setLoader(Sound.class, new SoundLoader(resolver));
        setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
        setLoader(Texture.class, new TextureLoader(resolver));
        setLoader(Skin.class, new SkinLoader(resolver));
        setLoader(ParticleEffect.class, new ParticleEffectLoader(resolver));
        setLoader(com.badlogic.gdx.graphics.g3d.particles.ParticleEffect.class, new com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader(resolver));
        setLoader(PolygonRegion.class, new PolygonRegionLoader(resolver));
        setLoader(I18NBundle.class, new I18NBundleLoader(resolver));
        setLoader(Model.class, ".g3dj", new G3dModelLoader(new JsonReader(), resolver));
        setLoader(Model.class, ".g3db", new G3dModelLoader(new UBJsonReader(), resolver));
        setLoader(Model.class, ".obj", new ObjLoader(resolver));
        this.executor = new AsyncExecutor(1);
    }

    public synchronized <T> T get(String fileName) {
        T asset;
        Class<T> type = (Class) this.assetTypes.get(fileName);
        if (type == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asset not loaded: ");
            stringBuilder.append(fileName);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        ObjectMap<String, RefCountedContainer> assetsByType = (ObjectMap) this.assets.get(type);
        if (assetsByType == null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Asset not loaded: ");
            stringBuilder2.append(fileName);
            throw new GdxRuntimeException(stringBuilder2.toString());
        }
        RefCountedContainer assetContainer = (RefCountedContainer) assetsByType.get(fileName);
        if (assetContainer == null) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Asset not loaded: ");
            stringBuilder3.append(fileName);
            throw new GdxRuntimeException(stringBuilder3.toString());
        }
        asset = assetContainer.getObject(type);
        if (asset == null) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Asset not loaded: ");
            stringBuilder4.append(fileName);
            throw new GdxRuntimeException(stringBuilder4.toString());
        }
        return asset;
    }

    public synchronized <T> T get(String fileName, Class<T> type) {
        T asset;
        ObjectMap<String, RefCountedContainer> assetsByType = (ObjectMap) this.assets.get(type);
        if (assetsByType == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asset not loaded: ");
            stringBuilder.append(fileName);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        RefCountedContainer assetContainer = (RefCountedContainer) assetsByType.get(fileName);
        if (assetContainer == null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Asset not loaded: ");
            stringBuilder2.append(fileName);
            throw new GdxRuntimeException(stringBuilder2.toString());
        }
        asset = assetContainer.getObject(type);
        if (asset == null) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Asset not loaded: ");
            stringBuilder3.append(fileName);
            throw new GdxRuntimeException(stringBuilder3.toString());
        }
        return asset;
    }

    public synchronized <T> Array<T> getAll(Class<T> type, Array<T> out) {
        ObjectMap<String, RefCountedContainer> assetsByType = (ObjectMap) this.assets.get(type);
        if (assetsByType != null) {
            Iterator i$ = assetsByType.entries().iterator();
            while (i$.hasNext()) {
                out.add(((RefCountedContainer) ((Entry) i$.next()).value).getObject(type));
            }
        }
        return out;
    }

    public synchronized <T> T get(AssetDescriptor<T> assetDescriptor) {
        return get(assetDescriptor.fileName, assetDescriptor.type);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void unload(java.lang.String r8) {
        /*
        r7 = this;
        monitor-enter(r7);
        r0 = r7.tasks;	 Catch:{ all -> 0x0133 }
        r0 = r0.size();	 Catch:{ all -> 0x0133 }
        r1 = 1;
        if (r0 <= 0) goto L_0x0036;
    L_0x000a:
        r0 = r7.tasks;	 Catch:{ all -> 0x0133 }
        r0 = r0.firstElement();	 Catch:{ all -> 0x0133 }
        r0 = (com.badlogic.gdx.assets.AssetLoadingTask) r0;	 Catch:{ all -> 0x0133 }
        r2 = r0.assetDesc;	 Catch:{ all -> 0x0133 }
        r2 = r2.fileName;	 Catch:{ all -> 0x0133 }
        r2 = r2.equals(r8);	 Catch:{ all -> 0x0133 }
        if (r2 == 0) goto L_0x0036;
    L_0x001c:
        r0.cancel = r1;	 Catch:{ all -> 0x0133 }
        r1 = r7.log;	 Catch:{ all -> 0x0133 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0133 }
        r2.<init>();	 Catch:{ all -> 0x0133 }
        r3 = "Unload (from tasks): ";
        r2.append(r3);	 Catch:{ all -> 0x0133 }
        r2.append(r8);	 Catch:{ all -> 0x0133 }
        r2 = r2.toString();	 Catch:{ all -> 0x0133 }
        r1.debug(r2);	 Catch:{ all -> 0x0133 }
        monitor-exit(r7);
        return;
    L_0x0036:
        r0 = -1;
        r2 = 0;
    L_0x0038:
        r3 = r7.loadQueue;	 Catch:{ all -> 0x0133 }
        r3 = r3.size;	 Catch:{ all -> 0x0133 }
        if (r2 >= r3) goto L_0x0053;
    L_0x003e:
        r3 = r7.loadQueue;	 Catch:{ all -> 0x0133 }
        r3 = r3.get(r2);	 Catch:{ all -> 0x0133 }
        r3 = (com.badlogic.gdx.assets.AssetDescriptor) r3;	 Catch:{ all -> 0x0133 }
        r3 = r3.fileName;	 Catch:{ all -> 0x0133 }
        r3 = r3.equals(r8);	 Catch:{ all -> 0x0133 }
        if (r3 == 0) goto L_0x0050;
    L_0x004e:
        r0 = r2;
        goto L_0x0053;
    L_0x0050:
        r2 = r2 + 1;
        goto L_0x0038;
    L_0x0053:
        r2 = -1;
        if (r0 == r2) goto L_0x0078;
    L_0x0056:
        r2 = r7.toLoad;	 Catch:{ all -> 0x0133 }
        r2 = r2 - r1;
        r7.toLoad = r2;	 Catch:{ all -> 0x0133 }
        r1 = r7.loadQueue;	 Catch:{ all -> 0x0133 }
        r1.removeIndex(r0);	 Catch:{ all -> 0x0133 }
        r1 = r7.log;	 Catch:{ all -> 0x0133 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0133 }
        r2.<init>();	 Catch:{ all -> 0x0133 }
        r3 = "Unload (from queue): ";
        r2.append(r3);	 Catch:{ all -> 0x0133 }
        r2.append(r8);	 Catch:{ all -> 0x0133 }
        r2 = r2.toString();	 Catch:{ all -> 0x0133 }
        r1.debug(r2);	 Catch:{ all -> 0x0133 }
        monitor-exit(r7);
        return;
    L_0x0078:
        r1 = r7.assetTypes;	 Catch:{ all -> 0x0133 }
        r1 = r1.get(r8);	 Catch:{ all -> 0x0133 }
        r1 = (java.lang.Class) r1;	 Catch:{ all -> 0x0133 }
        if (r1 != 0) goto L_0x0099;
    L_0x0082:
        r2 = new com.badlogic.gdx.utils.GdxRuntimeException;	 Catch:{ all -> 0x0133 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0133 }
        r3.<init>();	 Catch:{ all -> 0x0133 }
        r4 = "Asset not loaded: ";
        r3.append(r4);	 Catch:{ all -> 0x0133 }
        r3.append(r8);	 Catch:{ all -> 0x0133 }
        r3 = r3.toString();	 Catch:{ all -> 0x0133 }
        r2.<init>(r3);	 Catch:{ all -> 0x0133 }
        throw r2;	 Catch:{ all -> 0x0133 }
    L_0x0099:
        r2 = r7.assets;	 Catch:{ all -> 0x0133 }
        r2 = r2.get(r1);	 Catch:{ all -> 0x0133 }
        r2 = (com.badlogic.gdx.utils.ObjectMap) r2;	 Catch:{ all -> 0x0133 }
        r2 = r2.get(r8);	 Catch:{ all -> 0x0133 }
        r2 = (com.badlogic.gdx.assets.RefCountedContainer) r2;	 Catch:{ all -> 0x0133 }
        r2.decRefCount();	 Catch:{ all -> 0x0133 }
        r3 = r2.getRefCount();	 Catch:{ all -> 0x0133 }
        if (r3 > 0) goto L_0x00ec;
    L_0x00b0:
        r3 = r7.log;	 Catch:{ all -> 0x0133 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0133 }
        r4.<init>();	 Catch:{ all -> 0x0133 }
        r5 = "Unload (dispose): ";
        r4.append(r5);	 Catch:{ all -> 0x0133 }
        r4.append(r8);	 Catch:{ all -> 0x0133 }
        r4 = r4.toString();	 Catch:{ all -> 0x0133 }
        r3.debug(r4);	 Catch:{ all -> 0x0133 }
        r3 = java.lang.Object.class;
        r3 = r2.getObject(r3);	 Catch:{ all -> 0x0133 }
        r3 = r3 instanceof com.badlogic.gdx.utils.Disposable;	 Catch:{ all -> 0x0133 }
        if (r3 == 0) goto L_0x00db;
    L_0x00d0:
        r3 = java.lang.Object.class;
        r3 = r2.getObject(r3);	 Catch:{ all -> 0x0133 }
        r3 = (com.badlogic.gdx.utils.Disposable) r3;	 Catch:{ all -> 0x0133 }
        r3.dispose();	 Catch:{ all -> 0x0133 }
    L_0x00db:
        r3 = r7.assetTypes;	 Catch:{ all -> 0x0133 }
        r3.remove(r8);	 Catch:{ all -> 0x0133 }
        r3 = r7.assets;	 Catch:{ all -> 0x0133 }
        r3 = r3.get(r1);	 Catch:{ all -> 0x0133 }
        r3 = (com.badlogic.gdx.utils.ObjectMap) r3;	 Catch:{ all -> 0x0133 }
        r3.remove(r8);	 Catch:{ all -> 0x0133 }
        goto L_0x0102;
    L_0x00ec:
        r3 = r7.log;	 Catch:{ all -> 0x0133 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0133 }
        r4.<init>();	 Catch:{ all -> 0x0133 }
        r5 = "Unload (decrement): ";
        r4.append(r5);	 Catch:{ all -> 0x0133 }
        r4.append(r8);	 Catch:{ all -> 0x0133 }
        r4 = r4.toString();	 Catch:{ all -> 0x0133 }
        r3.debug(r4);	 Catch:{ all -> 0x0133 }
    L_0x0102:
        r3 = r7.assetDependencies;	 Catch:{ all -> 0x0133 }
        r3 = r3.get(r8);	 Catch:{ all -> 0x0133 }
        r3 = (com.badlogic.gdx.utils.Array) r3;	 Catch:{ all -> 0x0133 }
        if (r3 == 0) goto L_0x0126;
    L_0x010c:
        r4 = r3.iterator();	 Catch:{ all -> 0x0133 }
    L_0x0110:
        r5 = r4.hasNext();	 Catch:{ all -> 0x0133 }
        if (r5 == 0) goto L_0x0126;
    L_0x0116:
        r5 = r4.next();	 Catch:{ all -> 0x0133 }
        r5 = (java.lang.String) r5;	 Catch:{ all -> 0x0133 }
        r6 = r7.isLoaded(r5);	 Catch:{ all -> 0x0133 }
        if (r6 == 0) goto L_0x0125;
    L_0x0122:
        r7.unload(r5);	 Catch:{ all -> 0x0133 }
    L_0x0125:
        goto L_0x0110;
    L_0x0126:
        r4 = r2.getRefCount();	 Catch:{ all -> 0x0133 }
        if (r4 > 0) goto L_0x0131;
    L_0x012c:
        r4 = r7.assetDependencies;	 Catch:{ all -> 0x0133 }
        r4.remove(r8);	 Catch:{ all -> 0x0133 }
    L_0x0131:
        monitor-exit(r7);
        return;
    L_0x0133:
        r8 = move-exception;
        monitor-exit(r7);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.AssetManager.unload(java.lang.String):void");
    }

    public synchronized <T> boolean containsAsset(T asset) {
        ObjectMap<String, RefCountedContainer> typedAssets = (ObjectMap) this.assets.get(asset.getClass());
        if (typedAssets == null) {
            return false;
        }
        Iterator i$ = typedAssets.keys().iterator();
        while (i$.hasNext()) {
            T otherAsset = ((RefCountedContainer) typedAssets.get((String) i$.next())).getObject(Object.class);
            if (otherAsset != asset) {
                if (asset.equals(otherAsset)) {
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T> java.lang.String getAssetFileName(T r8) {
        /*
        r7 = this;
        monitor-enter(r7);
        r0 = r7.assets;	 Catch:{ all -> 0x004f }
        r0 = r0.keys();	 Catch:{ all -> 0x004f }
        r0 = r0.iterator();	 Catch:{ all -> 0x004f }
    L_0x000b:
        r1 = r0.hasNext();	 Catch:{ all -> 0x004f }
        if (r1 == 0) goto L_0x004c;
    L_0x0011:
        r1 = r0.next();	 Catch:{ all -> 0x004f }
        r1 = (java.lang.Class) r1;	 Catch:{ all -> 0x004f }
        r2 = r7.assets;	 Catch:{ all -> 0x004f }
        r2 = r2.get(r1);	 Catch:{ all -> 0x004f }
        r2 = (com.badlogic.gdx.utils.ObjectMap) r2;	 Catch:{ all -> 0x004f }
        r3 = r2.keys();	 Catch:{ all -> 0x004f }
        r3 = r3.iterator();	 Catch:{ all -> 0x004f }
    L_0x0027:
        r4 = r3.hasNext();	 Catch:{ all -> 0x004f }
        if (r4 == 0) goto L_0x004b;
    L_0x002d:
        r4 = r3.next();	 Catch:{ all -> 0x004f }
        r4 = (java.lang.String) r4;	 Catch:{ all -> 0x004f }
        r5 = r2.get(r4);	 Catch:{ all -> 0x004f }
        r5 = (com.badlogic.gdx.assets.RefCountedContainer) r5;	 Catch:{ all -> 0x004f }
        r6 = java.lang.Object.class;
        r5 = r5.getObject(r6);	 Catch:{ all -> 0x004f }
        if (r5 == r8) goto L_0x0049;
    L_0x0041:
        r6 = r8.equals(r5);	 Catch:{ all -> 0x004f }
        if (r6 == 0) goto L_0x0048;
    L_0x0047:
        goto L_0x0049;
    L_0x0048:
        goto L_0x0027;
    L_0x0049:
        monitor-exit(r7);
        return r4;
    L_0x004b:
        goto L_0x000b;
    L_0x004c:
        r0 = 0;
        monitor-exit(r7);
        return r0;
    L_0x004f:
        r8 = move-exception;
        monitor-exit(r7);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.AssetManager.getAssetFileName(java.lang.Object):java.lang.String");
    }

    public synchronized boolean isLoaded(String fileName) {
        if (fileName == null) {
            return false;
        }
        return this.assetTypes.containsKey(fileName);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean isLoaded(java.lang.String r5, java.lang.Class r6) {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.assets;	 Catch:{ all -> 0x0022 }
        r0 = r0.get(r6);	 Catch:{ all -> 0x0022 }
        r0 = (com.badlogic.gdx.utils.ObjectMap) r0;	 Catch:{ all -> 0x0022 }
        r1 = 0;
        if (r0 != 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r4);
        return r1;
    L_0x000e:
        r2 = r0.get(r5);	 Catch:{ all -> 0x0022 }
        r2 = (com.badlogic.gdx.assets.RefCountedContainer) r2;	 Catch:{ all -> 0x0022 }
        if (r2 != 0) goto L_0x0018;
    L_0x0016:
        monitor-exit(r4);
        return r1;
    L_0x0018:
        r3 = r2.getObject(r6);	 Catch:{ all -> 0x0022 }
        if (r3 == 0) goto L_0x0020;
    L_0x001e:
        r1 = 1;
    L_0x0020:
        monitor-exit(r4);
        return r1;
    L_0x0022:
        r5 = move-exception;
        monitor-exit(r4);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.AssetManager.isLoaded(java.lang.String, java.lang.Class):boolean");
    }

    public <T> AssetLoader getLoader(Class<T> type) {
        return getLoader(type, null);
    }

    public <T> AssetLoader getLoader(Class<T> type, String fileName) {
        ObjectMap<String, AssetLoader> loaders = (ObjectMap) this.loaders.get(type);
        if (loaders != null) {
            if (loaders.size >= 1) {
                if (fileName == null) {
                    return (AssetLoader) loaders.get("");
                }
                AssetLoader result = null;
                int l = -1;
                Iterator i$ = loaders.entries().iterator();
                while (i$.hasNext()) {
                    Entry<String, AssetLoader> entry = (Entry) i$.next();
                    if (((String) entry.key).length() > l && fileName.endsWith((String) entry.key)) {
                        result = entry.value;
                        l = ((String) entry.key).length();
                    }
                }
                return result;
            }
        }
        return null;
    }

    public synchronized <T> void load(String fileName, Class<T> type) {
        load(fileName, type, null);
    }

    public synchronized <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
        if (getLoader(type, fileName) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No loader for type: ");
            stringBuilder.append(ClassReflection.getSimpleName(type));
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        int i = 0;
        if (this.loadQueue.size == 0) {
            this.loaded = 0;
            this.toLoad = 0;
        }
        int i2 = 0;
        while (i2 < this.loadQueue.size) {
            AssetDescriptor desc = (AssetDescriptor) this.loadQueue.get(i2);
            if (!desc.fileName.equals(fileName) || desc.type.equals(type)) {
                i2++;
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Asset with name '");
                stringBuilder2.append(fileName);
                stringBuilder2.append("' already in preload queue, but has different type (expected: ");
                stringBuilder2.append(ClassReflection.getSimpleName(type));
                stringBuilder2.append(", found: ");
                stringBuilder2.append(ClassReflection.getSimpleName(desc.type));
                stringBuilder2.append(")");
                throw new GdxRuntimeException(stringBuilder2.toString());
            }
        }
        while (true) {
            i2 = i;
            if (i2 >= this.tasks.size()) {
                break;
            }
            AssetDescriptor desc2 = ((AssetLoadingTask) this.tasks.get(i2)).assetDesc;
            if (!desc2.fileName.equals(fileName) || desc2.type.equals(type)) {
                i = i2 + 1;
            } else {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Asset with name '");
                stringBuilder2.append(fileName);
                stringBuilder2.append("' already in task list, but has different type (expected: ");
                stringBuilder2.append(ClassReflection.getSimpleName(type));
                stringBuilder2.append(", found: ");
                stringBuilder2.append(ClassReflection.getSimpleName(desc2.type));
                stringBuilder2.append(")");
                throw new GdxRuntimeException(stringBuilder2.toString());
            }
        }
        Class otherType = (Class) this.assetTypes.get(fileName);
        if (otherType == null || otherType.equals(type)) {
            this.toLoad++;
            desc2 = new AssetDescriptor(fileName, (Class) type, (AssetLoaderParameters) parameter);
            this.loadQueue.add(desc2);
            Logger logger = this.log;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Queued: ");
            stringBuilder2.append(desc2);
            logger.debug(stringBuilder2.toString());
        } else {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Asset with name '");
            stringBuilder3.append(fileName);
            stringBuilder3.append("' already loaded, but has different type (expected: ");
            stringBuilder3.append(ClassReflection.getSimpleName(type));
            stringBuilder3.append(", found: ");
            stringBuilder3.append(ClassReflection.getSimpleName(otherType));
            stringBuilder3.append(")");
            throw new GdxRuntimeException(stringBuilder3.toString());
        }
    }

    public synchronized void load(AssetDescriptor desc) {
        load(desc.fileName, desc.type, desc.params);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean update() {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = 0;
        r1 = 1;
        r2 = r4.tasks;	 Catch:{ Throwable -> 0x0041 }
        r2 = r2.size();	 Catch:{ Throwable -> 0x0041 }
        if (r2 != 0) goto L_0x0027;
    L_0x000b:
        r2 = r4.loadQueue;	 Catch:{ Throwable -> 0x0041 }
        r2 = r2.size;	 Catch:{ Throwable -> 0x0041 }
        if (r2 == 0) goto L_0x001d;
    L_0x0011:
        r2 = r4.tasks;	 Catch:{ Throwable -> 0x0041 }
        r2 = r2.size();	 Catch:{ Throwable -> 0x0041 }
        if (r2 != 0) goto L_0x001d;
    L_0x0019:
        r4.nextTask();	 Catch:{ Throwable -> 0x0041 }
        goto L_0x000b;
    L_0x001d:
        r2 = r4.tasks;	 Catch:{ Throwable -> 0x0041 }
        r2 = r2.size();	 Catch:{ Throwable -> 0x0041 }
        if (r2 != 0) goto L_0x0027;
    L_0x0025:
        monitor-exit(r4);
        return r1;
    L_0x0027:
        r2 = r4.updateTask();	 Catch:{ Throwable -> 0x0041 }
        if (r2 == 0) goto L_0x003d;
    L_0x002d:
        r2 = r4.loadQueue;	 Catch:{ Throwable -> 0x0041 }
        r2 = r2.size;	 Catch:{ Throwable -> 0x0041 }
        if (r2 != 0) goto L_0x003d;
    L_0x0033:
        r2 = r4.tasks;	 Catch:{ Throwable -> 0x0041 }
        r2 = r2.size();	 Catch:{ Throwable -> 0x0041 }
        if (r2 != 0) goto L_0x003d;
    L_0x003b:
        r0 = 1;
    L_0x003d:
        monitor-exit(r4);
        return r0;
    L_0x003f:
        r0 = move-exception;
        goto L_0x004f;
    L_0x0041:
        r2 = move-exception;
        r4.handleTaskError(r2);	 Catch:{ all -> 0x003f }
        r3 = r4.loadQueue;	 Catch:{ all -> 0x003f }
        r3 = r3.size;	 Catch:{ all -> 0x003f }
        if (r3 != 0) goto L_0x004d;
    L_0x004b:
        r0 = 1;
    L_0x004d:
        monitor-exit(r4);
        return r0;
    L_0x004f:
        monitor-exit(r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.AssetManager.update():boolean");
    }

    public boolean update(int millis) {
        boolean done;
        long endTime = TimeUtils.millis() + ((long) millis);
        while (true) {
            done = update();
            if (done) {
                break;
            } else if (TimeUtils.millis() > endTime) {
                break;
            } else {
                ThreadUtils.yield();
            }
        }
        return done;
    }

    public void finishLoading() {
        this.log.debug("Waiting for loading to complete...");
        while (!update()) {
            ThreadUtils.yield();
        }
        this.log.debug("Loading complete.");
    }

    public void finishLoadingAsset(String fileName) {
        Logger logger = this.log;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Waiting for asset to be loaded: ");
        stringBuilder.append(fileName);
        logger.debug(stringBuilder.toString());
        while (!isLoaded(fileName)) {
            update();
            ThreadUtils.yield();
        }
        logger = this.log;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Asset loaded: ");
        stringBuilder.append(fileName);
        logger.debug(stringBuilder.toString());
    }

    synchronized void injectDependencies(String parentAssetFilename, Array<AssetDescriptor> dependendAssetDescs) {
        ObjectSet<String> injected = this.injected;
        Iterator i$ = dependendAssetDescs.iterator();
        while (i$.hasNext()) {
            AssetDescriptor desc = (AssetDescriptor) i$.next();
            if (!injected.contains(desc.fileName)) {
                injected.add(desc.fileName);
                injectDependency(parentAssetFilename, desc);
            }
        }
        injected.clear();
    }

    private synchronized void injectDependency(String parentAssetFilename, AssetDescriptor dependendAssetDesc) {
        Array<String> dependencies = (Array) this.assetDependencies.get(parentAssetFilename);
        if (dependencies == null) {
            dependencies = new Array();
            this.assetDependencies.put(parentAssetFilename, dependencies);
        }
        dependencies.add(dependendAssetDesc.fileName);
        Logger logger;
        StringBuilder stringBuilder;
        if (isLoaded(dependendAssetDesc.fileName)) {
            logger = this.log;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Dependency already loaded: ");
            stringBuilder.append(dependendAssetDesc);
            logger.debug(stringBuilder.toString());
            ((RefCountedContainer) ((ObjectMap) this.assets.get((Class) this.assetTypes.get(dependendAssetDesc.fileName))).get(dependendAssetDesc.fileName)).incRefCount();
            incrementRefCountedDependencies(dependendAssetDesc.fileName);
        } else {
            logger = this.log;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Loading dependency: ");
            stringBuilder.append(dependendAssetDesc);
            logger.info(stringBuilder.toString());
            addTask(dependendAssetDesc);
        }
    }

    private void nextTask() {
        AssetDescriptor assetDesc = (AssetDescriptor) this.loadQueue.removeIndex(0);
        if (isLoaded(assetDesc.fileName)) {
            Logger logger = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Already loaded: ");
            stringBuilder.append(assetDesc);
            logger.debug(stringBuilder.toString());
            ((RefCountedContainer) ((ObjectMap) this.assets.get((Class) this.assetTypes.get(assetDesc.fileName))).get(assetDesc.fileName)).incRefCount();
            incrementRefCountedDependencies(assetDesc.fileName);
            if (!(assetDesc.params == null || assetDesc.params.loadedCallback == null)) {
                assetDesc.params.loadedCallback.finishedLoading(this, assetDesc.fileName, assetDesc.type);
            }
            this.loaded++;
            return;
        }
        logger = this.log;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Loading: ");
        stringBuilder.append(assetDesc);
        logger.info(stringBuilder.toString());
        addTask(assetDesc);
    }

    private void addTask(AssetDescriptor assetDesc) {
        AssetLoader loader = getLoader(assetDesc.type, assetDesc.fileName);
        if (loader == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No loader for type: ");
            stringBuilder.append(ClassReflection.getSimpleName(assetDesc.type));
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        this.tasks.push(new AssetLoadingTask(this, assetDesc, loader, this.executor));
    }

    protected <T> void addAsset(String fileName, Class<T> type, T asset) {
        this.assetTypes.put(fileName, type);
        ObjectMap<String, RefCountedContainer> typeToAssets = (ObjectMap) this.assets.get(type);
        if (typeToAssets == null) {
            typeToAssets = new ObjectMap();
            this.assets.put(type, typeToAssets);
        }
        typeToAssets.put(fileName, new RefCountedContainer(asset));
    }

    private boolean updateTask() {
        AssetLoadingTask task = (AssetLoadingTask) this.tasks.peek();
        if (!task.cancel) {
            if (!task.update()) {
                return false;
            }
        }
        if (this.tasks.size() == 1) {
            this.loaded++;
        }
        this.tasks.pop();
        if (task.cancel) {
            return true;
        }
        addAsset(task.assetDesc.fileName, task.assetDesc.type, task.getAsset());
        if (!(task.assetDesc.params == null || task.assetDesc.params.loadedCallback == null)) {
            task.assetDesc.params.loadedCallback.finishedLoading(this, task.assetDesc.fileName, task.assetDesc.type);
        }
        long endTime = TimeUtils.nanoTime();
        Logger logger = this.log;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Loaded: ");
        stringBuilder.append(((float) (endTime - task.startTime)) / 1000000.0f);
        stringBuilder.append("ms ");
        stringBuilder.append(task.assetDesc);
        logger.debug(stringBuilder.toString());
        return true;
    }

    private void incrementRefCountedDependencies(String parent) {
        Array<String> dependencies = (Array) this.assetDependencies.get(parent);
        if (dependencies != null) {
            Iterator i$ = dependencies.iterator();
            while (i$.hasNext()) {
                String dependency = (String) i$.next();
                ((RefCountedContainer) ((ObjectMap) this.assets.get((Class) this.assetTypes.get(dependency))).get(dependency)).incRefCount();
                incrementRefCountedDependencies(dependency);
            }
        }
    }

    private void handleTaskError(Throwable t) {
        this.log.error("Error loading asset.", t);
        if (this.tasks.isEmpty()) {
            throw new GdxRuntimeException(t);
        }
        AssetLoadingTask task = (AssetLoadingTask) this.tasks.pop();
        AssetDescriptor assetDesc = task.assetDesc;
        if (task.dependenciesLoaded && task.dependencies != null) {
            Iterator i$ = task.dependencies.iterator();
            while (i$.hasNext()) {
                unload(((AssetDescriptor) i$.next()).fileName);
            }
        }
        this.tasks.clear();
        if (this.listener != null) {
            this.listener.error(assetDesc, t);
            return;
        }
        throw new GdxRuntimeException(t);
    }

    public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, AssetLoader<T, P> loader) {
        setLoader(type, null, loader);
    }

    public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, String suffix, AssetLoader<T, P> loader) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (loader == null) {
            throw new IllegalArgumentException("loader cannot be null.");
        } else {
            Logger logger = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Loader set: ");
            stringBuilder.append(ClassReflection.getSimpleName(type));
            stringBuilder.append(" -> ");
            stringBuilder.append(ClassReflection.getSimpleName(loader.getClass()));
            logger.debug(stringBuilder.toString());
            ObjectMap<String, AssetLoader> loaders = (ObjectMap) this.loaders.get(type);
            if (loaders == null) {
                ObjectMap objectMap = this.loaders;
                ObjectMap<String, AssetLoader> objectMap2 = new ObjectMap();
                loaders = objectMap2;
                objectMap.put(type, objectMap2);
            }
            loaders.put(suffix == null ? "" : suffix, loader);
        }
    }

    public synchronized int getLoadedAssets() {
        return this.assetTypes.size;
    }

    public synchronized int getQueuedAssets() {
        return this.loadQueue.size + this.tasks.size();
    }

    public synchronized float getProgress() {
        if (this.toLoad == 0) {
            return 1.0f;
        }
        return Math.min(1.0f, ((float) this.loaded) / ((float) this.toLoad));
    }

    public synchronized void setErrorListener(AssetErrorListener listener) {
        this.listener = listener;
    }

    public synchronized void dispose() {
        this.log.debug("Disposing.");
        clear();
        this.executor.dispose();
    }

    public synchronized void clear() {
        this.loadQueue.clear();
        while (!update()) {
        }
        ObjectIntMap<String> dependencyCount = new ObjectIntMap();
        while (this.assetTypes.size > 0) {
            dependencyCount.clear();
            Array<String> assets = this.assetTypes.keys().toArray();
            Iterator i$ = assets.iterator();
            while (i$.hasNext()) {
                dependencyCount.put((String) i$.next(), 0);
            }
            i$ = assets.iterator();
            while (i$.hasNext()) {
                Array<String> dependencies = (Array) this.assetDependencies.get((String) i$.next());
                if (dependencies != null) {
                    Iterator i$2 = dependencies.iterator();
                    while (i$2.hasNext()) {
                        String dependency = (String) i$2.next();
                        dependencyCount.put(dependency, dependencyCount.get(dependency, 0) + 1);
                    }
                }
            }
            i$ = assets.iterator();
            while (i$.hasNext()) {
                String asset = (String) i$.next();
                if (dependencyCount.get(asset, 0) == 0) {
                    unload(asset);
                }
            }
        }
        this.assets.clear();
        this.assetTypes.clear();
        this.assetDependencies.clear();
        this.loaded = 0;
        this.toLoad = 0;
        this.loadQueue.clear();
        this.tasks.clear();
    }

    public Logger getLogger() {
        return this.log;
    }

    public void setLogger(Logger logger) {
        this.log = logger;
    }

    public synchronized int getReferenceCount(String fileName) {
        Class type;
        type = (Class) this.assetTypes.get(fileName);
        if (type == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asset not loaded: ");
            stringBuilder.append(fileName);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        return ((RefCountedContainer) ((ObjectMap) this.assets.get(type)).get(fileName)).getRefCount();
    }

    public synchronized void setReferenceCount(String fileName, int refCount) {
        Class type = (Class) this.assetTypes.get(fileName);
        if (type == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asset not loaded: ");
            stringBuilder.append(fileName);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        ((RefCountedContainer) ((ObjectMap) this.assets.get(type)).get(fileName)).setRefCount(refCount);
    }

    public synchronized String getDiagnostics() {
        StringBuffer buffer;
        buffer = new StringBuffer();
        Iterator i$ = this.assetTypes.keys().iterator();
        while (i$.hasNext()) {
            String fileName = (String) i$.next();
            buffer.append(fileName);
            buffer.append(", ");
            Class type = (Class) this.assetTypes.get(fileName);
            RefCountedContainer assetRef = (RefCountedContainer) ((ObjectMap) this.assets.get(type)).get(fileName);
            Array<String> dependencies = (Array) this.assetDependencies.get(fileName);
            buffer.append(ClassReflection.getSimpleName(type));
            buffer.append(", refs: ");
            buffer.append(assetRef.getRefCount());
            if (dependencies != null) {
                buffer.append(", deps: [");
                Iterator i$2 = dependencies.iterator();
                while (i$2.hasNext()) {
                    buffer.append((String) i$2.next());
                    buffer.append(",");
                }
                buffer.append("]");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public synchronized Array<String> getAssetNames() {
        return this.assetTypes.keys().toArray();
    }

    public synchronized Array<String> getDependencies(String fileName) {
        return (Array) this.assetDependencies.get(fileName);
    }

    public synchronized Class getAssetType(String fileName) {
        return (Class) this.assetTypes.get(fileName);
    }
}
