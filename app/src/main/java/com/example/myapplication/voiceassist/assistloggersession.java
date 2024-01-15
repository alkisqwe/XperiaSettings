package com.example.myapplication.voiceassist;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class assistloggersession extends VoiceInteractionSession
{
    public boolean flashlight = true;
    public int type = 0;
    public assistloggersession(Context context)
    {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content)
    {
        super.onHandleAssist(data,structure, content);
        try
        {
            String packagetemp = data.getString("android.intent.extra.ASSIST_PACKAGE");
            if(type == 5)
            {
                homerconfig(packagetemp);
            }
            else if(type == 6)
            {
                powerconfig(packagetemp);
            }
            else
            {
                anyconfig(packagetemp);
            }
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void powerconfig(String packagee)
    {
        SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("my", Context.MODE_WORLD_READABLE);
        String w = pref.getString("power","");
        if(w.startsWith("openapp"))
        {
            customapp(w.substring(8,w.length()));
        }
        else if (w.startsWith("flashlight"))
        {
            flashlight();
        }
        else if (w.startsWith("killforeground"))
        {
            killforeground(packagee);
        }
        else if (w.startsWith("settings"))
        {
            expandsettings();
        }
        else if (w.startsWith("notifications"))
        {
            expandnotification();
        }
        else if (w.startsWith("sleep"))
        {
            sleep();
        }
        else if (w.startsWith("rotation"))
        {
            rotation();
        }
        else if (w.startsWith("recentapps"))
        {
            recents();
        }
        else if (w.startsWith("menu"))
        {
            menu();
        }
        else if (w.startsWith("home"))
        {
            home();
        }
        else if (w.startsWith("back"))
        {
            back();
        }
        else if (w.startsWith("customcommand"))
        {
            customcommand(w.substring(14,w.length()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void homerconfig(String packagee)
    {
        SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("my", Context.MODE_WORLD_READABLE);
        String w = pref.getString("home","");
        if(w.startsWith("openapp"))
        {
            customapp(w.substring(8,w.length()));
        }
        else if (w.startsWith("flashlight"))
        {
            flashlight();
        }
        else if (w.startsWith("killforeground"))
        {
            killforeground(packagee);
        }
        else if (w.startsWith("settings"))
        {
            expandsettings();
        }
        else if (w.startsWith("notifications"))
        {
            expandnotification();
        }
        else if (w.startsWith("sleep"))
        {
            sleep();
        }
        else if (w.startsWith("rotation"))
        {
            rotation();
        }
        else if (w.startsWith("recentapps"))
        {
            recents();
        }
        else if (w.startsWith("menu"))
        {
            menu();
        }
        else if (w.startsWith("home"))
        {
            home();
        }
        else if (w.startsWith("back"))
        {
            back();
        }
        else if (w.startsWith("customcommand"))
        {
            customcommand(w.substring(14,w.length()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void anyconfig(String packagee)
    {
        SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("my", Context.MODE_WORLD_READABLE);
        String w = pref.getString("any","");
        if(w.startsWith("openapp"))
        {
            customapp(w.substring(8,w.length()));
        }
        else if (w.startsWith("flashlight"))
        {
            flashlight();
        }
        else if (w.startsWith("killforeground"))
        {
            killforeground(packagee);
        }
        else if (w.startsWith("settings"))
        {
            expandsettings();
        }
        else if (w.startsWith("notifications"))
        {
            expandnotification();
        }
        else if (w.startsWith("sleep"))
        {
            sleep();
        }
        else if (w.startsWith("rotation"))
        {
            rotation();
        }
        else if (w.startsWith("recentapps"))
        {
            recents();
        }
        else if (w.startsWith("menu"))
        {
            menu();
        }
        else if (w.startsWith("home"))
        {
            home();
        }
        else if (w.startsWith("back"))
        {
            back();
        }
        else if (w.startsWith("customcommand"))
        {
            customcommand(w.substring(14,w.length()));
        }
    }

    @Override
    public void onShow(Bundle args, int showFlags)
    {
        super.onShow(args, showFlags);
        type = args.getInt("invocation_type");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void recents()
    {
        try
        {
            Runtime.getRuntime().exec("su -c input keyevent KEYCODE_APP_SWITCH");
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void rotation()
    {
        try
        {
            if (android.provider.Settings.System.getInt(getContext().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1)
            {
                Runtime.getRuntime().exec("su -c wm user-rotation lock");
                Toast.makeText(getContext().getApplicationContext(),"Auto-Rotation Disabled",Toast.LENGTH_SHORT).show();
                hide();
            }
            else
            {
                Runtime.getRuntime().exec("su -c wm user-rotation free");
                Toast.makeText(getContext().getApplicationContext(),"Auto-Rotation Enabled",Toast.LENGTH_SHORT).show();
                hide();
            }
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void killforeground(String packagee)
    {
        try
        {
            Runtime.getRuntime().exec("su -c am force-stop "+packagee.trim());
            Toast.makeText(getContext().getApplicationContext(),"killed",Toast.LENGTH_SHORT).show();
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void customapp(String packagee)
    {
        try
        {
            Intent launchIntent = getContext().getPackageManager().getLaunchIntentForPackage(packagee);
            if (launchIntent != null)
            {
                getContext().startActivity(launchIntent);
            }
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void menu()
    {
        try
        {
            Runtime.getRuntime().exec("su -c input keyevent 82");
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void back()
    {
        try
        {
            Runtime.getRuntime().exec("su -c input keyevent 4");
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void home()
    {
        try
        {
            Runtime.getRuntime().exec("su -c input keyevent 3");
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sleep()
    {
        try
        {
            Runtime.getRuntime().exec("su -c input keyevent POWER");
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void expandsettings()
    {
        try
        {
            Runtime.getRuntime().exec("su -c cmd statusbar expand-settings");
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void customcommand(String command)
    {
        try
        {
            Runtime.getRuntime().exec(command);
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void expandnotification()
    {
        try
        {
            Runtime.getRuntime().exec("su -c cmd statusbar expand-notifications");
            hide();
        }
        catch (Exception e)
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void flashlight()
    {
        CameraManager camManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null;
        try
        {
            cameraId = camManager.getCameraIdList()[0];
            if(flashlight)
            {
                camManager.setTorchMode(cameraId,true);
                flashlight = false;
            }
            else
            {
                camManager.setTorchMode(cameraId,false);
                flashlight = true;
            }
            hide();
        }
        catch (Exception e){}
    }
}
