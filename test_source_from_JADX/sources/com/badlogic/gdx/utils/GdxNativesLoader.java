package com.badlogic.gdx.utils;

public class GdxNativesLoader {
    public static boolean disableNativesLoading = false;
    private static boolean nativesLoaded;

    public static synchronized void load() {
        synchronized (GdxNativesLoader.class) {
            if (nativesLoaded) {
                return;
            }
            nativesLoaded = true;
            if (disableNativesLoading) {
                System.out.println("Native loading is disabled.");
                return;
            }
            new SharedLibraryLoader().load("gdx");
        }
    }
}
