package com.badlogic.gdx.graphics;

import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;

class Texture$1 implements LoadedCallback {
    final /* synthetic */ int val$refCount;

    Texture$1(int i) {
        this.val$refCount = i;
    }

    public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
        assetManager.setReferenceCount(fileName, this.val$refCount);
    }
}
