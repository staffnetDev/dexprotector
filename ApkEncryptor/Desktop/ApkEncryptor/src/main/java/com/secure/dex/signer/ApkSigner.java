package com.secure.dex.signer;

import com.android.apksigner.ApkSignerTool;
import com.secure.dex.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ApkSigner {

    public ApkSigner() {
    }

    public boolean apksigner(String inputPath, String outputPath) {
        try {
            signWithTestKey(inputPath, outputPath, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void signWithTestKey(String inputPath, String outputPath, LogCallback callback) {
        try (LogWriter logger = new LogWriter(callback)) {
            long savedTimeMillis = System.currentTimeMillis();
            PrintStream oldOut = System.out;

            List<String> args = Arrays.asList(
                    "sign",
                    "--in",
                    inputPath,
                    "--out",
                    outputPath,
                    "--key",
                    new File(FileUtils.getHomePath(), "tools/testkey.pk8").getAbsolutePath(),
                    "--cert",
                    new File(FileUtils.getHomePath(), "tools/testkey.x509.pem").getAbsolutePath()
            );

            logger.write("Signing an APK file with these arguments: " + args);

            /* If the signing has a callback, we need to change System.out to our logger */
            if (callback != null) {
                try (PrintStream stream = new PrintStream(logger)) {
                    System.setOut(stream);
                }
            }

            try {
                ApkSignerTool.main(args.toArray(new String[0]));
            } catch (Exception e) {
                callback.errorCount.incrementAndGet();
                logger.write("An error occurred while trying to sign the APK file " + inputPath +
                        " and outputting it to " + outputPath + ": " + e.getMessage() + "\n" +
                        "Stack trace: " + e.getMessage());
            }

            logger.write("Signing an APK file took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");

            if (callback != null) {
                System.setOut(oldOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface LogCallback {
        AtomicInteger errorCount = new AtomicInteger(0);

        void onNewLineLogged(String line);
    }

    private static class LogWriter extends OutputStream {

        private final LogCallback mCallback;
        private String mCache = "";

        private LogWriter(LogCallback callback) {
            mCallback = callback;
        }

        @Override
        public void write(int b) {
            if (isLoggingDisabled()) return;

            mCache += (char) b;

            if (((char) b) == '\n') {
                mCallback.onNewLineLogged(mCache);
                mCache = "";
            }
        }

        private void write(String s) {
            if (isLoggingDisabled()) return;

            for (byte b : s.getBytes()) {
                write(b);
            }
        }

        private boolean isLoggingDisabled() {
            return mCallback == null;
        }
    }
}