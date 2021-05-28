package com.mcal.dexprotect.task;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

public class DexPatcher {
    public static boolean dexPatch(Context context, String dexPath) {

        try {
            //DexBackedDexFile dex = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new BufferedInputStream(new FileInputStream(dexPath)));
            /*byte[] processDex = processDex(context, dex);

            try (FileOutputStream fos = new FileOutputStream(dexPath)) {
                fos.write(processDex);
            }*/
            renameAppClass(context, dexPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean renameAppClass(@NotNull Context context, String dexPath) {
        try {
            /*//DexBackedDexFile dexFile = DexFileFactory.loadDexFile(dexPath, Opcodes.forApi(19));

            DexBackedDexFile dexFile = DexBackedDexFile.fromInputStream(Opcodes.getDefault(), new BufferedInputStream(new FileInputStream(dexPath)));

            DexRewriter rewriter = new DexRewriter(Opcodes.forApi(19), new RewriterModule() {
                @NonNull
                public Rewriter<String> getTypeRewriter(@NonNull Rewriters rewriters) {
                    return value -> {
                        if (value.equals("Lcom/mcal/dexprotect/ProxyApplication;")) {
                            return "L" + Preferences.getApplicationName().replace(".", "/") + ";";
                        }
                        return value;
                    };
                }
            });
            DexFile rewrittenDexFile = rewriter.rewriteDexFile(dexFile);
            DexPool.writeTo(dexPath, rewrittenDexFile);
            //new DexOptimizer().merge_multidex(xpath + "/gen");
            //rewrite_dex();*/
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*private static byte @NotNull [] processDex(Context context, DexBackedDexFile dex) throws Exception {
        DexBuilder dexBuilder = new DexBuilder(Opcodes.getDefault());
        AssetManager am = context.getAssets();

        try (InputStream fis = am.open("app.smali")) {
            String src = new String(StreamUtil.readBytes(fis), StandardCharsets.UTF_8);
            src = src.replace("Lcom/mcal/dexprotect/ProxyApplication;", "L" + Preferences.getApplicationName().replace('.', '/') + ";");
            ClassDef classDef = Smali.assembleSmaliFile(src, dexBuilder, new SmaliOptions());
            if (classDef == null)
                throw new Exception("Parse smali failed");
            for (DexBackedClassDef dexBackedClassDef : dex.getClasses()) {
                dexBuilder.internClassDef(dexBackedClassDef);
            }
        }
        MemoryDataStore store = new MemoryDataStore();
        dexBuilder.writeTo(store);
        return Arrays.copyOf(store.getBufferData(), store.getSize());
    }*/
}