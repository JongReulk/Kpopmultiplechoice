package com.tenriver.kpopmultiplechoice.select;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import android.widget.Toast;

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
import com.tenriver.kpopmultiplechoice.Setting_modehelp;
import com.tenriver.kpopmultiplechoice.Setting_singerhelp;
import com.tenriver.kpopmultiplechoice.quizActivity.QuizChallenge;
import com.tenriver.kpopmultiplechoice.singerActivity;

import static com.tenriver.kpopmultiplechoice.select.MainActivity.KEY_POINT;
import static com.tenriver.kpopmultiplechoice.select.MainActivity.SHARED_POINT;

public class ModeActivity extends AppCompatActivity {

    private String BANNER_AD_ID;

    private boolean isFinished = false;

    //Sound
    SoundPool soundPool;	//작성
    int soundID;		//작성

    private float soundPoolVolume;


    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;
    private TextView mode_Title;
    private TextView txt_modequiz;

    // 각 모드 카드뷰
    private CardView baby_cardview;
    private CardView classic_cardview;
    private CardView master_cardview;
    private CardView challenge_cardview;
    private CardView singer_cardview;

    // 각 모드 타이틀
    private TextView singer_title;
    private TextView baby_title;
    private TextView classic_title;
    private TextView master_title;
    private TextView challenge_title;

    private TextView challenge_text;
    private TextView master_text;

    // 포인트 관련
    private TextView txtHintPoint;
    private int hintPoint;



    Animation anim;

    // 현재 골라진 모드를 숫자로 지정
    private int currentMode = 10000;
    private String whichMode;

    private static final String MODE_SHARED = "modeshared";
    private static final String GAMEMODE_SELECT = "gamemodeselect";
    private static final String WHICHMODE_SELECT = "whichmodeselect";

