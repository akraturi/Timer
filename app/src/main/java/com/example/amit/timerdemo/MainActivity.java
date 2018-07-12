package com.example.amit.timerdemo;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView mTimerTextView;
    private Button mGoButton;
    private SeekBar mSeekBar;
    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;
    private CountDownTimer mCountDownTimer;
    private long mCountLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerTextView=findViewById(R.id.timerTextView);


        mGoButton=findViewById(R.id.goButton);
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(mCountDownTimer==null) {
                   createTimer();
                }
                else if(mGoButton.getText().equals("STOP")){
                    mCountDownTimer.cancel();
                    mGoButton.setText("CONT");

                }
                else if(mGoButton.getText().equals("CONT")){
                    mSeekBar.setProgress((int)mCountLeft/1000);
                    createTimer();
                    mGoButton.setText("STOP");
                }
                if(mediaPlayer2!=null&&mediaPlayer2.isPlaying())
                {
                    mediaPlayer2.stop();
                   // mGoButton.setText("RES");
                    mSeekBar.setEnabled(true);
                }
                else{
                   mSeekBar.setEnabled(false);
                }
            }
        });


        mSeekBar=findViewById(R.id.seekBar);
        mSeekBar.setMax(600);
        mSeekBar.setProgress(0);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    public void updateTimer(int i)
    {
        int minutes=i/60;
        int seconds=i-minutes*60;
        String minuteString=Integer.toString(minutes);
        String secondString=Integer.toString(seconds);
        if(seconds<10){
            secondString="0"+secondString;
        }
        if(minutes<10)
        {
            minuteString="0"+minuteString;
        }

        mTimerTextView.setText(minuteString+":"+secondString);
    }
    public void createTimer()
    {
        mCountDownTimer = new CountDownTimer(mSeekBar.getProgress() * 1000 + 100, 1000) {
            @Override
            public void onTick(long l) {
                updateTimer((int) l / 1000);
                mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.tick);
                releaseMediaPlayer();
                //createMediaPlayer(getApplicationContext(),R.raw.tick);
                mediaPlayer1.start();
                mCountLeft=l;
            }


            @Override
            public void onFinish() {
                mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.buzzer);
                //releaseMediaPlayer();
                //createMediaPlayer(getApplicationContext(),R.raw.buzzer);
               // mediaPlayer.seekTo(15000);
                mediaPlayer2.start();
                mSeekBar.setEnabled(true);
            }
        };
        mCountDownTimer.start();
        mGoButton.setText("STOP");
    }
    /*public void createMediaPlayer(Context context, int resId)
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            }catch(IOException ioe)
            {
                Log.e("Exception:",ioe.toString());
            }

        }
        mediaPlayer=MediaPlayer.create(context,resId);

    }*/
    public void releaseMediaPlayer()
    {
        mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }


}
