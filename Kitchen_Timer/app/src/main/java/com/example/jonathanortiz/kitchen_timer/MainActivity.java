package com.example.jonathanortiz.kitchen_timer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";

    // Counter for the number of seconds.
    private int seconds = 0;

    // Button values
    private int value1 = 0;
    private int value2 = 0;
    private int value3 = 0;

    // TimeStamps
    private int stamp1 = 0;
    private int stamp2 = 0;
    private int stamp3 = 0;

    // Flag
    private int flag = 0; // Does not update button values when buttons are clicked
    private int flag2 = 0; // Does not update button values when the timer is stopped then started again

    //Counter
    private int count = 0; // Keeps track of time throughout program

    // Countdown timer.
    private CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
    }

    public void onClickPlus(View v) {
        seconds += 60;
        flag2 = 1;
        displayTime();
    };

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        flag2 = 1;
        displayTime();
    };

    public void onReset(View v) {
        seconds = 0;
        cancelTimer();
        displayTime();
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        if (timer == null) {
            if(flag2 == 1) { // Makes sure that + or - was pressed before
                count++;
                if (flag == 0) { // Makes sure that time is being updated
                    // Finds minimum
                    int x = Math.min(stamp1, stamp2);
                    int y = Math.min(x, stamp3);
                    // Updates button according to least used
                    if (y == stamp1) {
                        stamp1 = count;
                        value1 = seconds;
                    } else if (y == stamp2) {
                        stamp2 = count;
                        value2 = seconds;
                    } else if (y == stamp3) {
                        stamp3 = count;
                        value3 = seconds;
                    }
                    displayButtons();
                } else { // Updates timestamp if a button is pressed
                    if (flag == 1) {
                        stamp1 = count;
                    } else if (flag == 2) {
                        stamp2 = count;
                    } else if (flag == 3) {
                        stamp3 = count;
                    }
                }
            }
            flag2 = 0;
            // We create a new timer.
            timer = new CountDownTimer(seconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null;
                    displayTime();
                }
            };
            timer.start();
        }
    }

    public void onClickStop(View v) {
        cancelTimer();
        displayTime();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display);
        int m = seconds / 60;
        int s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));
        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }

    // Displays text on buttons
    private void displayButtons(){
        if(value1 != 0){
            Button firstbutt = (Button) findViewById(R.id.first_butt);
            int m = value1 / 60;
            int s = value1 % 60;
            firstbutt.setText(String.format("%d:%02d", m, s));
        }
        if(value2 != 0){
            Button secondbutt = (Button) findViewById(R.id.second_butt);
            int m2 = value2 / 60;
            int s2 = value2 % 60;
            secondbutt.setText(String.format("%d:%02d", m2, s2));
        }
        if(value3 != 0){
            Button thirdbutt = (Button) findViewById(R.id.third_butt);
            int m3 = value3 / 60;
            int s3 = value3 % 60;
            thirdbutt.setText(String.format("%d:%02d", m3, s3));
        }
    }

    // Starts first button
    public void onClickFirst(View v){
        seconds = value1;
        flag = 1;
        flag2 = 1;
        displayTime();
        onClickStart(null);
        flag = 0;
    }

    // Starts second button
    public void onClickSecond(View v){
        seconds = value2;
        flag = 2;
        flag2 = 1;
        displayTime();
        onClickStart(null);
        flag = 0;
    }

    // Starts third button
    public void onClickThird(View v){
        seconds = value3;
        flag = 3;
        flag2 = 1;
        displayTime();
        onClickStart(null);
        flag = 0;
    }

}
