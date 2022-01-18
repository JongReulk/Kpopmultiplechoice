package com.tenriver.kpopmultiplechoice.select;

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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.tenriver.kpopmultiplechoice.R;
import com.tenriver.kpopmultiplechoice.quizActivity.QuizChallenge;

public class ModeActivity extends AppCompatActivity {

    private static final String BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111";

    private boolean isFinished = false;

    //Sound
    SoundPool soundPool;	//작성
    int soundID;		//작성

    private float soundPoolVolume;


    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;
    private TextView mode_Title;

    // 각 모드 리니어레이아웃
    private LinearLayout basic_linear;
    private LinearLayout challenge_linear;
    private LinearLayout beginner_linear;
    private LinearLayout consonants_linear;

    private CardView beginner_cardview;
    

    // 리니어레이아웃 안의 모드 텍스트
    private TextView basic_text;
    private TextView challenge_text;
    private TextView beginner_text;
    private TextView consonants_text;

    Animation anim;

    // 현재 골라진 모드를 숫자로 지정
    private int currentMode = -1; // -1은 beginner 0은 basic, 1은 challenge

    private static final String MODE_SHARED = "modeshared";
    private static final String GAMEMODE_SELECT = "gamemodeselect";

    private int select_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {


            }
        });

        AdView mAdview = findViewById(R.id.mode_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" + BANNER_AD_ID);




        if (!MainActivity.mediaplayer_main.isPlaying()) {
            MainActivity.mediaplayer_main.start();
        }



        basic_linear = findViewById(R.id.basic_linear);
        challenge_linear = findViewById(R.id.challenge_linear);
        beginner_linear = findViewById(R.id.beginner_linear);
        consonants_linear = findViewById(R.id.consonants_linear);
        basic_text = findViewById(R.id.txt_basicmode);
        challenge_text = findViewById(R.id.txt_challengemode);
        beginner_text = findViewById(R.id.txt_beginnermode);
        consonants_text = findViewById(R.id.txt_consonantsmode);

        beginner_cardview = findViewById(R.id.beginner_card);



        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));
        mode_Title = findViewById(R.id.gamemodeView);


        // text blink animation
        anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(100);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        //페이드인 애니메이션
        Animation textfadein = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_text);
        kpop1.startAnimation(textfadein);
        kpop2.startAnimation(textfadein);
        kpop3.startAnimation(textfadein);
        mode_Title.startAnimation(textfadein);
        //basic_linear.setVisibility(View.GONE);
        //challenge_linear.setVisibility(View.GONE); // 처음 시작은 beginner이므로 챌린지는 보이지않게
        //consonants_linear.setVisibility(View.GONE);



        SharedPreferences music = getSharedPreferences(MainActivity.SHARED_MUSIC, MODE_PRIVATE);

        Boolean bgmCb_difficulty = music.getBoolean("bgmCb", true);
        Boolean effectCb_difficulty = music.getBoolean("effectCb", true);

        ImageView imageview_lp = findViewById(R.id.imageView_lp);
        Animation lpLotate = AnimationUtils.loadAnimation(getApplication(), R.anim.rotate_lp);

        if (bgmCb_difficulty) {
            imageview_lp.startAnimation(lpLotate);
        } else {
            imageview_lp.clearAnimation();
        }

        //Sound
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);    //작성
        soundID = soundPool.load(this, R.raw.buttonsound1, 1);

        if (soundPool != null) {
            if (!effectCb_difficulty) {
                soundPoolVolume = 0.0f;
            } else {
                soundPoolVolume = 0.5f;
            }
        }

        beginner_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("로그", "beginner_ 카드뷰 클릭!");
            }
        });





//        mode_confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // beginner mode
//                if( currentMode == -1 )
//                {
//                    select_num = currentMode;
//
//                    updateMode();
//
//                    beginner_text.startAnimation(anim);
//
//                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);
//
//                    mode_down.setEnabled(false);
//                    mode_up.setEnabled(false);
//                    mode_confirm.setEnabled(false);
//
//                    mode_confirm.setTextColor(Color.GRAY);
//
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (!isFinished) {
//                                Intent Modeintent = new Intent(getApplicationContext(), YearActivity.class);
//
//                                Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
//                                startActivity(Modeintent);
//                                finish();
//                            } else {
//                                handler.removeCallbacks(this);
//                            }
//
//                        }
//                    }, 600);
//                }
//
//                // basic mode
//                else if( currentMode == 0 )
//                {
//                    select_num = currentMode;
//
//                    updateMode();
//
//                    basic_text.startAnimation(anim);
//
//                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);
//
//                    mode_down.setEnabled(false);
//                    mode_up.setEnabled(false);
//                    mode_confirm.setEnabled(false);
//
//                    mode_confirm.setTextColor(Color.GRAY);
//
//
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (!isFinished) {
//                                Intent Modeintent = new Intent(getApplicationContext(), YearActivity.class);
//
//                                Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
//                                startActivity(Modeintent);
//                                finish();
//                            } else {
//                                handler.removeCallbacks(this);
//                            }
//
//                        }
//                    }, 600);
//                }
//
//                // challenge mode
//                else if ( currentMode == 1 )
//                {
//                    challenge_text.startAnimation(anim);
//
//                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);
//
//                    mode_down.setEnabled(false);
//                    mode_up.setEnabled(false);
//                    mode_confirm.setEnabled(false);
//
//                    mode_confirm.setTextColor(Color.GRAY);
//
//
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (!isFinished) {
//                                Intent Modeintent = new Intent(getApplicationContext(), QuizChallenge.class);
//
//                                Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
//                                startActivity(Modeintent);
//                                finish();
//                            } else {
//                                handler.removeCallbacks(this);
//                            }
//                        }
//                    }, 600);
//                }
//
//                else if ( currentMode == 2 ) // consonants 모드 일때
//                {
//                    select_num = currentMode;
//
//                    updateMode();
//
//                    consonants_text.startAnimation(anim);
//
//                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);
//
//                    mode_down.setEnabled(false);
//                    mode_up.setEnabled(false);
//                    mode_confirm.setEnabled(false);
//
//                    mode_confirm.setTextColor(Color.GRAY);
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (!isFinished) {
//                                Intent Modeintent = new Intent(getApplicationContext(), YearActivity.class);
//
//                                Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
//                                startActivity(Modeintent);
//                                finish();
//                            } else {
//                                handler.removeCallbacks(this);
//                            }
//                        }
//                    }, 600);
//                }
//            }
//        });
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
        isFinished = true;

        Intent mainintent = new Intent(this,MainActivity.class);
        startActivity(mainintent);
        finish();

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        MainActivity.mediaplayer_main.pause();
    }

    private void updateMode() {
        SharedPreferences modeshared = getSharedPreferences(MODE_SHARED,MODE_PRIVATE);

        SharedPreferences.Editor modeEditor = modeshared.edit();
        modeEditor.putInt(GAMEMODE_SELECT,select_num);
        modeEditor.apply();
    }



}

