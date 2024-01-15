package com.example.myapplication.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class statusbar extends AppCompatActivity
{
    public Switch seconds;
    public LinearLayout secondslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusbar);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("my", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        seconds = (Switch) findViewById(R.id.secondsswitch);
        secondslayout = (LinearLayout) findViewById(R.id.secondsclicklayout);
        secondslayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    seconds.setPressed(true);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    seconds.setPressed(false);
                }
                return false;
            }
        });
        try
        {
            String q = Settings.Secure.getString(getContentResolver(), "clock_seconds");
            if (q.startsWith("1"))
            {
                seconds.setChecked(true);
            }
            else if (q.startsWith("0"))
            {
                seconds.setChecked(false);
            }
        }
        catch (Exception e)
        {

        }
        secondslayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(seconds.isChecked())
                {
                    editor.putBoolean("seconds", false);
                    editor.commit();
                    runasroot("settings put secure clock_seconds 0");
                    seconds.setChecked(false);
                }
                else
                {
                    editor.putBoolean("seconds", true);
                    editor.commit();
                    runasroot("settings put secure clock_seconds 1");
                    seconds.setChecked(true);
                }
            }
        });
        seconds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    editor.putBoolean("seconds", true);
                    editor.commit();
                    runasroot("settings put secure clock_seconds 1");
                }
                else
                {
                    editor.putBoolean("seconds", false);
                    editor.commit();
                    runasroot("settings put secure clock_seconds 0");
                }
            }
        });
    }

    public void runasroot(String cmds)
    {
        try
        {
            Process p = Runtime.getRuntime().exec("su -c " + cmds);
        }
        catch (Exception e)
        {

        }
    }
}
