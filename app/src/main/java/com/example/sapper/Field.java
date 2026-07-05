package com.example.sapper;

import static android.view.MotionEvent.*;

import androidx.annotation.NonNull;
import androidx.core.util.TypedValueCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class Field extends View implements ScaleGestureDetector.OnScaleGestureListener {
    private MinsField m_minsField = null;
    private int m_scrollLimitWidth = dpToPx(200);
    private int m_scrollLimitHeight = dpToPx(200);
    private int m_shiftHeight = dpToPx(100);
    private int m_shiftWidth = dpToPx(100);
    private long m_offsetTop = 0;
    private long m_offsetLeft = 0;
    private final Paint m_paint = new Paint();
    private int m_maxWidthCell = dpToPx(200);
    private int m_maxHeightCell = dpToPx(200);
    private int m_maxStrokeWidth = dpToPx(8);
    private int m_widthCell = 0;
    private int m_heightCell = 0;
    private int m_minWidthCell = dpToPx(20);
    private int m_minHeightCell = dpToPx(20);
    private int m_minStrokeWidth = dpToPx(2);
    private float m_lastX = 0;
    private float m_lastY = 0;
    private long m_timeLastPressDown = 0;
    private long m_offsetTopLastPressDown = 0;
    private long m_offsetLeftLastPressDown = 0;
    private final ScaleGestureDetector m_scaleGestureDetector =
            new ScaleGestureDetector(this.getContext(), this);
    private int m_scaleFactor = 0x28888888;


    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paint.setColor(0xffffffff);

        normalizeScale();
    }

    public int getMaxWidthCell() {
        return pxToDp(m_maxWidthCell);
    }

    public void setMaxWidthCell(int dp) {
        m_maxWidthCell = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getMaxHeightCell() {
        return pxToDp(m_maxHeightCell);
    }

    public void setMaxHeightCell(int dp) {
        m_maxHeightCell = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getMaxStrokeWidth() {
        return pxToDp(m_maxStrokeWidth);
    }

    public void setMaxStrokeWidth(int dp) {
        m_maxStrokeWidth = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getMinWidthCell() {
        return pxToDp(m_minWidthCell);
    }

    public void setMinWidthCell(int dp) {
        m_minWidthCell = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getMinHeightCell() {
        return pxToDp(m_minHeightCell);
    }

    public void setMinHeightCell(int dp) {
        m_minHeightCell = dpToPx(dp);
        postInvalidateOnAnimation();
    }
    public int getMinStrokeWidth() {
        return pxToDp(m_minStrokeWidth);
    }

    public void setMinStrokeWidth(int dp) {
        m_minStrokeWidth = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getScrollLimitWidth() {
        return pxToDp(m_scrollLimitWidth);
    }

    public void setScrollLimitWidth(int dp) {
        m_scrollLimitWidth = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getScrollLimitHeight() {
        return pxToDp(m_scrollLimitHeight);
    }

    public void setScrollLimitHeight(int dp) {
        m_scrollLimitHeight = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getShiftWidth() {
        return pxToDp(m_shiftWidth);
    }

    public void setShiftWidth(int dp) {
        m_shiftWidth = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getShiftHeight() {
        return pxToDp(m_shiftHeight);
    }

    public void setShiftHeight(int dp) {
        m_shiftHeight = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    public int getScaleFactor() {
        return m_scaleFactor;
    }

    public void setScaleFactor(int dp) {
        m_scaleFactor = dpToPx(dp);
        postInvalidateOnAnimation();
    }

    private int dpToPx(int dp) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValueCompat.dpToPx(dp, metrics);
    }

    private int pxToDp(int px) {
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

    public int getStrokeWidth() {
        return (int) m_paint.getStrokeWidth();
    }

    public MinsField getMinsField() {
        return m_minsField;
    }

    public void setMinsField(MinsField field) {
        m_minsField = field;

        m_offsetLeft = 0;
        m_offsetTop = 0;

        m_offsetLeftLastPressDown = 0;
        m_offsetTopLastPressDown = 0;

        m_timeLastPressDown = System.currentTimeMillis();

        postInvalidateOnAnimation();
    }

    protected void normalizeScale() {
        postInvalidateOnAnimation();
        int deltaWidthCell = m_maxWidthCell - m_minWidthCell;
        int deltaHeightCell = m_maxHeightCell - m_minHeightCell;
        int deltaStrokeWidth = m_maxStrokeWidth - m_minStrokeWidth;

        m_widthCell = m_minWidthCell + (m_scaleFactor / (0x7fffffff / deltaWidthCell));
        m_heightCell = m_minHeightCell + (m_scaleFactor / (0x7fffffff / deltaHeightCell));
        m_paint.setStrokeWidth((float) (m_minStrokeWidth +
                (m_scaleFactor / (0x7fffffff / deltaStrokeWidth))));
    }

    @Override
    public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScale(@NonNull ScaleGestureDetector detector) {
        int scaleFactor = (int) ((float) m_scaleFactor * detector.getScaleFactor());

        m_scaleFactor = Math.max(1, scaleFactor);

        normalizeScale();

        return true;
    }

    @Override
    public void onScaleEnd(@NonNull ScaleGestureDetector detector) {
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int strokeWidth = getStrokeWidth();

        int widthCell = m_widthCell + strokeWidth;
        int heightCell = m_heightCell + strokeWidth;

        long widthField = (m_minsField != null ? m_minsField.getCountCols() : 0)
                * widthCell + strokeWidth;
        long heightField = (m_minsField != null ? m_minsField.getCountRows() : 0)
                * heightCell + strokeWidth;

        int width = getWidth();
        int height = getHeight();

        int curX = (int) event.getX();
        int curY = (int) event.getY();

        m_scaleGestureDetector.onTouchEvent(event);

        Log.d("offsetTop", String.valueOf(m_offsetTop));

        switch (event.getAction()) {
            case ACTION_DOWN:
                m_lastX = curX;
                m_lastY = curY;

                m_timeLastPressDown = 0;

                m_offsetTopLastPressDown = -1;
                m_offsetLeftLastPressDown = -1;

                return true;

            case ACTION_MOVE:
                if (m_scaleGestureDetector.isInProgress()) {
                    return true;
                }

                float deltaX = curX - m_lastX;
                float deltaY = curY - m_lastY;

                int totalShiftX = (m_shiftWidth + m_scrollLimitWidth) << 1;
                int totalShiftY = (m_shiftHeight + m_scrollLimitHeight) << 1;

                m_offsetLeft = Math.max(0L, Math.min((long) (m_offsetLeft + deltaX),
                        widthField + (m_shiftWidth * 2L) > width ?
                                widthField + totalShiftX : width + m_scrollLimitWidth));

                m_offsetTop = Math.max(0L, Math.min((long) (m_offsetTop + deltaY),
                        heightField + (m_shiftHeight * 2L) > height ?
                                heightField + totalShiftY : height + m_scrollLimitHeight));

                if (m_lastX != curX || m_lastY != curY) {
                    postInvalidateOnAnimation();
                }

                m_lastX = curX;
                m_lastY = curY;

                return true;

            case ACTION_UP:
            case ACTION_CANCEL:
                m_timeLastPressDown = System.currentTimeMillis();

                m_offsetTopLastPressDown = m_offsetTop;
                m_offsetLeftLastPressDown = m_offsetLeft;

                postInvalidateOnAnimation();

                return true;

            case MotionEvent.ACTION_POINTER_DOWN:
                m_lastX = (int) event.getX(event.getActionIndex());
                m_lastY = (int) event.getY(event.getActionIndex());
                return true;

            case MotionEvent.ACTION_POINTER_UP:
                int pointerIndex = (event.getActionIndex() == 0) ? 1 : 0;
                m_lastX = (int) event.getX(pointerIndex);
                m_lastY = (int) event.getY(pointerIndex);
                return true;
        }

        return true;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        Log.d("onDrawTop", String.valueOf(m_offsetTop));

        if (m_minsField == null) {
            return;
        }

        long rows = m_minsField.getCountRows();
        long cols = m_minsField.getCountCols();

        if (rows == 0 || cols == 0) {
            return;
        }

        int strokeWidth = getStrokeWidth();
        int halfStrokeWidth = strokeWidth / 2;

        int offsetWidth = m_scrollLimitWidth + m_shiftWidth;
        int offsetHeight = m_scrollLimitHeight + m_shiftHeight;

        int widthCell = m_widthCell + strokeWidth;
        int heightCell = m_heightCell + strokeWidth;

        long widthFieldRow = cols * widthCell;
        long heightFieldRow = rows * heightCell;

        long widthField = widthFieldRow + strokeWidth;
        long heightField = heightFieldRow + strokeWidth;

        long widthEnd = widthFieldRow + halfStrokeWidth + offsetWidth;
        long heightEnd = heightFieldRow + halfStrokeWidth + offsetHeight;

        int width = getWidth();
        int height = getHeight();

        if (0 != m_timeLastPressDown) {
            int deltaTime = (int) (System.currentTimeMillis() - m_timeLastPressDown);
            int timeDistance = dpToPx(1) * (deltaTime / 4);

            if (-1 != m_offsetLeftLastPressDown) {
                if (m_offsetLeft < offsetWidth) {
                    m_offsetLeft = m_offsetLeftLastPressDown + timeDistance;
                    if (m_offsetLeft >= offsetWidth) {
                        m_offsetLeft = offsetWidth;
                        m_offsetLeftLastPressDown = -1;
                    } else {
                        postInvalidateOnAnimation();
                    }
                } else if (widthField + (m_shiftWidth * 2L) <= width) {
                    if (m_offsetLeft > width - widthField - m_shiftWidth + m_scrollLimitWidth) {
                        m_offsetLeft = m_offsetLeftLastPressDown - timeDistance;
                        if (m_offsetLeft <= width - widthField - m_shiftWidth + m_scrollLimitWidth) {
                            m_offsetLeft = width - widthField - m_shiftWidth + m_scrollLimitWidth;
                            m_offsetLeftLastPressDown = -1;
                        } else {
                            postInvalidateOnAnimation();
                        }
                    }
                } else {
                    m_offsetLeftLastPressDown = -1;
                }
            }

            if (-1 != m_offsetTopLastPressDown) {
                if (m_offsetTop < offsetHeight) {
                    m_offsetTop = m_offsetTopLastPressDown + timeDistance;
                    if (m_offsetTop >= offsetHeight) {
                        m_offsetTop = offsetHeight;
                        m_offsetTopLastPressDown = -1;
                    } else {
                        postInvalidateOnAnimation();
                    }
                } else if (heightField + (m_shiftHeight * 2L) <= height) {
                    if (m_offsetTop > height - heightField - m_shiftHeight + m_scrollLimitHeight) {
                        m_offsetTop = m_offsetTopLastPressDown - timeDistance;
                        if (m_offsetTop <= height - heightField - m_shiftHeight + m_scrollLimitHeight) {
                            m_offsetTop = height - heightField - m_shiftHeight + m_scrollLimitHeight;
                            m_offsetTopLastPressDown = -1;
                        } else {
                            postInvalidateOnAnimation();
                        }
                    }
                } else {
                    m_offsetTopLastPressDown = -1;
                }
            }
        }


        int startX = 0;
        int stopX = 0;

        if (widthField + (m_shiftWidth * 2L) > width) {
            startX = (int) (m_offsetLeft < offsetWidth
                    ? m_offsetLeft - halfStrokeWidth
                    : ((m_offsetLeft - halfStrokeWidth) % widthCell) - widthCell);

            stopX = (int) Math.min(width + halfStrokeWidth, startX + widthField);
        } else {
            startX = (int) m_offsetLeft - m_scrollLimitWidth - halfStrokeWidth;

            stopX = startX + (int) widthFieldRow + strokeWidth;
        }


        int startY = 0;
        int stopY = 0;

        if (heightField + (m_shiftHeight * 2L) > height) {
            startY = (int) (m_offsetTop < offsetHeight
                    ? m_offsetTop - halfStrokeWidth
                    : ((m_offsetTop - halfStrokeWidth) % heightCell) - heightCell);

            stopY = (int) Math.min(height + halfStrokeWidth, startY + heightField);
        } else {
            startY =  (int) m_offsetTop - m_scrollLimitHeight - halfStrokeWidth;

            stopY = startY + (int) heightFieldRow + strokeWidth;
        }


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