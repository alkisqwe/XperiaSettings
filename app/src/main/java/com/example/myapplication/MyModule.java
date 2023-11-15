package com.example.myapplication;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MyModule implements IXposedHookInitPackageResources, IXposedHookLoadPackage
{
    public String HOOK_PACKAGE = "com.android.systemui";
    public boolean swapbuttons = false;

    private static XSharedPreferences getPref(String path)
    {
        XSharedPreferences pref = new XSharedPreferences(BuildConfig.APPLICATION_ID, path);
        return pref.getFile().canRead() ? pref : null;
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable
    {
        if (resparam.packageName.equals("com.android.systemui"))
        {
            XSharedPreferences pref;
            pref = getPref("my");
            if (pref != null)
            {
                swapbuttons = pref.getBoolean("swap",false);
            }
            if(swapbuttons)
            {
                String navBarLayout = "left[.25W],recent[0.5WC];home;back[0.5WC],right[.25W]";
                navBarLayout = navBarLayout.replaceAll("\\s", "");
                if (!navBarLayout.equals(""))
                {
                    resparam.res.setReplacement(
                            "com.android.systemui",
                            "string",
                            "config_navBarLayout",
                            navBarLayout);
                }
            }
        }
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable
    {

    }
}
