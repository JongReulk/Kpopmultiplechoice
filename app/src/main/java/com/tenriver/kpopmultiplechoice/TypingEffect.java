package com.tenriver.kpopmultiplechoice;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class TypingEffect extends androidx.appcompat.widget.AppCompatTextView {

    private CharSequence myText;
    private int myIndex;
    private long myDelay = 150;

    public TypingEffect(Context context) {
        super(context);
    }

    public TypingEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler myHandler = new Handler();

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(myText.subSequence(0,myIndex++));
            if(myIndex<=myText.length()){
                myHandler.postDelayed(characterAdder, myDelay);
            }
        }
    };

    public void animateText(CharSequence myTxt){
        myText = myTxt;
        myIndex = 0;
        setText("");

        myHandler.removeCallbacks(characterAdder);

        myHandler.postDelayed(characterAdder,myDelay);
    }

    public void setCharacterDelay(long m){
        myDelay = m;
    }

}
