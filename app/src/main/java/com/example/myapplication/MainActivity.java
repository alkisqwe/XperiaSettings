package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.ui.statusbar;

public class MainActivity extends AppCompatActivity
{
    public LinearLayout statusbar;
    public LinearLayout display;
    public LinearLayout buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusbar = (LinearLayout) findViewById(R.id.statusbar);
        display = (LinearLayout) findViewById(R.id.display);
        buttons = (LinearLayout) findViewById(R.id.buttons);
        statusbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(MainActivity.this, com.example.myapplication.ui.statusbar.class);
                startActivity(myIntent);
            }
        });
        display.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(MainActivity.this, com.example.myapplication.ui.display.class);
                startActivity(myIntent);
            }
        });
        buttons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(MainActivity.this, com.example.myapplication.ui.buttons.class);
                startActivity(myIntent);
            }
        });
    }
}