package com.example.myapplication.voiceassist;

import android.os.Build;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.service.voice.VoiceInteractionSessionService;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class assistloggersessionservice extends VoiceInteractionSessionService
{
    @Override
    public VoiceInteractionSession onNewSession(Bundle args)
    {
        return(new assistloggersession(this));
    }
}
