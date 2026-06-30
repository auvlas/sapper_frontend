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
    private long m_offsetTop = 0;
    private long m_offsetLeft = 0;
    private Paint m_paint = new Paint();
    private int m_heightCell = dpToPx(40);
    private int m_widthCell = dpToPx(40);
    private float m_lastX = 0;
    private float m_lastY = 0;

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paint.setColor(0xffffffff);
        m_paint.setStrokeWidth(dpToPx(4));
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
        // Получаем текущие размеры поля для ограничения скроллинга
        int widthCell = m_widthCell + getStrokeWidth();
        int heightCell = m_heightCell + getStrokeWidth();

        long widthEnd = (m_minsField != null ? m_minsField.getCountCols() : 0) * widthCell;
        long heightEnd = (m_minsField != null ? m_minsField.getCountRows() : 0) * heightCell;

        int width = getWidth();
        int height = getHeight();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Запоминаем точку, где палец коснулся экрана
                m_lastX = event.getX();
                m_lastY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                // Вычисляем, на сколько сместился палец
                float deltaX = event.getX() - m_lastX;
                float deltaY = event.getY() - m_lastY;

                // Обновляем смещение по горизонтали (скроллим в противоположную сторону)
                m_offsetLeft -= deltaX;
                // Ограничиваем скроллинг, чтобы не уходить в минус и не листать дальше края поля
                if (widthEnd > width) {
                    m_offsetLeft = Math.max(0, Math.min(m_offsetLeft, widthEnd - width));
                } else {
                    m_offsetLeft = 0;
                }

                // Обновляем смещение по вертикали
                m_offsetTop -= deltaY;
                if (heightEnd > height) {
                    m_offsetTop = Math.max(0, Math.min(m_offsetTop, heightEnd - height));
                } else {
                    m_offsetTop = 0;
                }

                // Сохраняем текущие координаты пальца для следующего шага
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

        int width = getWidth();
        int height = getHeight();

        int widthCell = m_widthCell + getStrokeWidth();
        int heightCell = m_heightCell + getStrokeWidth();

        long widthEnd = cols * widthCell;
        long heightEnd = rows * heightCell;

        long offsetLeft = m_offsetLeft;
        long offsetTop = m_offsetTop;

        if (widthEnd < width) {
            offsetLeft = 0;
        } else if (offsetLeft + width > widthEnd) {
            offsetLeft = widthEnd - width;
        }

        if (heightEnd < height) {
            offsetTop = 0;
        } else if (offsetTop + height > heightEnd) {
            offsetTop = heightEnd - height;
        }

        long startX = (widthCell - (offsetLeft % widthCell)) % widthCell;
        long startY = (heightCell - (offsetTop % heightCell)) % heightCell;

        long stopX = width < widthEnd ? width : widthEnd;
        long stopY = height < heightEnd ? height : heightEnd;

        if (startX < 0) {
            startX += widthCell;
        }

        if (startY < 0) {
            startY += heightCell;
        }

        Log.d("top", String.valueOf(offsetTop));
        Log.d("left", String.valueOf(offsetLeft));

        for (; startX <= stopX; startX += widthCell) {
            canvas.drawLine(startX, 0.0f, startX, stopY, m_paint);
        }

        for (; startY <= stopY; startY += heightCell) {
            canvas.drawLine(0.0f, startY, stopX, startY, m_paint);
        }
    }
}