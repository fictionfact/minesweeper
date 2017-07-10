package com.example.gigabyte.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView bomb_number;
    public Button new_game_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bomb_number = (TextView)findViewById(R.id.bomb_remaining);
        new_game_btn = (Button)findViewById(R.id.restart_button);

        GameEngine.getInstance().createGrid(this);

        new_game_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                GameEngine.getInstance().createGrid(com.example.gigabyte.minesweeper.MainActivity.this);
            }
        });
    }
}
