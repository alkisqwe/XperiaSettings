package com.example.myapplication.ui;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class buttons extends AppCompatActivity
{
    public Switch swap;
    public LinearLayout swaplayout;
    public LinearLayout assisthome;
    public LinearLayout assistpower;
    public LinearLayout assistany;
    public TextView assisthometext;
    public TextView assistpowertext;
    public TextView assistanytext;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttons);
        pref = getApplicationContext().getSharedPreferences("my", Context.MODE_WORLD_READABLE);
        editor = pref.edit();
        swap = (Switch) findViewById(R.id.swapswitch);
        swaplayout = (LinearLayout) findViewById(R.id.swapswitchlayout);
        assisthome = (LinearLayout) findViewById(R.id.assistswitchhome);
        assistpower = (LinearLayout) findViewById(R.id.assistswitchpower);
        assisthometext = (TextView) findViewById(R.id.assistswitchhometext);
        assistpowertext = (TextView) findViewById(R.id.assistswitchpowertext);
        assistany = (LinearLayout) findViewById(R.id.assistswitchany);
        assistanytext = (TextView) findViewById(R.id.assistswitchanytext);
        configdialog();
        configdialog2();
        configdialog3();
        swaplayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    swap.setPressed(true);
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    swap.setPressed(false);
                }
                return false;
            }
        });
        try
        {
            boolean w = pref.getBoolean("swap",false);
            swap.setChecked(w);
        }
        catch (Exception e)
        {

        }
        swaplayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(swap.isChecked())
                {
                    editor.putBoolean("swap", false);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reboot Is Required",Toast.LENGTH_SHORT).show();
                    swap.setChecked(false);
                }
                else
                {
                    editor.putBoolean("swap", true);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reboot Is Required",Toast.LENGTH_SHORT).show();
                    swap.setChecked(true);
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
    }

    public void configdialog()
    {
        final Dialog dialog = new Dialog(buttons.this);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialog.setContentView(R.layout.popup);
        final Dialog dialogref1 = new Dialog(buttons.this);
        dialogref1.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialogref1.setContentView(R.layout.popupref1);
        final EditText packagetext = (EditText) dialogref1.findViewById(R.id.packagetext);
        final Button okbutton2 = (Button) dialogref1.findViewById(R.id.okbutton2);
        okbutton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "openapp="+packagetext.getText().toString());
                editor.commit();
                assisthometext.setText(packagetext.getText().toString());
                dialogref1.hide();
                dialog.hide();
            }
        });
        final Button calcelbutton2 = (Button) dialogref1.findViewById(R.id.cancelbutton2);
        calcelbutton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialogref1.cancel();
            }
        });
        final Dialog dialogref2 = new Dialog(buttons.this);
        dialogref2.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialogref2.setContentView(R.layout.popupref2);
        final EditText customcommandtext = (EditText) dialogref2.findViewById(R.id.customcommandtext);
        final Button okbutton = (Button) dialogref2.findViewById(R.id.okbutton);
        okbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "customcommand="+customcommandtext.getText().toString());
                editor.commit();
                assisthometext.setText(customcommandtext.getText().toString());
                dialogref2.hide();
                dialog.hide();
            }
        });
        final Button calcelbutton = (Button) dialogref2.findViewById(R.id.cancelbutton);
        calcelbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialogref2.cancel();
            }
        });
        assisthome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Settings.Secure.getString(getContentResolver(),"voice_interaction_service").startsWith("com.example.xperiasettings"))
                {
                    if(runasroot("settings get secure assist_long_press_home_enabled").startsWith("1"))
                    {
                        dialog.show();
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int height = displayMetrics.heightPixels;
                        int width = displayMetrics.widthPixels;
                        float widthfinal = (90.0f/100.0f)*width;
                        float heightfinal = (90.0f/100.0f)*height;
                        dialog.getWindow().setLayout(Math.round(widthfinal),Math.round(heightfinal));
                    }
                    else
                    {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$ButtonNavigationSettingsActivity"));
                        startActivity(intent);
                    }
                }
                else
                {
                    startActivity(new Intent(Settings.ACTION_VOICE_INPUT_SETTINGS));
                }
            }
        });
        LinearLayout openapp = (LinearLayout)dialog.findViewById(R.id.openapp);
        openapp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialogref1.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                float widthfinal = (90.0f/100.0f)*width;
                dialogref1.getWindow().setLayout(Math.round(widthfinal),ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        LinearLayout flashlight = (LinearLayout)dialog.findViewById(R.id.flashlight);
        flashlight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "flashlight");
                editor.commit();
                assisthometext.setText("Toggle Flashlight");
                dialog.hide();
            }
        });
        LinearLayout killforeground = (LinearLayout)dialog.findViewById(R.id.killforeground);
        killforeground.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "killforeground");
                editor.commit();
                assisthometext.setText("Kill Foreground App");
                dialog.hide();
            }
        });
        LinearLayout settings = (LinearLayout)dialog.findViewById(R.id.quicksettings);
        settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "settings");
                editor.commit();
                assisthometext.setText("Show Settings");
                dialog.hide();
            }
        });
        LinearLayout notifications = (LinearLayout)dialog.findViewById(R.id.notifications);
        notifications.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "notifications");
                editor.commit();
                assisthometext.setText("Show Notifications");
                dialog.hide();
            }
        });
        LinearLayout sleep = (LinearLayout)dialog.findViewById(R.id.sleep);
        sleep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "sleep");
                editor.commit();
                assisthometext.setText("Sleep");
                dialog.hide();
            }
        });
        LinearLayout rotation = (LinearLayout)dialog.findViewById(R.id.rotation);
        rotation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "rotation");
                editor.commit();
                assisthometext.setText("Toggle Rotation");
                dialog.hide();
            }
        });
        LinearLayout recentapps = (LinearLayout)dialog.findViewById(R.id.recentapps);
        recentapps.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "recentapps");
                editor.commit();
                assisthometext.setText("Recent Apps");
                dialog.hide();
            }
        });
        LinearLayout menu = (LinearLayout)dialog.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "menu");
                editor.commit();
                assisthometext.setText("Menu");
                dialog.hide();
            }
        });
        LinearLayout home = (LinearLayout)dialog.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "home");
                editor.commit();
                assisthometext.setText("Home");
                dialog.hide();
            }
        });
        LinearLayout back = (LinearLayout)dialog.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("home", "back");
                editor.commit();
                assisthometext.setText("Back");
                dialog.hide();
            }
        });
        LinearLayout customcommand = (LinearLayout)dialog.findViewById(R.id.customcommand);
        customcommand.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialogref2.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                float widthfinal = (90.0f/100.0f)*width;
                dialogref2.getWindow().setLayout(Math.round(widthfinal),ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        String w = pref.getString("home","");
        if(w.startsWith("openapp"))
        {
            assisthometext.setText(w.substring(8,w.length()));
        }
        else if (w.startsWith("flashlight"))
        {
            assisthometext.setText("Toggle Flashlight");
        }
        else if (w.startsWith("killforeground"))
        {
            assisthometext.setText("Kill Foreground App");
        }
        else if (w.startsWith("settings"))
        {
            assisthometext.setText("Show Settings");
        }
        else if (w.startsWith("notifications"))
        {
            assisthometext.setText("Show Notifications");
        }
        else if (w.startsWith("sleep"))
        {
            assisthometext.setText("Sleep");
        }
        else if (w.startsWith("rotation"))
        {
            assisthometext.setText("Toggle Rotation");
        }
        else if (w.startsWith("recentapps"))
        {
            assisthometext.setText("Recent Apps");
        }
        else if (w.startsWith("menu"))
        {
            assisthometext.setText("Menu");
        }
        else if (w.startsWith("home"))
        {
            assisthometext.setText("Home");
        }
        else if (w.startsWith("back"))
        {
            assisthometext.setText("Back");
        }
        else if (w.startsWith("customcommand"))
        {
            assisthometext.setText(w.substring(14,w.length()));
        }
    }

    public void configdialog2()
    {
        final Dialog dialog2 = new Dialog(buttons.this);
        dialog2.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialog2.setContentView(R.layout.popup2);
        final Dialog dialog2ref1 = new Dialog(buttons.this);
        dialog2ref1.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialog2ref1.setContentView(R.layout.popup2ref1);
        final EditText packagetexttwo = (EditText) dialog2ref1.findViewById(R.id.packagetexttwo);
        final Button okbutton2two = (Button) dialog2ref1.findViewById(R.id.okbutton2two);
        okbutton2two.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "openapp="+packagetexttwo.getText().toString());
                editor.commit();
                assistpowertext.setText(packagetexttwo.getText().toString());
                dialog2ref1.hide();
                dialog2.hide();
            }
        });
        final Button calcelbutton2two = (Button) dialog2ref1.findViewById(R.id.cancelbutton2two);
        calcelbutton2two.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog2ref1.cancel();
            }
        });
        final Dialog dialog2ref2 = new Dialog(buttons.this);
        dialog2ref2.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialog2ref2.setContentView(R.layout.popup2ref2);
        final EditText customcommandtexttwo = (EditText) dialog2ref2.findViewById(R.id.customcommandtexttwo);
        final Button okbuttontwo = (Button) dialog2ref2.findViewById(R.id.okbuttontwo);
        okbuttontwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "customcommand="+customcommandtexttwo.getText().toString());
                editor.commit();
                assistpowertext.setText(customcommandtexttwo.getText().toString());
                dialog2ref2.hide();
                dialog2.hide();
            }
        });
        final Button calcelbuttontwo = (Button) dialog2ref2.findViewById(R.id.cancelbuttontwo);
        calcelbuttontwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog2ref2.cancel();
            }
        });
        assistpower.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Settings.Secure.getString(getContentResolver(),"voice_interaction_service").startsWith("com.example.xperiasettings"))
                {
                    if(Settings.Global.getString(getContentResolver(),"power_button_long_press").startsWith("5"))
                    {
                        dialog2.show();
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int height = displayMetrics.heightPixels;
                        int width = displayMetrics.widthPixels;
                        float widthfinal = (90.0f/100.0f)*width;
                        float heightfinal = (90.0f/100.0f)*height;
                        dialog2.getWindow().setLayout(Math.round(widthfinal),Math.round(heightfinal));
                    }
                    else
                    {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$PowerMenuSettingsActivity"));
                        startActivity(intent);
                    }
                }
                else
                {
                    startActivity(new Intent(Settings.ACTION_VOICE_INPUT_SETTINGS));
                }
            }
        });
        LinearLayout openapptwo = (LinearLayout)dialog2.findViewById(R.id.openapptwo);
        openapptwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog2ref1.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                float widthfinal = (90.0f/100.0f)*width;
                dialog2ref1.getWindow().setLayout(Math.round(widthfinal),ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        LinearLayout flashlighttwo = (LinearLayout)dialog2.findViewById(R.id.flashlighttwo);
        flashlighttwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "flashlight");
                editor.commit();
                assistpowertext.setText("Toggle Flashlight");
                dialog2.hide();
            }
        });
        LinearLayout killforegroundtwo = (LinearLayout)dialog2.findViewById(R.id.killforegroundtwo);
        killforegroundtwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "killforeground");
                editor.commit();
                assistpowertext.setText("Kill Foreground App");
                dialog2.hide();
            }
        });
        LinearLayout settingstwo = (LinearLayout)dialog2.findViewById(R.id.quicksettingstwo);
        settingstwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "settings");
                editor.commit();
                assistpowertext.setText("Show Settings");
                dialog2.hide();
            }
        });
        LinearLayout notificationstwo = (LinearLayout)dialog2.findViewById(R.id.notificationstwo);
        notificationstwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "notifications");
                editor.commit();
                assistpowertext.setText("Show Notifications");
                dialog2.hide();
            }
        });
        LinearLayout sleeptwo = (LinearLayout)dialog2.findViewById(R.id.sleeptwo);
        sleeptwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "sleep");
                editor.commit();
                assistpowertext.setText("Sleep");
                dialog2.hide();
            }
        });
        LinearLayout rotationtwo = (LinearLayout)dialog2.findViewById(R.id.rotationtwo);
        rotationtwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "rotation");
                editor.commit();
                assistpowertext.setText("Toggle Rotation");
                dialog2.hide();
            }
        });
        LinearLayout recentappstwo = (LinearLayout)dialog2.findViewById(R.id.recentappstwo);
        recentappstwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "recentapps");
                editor.commit();
                assistpowertext.setText("Recent Apps");
                dialog2.hide();
            }
        });
        LinearLayout menutwo = (LinearLayout)dialog2.findViewById(R.id.menutwo);
        menutwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "menu");
                editor.commit();
                assistpowertext.setText("Menu");
                dialog2.hide();
            }
        });
        LinearLayout hometwo = (LinearLayout)dialog2.findViewById(R.id.hometwo);
        hometwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "home");
                editor.commit();
                assistpowertext.setText("Home");
                dialog2.hide();
            }
        });
        LinearLayout backtwo = (LinearLayout)dialog2.findViewById(R.id.backtwo);
        backtwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("power", "back");
                editor.commit();
                assistpowertext.setText("Back");
                dialog2.hide();
            }
        });
        LinearLayout customcommandtwo = (LinearLayout)dialog2.findViewById(R.id.customcommandtwo);
        customcommandtwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog2ref2.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                float widthfinal = (90.0f/100.0f)*width;
                dialog2ref2.getWindow().setLayout(Math.round(widthfinal),ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        String w = pref.getString("power","");
        if(w.startsWith("openapp"))
        {
            assistpowertext.setText(w.substring(8,w.length()));
        }
        else if (w.startsWith("flashlight"))
        {
            assistpowertext.setText("Toggle Flashlight");
        }
        else if (w.startsWith("killforeground"))
        {
            assistpowertext.setText("Kill Foreground App");
        }
        else if (w.startsWith("settings"))
        {
            assistpowertext.setText("Show Settings");
        }
        else if (w.startsWith("notifications"))
        {
            assistpowertext.setText("Show Notifications");
        }
        else if (w.startsWith("sleep"))
        {
            assistpowertext.setText("Sleep");
        }
        else if (w.startsWith("rotation"))
        {
            assistpowertext.setText("Toggle Rotation");
        }
        else if (w.startsWith("recentapps"))
        {
            assistpowertext.setText("Recent Apps");
        }
        else if (w.startsWith("menu"))
        {
            assistpowertext.setText("Menu");
        }
        else if (w.startsWith("home"))
        {
            assistpowertext.setText("Home");
        }
        else if (w.startsWith("back"))
        {
            assistpowertext.setText("Back");
        }
        else if (w.startsWith("customcommand"))
        {
            assistpowertext.setText(w.substring(14,w.length()));
        }
    }


    public void configdialog3()
    {
        final Dialog dialog3 = new Dialog(buttons.this);
        dialog3.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialog3.setContentView(R.layout.popup3);
        final Dialog dialog3ref1 = new Dialog(buttons.this);
        dialog3ref1.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialog3ref1.setContentView(R.layout.popup3ref1);
        final EditText packagetextthree = (EditText) dialog3ref1.findViewById(R.id.packagetextthree);
        final Button okbutton2three = (Button) dialog3ref1.findViewById(R.id.okbutton2three);
        okbutton2three.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "openapp="+packagetextthree.getText().toString());
                editor.commit();
                assistanytext.setText(packagetextthree.getText().toString());
                dialog3ref1.hide();
                dialog3.hide();
            }
        });
        final Button calcelbutton2three = (Button) dialog3ref1.findViewById(R.id.cancelbutton2three);
        calcelbutton2three.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog3ref1.cancel();
            }
        });
        final Dialog dialog3ref2 = new Dialog(buttons.this);
        dialog3ref2.getWindow().setBackgroundDrawableResource(R.drawable.dialo);
        dialog3ref2.setContentView(R.layout.popup3ref2);
        final EditText customcommandtextthree = (EditText) dialog3ref2.findViewById(R.id.customcommandtextthree);
        final Button okbuttonthree = (Button) dialog3ref2.findViewById(R.id.okbuttonthree);
        okbuttonthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "customcommand="+customcommandtextthree.getText().toString());
                editor.commit();
                assistanytext.setText(customcommandtextthree.getText().toString());
                dialog3ref2.hide();
                dialog3.hide();
            }
        });
        final Button calcelbuttonthree = (Button) dialog3ref2.findViewById(R.id.cancelbuttonthree);
        calcelbuttonthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog3ref2.cancel();
            }
        });
        assistany.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Settings.Secure.getString(getContentResolver(),"voice_interaction_service").startsWith("com.example.xperiasettings"))
                {
                    dialog3.show();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    int width = displayMetrics.widthPixels;
                    float widthfinal = (90.0f/100.0f)*width;
                    float heightfinal = (90.0f/100.0f)*height;
                    dialog3.getWindow().setLayout(Math.round(widthfinal),Math.round(heightfinal));
                }
                else
                {
                    startActivity(new Intent(Settings.ACTION_VOICE_INPUT_SETTINGS));
                }
            }
        });
        LinearLayout openappthree = (LinearLayout)dialog3.findViewById(R.id.openappthree);
        openappthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog3ref1.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                float widthfinal = (90.0f/100.0f)*width;
                dialog3ref1.getWindow().setLayout(Math.round(widthfinal),ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        LinearLayout flashlightthree = (LinearLayout)dialog3.findViewById(R.id.flashlightthree);
        flashlightthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "flashlight");
                editor.commit();
                assistanytext.setText("Toggle Flashlight");
                dialog3.hide();
            }
        });
        LinearLayout killforegroundthree = (LinearLayout)dialog3.findViewById(R.id.killforegroundthree);
        killforegroundthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "killforeground");
                editor.commit();
                assistanytext.setText("Kill Foreground App");
                dialog3.hide();
            }
        });
        LinearLayout settingsthree = (LinearLayout)dialog3.findViewById(R.id.quicksettingsthree);
        settingsthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "settings");
                editor.commit();
                assistanytext.setText("Show Settings");
                dialog3.hide();
            }
        });
        LinearLayout notificationsthree = (LinearLayout)dialog3.findViewById(R.id.notificationsthree);
        notificationsthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "notifications");
                editor.commit();
                assistanytext.setText("Show Notifications");
                dialog3.hide();
            }
        });
        LinearLayout sleepthree = (LinearLayout)dialog3.findViewById(R.id.sleepthree);
        sleepthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "sleep");
                editor.commit();
                assistanytext.setText("Sleep");
                dialog3.hide();
            }
        });
        LinearLayout rotationthree = (LinearLayout)dialog3.findViewById(R.id.rotationthree);
        rotationthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "rotation");
                editor.commit();
                assistanytext.setText("Toggle Rotation");
                dialog3.hide();
            }
        });
        LinearLayout recentappsthree = (LinearLayout)dialog3.findViewById(R.id.recentappsthree);
        recentappsthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "recentapps");
                editor.commit();
                assistanytext.setText("Recent Apps");
                dialog3.hide();
            }
        });
        LinearLayout menuthree = (LinearLayout)dialog3.findViewById(R.id.menuthree);
        menuthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "menu");
                editor.commit();
                assistanytext.setText("Menu");
                dialog3.hide();
            }
        });
        LinearLayout homethree = (LinearLayout)dialog3.findViewById(R.id.homethree);
        homethree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "home");
                editor.commit();
                assistanytext.setText("Home");
                dialog3.hide();
            }
        });
        LinearLayout backthree = (LinearLayout)dialog3.findViewById(R.id.backthree);
        backthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("any", "back");
                editor.commit();
                assistanytext.setText("Back");
                dialog3.hide();
            }
        });
        LinearLayout customcommandthree = (LinearLayout)dialog3.findViewById(R.id.customcommandthree);
        customcommandthree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog3ref2.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                float widthfinal = (90.0f/100.0f)*width;
                dialog3ref2.getWindow().setLayout(Math.round(widthfinal),ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        String w = pref.getString("any","");
        if(w.startsWith("openapp"))
        {
            assistanytext.setText(w.substring(8,w.length()));
        }
        else if (w.startsWith("flashlight"))
        {
            assistanytext.setText("Toggle Flashlight");
        }
        else if (w.startsWith("killforeground"))
        {
            assistanytext.setText("Kill Foreground App");
        }
        else if (w.startsWith("settings"))
        {
            assistanytext.setText("Show Settings");
        }
        else if (w.startsWith("notifications"))
        {
            assistanytext.setText("Show Notifications");
        }
        else if (w.startsWith("sleep"))
        {
            assistanytext.setText("Sleep");
        }
        else if (w.startsWith("rotation"))
        {
            assistanytext.setText("Toggle Rotation");
        }
        else if (w.startsWith("recentapps"))
        {
            assistanytext.setText("Recent Apps");
        }
        else if (w.startsWith("menu"))
        {
            assistanytext.setText("Menu");
        }
        else if (w.startsWith("home"))
        {
            assistanytext.setText("Home");
        }
        else if (w.startsWith("back"))
        {
            assistanytext.setText("Back");
        }
        else if (w.startsWith("customcommand"))
        {
            assistanytext.setText(w.substring(14,w.length()));
        }
    }

    public String runasroot(String cmds)
    {
        try
        {
            Process p=Runtime.getRuntime().exec("su -c "+cmds);
            BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
            String o = "";
            String line;
            while((line=b.readLine())!=null)
            {
                o+=line;
            }
            return o;
        }
        catch (Exception e)
        {
            return "0";
        }
    }
}
