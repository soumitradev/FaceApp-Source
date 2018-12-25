package com.badlogic.gdx.utils;

import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.BuildTarget.TargetOs;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;

public class GdxBuild {
    public static void main(String[] args) throws Exception {
        String JNI_DIR = "jni";
        new NativeCodeGenerator().generate("src", "bin", JNI_DIR, new String[]{"**/*"}, null);
        String[] excludeCpp = new String[]{"android/**", "iosgl/**"};
        BuildTarget win32home = BuildTarget.newDefaultTarget(TargetOs.Windows, false);
        win32home.compilerPrefix = "";
        win32home.buildFileName = "build-windows32home.xml";
        win32home.excludeFromMasterBuildFile = true;
        win32home.cppExcludes = excludeCpp;
        BuildTarget.newDefaultTarget(TargetOs.Windows, false).cppExcludes = excludeCpp;
        BuildTarget.newDefaultTarget(TargetOs.Windows, true).cppExcludes = excludeCpp;
        BuildTarget.newDefaultTarget(TargetOs.Linux, false).cppExcludes = excludeCpp;
        BuildTarget.newDefaultTarget(TargetOs.Linux, true).cppExcludes = excludeCpp;
        BuildTarget android = BuildTarget.newDefaultTarget(TargetOs.Android, false);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(android.linkerFlags);
        stringBuilder.append(" -lGLESv2 -llog");
        android.linkerFlags = stringBuilder.toString();
        android.cppExcludes = new String[]{"iosgl/**"};
        BuildTarget.newDefaultTarget(TargetOs.MacOsX, false).cppExcludes = excludeCpp;
        BuildTarget.newDefaultTarget(TargetOs.MacOsX, true).cppExcludes = excludeCpp;
        BuildTarget ios = BuildTarget.newDefaultTarget(TargetOs.IOS, false);
        ios.cppExcludes = new String[]{"android/**"};
        ios.headerDirs = new String[]{"iosgl"};
        new AntScriptGenerator().generate(new BuildConfig("gdx", "../target/native", "libs", JNI_DIR), new BuildTarget[]{mac, mac64, win32home, win32, win64, lin32, lin64, android, ios});
    }
}
