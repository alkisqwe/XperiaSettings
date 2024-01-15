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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class display extends AppCompatActivity
{
    public Switch screenalwayson;
    public Switch fourk;
    public Switch reversepotrait;
    public Switch allway;
    public LinearLayout screenalwaysonlayout;
    public LinearLayout fourklayout;
    public LinearLayout reversepotraitlayout;
    public LinearLayout allwaylayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("my", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        screenalwayson = (Switch) findViewById(R.id.screenalwaysonswitch);
        fourk = (Switch) findViewById(R.id.resolutionswitch);
        reversepotrait = (Switch) findViewById(R.id.reversepotraitswitch);
        allway = (Switch) findViewById(R.id.allway);
        screenalwaysonlayout = (LinearLayout) findViewById(R.id.screenalwaysonswitchlayout);
        fourklayout = (LinearLayout) findViewById(R.id.resolutionswitchlayout);
        reversepotraitlayout = (LinearLayout) findViewById(R.id.reversepotraitswitchlayout);
        allwaylayout = (LinearLayout) findViewById(R.id.allwaylayout);
        screenalwaysonlayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    screenalwayson.setPressed(true);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    screenalwayson.setPressed(false);
                }
                return false;
            }
        });
        fourklayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    fourk.setPressed(true);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    fourk.setPressed(false);
                }
                return false;
            }
        });
        reversepotraitlayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    reversepotrait.setPressed(true);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    reversepotrait.setPressed(false);
                }
                return false;
            }
        });
        allwaylayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    allway.setPressed(true);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    allway.setPressed(false);
                }
                return false;
            }
        });
        try
        {
            String e = Settings.System.getString(getContentResolver(),"screen_off_timeout");
            if(e.startsWith("600000000"))
            {
                screenalwayson.setChecked(true);
            }
            else
            {
                screenalwayson.setChecked(false);
            }
            String t = Settings.System.getString(getContentResolver(),"user_rotation");
            if(t.startsWith("2"))
            {
                reversepotrait.setChecked(true);
            }
            else
            {
                reversepotrait.setChecked(false);
            }
            boolean r = pref.getBoolean("4k",false);
            boolean y = pref.getBoolean("allway",false);
            fourk.setChecked(r);
            allway.setChecked(y);
        }
        catch (Exception e)
        {

        }
        screenalwaysonlayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(screenalwayson.isChecked())
                {
                    editor.putBoolean("always", false);
                    editor.commit();
                    runasroot("settings put system screen_off_timeout 60000");
                    Toast.makeText(getApplicationContext(),"Screen Timeout Set To 1 Minute",Toast.LENGTH_SHORT).show();
                    screenalwayson.setChecked(false);
                }
                else
                {
                    editor.putBoolean("always", true);
                    editor.commit();
                    runasroot("settings put system screen_off_timeout 600000000");
                    screenalwayson.setChecked(true);
                }
            }
        });
        fourklayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(fourk.isChecked())
                {
                    editor.putBoolean("4k", false);
                    editor.commit();
                    runasroot("wm size 1080x2520 && su -c wm density 450");
                    fourk.setChecked(false);
                }
                else
                {
                    editor.putBoolean("4k", true);
                    editor.commit();
                    runasroot("wm size 1644x3840 && su -c wm density 630");
                    fourk.setChecked(true);
                }
            }
        });
        reversepotraitlayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(reversepotrait.isChecked())
                {
                    editor.putBoolean("user_rotation", false);
                    editor.commit();
                    runasroot("settings put system user_rotation 0");
                    reversepotrait.setChecked(false);
                }
                else
                {
                    editor.putBoolean("user_rotation", true);
                    editor.commit();
                    runasroot("settings put system user_rotation 2");
                    Toast.makeText(getApplicationContext(),"Auto Rotation Will Disable This Setting",Toast.LENGTH_SHORT).show();
                    reversepotrait.setChecked(true);
                }
            }
        });
        allwaylayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(allway.isChecked())
                {
                    editor.putBoolean("allway", false);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reboot Is Required",Toast.LENGTH_SHORT).show();
                    allway.setChecked(false);
                }
                else
                {
                    editor.putBoolean("allway", true);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reboot Is Required",Toast.LENGTH_SHORT).show();
                    allway.setChecked(true);
                }
            }
        });
        allway.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if(isChecked)
                {
                    editor.putBoolean("allway", true);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reboot Is Required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editor.putBoolean("allway", false);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reboot Is Required",Toast.LENGTH_SHORT).show();
                }
            }
        });
        screenalwayson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if(isChecked)
                {
                    editor.putBoolean("always", true);
                    editor.commit();
                    runasroot("settings put system screen_off_timeout 600000000");
                }
                else
                {
                    editor.putBoolean("always", false);
                    editor.commit();
                    runasroot("settings put system screen_off_timeout 60000");
                    Toast.makeText(getApplicationContext(),"Screen Timeout Set To 1 Minute",Toast.LENGTH_SHORT).show();
                }
            }
        });
        fourk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if(isChecked)
                {
                    editor.putBoolean("4k", true);
                    editor.commit();
                    runasroot("wm size 1644x3840 && su -c wm density 630");
                }
                else
                {
                    editor.putBoolean("4k", false);
                    editor.commit();
                    runasroot("wm size 1080x2520 && su -c wm density 450");
                }
            }
        });
        reversepotrait.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if(isChecked)
                {
                    editor.putBoolean("user_rotation", true);
                    editor.commit();
                    runasroot("settings put system user_rotation 2");
                    Toast.makeText(getApplicationContext(),"Auto Rotation Will Disable This Setting",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editor.putBoolean("user_rotation", false);
                    editor.commit();
                    runasroot("settings put system user_rotation 0");
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
