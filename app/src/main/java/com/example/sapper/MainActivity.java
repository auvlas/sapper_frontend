package com.example.sapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sapper.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding m_binding = null;

    // Used to load the 'sapper' library on application startup.
    static {
        System.loadLibrary("sapper");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(m_binding.getRoot());

        setContentView(R.layout.activity_main);

        Button startGame = findViewById(R.id.start_game_button);

        startGame.setOnClickListener(this::onClickStartGame);
    }

    public void onClickStartGame(View view) {
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("rows", (long) 1);
        intent.putExtra("columns", (long) 1);
        intent.putExtra("mins", (long) 1);
        intent.putExtra("radius_", (long) 1);
        intent.putExtra("show_seconds", (long) 1);

        startActivity(intent);
    }
}