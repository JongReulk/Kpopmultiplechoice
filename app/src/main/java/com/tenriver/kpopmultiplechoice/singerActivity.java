package com.tenriver.kpopmultiplechoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.tenriver.kpopmultiplechoice.quizActivity.quiz_beginner;
import com.tenriver.kpopmultiplechoice.select.MainActivity;
import com.tenriver.kpopmultiplechoice.select.ModeActivity;

import java.util.Random;

public class singerActivity extends AppCompatActivity {
    private ScrollView singer_scroll;

    private LinearLayout singer_linear;
    private TextView singer_Title;
    private TextView singer_mvquiz;
    private TextView singer_view;

    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;

    private AdView mAdview;

    private TextView singer_select;

    private ImageButton singer_help;

    // 모드 선택 변수
    private static final String MODE_SHARED = "modeshared";
    private static final String SINGER_SELECT = "singerselect";

    private static boolean isClicked;

    private float soundPoolVolume;
    SoundPool soundPool;	//작성
    int soundID;		    //작성

    // 연도별 버튼들
    private Button singer_blackpink;
    private Button singer_bol4;
    private Button singer_bts;
    private Button singer_gfriend;
    private Button singer_heize;
    private Button singer_iu;
    private Button singer_mamamoo;
    private Button singer_monstax;
    private Button singer_ohmygirl;
    private Button singer_redvelvet;
    private Button singer_taeyeon;
    private Button singer_twice;
    private Button singer_winner;

    private String m_singer; // 가수 선택 String
    private String singer_string; // 선택 후 표시될 가수 String

    private Setting_singerhelp setting_singerhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);

        getWindow().setWindowAnimations(0);

        singer_blackpink = findViewById(R.id.singer_BLACKPINK);
        singer_bol4 = findViewById(R.id.singer_BOL4);
        singer_bts = findViewById(R.id.singer_BTS);
        singer_gfriend = findViewById(R.id.singer_GFRIEND);
        singer_heize = findViewById(R.id.singer_Heize);
        singer_iu = findViewById(R.id.singer_IU);
        singer_mamamoo = findViewById(R.id.singer_MAMAMOO);
        singer_monstax = findViewById(R.id.singer_MONSTAX);
        singer_ohmygirl = findViewById(R.id.singer_OHMYGIRL);
        singer_redvelvet = findViewById(R.id.singer_REDVELVET);
        singer_taeyeon = findViewById(R.id.singer_TAEYEON);
        singer_twice = findViewById(R.id.singer_TWICE);
        singer_winner = findViewById(R.id.singer_WINNER);

        singer_help = findViewById(R.id.singer_help);


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
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);	//작성
        soundID = soundPool.load(this,R.raw.buttonsound1,1);

        if(soundPool!=null){
            if(!effectCb_year){
                soundPoolVolume=0.0f;
            }

            else{
                soundPoolVolume=0.5f;
            }
        }


        singer_scroll = findViewById(R.id.singer_Scrollview);

        singer_linear = findViewById(R.id.singer_linearlayout);
        singer_select = findViewById(R.id.txt_selectSinger);

        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));
        singer_mvquiz = findViewById(R.id.singer_MvQuiz);
        singer_view = findViewById(R.id.singerView);


        //페이드인 애니메이션
        Animation textfadein = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_text);
        kpop1.startAnimation(textfadein);
        kpop2.startAnimation(textfadein);
        kpop3.startAnimation(textfadein);
        singer_mvquiz.startAnimation(textfadein);
        singer_view.startAnimation(textfadein);

        singer_select.setVisibility(View.GONE);

        singer_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singerhelpDialog();
            }
        });

        singer_blackpink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "BLACKPINK";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_bol4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "BOL4";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "BTS";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_gfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "GFRIEND";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_heize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "Heize";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_iu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "IU";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_mamamoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "MAMAMOO";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_monstax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "MONSTA X";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_ohmygirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "OH MY GIRL";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_redvelvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "Red Velvet";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_taeyeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "TAEYEON";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_twice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "TWICE";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

                checkAndintent();

            }
        });

        singer_winner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_singer = "WINNER";
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                singer_string = m_singer;

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

        Intent mainintent = new Intent(this, ModeActivity.class);
        startActivity(mainintent);
        finish();
    }

    private void singerhelpDialog(){
        setting_singerhelp = new Setting_singerhelp(this);
        setting_singerhelp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button confirmBtn = setting_singerhelp.findViewById(R.id.btn_confirm);

        setting_singerhelp.setCancelable(false); // 밖에 선택해도 창이 안꺼짐
        setting_singerhelp.show();


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_singerhelp.dismiss();
            }
        });

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        MainActivity.mediaplayer_main.pause();
    }

    // 가수 체크 후 페이지 이동
    private void checkAndintent() {
        Log.d("가수", " : "+ m_singer);
        singer_select.setText(singer_string);

        singer_scroll.setVisibility(View.GONE);

        singer_select.setVisibility(View.VISIBLE);

        updateSinger();


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

    // 선택 가수 업데이트
    private void updateSinger() {
        SharedPreferences modeshared = getSharedPreferences(MODE_SHARED,MODE_PRIVATE);

        SharedPreferences.Editor modeEditor = modeshared.edit();
        modeEditor.putString(SINGER_SELECT,m_singer);
        modeEditor.apply();
    }
}