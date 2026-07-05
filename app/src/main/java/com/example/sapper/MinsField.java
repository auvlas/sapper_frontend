package com.example.sapper;

public class MinsField implements Grid.Map, AutoCloseable {
    private long m_nativePtr = 0;

    public MinsField(long rows, long cols, long mins, long radiusMins) {
        init(rows, cols, mins, radiusMins);
    }

    @Override
    public synchronized void close() {
        if (m_nativePtr != 0) {
            destroy();
            m_nativePtr = 0;
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private native void init(long rows, long cols, long mins, long radiusMins);

    public native long getMins();
    public native byte getMins(long row, long col);

    public native boolean getMin(long row, long col);

    public native void setUpFlag(long row , long col);

    public native void setDownFlag(long row , long col);

    public native boolean getFlag(long row, long col);

    public native long getRows();

    public native long getCols();

    public native long getCells();

    public native long getEmpty();

    public native long getEmptyOpen();

    public native long getEmptyClose();

    public native float getVictoryPerfect();

    public native boolean getLive();

    public native boolean getVictory();

    public native void setOpen(long row, long col);

    public native boolean getOpen(long row, long col);

    private native void destroy();
}