package com.example.sapper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sapper.databinding.GameBinding;


public class Game extends AppCompatActivity {
    private GameBinding m_binding = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_binding = GameBinding.inflate(getLayoutInflater());
        setContentView(m_binding.getRoot());

        Intent intent = getIntent();

        long rows = intent.getLongExtra("rows", 10);
        long columns = intent.getLongExtra("columns", 10);
        long mins = intent.getLongExtra("mins",10);
        long radiusCells = intent.getLongExtra("radius_cells", 1);
        long showSeconds = intent.getLongExtra("show_seconds", 0);

        MinsField m_field = new MinsField(rows, columns, mins, radiusCells);

        m_binding.rows.setText(getString(R.string.height) + rows);

        m_binding.columns.setText(getString(R.string.width) + columns);

        m_binding.mins.setText(getString(R.string.mins) + mins);

        m_binding.xRay.setText(R.string.x_ray);

        m_binding.radar.setText(R.string.radar);

        m_binding.field.setMap(m_field);
    }

    protected void endGame(Class<?> cls) {
        Intent intent = getIntent();

        intent.setClass(getApplicationContext(), cls);

        startActivity(intent);
        finish();
    }

    public void victory(View view) {
        endGame(Victory.class);
    }

    public void defeat(View view) {
        endGame(Defeat.class);
    }
}