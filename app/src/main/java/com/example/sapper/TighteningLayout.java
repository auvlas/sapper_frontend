package com.example.sapper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.util.TypedValueCompat;

public class TighteningLayout extends LinearLayout {
    private LinearLayout m_actionBar = null;
    private Button m_visibleButton = null;
    private TextView m_title = null;
    private LinearLayout m_mainLayout = null;
    private CharSequence m_openLabelVisibleButton = "﹀";
    private CharSequence m_closeLabelVisibleButton = "＞";

    public TighteningLayout(Context context) {
        this(context, null);
    }

    public TighteningLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TighteningLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TighteningLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setOrientation(VERTICAL);

        m_actionBar = new LinearLayout(context);

        LayoutParams paramsActionBar = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(60)
        );

        m_visibleButton = new Button(context);

        LayoutParams paramsVisibleButton = new LayoutParams(
                dpToPx(40), ViewGroup.LayoutParams.MATCH_PARENT
        );

        int buttonMargin = dpToPx(10);

        paramsVisibleButton.setMargins(buttonMargin, buttonMargin, buttonMargin, buttonMargin);

        m_actionBar.addView(m_visibleButton, paramsVisibleButton);

        m_visibleButton.setClickable(false);

        m_title = new TextView(context);

        LayoutParams paramsTitle = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        int titlePaddingHorizontal = dpToPx(20);

        m_title.setPadding(titlePaddingHorizontal, 0, titlePaddingHorizontal, 0);
        m_title.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        m_actionBar.addView(m_title, paramsTitle);

        super.addView(m_actionBar, paramsActionBar);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.TighteningLayout, defStyleAttr, defStyleRes);

            CharSequence textTitle = typedArray.getText(
                    R.styleable.TighteningLayout_textTitle);

            if (textTitle != null) {
                m_title.setText(textTitle);
            }

            CharSequence openLabelVisibleButton = typedArray.getText(
                    R.styleable.TighteningLayout_openLabelVisibleButton);

            if (openLabelVisibleButton != null) {
                m_openLabelVisibleButton = openLabelVisibleButton;
            }

            CharSequence closeLabelVisibleButton = typedArray.getText(
                    R.styleable.TighteningLayout_closeLabelVisibleButton);

            if (closeLabelVisibleButton != null) {
                m_closeLabelVisibleButton = closeLabelVisibleButton;
            }

            int defaultTitleSize = dpToPx(28);
            int textSizePxTitle = typedArray.getDimensionPixelSize(
                    R.styleable.TighteningLayout_textSizeTitle, defaultTitleSize);

            m_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePxTitle);

            int defaultVisibleButtonSize = dpToPx(18);
            int textSizePxVisibleButton = typedArray.getDimensionPixelSize(
                    R.styleable.TighteningLayout_textSizeVisibleButton, defaultVisibleButtonSize);

            m_visibleButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePxVisibleButton);

            if (typedArray.hasValue(R.styleable.TighteningLayout_backgroundVisibleButton)) {
                Drawable buttonBg = typedArray.getDrawable(
                        R.styleable.TighteningLayout_backgroundVisibleButton);
                if (buttonBg != null) {
                    m_visibleButton.setBackground(buttonBg);
                }
            }

            typedArray.recycle();
        }

        m_mainLayout = new LinearLayout(context);

        LayoutParams paramsMainLayout = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        m_mainLayout.setOrientation(VERTICAL);

        open();

        super.addView(m_mainLayout, -1, paramsMainLayout);

        m_actionBar.setOnClickListener(this::onClickVisibleButton);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (null == m_mainLayout) {
            super.addView(child, index, params);
        } else {
            m_mainLayout.addView(child, index, params);
        }
    }

    private int pxToDp(int px) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValueCompat.pxToDp(px, metrics);
    }

    private int dpToPx(int dp) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValueCompat.dpToPx(dp, metrics);
    }

    void close() {
        m_visibleButton.setText(m_closeLabelVisibleButton);
        m_mainLayout.setVisibility(GONE);
    }

    void open() {
        m_visibleButton.setText(m_openLabelVisibleButton);
        m_mainLayout.setVisibility(VISIBLE);
    }

    void onClickVisibleButton(View view) {
        switch (m_mainLayout.getVisibility()) {
            case VISIBLE -> close();
            case INVISIBLE -> open();
            case GONE -> open();
        }
    }
}