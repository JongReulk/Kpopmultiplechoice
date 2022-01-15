package com.tenriver.kpopmultiplechoice;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.Window;

import androidx.annotation.NonNull;

public class InternetDialog extends Dialog {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;


    public InternetDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_internet);

    }
}