    private int select_num;
    private ImageButton modeHelp;
    private Setting_modehelp setting_modehelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);

        BANNER_AD_ID = getString(R.string.BANNER_TEST);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {


            }
        });


        if (!MainActivity.mediaplayer_main.isPlaying()) {
            MainActivity.mediaplayer_main.start();
        }



        baby_cardview = findViewById(R.id.card_baby);
        classic_cardview = findViewById(R.id.card_classic);
        master_cardview = findViewById(R.id.card_master);
        challenge_cardview = findViewById(R.id.card_challenge);
        singer_cardview = findViewById(R.id.card_singer);

        singer_title = findViewById(R.id.title_singer);
        baby_title = findViewById(R.id.title_baby);
        classic_title = findViewById(R.id.title_classic);
        master_title = findViewById(R.id.title_master);
        challenge_title = findViewById(R.id.title_challenge);

        challenge_text = findViewById(R.id.text_challenge);
        master_text = findViewById(R.id.text_master);

        modeHelp = findViewById(R.id.mode_help);


        txtHintPoint = findViewById(R.id.txtPoint);


        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));
        mode_Title = findViewById(R.id.gamemodeView);
        txt_modequiz = findViewById(R.id.txtMvQuiz);


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
        txt_modequiz.startAnimation(textfadein);
        //basic_linear.setVisibility(View.GONE);
        //challenge_linear.setVisibility(View.GONE); // 처음 시작은 beginner이므로 챌린지는 보이지않게
        //consonants_linear.setVisibility(View.GONE);


        // 포인트 가져오기
        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        hintPoint = point.getInt(KEY_POINT,100);

        txtHintPoint.setText(""+hintPoint + " POINTS");


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

        titleMarquee();

        if (hintPoint < 30){
            challenge_title.setAlpha(0.5f);
            challenge_text.setAlpha(0.5f);
            if (hintPoint < 10) {
                master_title.setAlpha(0.5f);
                master_text.setAlpha(0.5f);
            }
        }

        modeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modehelpDialog();
            }
        });

        singer_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("로그", "singer_ 카드뷰 클릭!");

                currentMode = 10000;

                updateMode();
                updateWhichMode("singer");

                singer_title.startAnimation(anim);

                soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!isFinished) {
                            Intent Singerintent = new Intent(getApplicationContext(), singerActivity.class);

                            Singerintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                            startActivity(Singerintent);
                            finish();
                        } else {
                            handler.removeCallbacks(this);
                        }

                    }
                }, 600);
            }
        });


        // 각 모드들 카드 클릭
        baby_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("로그", "baby_ 카드뷰 클릭!");

                currentMode = 10000;

                updateMode();
                updateWhichMode("year");

                baby_title.startAnimation(anim);

                soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!isFinished) {
                            Intent Modeintent = new Intent(getApplicationContext(), YearActivity.class);

                            Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                            startActivity(Modeintent);
                            finish();
                        } else {
                            handler.removeCallbacks(this);
                        }

                    }
                }, 600);
            }
        });

        classic_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("로그", "classic_ 카드뷰 클릭!");

                currentMode = 5000;

                updateMode();
                updateWhichMode("year");

                classic_title.startAnimation(anim);

                soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!isFinished) {
                            Intent Modeintent = new Intent(getApplicationContext(), YearActivity.class);

                            Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                            startActivity(Modeintent);
                            finish();
                        } else {
                            handler.removeCallbacks(this);
                        }

                    }
                }, 600);
            }
        });

        master_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hintPoint < 10) {
                    Toast.makeText(getApplicationContext(), getString(R.string.need10points), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("로그", "master_ 카드뷰 클릭!");

                    currentMode = 3000;

                    updateMode();
                    updateWhichMode("year");

                    master_title.startAnimation(anim);

                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (!isFinished) {
                                Intent Modeintent = new Intent(getApplicationContext(), YearActivity.class);

                                Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                startActivity(Modeintent);
                                finish();
                            } else {
                                handler.removeCallbacks(this);
                            }

                        }
                    }, 600);
                }
            }
        });

        challenge_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hintPoint < 30){
                    Toast.makeText(getApplicationContext(), getString(R.string.need30points), Toast.LENGTH_SHORT).show();
                }

                else {

                    challenge_title.startAnimation(anim);

                    soundPool.play(soundID, soundPoolVolume, soundPoolVolume, 0, 0, 1f);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (!isFinished) {
                                Intent Modeintent = new Intent(getApplicationContext(), QuizChallenge.class);

                                Modeintent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                                startActivity(Modeintent);
                                finish();
                            } else {
                                handler.removeCallbacks(this);
                            }
                        }
                    }, 600);
                }


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
        modeEditor.putInt(GAMEMODE_SELECT,currentMode);
        modeEditor.apply();
    }

    private void updateWhichMode(String whichmode) {
        SharedPreferences modeshared = getSharedPreferences(MODE_SHARED,MODE_PRIVATE);

        SharedPreferences.Editor modeEditor = modeshared.edit();
        modeEditor.putString(WHICHMODE_SELECT,whichmode);
        modeEditor.apply();
    }

    private void titleMarquee() {
        // 각 모드 제목들 MARQUEE
        baby_title.setSingleLine(true);
        baby_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        baby_title.setSelected(true);

        classic_title.setSingleLine(true);
        classic_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        classic_title.setSelected(true);

        master_title.setSingleLine(true);
        master_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        master_title.setSelected(true);


        challenge_title.setSingleLine(true);
        challenge_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        challenge_title.setSelected(true);

        singer_title.setSingleLine(true);
        singer_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        singer_title.setSelected(true);

    }

    private void modehelpDialog(){
        setting_modehelp = new Setting_modehelp(this);
        setting_modehelp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button confirmBtn = setting_modehelp.findViewById(R.id.btn_confirm);

        setting_modehelp.setCancelable(false); // 밖에 선택해도 창이 안꺼짐
        setting_modehelp.show();


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_modehelp.dismiss();
            }
        });

    }

}

