package com.mcal.apkprotector.signer;

import android.content.Context;

import java.io.File;

public class SignatureTool {
    public static boolean sign(Context context, File input, File output) {
        try {
            SignUtils signUtils = SignUtils.getInstance(context, IOUtils.getTempFile(context));
            if (signUtils.sign(input, output, -1)) {
                if (signUtils.verify(output)) {
                    return true;
                }
                return true;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
