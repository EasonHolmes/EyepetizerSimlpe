package com.cui.video.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuiyang on 16/9/8.
 * <p>
 * textView = (FlipTextView) findViewById(R.id.fliptxt);
 * textView.setFlipText("a;lsj",5000);
 */

public class FlipTextView extends TextView {
    private String content;
    private int index;
    private Timer timer;
    private TimerTasks timerTasks;
    private final int DELAYEDTIME = 260;
    private Handler hand = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            setText(content.substring(0, index));
            return false;
        }
    });

    public FlipTextView(Context context) {
        super(context);
        initTimer();
    }

    public FlipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTimer();
    }

    public FlipTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTimer();
    }

    private void initTimer() {
        timer = new Timer();
        timerTasks = new TimerTasks();
    }

    public void setFlipText(String txt, int millisecond) {
        this.content = txt;
        timer.schedule(timerTasks, DELAYEDTIME, millisecond / content.length());
        timerTasks = new TimerTasks();
    }

    public void cancle() {
        if (timerTasks != null) {
            timerTasks.cancel();
            timerTasks = null;
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

    }


    private class TimerTasks extends TimerTask {
        @Override
        public void run() {
            if (index < content.length()) {
                hand.sendEmptyMessage(0);
                index++;
            } else {
                if (timer != null)
                    timer.cancel();
            }
        }
    }
}
