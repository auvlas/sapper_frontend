package com.example.sapper;

import static android.view.MotionEvent.*;

import static androidx.core.util.TypedValueCompat.dpToPx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Field extends View {
    private MinsField m_minsField = null;
    private long m_shiftHeight = dpToPx(100);
    private long m_shiftWidth = dpToPx(100);
    private long m_offsetTop = 0;
    private long m_offsetLeft = 0;
    private Paint m_paint = new Paint();
    private int m_heightCell = dpToPx(100);
    private int m_widthCell = dpToPx(100);
    private float m_lastX = 0;
    private float m_lastY = 0;

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paint.setColor(0xffffffff);
        m_paint.setStrokeWidth(dpToPx(8));
    }

    public int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    public int pxToDp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density);
    }

    public int getColor() {
        return m_paint.getColor();
    }

    public void setColor(int color) {
        m_paint.setColor(color);
        postInvalidateOnAnimation();
    }

    public void setStrokeWidth(int strokeWidth) {
        m_paint.setStrokeWidth(strokeWidth);
        postInvalidateOnAnimation();
    }

    public int getStrokeWidth() {
        return (int) m_paint.getStrokeWidth();
    }

    public MinsField getMinsField() {
        return m_minsField;
    }

    public void setMinsField(MinsField field) {
        m_minsField = field;
        postInvalidateOnAnimation();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        
        // Render scroll
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int widthCell = m_widthCell + getStrokeWidth();
        int heightCell = m_heightCell + getStrokeWidth();

        long widthEnd = (m_minsField != null ? m_minsField.getCountCols() : 0) * widthCell;
        long heightEnd = (m_minsField != null ? m_minsField.getCountRows() : 0) * heightCell;

        int width = getWidth();
        int height = getHeight();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                m_lastX = event.getX();
                m_lastY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - m_lastX;
                float deltaY = event.getY() - m_lastY;

                m_offsetLeft -= deltaX;
                if (widthEnd > width) {
                    m_offsetLeft = Math.max(0, Math.min(m_offsetLeft,
                            widthEnd - width + getStrokeWidth() + m_shiftWidth * 2));
                } else {
                    m_offsetLeft = width / 2 - widthEnd / 2;
                }

                m_offsetTop -= deltaY;
                if (heightEnd > height) {
                    m_offsetTop = Math.max(0, Math.min(m_offsetTop,
                            heightEnd - height + getStrokeWidth() +  m_shiftHeight * 2));
                } else {
                    m_offsetTop = height / 2 - heightEnd / 2;
                }

                m_lastX = event.getX();
                m_lastY = event.getY();

                postInvalidateOnAnimation();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("onDraw", "draw");

        if (m_minsField == null) {
            return;
        }

        long rows = m_minsField.getCountRows();
        long cols = m_minsField.getCountCols();

        int strokeWidth = getStrokeWidth();
        int halfStrokeWidth = strokeWidth / 2;

        int width = getWidth();
        int height = getHeight();

        int widthCell = m_widthCell + strokeWidth;
        int heightCell = m_heightCell + strokeWidth;

        long widthEnd = cols * widthCell + strokeWidth + m_shiftWidth * 2;
        long heightEnd = rows * heightCell + strokeWidth + m_shiftHeight * 2;

        long offsetLeft = m_offsetLeft - strokeWidth;
        long offsetTop = m_offsetTop - strokeWidth;

        long startX = (-(offsetLeft % widthCell)) % widthCell - halfStrokeWidth;
        long startY = (-(offsetTop % heightCell)) % heightCell - halfStrokeWidth;

        long stopX = (width < widthEnd - offsetLeft ? width : widthEnd - offsetLeft) + halfStrokeWidth;
        long stopY = (height < heightEnd - offsetTop ? height : heightEnd - offsetTop) + halfStrokeWidth;

        for (long x = startX; x <= stopX; x += widthCell) {
            canvas.drawLine(x, startY - halfStrokeWidth, x, stopY - halfStrokeWidth, m_paint);
        }

        for (long y = startY; y <= stopY; y += heightCell) {
            canvas.drawLine(startX - halfStrokeWidth, y, stopX - halfStrokeWidth, y, m_paint);
        }
    }
}