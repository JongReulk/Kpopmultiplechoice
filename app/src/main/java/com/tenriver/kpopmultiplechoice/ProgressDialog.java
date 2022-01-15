package com.tenriver.kpopmultiplechoice;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

//대화 상자 띄우기 액티비티
public class ProgressDialog extends Dialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
    }
}
