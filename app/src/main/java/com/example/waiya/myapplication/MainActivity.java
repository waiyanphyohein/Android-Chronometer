package com.example.waiya.myapplication; /**
 * Created by waiya on 1/1/2018.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{

    private Button mBtnLap;
    private Button mBtnStop;
    private Button mBtnReset;
    private Button mBtnStart;

    private ScrollView mSvLaps;
    private TextView mTvTime;
    private TextView mEtLaps;



    private int CounterLaps = 1;
    private Context mContext;
    private Chronometer mChronometer;
    private Thread mThreadChrono;

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState)
    {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BUTTON ID INITIALIZER
        mBtnStart= (Button) findViewById(R.id.Start);
        mBtnStop = (Button) findViewById(R.id.Stop);
        mBtnLap  = (Button) findViewById(R.id.Lap);
        mBtnReset= (Button) findViewById(R.id.Reset);

        mTvTime = (TextView) findViewById(R.id.tv_time);
        mEtLaps = (TextView) findViewById(R.id.Edit_Laps);

        mSvLaps = (ScrollView) findViewById(R.id.Scroll_Lap);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                try
//                {

                    if(mChronometer == null){
                        mChronometer = new Chronometer(mContext);
                        mThreadChrono = new Thread(mChronometer);
                        mThreadChrono.start();
                        mChronometer.start();


                        mEtLaps.setText("");
                    }
                    else if (mChronometer != null && mChronometer.mIsStop){
                        mChronometer.resume();

                    }
//                  Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                    //Button has been pressed.
                    //mTvTime.setText("Button Start!");

            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mChronometer != null)
                {
                    mChronometer.suspend();
                }
            }
        });

        mBtnLap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(mChronometer == null)
                {
                    return; //Do Nothing
                }
                mEtLaps.append("LAP "+ String.valueOf(CounterLaps++) +" : "+ mTvTime.getText() + "\n");
                mSvLaps.post(new Runnable() {
                    @Override
                    public void run() {
                        mSvLaps.smoothScrollTo(8,mEtLaps.getBottom());
                    }
                });
            }


        });

        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(mChronometer != null)
                {
                    mChronometer.stop();
                    mChronometer = null;
                    mThreadChrono.interrupt();
                    mThreadChrono = null;

                    CounterLaps = 1;

                    mTvTime.setText("0000:00:000");
                    mEtLaps.setText("");
                }

            }
        });
    }
    public void updateTimerText(final String time){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                if(mChronometer != null)
                    mTvTime.setText(time);
                else
                    mTvTime.setText("0000:00:000");
            }
        });
    }

}
