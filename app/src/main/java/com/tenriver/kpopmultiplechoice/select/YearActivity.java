package com.tenriver.kpopmultiplechoice.select;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.tenriver.kpopmultiplechoice.R;
import com.tenriver.kpopmultiplechoice.quizActivity.QuizAlphabet;
import com.tenriver.kpopmultiplechoice.quizActivity.quiz_beginner;

import java.util.Random;

public class YearActivity extends AppCompatActivity {

    private static final String BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111";

    private ScrollView year_scroll;

    private LinearLayout year_linear;
    private int button_total;
    private int button_num;
    private TextView year_Title;
    private TextView year_mvquiz;
    private TextView year_view;

    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;

    private AdView mAdview;

    private TextView year_select;

    // 모드 선택 변수
    private static final String MODE_SHARED = "modeshared";
    private static final String YEAR_SELECT = "yearselect";

    private static boolean isClicked;

    private float soundPoolVolume;
    SoundPool soundPool;	//작성
    int soundID;		    //작성

    // 연도별 버튼들
    private Button year_2016;
    private Button year_2017;
    private Button year_2018;
    private Button year_2019;
    private Button year_2020;
    private Button year_2021;
    private Button year_random;
    private Button year_all;

    private int year_num; // 연도 선택 Int
    private String year_num_string; // 선택 후 표시될 연도 String



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_new);
        getWindow().setWindowAnimations(0);

        year_2016 = findViewById(R.id.year_2016);
        year_2017 = findViewById(R.id.year_2017);
        year_2018 = findViewById(R.id.year_2018);
        year_2019 = findViewById(R.id.year_2019);
        year_2020 = findViewById(R.id.year_2020);
        year_2021 = findViewById(R.id.year_2021);
        year_random = findViewById(R.id.year_random);
        year_all = findViewById(R.id.year_all);



        SharedPreferences music = getSharedPreferences(MainActivity.SHARED_MUSIC,MODE_PRIVATE);
        Boolean bgmCb_difficulty = music.getBoolean("bgmCb",true);
        Boolean effectCb_year = music.getBoolean("effectCb",true);

        ImageView imageview_lp = findViewById(R.id.imageView_lp);
        Animation lpLotate = AnimationUtils.loadAnimation(getApplication(), R.anim.rotate_lp);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        // 모드 뭐 선택했는지 가져오기
        SharedPreferences mode_shared = getSharedPreferences(MODE_SHARED,MODE_PRIVATE);


        // 광고 부분
        /*
        mAdview = findViewById(R.id.yearadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" + BANNER_AD_ID);
       
         */

        if(!MainActivity.mediaplayer_main.isPlaying())
        {
            MainActivity.mediaplayer_main.start();
        }


        if(bgmCb_difficulty){
            imageview_lp.startAnimation(lpLotate);
        }

        else{
            imageview_lp.clearAnimation();
        }

        //Sound
        soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);	//작성
        soundID = soundPool.load(this,R.raw.buttonsound1,1);

        if(soundPool!=null){
            if(!effectCb_year){
                soundPoolVolume=0.0f;
            }

            else{
                soundPoolVolume=0.5f;
            }
        }


        year_scroll = findViewById(R.id.year_Scrollview);

        year_linear = findViewById(R.id.year_linearlayout);
        year_select = findViewById(R.id.txt_selectYear);

        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));
        year_mvquiz = findViewById(R.id.year_MvQuiz);
        year_view = findViewById(R.id.yearView);
        View year_v;

        button_total = year_linear.getChildCount();


        //페이드인 애니메이션
        Animation textfadein = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_text);
        kpop1.startAnimation(textfadein);
        kpop2.startAnimation(textfadein);
        kpop3.startAnimation(textfadein);
        year_mvquiz.startAnimation(textfadein);
        year_view.startAnimation(textfadein);

        year_select.setVisibility(View.GONE);

        year_2016.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year_num = 2016;
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                year_num_string = String.valueOf(year_num);

                checkAndintent();

            }
        });

        year_2017.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year_num = 2017;
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                year_num_string = String.valueOf(year_num);

                checkAndintent();

            }
        });

        year_2018.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year_num = 2018;
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                year_num_string = String.valueOf(year_num);

                checkAndintent();

            }
        });

        year_2019.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year_num = 2019;
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                year_num_string = String.valueOf(year_num);

                checkAndintent();

            }
        });

        year_2020.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year_num = 2020;
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);

                year_num_string = String.valueOf(year_num);

                checkAndintent();

            }
        });

        year_2021.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year_num = 2021;
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);

                year_num_string = String.valueOf(year_num);

                checkAndintent();

            }
        });

        year_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int random_year = random.nextInt(button_total-2);
                year_num = random_year + 2016;

                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);

                year_num_string = String.valueOf(year_num);

                checkAndintent();

            }
        });

        year_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year_num = 101; // 전체면 101
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);

                year_num_string = getString(R.string.All);

                checkAndintent();

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        if(!MainActivity.mediaplayer_main.isPlaying())
        {
            MainActivity.mediaplayer_main.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent mainintent = new Intent(this,ModeActivity.class);
        startActivity(mainintent);
        finish();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        MainActivity.mediaplayer_main.pause();
    }

    private void checkAndintent() {
        Log.d("연도", " : "+year_num);
        year_select.setText(year_num_string);

        year_scroll.setVisibility(View.GONE);

        year_select.setVisibility(View.VISIBLE);

        updateYear();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), quiz_beginner.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_QUIZ);

                finish();
            }
        }, 600); //딜레이 타임 조절
    }

    private void updateYear() {
        SharedPreferences modeshared = getSharedPreferences(MODE_SHARED,MODE_PRIVATE);

        SharedPreferences.Editor modeEditor = modeshared.edit();
        modeEditor.putInt(YEAR_SELECT,year_num);
        modeEditor.apply();
    }

}