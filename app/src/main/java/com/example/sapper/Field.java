package com.example.sapper;

import static android.view.MotionEvent.ACTION_MOVE;

import android.annotation.Nullable;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Field extends View {
    private MinsField m_minsField = null;
    private int m_offsetTop = 0;
    private int m_offsetLeft = 0;

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MinsField getMinsField() {
        return m_minsField;
    }

    public void setMinsField(MinsField field) {
        m_minsField = field;
    }

    @Override
    public void computeScroll() {
        computeScroll();
        
        // Render scroll
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event) {
            case ACTION_MOVE:
                // Scroll
                return true;
        }
        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // Calculate offsets
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw
    }
}
