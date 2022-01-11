package com.reactnativecspipmodule;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import android.app.Activity;
import android.os.Build;
import android.app.PictureInPictureParams;
import android.util.Rational;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Process;
import android.content.pm.PackageManager;


@ReactModule(name = CsPipModuleModule.NAME)
public class CsPipModuleModule extends ReactContextBaseJavaModule {
    public static final String NAME = "CsPipModule";

    private final ReactApplicationContext reactContext;
    private boolean isPipSupported = false;
    private boolean isCustomAspectRatioSupported = false;
    private boolean hasFeature = false;
    private Rational aspectRatio;
    private static final int ASPECT_WIDTH = 16;
    private static final int ASPECT_HEIGHT = 9;

    public CsPipModuleModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        final PackageManager pm = reactContext.getPackageManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            isPipSupported = true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            isCustomAspectRatioSupported = true;
            aspectRatio = new Rational(ASPECT_WIDTH, ASPECT_HEIGHT);
        }
        if(pm.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
          hasFeature = true;
        }
    }

    @ReactMethod
   public void enterPiPMode() {
        if (isPipSupported && hasFeature) {
            AppOpsManager manager = (AppOpsManager) reactContext.getSystemService(Context.APP_OPS_SERVICE);
            if (manager != null) {
                int modeAllowed = manager.checkOpNoThrow(AppOpsManager.OPSTR_PICTURE_IN_PICTURE, Process.myUid(),
                        reactContext.getPackageName());
                if (modeAllowed == AppOpsManager.MODE_ALLOWED) {
                  try {
                    final Activity activity = getCurrentActivity();
                    if (activity == null) {
                      return;
                    }
                    if (isCustomAspectRatioSupported) {
                      PictureInPictureParams params = new PictureInPictureParams.Builder()
                        .setAspectRatio(this.aspectRatio).build();
                      activity.enterPictureInPictureMode(params);
                    } else {
                      activity.enterPictureInPictureMode();
                    }
                  }  catch (Exception e) {}
                }
            }
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
            promise.resolve(isPipSupported && hasFeature);
        }catch (Exception e){
            promise.reject("Can not detect PiP support", e);
        }
    }
}
