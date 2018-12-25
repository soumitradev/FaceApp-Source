package com.crashlytics.android.core;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Build.VERSION;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.IdManager$DeviceIdentifierType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class SessionProtobufHelper {
    private static final String SIGNAL_DEFAULT = "0";
    private static final ByteString SIGNAL_DEFAULT_BYTE_STRING = ByteString.copyFromUtf8("0");
    private static final ByteString UNITY_PLATFORM_BYTE_STRING = ByteString.copyFromUtf8("Unity");

    private SessionProtobufHelper() {
    }

    public static void writeBeginSession(CodedOutputStream cos, String sessionId, String generator, long startedAtSeconds) throws Exception {
        cos.writeBytes(1, ByteString.copyFromUtf8(generator));
        cos.writeBytes(2, ByteString.copyFromUtf8(sessionId));
        cos.writeUInt64(3, startedAtSeconds);
    }

    public static void writeSessionApp(CodedOutputStream cos, String packageName, String apiKey, String versionCode, String versionName, String installUuid, int deliveryMechanism, String unityVersion) throws Exception {
        CodedOutputStream codedOutputStream = cos;
        ByteString packageNameBytes = ByteString.copyFromUtf8(packageName);
        ByteString apiKeyBytes = ByteString.copyFromUtf8(apiKey);
        ByteString versionCodeBytes = ByteString.copyFromUtf8(versionCode);
        ByteString versionNameBytes = ByteString.copyFromUtf8(versionName);
        ByteString installIdBytes = ByteString.copyFromUtf8(installUuid);
        ByteString unityVersionBytes = unityVersion != null ? ByteString.copyFromUtf8(unityVersion) : null;
        codedOutputStream.writeTag(7, 2);
        codedOutputStream.writeRawVarint32(getSessionAppSize(packageNameBytes, apiKeyBytes, versionCodeBytes, versionNameBytes, installIdBytes, deliveryMechanism, unityVersionBytes));
        codedOutputStream.writeBytes(1, packageNameBytes);
        codedOutputStream.writeBytes(2, versionCodeBytes);
        codedOutputStream.writeBytes(3, versionNameBytes);
        codedOutputStream.writeTag(5, 2);
        codedOutputStream.writeRawVarint32(getSessionAppOrgSize(apiKeyBytes));
        codedOutputStream.writeBytes(1, apiKeyBytes);
        codedOutputStream.writeBytes(6, installIdBytes);
        if (unityVersionBytes != null) {
            codedOutputStream.writeBytes(8, UNITY_PLATFORM_BYTE_STRING);
            codedOutputStream.writeBytes(9, unityVersionBytes);
        }
        codedOutputStream.writeEnum(10, deliveryMechanism);
    }

    public static void writeSessionOS(CodedOutputStream cos, boolean isRooted) throws Exception {
        ByteString releaseBytes = ByteString.copyFromUtf8(VERSION.RELEASE);
        ByteString codeNameBytes = ByteString.copyFromUtf8(VERSION.CODENAME);
        cos.writeTag(8, 2);
        cos.writeRawVarint32(getSessionOSSize(releaseBytes, codeNameBytes, isRooted));
        cos.writeEnum(1, 3);
        cos.writeBytes(2, releaseBytes);
        cos.writeBytes(3, codeNameBytes);
        cos.writeBool(4, isRooted);
    }

    public static void writeSessionDevice(CodedOutputStream cos, String clsDeviceId, int arch, String model, int availableProcessors, long totalRam, long diskSpace, boolean isEmulator, Map<IdManager$DeviceIdentifierType, String> ids, int state, String manufacturer, String modelClass) throws Exception {
        CodedOutputStream codedOutputStream = cos;
        ByteString clsDeviceIDBytes = ByteString.copyFromUtf8(clsDeviceId);
        ByteString modelBytes = stringToByteString(model);
        ByteString modelClassBytes = stringToByteString(modelClass);
        ByteString stringToByteString = stringToByteString(manufacturer);
        codedOutputStream.writeTag(9, 2);
        int i = availableProcessors;
        long j = totalRam;
        long j2 = diskSpace;
        boolean z = isEmulator;
        ByteString manufacturerBytes = stringToByteString;
        ByteString modelClassBytes2 = modelClassBytes;
        codedOutputStream.writeRawVarint32(getSessionDeviceSize(arch, clsDeviceIDBytes, modelBytes, i, j, j2, z, ids, state, stringToByteString, modelClassBytes));
        codedOutputStream.writeBytes(1, clsDeviceIDBytes);
        codedOutputStream.writeEnum(3, arch);
        codedOutputStream.writeBytes(4, modelBytes);
        codedOutputStream.writeUInt32(5, i);
        codedOutputStream.writeUInt64(6, j);
        codedOutputStream.writeUInt64(7, j2);
        codedOutputStream.writeBool(10, z);
        for (Entry<IdManager$DeviceIdentifierType, String> id : ids.entrySet()) {
            codedOutputStream.writeTag(11, 2);
            codedOutputStream.writeRawVarint32(getDeviceIdentifierSize((IdManager$DeviceIdentifierType) id.getKey(), (String) id.getValue()));
            codedOutputStream.writeEnum(1, ((IdManager$DeviceIdentifierType) id.getKey()).protobufIndex);
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8((String) id.getValue()));
        }
        codedOutputStream.writeUInt32(12, state);
        ByteString manufacturerBytes2 = manufacturerBytes;
        if (manufacturerBytes2 != null) {
            codedOutputStream.writeBytes(13, manufacturerBytes2);
        }
        ByteString modelClassBytes3 = modelClassBytes2;
        if (modelClassBytes3 != null) {
            codedOutputStream.writeBytes(14, modelClassBytes3);
        }
    }

    public static void writeSessionUser(CodedOutputStream cos, String id, String name, String email) throws Exception {
        ByteString idBytes = ByteString.copyFromUtf8(id == null ? "" : id);
        ByteString nameBytes = stringToByteString(name);
        ByteString emailBytes = stringToByteString(email);
        int size = 0 + CodedOutputStream.computeBytesSize(1, idBytes);
        if (name != null) {
            size += CodedOutputStream.computeBytesSize(2, nameBytes);
        }
        if (email != null) {
            size += CodedOutputStream.computeBytesSize(3, emailBytes);
        }
        cos.writeTag(6, 2);
        cos.writeRawVarint32(size);
        cos.writeBytes(1, idBytes);
        if (name != null) {
            cos.writeBytes(2, nameBytes);
        }
        if (email != null) {
            cos.writeBytes(3, emailBytes);
        }
    }

    public static void writeSessionEvent(CodedOutputStream cos, long eventTime, String eventType, TrimmedThrowableData exception, Thread exceptionThread, StackTraceElement[] exceptionStack, Thread[] otherThreads, List<StackTraceElement[]> otherStacks, Map<String, String> customAttributes, LogFileManager logFileManager, RunningAppProcessInfo runningAppProcessInfo, int orientation, String packageName, String buildId, Float batteryLevel, int batteryVelocity, boolean proximityEnabled, long usedRamInBytes, long diskUsedInBytes) throws Exception {
        ByteString byteString;
        CodedOutputStream codedOutputStream = cos;
        String str = buildId;
        ByteString packageNameBytes = ByteString.copyFromUtf8(packageName);
        if (str == null) {
            byteString = null;
        } else {
            byteString = ByteString.copyFromUtf8(str.replace("-", ""));
        }
        ByteString optionalBuildIdBytes = byteString;
        ByteString logByteString = logFileManager.getByteStringForLog();
        if (logByteString == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "No log data to include with this event.");
        }
        logFileManager.clearLog();
        codedOutputStream.writeTag(10, 2);
        long j = eventTime;
        codedOutputStream.writeRawVarint32(getSessionEventSize(j, eventType, exception, exceptionThread, exceptionStack, otherThreads, otherStacks, 8, customAttributes, runningAppProcessInfo, orientation, packageNameBytes, optionalBuildIdBytes, batteryLevel, batteryVelocity, proximityEnabled, usedRamInBytes, diskUsedInBytes, logByteString));
        codedOutputStream.writeUInt64(1, j);
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(eventType));
        CodedOutputStream codedOutputStream2 = codedOutputStream;
        ByteString logByteString2 = logByteString;
        writeSessionEventApp(codedOutputStream2, exception, exceptionThread, exceptionStack, otherThreads, otherStacks, 8, packageNameBytes, optionalBuildIdBytes, customAttributes, runningAppProcessInfo, orientation);
        writeSessionEventDevice(codedOutputStream2, batteryLevel, batteryVelocity, proximityEnabled, orientation, usedRamInBytes, diskUsedInBytes);
        writeSessionEventLog(codedOutputStream, logByteString2);
    }

    private static void writeSessionEventApp(CodedOutputStream cos, TrimmedThrowableData exception, Thread exceptionThread, StackTraceElement[] exceptionStack, Thread[] otherThreads, List<StackTraceElement[]> otherStacks, int maxChainedExceptionsDepth, ByteString packageNameBytes, ByteString optionalBuildIdBytes, Map<String, String> customAttributes, RunningAppProcessInfo runningAppProcessInfo, int orientation) throws Exception {
        cos.writeTag(3, 2);
        cos.writeRawVarint32(getEventAppSize(exception, exceptionThread, exceptionStack, otherThreads, otherStacks, maxChainedExceptionsDepth, packageNameBytes, optionalBuildIdBytes, customAttributes, runningAppProcessInfo, orientation));
        writeSessionEventAppExecution(cos, exception, exceptionThread, exceptionStack, otherThreads, otherStacks, maxChainedExceptionsDepth, packageNameBytes, optionalBuildIdBytes);
        if (!(customAttributes == null || customAttributes.isEmpty())) {
            writeSessionEventAppCustomAttributes(cos, customAttributes);
        }
        if (runningAppProcessInfo != null) {
            cos.writeBool(3, runningAppProcessInfo.importance != 100);
        }
        cos.writeUInt32(4, orientation);
    }

    private static void writeSessionEventAppExecution(CodedOutputStream cos, TrimmedThrowableData exception, Thread exceptionThread, StackTraceElement[] exceptionStack, Thread[] otherThreads, List<StackTraceElement[]> otherStacks, int maxChainedExceptionsDepth, ByteString packageNameBytes, ByteString optionalBuildIdBytes) throws Exception {
        CodedOutputStream codedOutputStream = cos;
        Thread[] threadArr = otherThreads;
        ByteString byteString = optionalBuildIdBytes;
        codedOutputStream.writeTag(1, 2);
        codedOutputStream.writeRawVarint32(getEventAppExecutionSize(exception, exceptionThread, exceptionStack, otherThreads, otherStacks, maxChainedExceptionsDepth, packageNameBytes, optionalBuildIdBytes));
        writeThread(codedOutputStream, exceptionThread, exceptionStack, 4, true);
        int len = threadArr.length;
        for (int i = 0; i < len; i++) {
            writeThread(codedOutputStream, threadArr[i], (StackTraceElement[]) otherStacks.get(i), 0, false);
        }
        List<StackTraceElement[]> list = otherStacks;
        writeSessionEventAppExecutionException(codedOutputStream, exception, 1, maxChainedExceptionsDepth, 2);
        codedOutputStream.writeTag(3, 2);
        codedOutputStream.writeRawVarint32(getEventAppExecutionSignalSize());
        codedOutputStream.writeBytes(1, SIGNAL_DEFAULT_BYTE_STRING);
        codedOutputStream.writeBytes(2, SIGNAL_DEFAULT_BYTE_STRING);
        codedOutputStream.writeUInt64(3, 0);
        codedOutputStream.writeTag(4, 2);
        codedOutputStream.writeRawVarint32(getBinaryImageSize(packageNameBytes, optionalBuildIdBytes));
        codedOutputStream.writeUInt64(1, 0);
        codedOutputStream.writeUInt64(2, 0);
        codedOutputStream.writeBytes(3, packageNameBytes);
        if (byteString != null) {
            codedOutputStream.writeBytes(4, byteString);
        }
    }

    private static void writeSessionEventAppCustomAttributes(CodedOutputStream cos, Map<String, String> customAttributes) throws Exception {
        for (Entry<String, String> entry : customAttributes.entrySet()) {
            cos.writeTag(2, 2);
            cos.writeRawVarint32(getEventAppCustomAttributeSize((String) entry.getKey(), (String) entry.getValue()));
            cos.writeBytes(1, ByteString.copyFromUtf8((String) entry.getKey()));
            String value = (String) entry.getValue();
            cos.writeBytes(2, ByteString.copyFromUtf8(value == null ? "" : value));
        }
    }

    private static void writeSessionEventAppExecutionException(CodedOutputStream cos, TrimmedThrowableData exception, int chainDepth, int maxChainedExceptionsDepth, int field) throws Exception {
        cos.writeTag(field, 2);
        cos.writeRawVarint32(getEventAppExecutionExceptionSize(exception, 1, maxChainedExceptionsDepth));
        cos.writeBytes(1, ByteString.copyFromUtf8(exception.className));
        String message = exception.localizedMessage;
        if (message != null) {
            cos.writeBytes(3, ByteString.copyFromUtf8(message));
        }
        int overflowCount = 0;
        for (StackTraceElement element : exception.stacktrace) {
            writeFrame(cos, 4, element, true);
        }
        TrimmedThrowableData cause = exception.cause;
        if (cause == null) {
            return;
        }
        if (chainDepth < maxChainedExceptionsDepth) {
            writeSessionEventAppExecutionException(cos, cause, chainDepth + 1, maxChainedExceptionsDepth, 6);
            return;
        }
        while (true) {
            int overflowCount2 = overflowCount;
            if (cause != null) {
                cause = cause.cause;
                overflowCount = overflowCount2 + 1;
            } else {
                cos.writeUInt32(7, overflowCount2);
                return;
            }
        }
    }

    private static void writeThread(CodedOutputStream cos, Thread thread, StackTraceElement[] stackTraceElements, int importance, boolean isCrashedThread) throws Exception {
        cos.writeTag(1, 2);
        cos.writeRawVarint32(getThreadSize(thread, stackTraceElements, importance, isCrashedThread));
        cos.writeBytes(1, ByteString.copyFromUtf8(thread.getName()));
        cos.writeUInt32(2, importance);
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            writeFrame(cos, 3, stackTraceElement, isCrashedThread);
        }
    }

    private static void writeFrame(CodedOutputStream cos, int fieldIndex, StackTraceElement element, boolean isCrashedThread) throws Exception {
        cos.writeTag(fieldIndex, 2);
        cos.writeRawVarint32(getFrameSize(element, isCrashedThread));
        if (element.isNativeMethod()) {
            cos.writeUInt64(1, (long) Math.max(element.getLineNumber(), 0));
        } else {
            cos.writeUInt64(1, 0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(element.getClassName());
        stringBuilder.append(".");
        stringBuilder.append(element.getMethodName());
        cos.writeBytes(2, ByteString.copyFromUtf8(stringBuilder.toString()));
        if (element.getFileName() != null) {
            cos.writeBytes(3, ByteString.copyFromUtf8(element.getFileName()));
        }
        int i = 4;
        if (!element.isNativeMethod() && element.getLineNumber() > 0) {
            cos.writeUInt64(4, (long) element.getLineNumber());
        }
        if (!isCrashedThread) {
            i = 0;
        }
        cos.writeUInt32(5, i);
    }

    private static void writeSessionEventDevice(CodedOutputStream cos, Float batteryLevel, int batteryVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed) throws Exception {
        cos.writeTag(5, 2);
        cos.writeRawVarint32(getEventDeviceSize(batteryLevel, batteryVelocity, proximityEnabled, orientation, heapAllocatedSize, diskUsed));
        if (batteryLevel != null) {
            cos.writeFloat(1, batteryLevel.floatValue());
        }
        cos.writeSInt32(2, batteryVelocity);
        cos.writeBool(3, proximityEnabled);
        cos.writeUInt32(4, orientation);
        cos.writeUInt64(5, heapAllocatedSize);
        cos.writeUInt64(6, diskUsed);
    }

    private static void writeSessionEventLog(CodedOutputStream cos, ByteString log) throws Exception {
        if (log != null) {
            cos.writeTag(6, 2);
            cos.writeRawVarint32(getEventLogSize(log));
            cos.writeBytes(1, log);
        }
    }

    private static int getSessionAppSize(ByteString packageName, ByteString apiKey, ByteString versionCode, ByteString versionName, ByteString installUuid, int deliveryMechanism, ByteString unityVersion) {
        int size = ((0 + CodedOutputStream.computeBytesSize(1, packageName)) + CodedOutputStream.computeBytesSize(2, versionCode)) + CodedOutputStream.computeBytesSize(3, versionName);
        int orgSize = getSessionAppOrgSize(apiKey);
        size = (size + ((CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(orgSize)) + orgSize)) + CodedOutputStream.computeBytesSize(6, installUuid);
        if (unityVersion != null) {
            size = (size + CodedOutputStream.computeBytesSize(8, UNITY_PLATFORM_BYTE_STRING)) + CodedOutputStream.computeBytesSize(9, unityVersion);
        }
        return size + CodedOutputStream.computeEnumSize(10, deliveryMechanism);
    }

    private static int getSessionAppOrgSize(ByteString apiKey) {
        return 0 + CodedOutputStream.computeBytesSize(1, apiKey);
    }

    private static int getSessionOSSize(ByteString release, ByteString codeName, boolean isRooted) {
        return (((0 + CodedOutputStream.computeEnumSize(1, 3)) + CodedOutputStream.computeBytesSize(2, release)) + CodedOutputStream.computeBytesSize(3, codeName)) + CodedOutputStream.computeBoolSize(4, isRooted);
    }

    private static int getDeviceIdentifierSize(IdManager$DeviceIdentifierType type, String value) {
        return CodedOutputStream.computeEnumSize(1, type.protobufIndex) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(value));
    }

    private static int getSessionDeviceSize(int arch, ByteString clsDeviceID, ByteString model, int availableProcessors, long totalRam, long diskSpace, boolean isEmulator, Map<IdManager$DeviceIdentifierType, String> ids, int state, ByteString manufacturer, ByteString modelClass) {
        ByteString byteString = model;
        ByteString byteString2 = manufacturer;
        ByteString byteString3 = modelClass;
        int size = ((((((0 + CodedOutputStream.computeBytesSize(1, clsDeviceID)) + CodedOutputStream.computeEnumSize(3, arch)) + (byteString == null ? 0 : CodedOutputStream.computeBytesSize(4, byteString))) + CodedOutputStream.computeUInt32Size(5, availableProcessors)) + CodedOutputStream.computeUInt64Size(6, totalRam)) + CodedOutputStream.computeUInt64Size(7, diskSpace)) + CodedOutputStream.computeBoolSize(10, isEmulator);
        if (ids != null) {
            for (Entry<IdManager$DeviceIdentifierType, String> id : ids.entrySet()) {
                int idSize = getDeviceIdentifierSize((IdManager$DeviceIdentifierType) id.getKey(), (String) id.getValue());
                size += (CodedOutputStream.computeTagSize(11) + CodedOutputStream.computeRawVarint32Size(idSize)) + idSize;
                byteString = model;
            }
        }
        return ((size + CodedOutputStream.computeUInt32Size(12, state)) + (byteString2 == null ? 0 : CodedOutputStream.computeBytesSize(13, byteString2))) + (byteString3 == null ? 0 : CodedOutputStream.computeBytesSize(14, byteString3));
    }

    private static int getBinaryImageSize(ByteString packageNameBytes, ByteString optionalBuildIdBytes) {
        int size = ((0 + CodedOutputStream.computeUInt64Size(1, 0)) + CodedOutputStream.computeUInt64Size(2, 0)) + CodedOutputStream.computeBytesSize(3, packageNameBytes);
        if (optionalBuildIdBytes != null) {
            return size + CodedOutputStream.computeBytesSize(4, optionalBuildIdBytes);
        }
        return size;
    }

    private static int getSessionEventSize(long eventTime, String eventType, TrimmedThrowableData exception, Thread exceptionThread, StackTraceElement[] exceptionStack, Thread[] otherThreads, List<StackTraceElement[]> otherStacks, int maxChainedExceptionsDepth, Map<String, String> customAttributes, RunningAppProcessInfo runningAppProcessInfo, int orientation, ByteString packageNameBytes, ByteString optionalBuildIdBytes, Float batteryLevel, int batteryVelocity, boolean proximityEnabled, long heapAllocatedSize, long diskUsed, ByteString log) {
        int size = (0 + CodedOutputStream.computeUInt64Size(1, eventTime)) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(eventType));
        int eventAppSize = getEventAppSize(exception, exceptionThread, exceptionStack, otherThreads, otherStacks, maxChainedExceptionsDepth, packageNameBytes, optionalBuildIdBytes, customAttributes, runningAppProcessInfo, orientation);
        size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(eventAppSize)) + eventAppSize;
        int eventDeviceSize = getEventDeviceSize(batteryLevel, batteryVelocity, proximityEnabled, orientation, heapAllocatedSize, diskUsed);
        size += (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(eventDeviceSize)) + eventDeviceSize;
        if (log == null) {
            return size;
        }
        int logSize = getEventLogSize(log);
        return size + ((CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(logSize)) + logSize);
    }

    private static int getEventAppSize(TrimmedThrowableData exception, Thread exceptionThread, StackTraceElement[] exceptionStack, Thread[] otherThreads, List<StackTraceElement[]> otherStacks, int maxChainedExceptionsDepth, ByteString packageNameBytes, ByteString optionalBuildIdBytes, Map<String, String> customAttributes, RunningAppProcessInfo runningAppProcessInfo, int orientation) {
        RunningAppProcessInfo runningAppProcessInfo2 = runningAppProcessInfo;
        int executionSize = getEventAppExecutionSize(exception, exceptionThread, exceptionStack, otherThreads, otherStacks, maxChainedExceptionsDepth, packageNameBytes, optionalBuildIdBytes);
        boolean z = true;
        int size = 0 + ((CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(executionSize)) + executionSize);
        if (customAttributes != null) {
            for (Entry<String, String> entry : customAttributes.entrySet()) {
                int entrySize = getEventAppCustomAttributeSize((String) entry.getKey(), (String) entry.getValue());
                size += (CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(entrySize)) + entrySize;
            }
        }
        if (runningAppProcessInfo2 != null) {
            if (runningAppProcessInfo2.importance == 100) {
                z = false;
            }
            size += CodedOutputStream.computeBoolSize(3, z);
        }
        return size + CodedOutputStream.computeUInt32Size(4, orientation);
    }

    private static int getEventAppExecutionSize(TrimmedThrowableData exception, Thread exceptionThread, StackTraceElement[] exceptionStack, Thread[] otherThreads, List<StackTraceElement[]> otherStacks, int maxChainedExceptionDepth, ByteString packageNameBytes, ByteString optionalBuildIdBytes) {
        Thread[] threadArr = otherThreads;
        int threadSize = getThreadSize(exceptionThread, exceptionStack, 4, true);
        int size = 0 + ((CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize)) + threadSize);
        int len = threadArr.length;
        int size2 = size;
        for (size = 0; size < len; size++) {
            threadSize = getThreadSize(threadArr[size], (StackTraceElement[]) otherStacks.get(size), 0, false);
            size2 += (CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize)) + threadSize;
        }
        List<StackTraceElement[]> list = otherStacks;
        int exceptionSize = getEventAppExecutionExceptionSize(exception, 1, maxChainedExceptionDepth);
        size2 += (CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(exceptionSize)) + exceptionSize;
        int signalSize = getEventAppExecutionSignalSize();
        size2 += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(signalSize)) + signalSize;
        int binaryImageSize = getBinaryImageSize(packageNameBytes, optionalBuildIdBytes);
        return size2 + ((CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(binaryImageSize)) + binaryImageSize);
    }

    private static int getEventAppCustomAttributeSize(String key, String value) {
        String str;
        int size = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(key));
        if (value == null) {
            str = "";
        } else {
            str = value;
        }
        return size + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(str));
    }

    private static int getEventDeviceSize(Float batteryLevel, int batteryVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed) {
        int size = 0;
        if (batteryLevel != null) {
            size = 0 + CodedOutputStream.computeFloatSize(1, batteryLevel.floatValue());
        }
        return ((((size + CodedOutputStream.computeSInt32Size(2, batteryVelocity)) + CodedOutputStream.computeBoolSize(3, proximityEnabled)) + CodedOutputStream.computeUInt32Size(4, orientation)) + CodedOutputStream.computeUInt64Size(5, heapAllocatedSize)) + CodedOutputStream.computeUInt64Size(6, diskUsed);
    }

    private static int getEventLogSize(ByteString log) {
        return CodedOutputStream.computeBytesSize(1, log);
    }

    private static int getEventAppExecutionExceptionSize(TrimmedThrowableData ex, int chainDepth, int maxChainedExceptionsDepth) {
        int size = 0 + CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(ex.className));
        String message = ex.localizedMessage;
        if (message != null) {
            size += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(message));
        }
        int overflowCount = 0;
        int size2 = size;
        for (StackTraceElement element : ex.stacktrace) {
            int frameSize = getFrameSize(element, true);
            size2 += (CodedOutputStream.computeTagSize(4) + CodedOutputStream.computeRawVarint32Size(frameSize)) + frameSize;
        }
        TrimmedThrowableData cause = ex.cause;
        if (cause == null) {
            return size2;
        }
        if (chainDepth < maxChainedExceptionsDepth) {
            int exceptionSize = getEventAppExecutionExceptionSize(cause, chainDepth + 1, maxChainedExceptionsDepth);
            return size2 + ((CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(exceptionSize)) + exceptionSize);
        }
        while (true) {
            exceptionSize = overflowCount;
            if (cause == null) {
                return size2 + CodedOutputStream.computeUInt32Size(7, exceptionSize);
            }
            cause = cause.cause;
            overflowCount = exceptionSize + 1;
        }
    }

    private static int getEventAppExecutionSignalSize() {
        return ((0 + CodedOutputStream.computeBytesSize(1, SIGNAL_DEFAULT_BYTE_STRING)) + CodedOutputStream.computeBytesSize(2, SIGNAL_DEFAULT_BYTE_STRING)) + CodedOutputStream.computeUInt64Size(3, 0);
    }

    private static int getFrameSize(StackTraceElement element, boolean isCrashedThread) {
        int size;
        int i = 0;
        if (element.isNativeMethod()) {
            size = 0 + CodedOutputStream.computeUInt64Size(1, (long) Math.max(element.getLineNumber(), 0));
        } else {
            size = 0 + CodedOutputStream.computeUInt64Size(1, 0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(element.getClassName());
        stringBuilder.append(".");
        stringBuilder.append(element.getMethodName());
        size += CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(stringBuilder.toString()));
        if (element.getFileName() != null) {
            size += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(element.getFileName()));
        }
        if (!element.isNativeMethod() && element.getLineNumber() > 0) {
            size += CodedOutputStream.computeUInt64Size(4, (long) element.getLineNumber());
        }
        if (isCrashedThread) {
            i = 2;
        }
        return size + CodedOutputStream.computeUInt32Size(5, i);
    }

    private static int getThreadSize(Thread thread, StackTraceElement[] stackTraceElements, int importance, boolean isCrashedThread) {
        int size = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(thread.getName())) + CodedOutputStream.computeUInt32Size(2, importance);
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            int frameSize = getFrameSize(stackTraceElement, isCrashedThread);
            size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(frameSize)) + frameSize;
        }
        return size;
    }

    private static ByteString stringToByteString(String s) {
        return s == null ? null : ByteString.copyFromUtf8(s);
    }
}
