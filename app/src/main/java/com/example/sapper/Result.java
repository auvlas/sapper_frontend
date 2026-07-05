package com.example.sapper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapper.databinding.GameBinding;
import com.example.sapper.databinding.ResultBinding;

abstract public class Result extends AppCompatActivity {
    ResultBinding m_binding = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);


        m_binding = ResultBinding.inflate(getLayoutInflater());
        setContentView(m_binding.getRoot());

        Intent intent = getIntent();

        long rows = intent.getLongExtra("rows", 0);
        long columns = intent.getLongExtra("columns", 0);
        long mins = intent.getLongExtra("mins", 0);
        long radiusCells = intent.getLongExtra("radius_cells", 0);
        long showSeconds = intent.getLongExtra("show_seconds", 0);

        m_binding.rows.setText(String.valueOf(rows));
        m_binding.columns.setText(String.valueOf(columns));
        m_binding.mins.setText(String.valueOf(mins));
        m_binding.radiusCells.setText(String.valueOf(radiusCells));
        m_binding.result.setTextColor(getColor(R.color.white));

        Button menu = findViewById(R.id.menu_button);
        menu.setOnClickListener(this::onClickMenuButton);

        Button again = findViewById(R.id.again_button);
        again.setOnClickListener(this::onClickAgainButton);
    }

    public void onClickMenuButton(View view) {
        finish();
    }

    public void onClickAgainButton(View view) {
        Intent intent = getIntent();
        intent.setClass(getApplicationContext(), Game.class);

        startActivity(intent);

        finish();
    }
}