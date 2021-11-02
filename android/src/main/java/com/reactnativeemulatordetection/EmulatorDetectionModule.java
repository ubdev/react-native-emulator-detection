package com.reactnativeemulatordetection;

import android.os.Build;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.reactnativeemulatordetection.EmulatorDetector;

@ReactModule(name = EmulatorDetectionModule.NAME)
public class EmulatorDetectionModule extends ReactContextBaseJavaModule {
    public static final String NAME = "EmulatorDetection";

    public EmulatorDetectionModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
        promise.resolve(a * b);
    }

    public static native int nativeMultiply(int a, int b);

    @ReactMethod
    public void isRunningOnEmulator(Promise promise) {
        EmulatorDetector.with(getReactApplicationContext())
            .setCheckTelephony(false)
            //.addPackageName("可以新增要檢測的模擬器相關APP PackageName")
            .addPackageName("com.vphone.googlesign") // 夜神檢測
            .addPackageName("com.vphone.helper") // 夜神檢測
            .addPackageName("com.vphone.launcher") // 夜神檢測
            .addPackageName("com.mumu.launcher") // MUMU檢測
            .setDebug(false)
            .detect(new EmulatorDetector.OnEmulatorDetectorListener() {
                @Override
                public void onResult(boolean isEmulator) {
                    if (isEmulator || Build.getRadioVersion().length() == 0) {
                        promise.resolve(true);
                    } else {
                        promise.resolve(false);
                    }
                }
            });
    }
}
