package com.example.sapper;

public class MinsField {
    private long m_nativePtr = 0;

    public MinsField(long rows, long cols, long mins) {
        init(rows, cols, mins);
    }

    public void finalize() {
        destroy();
    }

    private native void init(long rows, long cols, long mins);

    public native long getCountMins();
    public native byte getCountMinsNear(long row, long col);

    public native boolean isMin(long row, long col);

    public native void upFlag(long row , long col);

    public native void downFlag(long row , long col);

    public native boolean isFlag(long row, long col);

    public native long getCountRows();

    public native long getCountCols();

    // public native long getCountCells();

    public native long getCountEmpty();

    public native long getCountEmptyOpen();

    public native long getCountEmptyClose();

    public native byte getPercentVictory();

    public native boolean isLive();

    public native boolean isVictory();

    public native boolean open(long row, long col);

    public native boolean isOpen(long row, long col);

    private native void destroy();
}