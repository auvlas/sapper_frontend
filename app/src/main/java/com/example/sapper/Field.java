package com.example.sapper;

import static android.view.MotionEvent.*;

import androidx.core.util.TypedValueCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.util.TypedValueCompat;

public class Field extends View {
    private MinsField m_minsField = null;
    private int m_scrollLimitWidth = dpToPx(200);
    private int m_scrollLimitHeight = dpToPx(200);
    private int m_shiftHeight = dpToPx(100);
    private int m_shiftWidth = dpToPx(100);
    private long m_offsetTop = m_scrollLimitWidth;
    private long m_offsetLeft = m_scrollLimitHeight;
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
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValueCompat.dpToPx(dp, metrics);
    }

    public int pxToDp(int px) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValueCompat.pxToDp(px, metrics);
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

                m_offsetLeft = Math.max(0, Math.min(m_offsetLeft,
                        widthEnd - width + getStrokeWidth() + ((m_shiftWidth + m_scrollLimitWidth) << 1)));

                m_offsetTop -= deltaY;

                m_offsetTop = Math.max(0, Math.min(m_offsetTop,
                        heightEnd - height + getStrokeWidth() + ((m_shiftHeight + m_scrollLimitHeight) << 1)));

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

        int offsetWidth = m_scrollLimitWidth + m_shiftWidth;
        int offsetHeight = m_scrollLimitHeight + m_shiftHeight;

        int width = getWidth();
        int height = getHeight();

        int widthCell = m_widthCell + strokeWidth;
        int heightCell = m_heightCell + strokeWidth;

        long widthEnd = cols * widthCell + halfStrokeWidth + offsetWidth;
        long heightEnd = rows * heightCell + halfStrokeWidth + offsetHeight;

        int startX = m_offsetLeft > offsetWidth ?
                (int) (-((m_offsetLeft - offsetWidth - halfStrokeWidth) % widthCell)) - halfStrokeWidth :
                (int) (offsetWidth - m_offsetLeft);
        int startY = m_offsetTop > offsetHeight ?
                (int) (-((m_offsetTop - offsetHeight - halfStrokeWidth) % heightCell)) - halfStrokeWidth :
                (int) (offsetHeight - m_offsetTop);

        Log.d("startX1", String.valueOf(startX));

        Log.d("startX2", String.valueOf(startX));

        int stopX = (width < widthEnd - m_offsetLeft ?
                width : (int) (widthEnd - m_offsetLeft)) + halfStrokeWidth;
        int stopY = (height < heightEnd - m_offsetTop ?
                height : (int) (heightEnd - m_offsetTop)) + halfStrokeWidth;

        int startXStroke = startX - halfStrokeWidth;
        int startYStroke = startY - halfStrokeWidth;
        int stopXStroke = stopX - halfStrokeWidth;
        int stopYStroke = stopY - halfStrokeWidth;

        for (int x = startX; x <= stopX; x += widthCell) {
            canvas.drawLine(x, startYStroke, x, stopYStroke, m_paint);
        }

        for (int y = startY; y <= stopY; y += heightCell) {
            canvas.drawLine(startXStroke, y, stopXStroke, y, m_paint);
        }
    }
}