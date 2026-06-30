package com.example.sapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sapper.databinding.GameBinding;


public class Game extends AppCompatActivity {
    private GameBinding m_binding = null;
    private long m_countRows;
    private long m_countColumns;
    private long m_countMins;
    private long m_showSeconds;
    private MinsField m_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_binding = GameBinding.inflate(getLayoutInflater());
        setContentView(m_binding.getRoot());

        Intent date = getIntent();

        m_countRows = date.getLongExtra("count_rows", 10);
        m_countColumns = date.getLongExtra("count_columns", 10);
        m_countMins = date.getLongExtra("count_mins",10);
        m_showSeconds = date.getLongExtra("count_show_seconds", 10);

        m_field = new MinsField(m_countRows, m_countColumns, m_countMins);

        m_binding.countRows.setText(getString(R.string.count_rows) + m_countRows);

        m_binding.countColumns.setText(getString(R.string.count_columns) + m_countColumns);

        m_binding.countMins.setText(getString(R.string.count_mins) + m_countMins);

        m_binding.field.setMinsField(m_field);

        openCell();
    }
    public void openCell(View view) {
        openCell();
    }
    public void openCell() {
        m_binding.freeCell.setText(getString(R.string.free_cell) + String.valueOf(m_field.getCountEmptyClose()));
    }

    public void victory(View view) {
        Intent intent = new Intent(getApplicationContext(), Victory.class);

        intent.putExtra("count_rows", m_countRows);
        intent.putExtra("count_columns", m_countColumns);
        intent.putExtra("count_mins", m_countMins);
        intent.putExtra("count_show_second", m_showSeconds);

        startActivity(intent);
        finish();
    }

    public void defeat(View view) {
        Intent intent = new Intent(getApplicationContext(), Defeat.class);

        intent.putExtra("percent_victory", m_field.getPercentVictory());
        intent.putExtra("count_rows", m_countRows);
        intent.putExtra("count_columns", m_countColumns);
        intent.putExtra("count_mins", m_countMins);
        intent.putExtra("count_show_second", m_showSeconds);

        startActivity(intent);
        finish();
    }
}