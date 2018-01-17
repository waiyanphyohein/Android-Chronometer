package com.example.waiya.myapplication;

import android.content.Context;

/**
 * Created by waiya on 1/1/2018.
 */

public class Chronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 360000;

    private Context mContext;
    private long lap = 0;
    private long since;
    private long mStartTime;
    private boolean mIsRunning = false;
    public boolean mIsStop = true;
    public Chronometer(Context mContext) {
        this.mContext = mContext;

    }
    public void start(){
        if(!mIsRunning)
        {
            mStartTime = System.currentTimeMillis();

            mIsRunning = true;
        }
            mIsStop = false;
    }

    public synchronized void resume()
    {
        mStartTime = System.currentTimeMillis();
        mIsStop = false;
    }


    public synchronized void suspend()
    {
        mIsStop= true;

    }


    public synchronized void stop(){

        mIsRunning = false;
        mIsStop = true;
    }



    @Override
    public void run() {


            while (mIsRunning) {
                while(!mIsStop)
                {
                    since = (System.currentTimeMillis() - mStartTime) + lap;

                    int seconds = (int) ((since / 1000) % 60);
                    int minutes = (int) ((since / MILLIS_TO_MINUTES) % 60);
                    int hours = (int) ((since / MILLIS_TO_HOURS) % 24);

                    int millis = (int) since % 1000;
                    ((MainActivity) mContext).updateTimerText
                            (
                                    String.format(
                                            "%02d%02d:%02d:%03d", hours, minutes, seconds, millis
                                    )
                            );

                }
                lap = since;

            }


    }

}
