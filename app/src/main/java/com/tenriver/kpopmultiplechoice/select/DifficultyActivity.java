package com.tenriver.kpopmultiplechoice.select;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.SoundPool;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.tenriver.kpopmultiplechoice.R;
import com.tenriver.kpopmultiplechoice.quizActivity.QuizMain;
import com.tenriver.kpopmultiplechoice.quizActivity.quiz_beginner;

public class  DifficultyActivity extends AppCompatActivity {
    private static final String BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111";

    private Button easyButton;
    private Button normalButton;
    private Button hardButton;

    private int highscore;
    private int year_num_Difficulty;
    private TextView textViewHighscore;
    private TextView textViewGuidegame;
    private TextView difficulty_title;
    private TextView textLevel;
    private ConstraintLayout layout_caution;
    private ConstraintLayout layout_tip;


    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;

    //광고
    private AdView mAdview;
    private InterstitialAd screenAd;

    private boolean isFinished = false;

    //Sound
    SoundPool soundPool;	//작성
    int soundID;		//작성

    private float soundPoolVolume;

    // 모드 선택 변수
    private static final String MODE_SHARED = "modeshared";
    private static final String GAMEMODE_SELECT = "gamemodeselect";

    private int mode_select;

    // 버튼
    private ImageButton df_up;
    private ImageButton df_down;
    private Button df_confirm;

    // 각 모드 리니어레이아웃
    private LinearLayout easy_linear;
    private LinearLayout normal_linear;
    private LinearLayout hard_linear;


    // 리니어레이아웃 안의 모드 텍스트
    private TextView easy_text;
    private TextView normal_text;
    private TextView hard_text;

    // 현재 골라진 난이도를 숫자로 지정
    private int currentMode = -1; // -1은 easy 0은 normal, 1은 hard

