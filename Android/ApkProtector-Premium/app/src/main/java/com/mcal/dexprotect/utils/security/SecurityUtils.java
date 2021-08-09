package com.mcal.dexprotect.utils.security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mcal.dexprotect.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class SecurityUtils {
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static boolean isEmulator() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
    }

    public static boolean illegalCodeCheck() throws ClassNotFoundException {
        return Class.forName("cc.binmt.signature.PmsHookApplication") != null ||
                Class.forName("arm.a") != null ||
                Class.forName("arm.ArmKill") != null ||
                Class.forName("np.manager.Copyright") != null ||
                Class.forName("anymy.sign.BinSignatureFix") != null ||
                Class.forName("apkeditor.patch.signature.Fix") != null ||
                Class.forName("com.anymy.reflection") != null ||
                Class.forName("bin.mt.apksignaturekillerplus.HookApplication") != null ||
                Class.forName("np.App") != null ||
                Class.forName("np.manager.FuckSign") != null ||
                Class.forName("np.manager.Copyright") != null ||
                Class.forName("cc.binmt.signature.Hook") != null;
    }

    public static boolean assetsCheck(Context context, String files) throws IOException {
        AssetManager mg = context.getResources().getAssets();
        InputStream is = null;
        try {
            is = mg.open(files);
            if (is != null) {
                is.close();
            }
            return true;
            //File exists so do something with it
        } catch (IOException ex) {
            return false;
            //file does not exist
        }/* finally {
            if (is != null) {
                is.close();
            }
        }*/
    }

    /**
     * Проверка установки приложения из маркета
     *
     * @return
     */
    public static boolean isVerifyInstaller() {
        try {
            /* getting context */
            @SuppressLint("PrivateApi") Class appGlobals = Class.forName("android.app.AppGlobals");
            Class application = Class.forName("android.app.Application");
            Method getInitialApplication = appGlobals.getMethod("getInitialApplication");
            Method createPackageContext = application.getMethod("createPackageContext", String.class, int.class);
            Context context = (Context) createPackageContext.invoke(getInitialApplication.invoke(appGlobals), BuildConfig.APPLICATION_ID, 0);

            /* getting PackageManager */
            Method getPackageManager = context.getClass().getMethod("getPackageManager");
            PackageManager pm = (PackageManager) getPackageManager.invoke(context);

            /* check install on gp */
            Method getInstallerPackageName = pm.getClass().getMethod("getInstallerPackageName", String.class);
            String installerPackageName = (String) getInstallerPackageName.invoke(pm, BuildConfig.APPLICATION_ID);
            return installerPackageName != null && installerPackageName.startsWith("com.android.vending");
        } catch (Exception e) {
            return false;
        }
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return applicationInfo.className;
    }
}
