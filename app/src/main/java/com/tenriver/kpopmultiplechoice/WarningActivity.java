package com.tenriver.kpopmultiplechoice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WarningActivity extends AppCompatActivity {

    // 텍스트뷰 객체들 생성
    private TextView WarnText1; // 경고문 1
    private TextView WarnText2; // 경고문 2
    private TextView WarnText3; // 경고문 3
    private boolean isPaused;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        //타이틀 텍스트뷰
        WarnText1 = (TextView) findViewById(R.id.warning1);
        WarnText2 = (TextView) findViewById(R.id.warning2);
        WarnText3 = (TextView) findViewById(R.id.warning3);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                if(isPaused){
                    finish();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), TitleActivity.class);
                    startActivity(intent);

                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    finish();
                }
            }
        },5000); // 5초 뒤에 Runner객체 실행하도록 함
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }
}
