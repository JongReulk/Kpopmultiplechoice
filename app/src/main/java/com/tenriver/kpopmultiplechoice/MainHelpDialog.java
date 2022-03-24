package com.tenriver.kpopmultiplechoice;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

public class MainHelpDialog extends Dialog {

    public MainHelpDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_mainhelp);

    }
}
