package org.jf.dexlib2.immutable.debug;


import com.google.common.collect.ImmutableList;
import org.jf.dexlib2.DebugItemType;
import org.jf.dexlib2.iface.debug.*;
import org.jf.util.ExceptionWithContext;
import org.jf.util.ImmutableConverter;

public abstract class ImmutableDebugItem implements DebugItem {
    private static final ImmutableConverter<ImmutableDebugItem, DebugItem> CONVERTER =
            new ImmutableConverter<ImmutableDebugItem, DebugItem>() {
                @Override
                protected boolean isImmutable(DebugItem item) {
                    return item instanceof ImmutableDebugItem;
                }


                @Override
                protected ImmutableDebugItem makeImmutable(DebugItem item) {
                    return ImmutableDebugItem.of(item);
                }
            };
    protected final int codeAddress;

    public ImmutableDebugItem(int codeAddress) {
        this.codeAddress = codeAddress;
    }


    public static ImmutableDebugItem of(DebugItem debugItem) {
        if (debugItem instanceof ImmutableDebugItem) {
            return (ImmutableDebugItem) debugItem;
        }
        switch (debugItem.getDebugItemType()) {
            case DebugItemType.START_LOCAL:
                return ImmutableStartLocal.of((StartLocal) debugItem);
            case DebugItemType.END_LOCAL:
                return ImmutableEndLocal.of((EndLocal) debugItem);
            case DebugItemType.RESTART_LOCAL:
                return ImmutableRestartLocal.of((RestartLocal) debugItem);
            case DebugItemType.PROLOGUE_END:
                return ImmutablePrologueEnd.of((PrologueEnd) debugItem);
            case DebugItemType.EPILOGUE_BEGIN:
                return ImmutableEpilogueBegin.of((EpilogueBegin) debugItem);
            case DebugItemType.SET_SOURCE_FILE:
                return ImmutableSetSourceFile.of((SetSourceFile) debugItem);
            case DebugItemType.LINE_NUMBER:
                return ImmutableLineNumber.of((LineNumber) debugItem);
            default:
                throw new ExceptionWithContext("Invalid debug item type: %d", debugItem.getDebugItemType());
        }
    }


    public static ImmutableList<ImmutableDebugItem> immutableListOf(Iterable<? extends DebugItem> list) {
        return CONVERTER.toList(list);
    }

    @Override
    public int getCodeAddress() {
        return codeAddress;
    }
}