    private ImageView Uparrowpink;
    private ImageView Downarrowpink;

    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {


            }
        });

        // 모드 뭐 선택했는지 가져오기
        SharedPreferences mode_shared = getSharedPreferences(MODE_SHARED,MODE_PRIVATE);

        mode_select = mode_shared.getInt(GAMEMODE_SELECT,-1);

        mAdview = findViewById(R.id.difficulty_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" + BANNER_AD_ID);

        Uparrowpink = findViewById(R.id.upPink);
        Downarrowpink = findViewById(R.id.downPink);

        if(!MainActivity.mediaplayer_main.isPlaying())
        {
            MainActivity.mediaplayer_main.start();
        }

        easy_linear = findViewById(R.id.easy_linear);
        normal_linear = findViewById(R.id.normal_linear);
        hard_linear = findViewById(R.id.hard_linear);
        easy_text = findViewById(R.id.txt_easy);
        normal_text = findViewById(R.id.txt_normal);
        hard_text = findViewById(R.id.txt_hard);

        df_up = findViewById(R.id.difficulty_up);
        df_down = findViewById(R.id.difficulty_down);
        df_confirm = findViewById(R.id.difficulty_confirm);

        difficulty_title = findViewById(R.id.difficulty_Title);

        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));

        // text blink animation
        anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(100);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        //페이드인 애니메이션
        Animation textfadein = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_text);
        difficulty_title.startAnimation(textfadein);
        kpop1.startAnimation(textfadein);
        kpop2.startAnimation(textfadein);
        kpop3.startAnimation(textfadein);
        normal_linear.setVisibility(View.GONE); // 처음 시작은 easy이므로 normal은 보이지않게
        hard_linear.setVisibility(View.GONE); // hard 안보임
        Uparrowpink.setVisibility(View.GONE); // 위 화살표 안보임
        
        ImageView remote_difficulty = (ImageView) findViewById(R.id.Remote_difficulty);
        ConstraintLayout remotebutton_difficulty = (ConstraintLayout) findViewById(R.id.Remote_button_difficulty);

        Animation RemoteUp = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_up);
        remote_difficulty.startAnimation(RemoteUp);

        Animation RemoteButtonUp = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_up);
        remotebutton_difficulty.startAnimation(RemoteButtonUp);

        RemoteUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                df_down.setEnabled(false);
                df_up.setEnabled(false);
                df_confirm.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                df_down.setEnabled(true);
                df_up.setEnabled(true);
                df_confirm.setEnabled(true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        SharedPreferences music = getSharedPreferences(MainActivity.SHARED_MUSIC,MODE_PRIVATE);

        Boolean bgmCb_difficulty = music.getBoolean("bgmCb",true);
        Boolean effectCb_difficulty = music.getBoolean("effectCb",true);

        ImageView imageview_lp = findViewById(R.id.imageView_lp);
        Animation lpLotate = AnimationUtils.loadAnimation(getApplication(), R.anim.rotate_lp);

        if(bgmCb_difficulty){
            imageview_lp.startAnimation(lpLotate);
        }

        else{
            imageview_lp.clearAnimation();
        }

        //Sound
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);	//작성
        soundID = soundPool.load(this,R.raw.buttonsound1,1);

        Intent intent = getIntent();
        final int year_num_Difficulty = intent.getIntExtra("year_num",2020);

        if(soundPool!=null){
            if(!effectCb_difficulty){
                soundPoolVolume=0.0f;
            }

            else{
                soundPoolVolume=0.5f;
            }
        }

        df_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentMode == 0)
                {
                    currentMode = -1;
                    Uparrowpink.setVisibility(View.INVISIBLE);
                    Downarrowpink.setVisibility(View.VISIBLE);
                    hard_linear.setVisibility(View.GONE);
                    normal_linear.setVisibility(View.GONE);
                    easy_linear.setVisibility(View.VISIBLE);

                }

                else if( currentMode == 1)
                {
                    currentMode = 0;
                    Uparrowpink.setVisibility(View.VISIBLE);
                    Downarrowpink.setVisibility(View.VISIBLE);
                    hard_linear.setVisibility(View.GONE);
                    normal_linear.setVisibility(View.VISIBLE);
                    easy_linear.setVisibility(View.GONE);
                }
            }
        });

        df_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentMode == -1)
                {
                    currentMode = 0;
                    Uparrowpink.setVisibility(View.VISIBLE);
                    Downarrowpink.setVisibility(View.VISIBLE);
                    hard_linear.setVisibility(View.GONE);
                    normal_linear.setVisibility(View.VISIBLE);
                    easy_linear.setVisibility(View.GONE);
                }
                else if( currentMode == 0)
                {
                    currentMode = 1;
                    Uparrowpink.setVisibility(View.VISIBLE);
                    Downarrowpink.setVisibility(View.INVISIBLE);
                    hard_linear.setVisibility(View.VISIBLE);
                    normal_linear.setVisibility(View.GONE);
                    easy_linear.setVisibility(View.GONE);
                }
            }
        });

        df_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // easy
                if( currentMode == -1 )
                {
                    easy_text.startAnimation(anim);

                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);

                    df_down.setEnabled(false);
                    df_up.setEnabled(false);
                    df_confirm.setEnabled(false);

                    df_confirm.setTextColor(Color.GRAY);

                    //리모컨이미지 내려감
                    Animation RemoteDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                    remote_difficulty.startAnimation(RemoteDown);

                    //리모컨버튼 내려감
                    Animation RemoteButtonDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                    remotebutton_difficulty.startAnimation(RemoteButtonDown);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            if(!isFinished){
                                if (mode_select==0){
                                    Intent Quizintent = new Intent(getApplicationContext(), QuizMain.class);
                                    Quizintent.putExtra("difficulty_time",10000); // 10초
                                    Quizintent.putExtra("year_num",year_num_Difficulty);
                                    Quizintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                    startActivity(Quizintent);
                                }

                                else {
                                    Intent Quizintent = new Intent(getApplicationContext(), quiz_beginner.class);
                                    Quizintent.putExtra("difficulty_time",10000); // 10초
                                    Quizintent.putExtra("year_num",year_num_Difficulty);
                                    Quizintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                    startActivity(Quizintent);
                                }
                                finish();
                            }

                            else{
                                handler.removeCallbacks(this);
                            }

                        }
                    },600);
                }

                // normal
                else if( currentMode == 0 )
                {
                    normal_text.startAnimation(anim);

                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);

                    df_down.setEnabled(false);
                    df_up.setEnabled(false);
                    df_confirm.setEnabled(false);

                    df_confirm.setTextColor(Color.GRAY);

                    //리모컨이미지 내려감
                    Animation RemoteDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                    remote_difficulty.startAnimation(RemoteDown);

                    //리모컨버튼 내려감
                    Animation RemoteButtonDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                    remotebutton_difficulty.startAnimation(RemoteButtonDown);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(!isFinished){
                                if (mode_select==0){
                                    Intent Quizintent = new Intent(getApplicationContext(),QuizMain.class);
                                    Quizintent.putExtra("difficulty_time",5000); // 5초
                                    Quizintent.putExtra("year_num",year_num_Difficulty);
                                    Quizintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                    startActivity(Quizintent);
                                }

                                else {
                                    Intent Quizintent = new Intent(getApplicationContext(),quiz_beginner.class);
                                    Quizintent.putExtra("difficulty_time",5000); // 5초
                                    Quizintent.putExtra("year_num",year_num_Difficulty);
                                    Quizintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                    startActivity(Quizintent);
                                }
                                finish();

                            }

                            else{
                                handler.removeCallbacks(this);
                            }
                        }
                    },600);
                }

                // hard
                else if ( currentMode == 1 )
                {
                    hard_text.startAnimation(anim);

                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);

                    df_down.setEnabled(false);
                    df_up.setEnabled(false);
                    df_confirm.setEnabled(false);

                    df_confirm.setTextColor(Color.GRAY);

                    //리모컨이미지 내려감
                    Animation RemoteDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                    remote_difficulty.startAnimation(RemoteDown);

                    //리모컨버튼 내려감
                    Animation RemoteButtonDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                    remotebutton_difficulty.startAnimation(RemoteButtonDown);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(!isFinished){
                                if (mode_select==0){
                                    Intent Quizintent = new Intent(getApplicationContext(),QuizMain.class);
                                    Quizintent.putExtra("difficulty_time",3000); // 3초
                                    Quizintent.putExtra("year_num",year_num_Difficulty);
                                    Quizintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                    startActivity(Quizintent);
                                }

                                else {
                                    Intent Quizintent = new Intent(getApplicationContext(),quiz_beginner.class);
                                    Quizintent.putExtra("difficulty_time",3000); // 3초
                                    Quizintent.putExtra("year_num",year_num_Difficulty);
                                    Quizintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                    startActivity(Quizintent);
                                }
                                finish();
                            }

                            else{
                                handler.removeCallbacks(this);
                            }
                        }
                    },600);
                }
            }
        });
    }


    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("데이터를 받았는가","?");

        if (requestCode == MainActivity.REQUEST_CODE_QUIZ){
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizMain.HIGH_SCORE, 0);
                Log.e("점수를 받았는가22","?" + score);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(QuizMain.HIGH_SCORE, score);
                Log.e("최고 점수",":" + score);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }*/

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
        isFinished = true;

        Intent yearintent = new Intent(this,YearActivity.class);
        startActivity(yearintent);
        finish();

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        MainActivity.mediaplayer_main.pause();
    }

    /*
    private void updateHighscore(int highscoreNew){
        highscore = highscoreNew;
        textViewHighscore.setText("최고 점수: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE,highscore);
        editor.apply();
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("최고 점수: " + highscore);
    }*/


}