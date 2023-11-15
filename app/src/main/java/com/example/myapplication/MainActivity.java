package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    public Switch seconds;
    public Switch swap;
    public Switch screenalwayson;
    public Switch fourk;
    public Switch reversepotrait;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("my", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        seconds = (Switch) findViewById(R.id.secondsswitch);
        swap = (Switch) findViewById(R.id.swapswitch);
        screenalwayson= (Switch) findViewById(R.id.screenalwaysonswitch);
        fourk = (Switch) findViewById(R.id.resolutionswitch);
        reversepotrait = (Switch) findViewById(R.id.reversepotraitswitch);
        try
        {
            String q = Settings.Secure.getString(getContentResolver(),"clock_seconds");
            if(q.startsWith("1"))
            {
                seconds.setChecked(true);
            }
            else if (q.startsWith("0"))
            {
                seconds.setChecked(false);
            }
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
            boolean w = pref.getBoolean("swap",false);
            boolean r = pref.getBoolean("4k",false);
            swap.setChecked(w);
            fourk.setChecked(r);
        }
        catch (Exception e)
        {

        }
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
        swap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    editor.putBoolean("swap", true);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reboot Is Required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editor.putBoolean("swap", false);
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