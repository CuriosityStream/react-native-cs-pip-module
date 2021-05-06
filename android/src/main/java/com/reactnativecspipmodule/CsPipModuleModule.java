package com.reactnativecspipmodule;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import android.os.Build;
import android.app.PictureInPictureParams;
import android.util.Rational;

@ReactModule(name = CsPipModuleModule.NAME)
public class CsPipModuleModule extends ReactContextBaseJavaModule {
    public static final String NAME = "CsPipModule";

    private final ReactApplicationContext reactContext;
    private boolean isPipSupported = false;
    private boolean isCustomAspectRatioSupported = false;
    private Rational aspectRatio;
    private static final int ASPECT_WIDTH = 16;
    private static final int ASPECT_HEIGHT = 9;

    public CsPipModuleModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            isPipSupported = true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            isCustomAspectRatioSupported = true;
            aspectRatio = new Rational(ASPECT_WIDTH, ASPECT_HEIGHT);
        }
    }

    @ReactMethod
    public void enterPiPMode() {
        if (isPipSupported) {
            if (isCustomAspectRatioSupported) {
                PictureInPictureParams params = new PictureInPictureParams.Builder()
                        .setAspectRatio(this.aspectRatio).build();
                getCurrentActivity().enterPictureInPictureMode(params);
            } else
                getCurrentActivity().enterPictureInPictureMode();
        }
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void isPiPSupported(Promise promise) {
        try{
            promise.resolve(isPipSupported);
        }catch (Exception e){
            promise.reject("Can not detect PiP support", e);
        }
    }
}
