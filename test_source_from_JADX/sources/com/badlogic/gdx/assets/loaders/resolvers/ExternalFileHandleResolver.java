package com.badlogic.gdx.assets.loaders.resolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class ExternalFileHandleResolver implements FileHandleResolver {
    public FileHandle resolve(String fileName) {
        return Gdx.files.external(fileName);
    }
}
