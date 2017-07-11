package com.example.gigabyte.minesweeper;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    RelativeLayout introMessage;
    public TextView bomb_number;
    public Button new_game_btn;
    MediaPlayer audioBackground;
    ToggleButton myToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToggle = (ToggleButton) findViewById(R.id.toggleSound);
        audioBackground = MediaPlayer.create(this, R.raw.revel);
        audioBackground.setLooping(true);
        audioBackground.setVolume(1,1);
        //Memulai audio

        introMessage = (RelativeLayout) findViewById(R.id.welcome_pesan);
        //bomb_number = (TextView)findViewById(R.id.bomb_remaining);
        new_game_btn = (Button)findViewById(R.id.restart_button);

        GameEngine.getInstance().createGrid(this);

        new_game_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                GameEngine.getInstance().createGrid(com.example.gigabyte.minesweeper.MainActivity.this);
            }
        });
    }
    public void dismisWelcomeMessageBox(View view) {
        introMessage.setVisibility(View.INVISIBLE);
        //appContent.setVisibility(View.VISIBLE);
        audioBackground.start();
    }
    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
			/*Mematikan suara audio*/
            audioBackground.setVolume(0, 0);
        } else {
			/*Menghidupkan kembali audio background*/
            audioBackground.setVolume(1, 1);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        audioBackground.stop();
        MainActivity.this.finish();
    }
}
