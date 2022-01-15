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

import java.util.Random;

public class YearActivity extends AppCompatActivity {

    private static final String BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111";

    private ScrollView year_scroll;
    private ImageButton year_up;
    private ImageButton year_down;
    private Button year_confirm;
    private LinearLayout year_linear;
    private int button_total;
    private int button_num;
    private TextView year_Title;

    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;

    private AdView mAdview;

    private TextView year_select;

    // 모드 선택 변수
    private static final String MODE_SHARED = "modeshared";
    private static final String GAMEMODE_SELECT = "gamemodeselect";

    private static boolean isClicked;

    private float soundPoolVolume;
    SoundPool soundPool;	//작성
    int soundID;		    //작성

    int mode_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_new);
        getWindow().setWindowAnimations(0);

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

        mode_select = mode_shared.getInt(GAMEMODE_SELECT,-1);

        mAdview = findViewById(R.id.yearadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" + BANNER_AD_ID);

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

        //리모컨 이미지
        ImageView remote = (ImageView) findViewById(R.id.Remote_year);
        ConstraintLayout remotebutton_year = (ConstraintLayout) findViewById(R.id.Remote_button_year);

        year_Title = findViewById(R.id.year_Title);
        year_scroll = findViewById(R.id.year_Scrollview);
        year_up = findViewById(R.id.year_up);
        year_down = findViewById(R.id.year_down);
        year_confirm = findViewById(R.id.year_confirm);
        year_linear = findViewById(R.id.year_linearlayout);
        year_select = findViewById(R.id.txt_selectYear);

        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));
        View year_v;

        year_up.setEnabled(true);
        year_down.setEnabled(true);
        year_confirm.setEnabled(true);

        //페이드인 애니메이션
        Animation textfadein = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_text);
        year_Title.startAnimation(textfadein);
        kpop1.startAnimation(textfadein);
        kpop2.startAnimation(textfadein);
        kpop3.startAnimation(textfadein);

        // 리모콘 애니메이션
        Animation RemoteUp = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_up);
        remote.startAnimation(RemoteUp);

        RemoteUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                year_down.setEnabled(false);
                year_confirm.setEnabled(false);
                year_up.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                year_down.setEnabled(true);
                year_confirm.setEnabled(true);
                year_up.setEnabled(true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation RemoteButtonUp = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_up);
        remotebutton_year.startAnimation(RemoteButtonUp);

        Button year_b;

        button_num = 0;

        button_total = year_linear.getChildCount();
        button_num = button_total/2;

        Log.e("total : " ," : " +button_total);

        year_v = year_linear.getChildAt(button_num);

        year_v.setSelected(true);
        year_b = (Button)year_v;
        year_b.setTextColor(getResources().getColor(R.color.black));
        year_b.setTextSize(24);

        year_select.setVisibility(View.GONE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e("Num "," : " + button_num);
                View year_v = year_linear.getChildAt(button_num-1);
                year_scroll.smoothScrollTo(0, year_v.getTop());

            }
        }, 50);


        year_scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        year_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_num < button_total-1) {

                    View year_v = year_linear.getChildAt(button_num);

                    year_v.setSelected(false);

                    Button year_b = (Button)year_v;
                    year_b.setTextColor(getResources().getColor(R.color.white));
                    year_b.setTextSize(18);

                    button_num++;

                    year_v = year_linear.getChildAt(button_num);
                    year_v.setSelected(true);

                    year_b = (Button)year_v;
                    year_b.setTextColor(getResources().getColor(R.color.black));
                    year_b.setTextSize(24);

                    year_down.setEnabled(false);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            View year_v = year_linear.getChildAt(button_num-1);
                            year_scroll.smoothScrollTo(0, year_v.getTop());
                            year_down.setEnabled(true);

                        }
                    }, 100);


                    Log.e("Button : " ," : " +button_num);
                }

            }
        });



        year_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);

                year_down.setEnabled(false);
                year_up.setEnabled(false);
                year_confirm.setEnabled(false);
                year_scroll.setVisibility(View.GONE);
                year_confirm.setTextColor(Color.GRAY);


                // 리모콘 애니메이션
                Animation RemoteDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                remote.startAnimation(RemoteDown);

                Animation RemoteButtonDown = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_down);
                remotebutton_year.startAnimation(RemoteButtonDown);


                int year_num = button_num + 2016;
                String year_num_string = String.valueOf(year_num);

                year_select.setText(year_num_string);

                if(button_num == button_total-2)
                {
                    Random random = new Random();
                    int random_year = random.nextInt(button_total-2);
                    year_num = random_year + 2016;
                    year_num_string = String.valueOf(year_num);

                    year_select.setText(year_num_string);
                }


                if(button_num == button_total-1)
                {
                    year_select.setText(getString(R.string.All));
                }


                year_select.setVisibility(View.VISIBLE);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        int year_num = button_num + 2016;

                        if(button_num == button_total-2)
                        {
                            Random random = new Random();
                            int random_year = random.nextInt(button_total-2);
                            year_num = random_year + 2016;
                            Log.e("RANDOM YEAR", " : " + year_num);
                        }

                        else if(button_num == button_total-1)
                        {
                            year_num = 101; // 전체면 101
                        }

                        if (mode_select == 2) {
                            Intent intent = new Intent(getApplicationContext(), QuizAlphabet.class);
                            intent.putExtra("year_num", year_num); // 연도
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                            startActivityForResult(intent, MainActivity.REQUEST_CODE_QUIZ);

                            // 액티비티 이동시 페이드인아웃 연출
                            //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), DifficultyActivity.class);
                            intent.putExtra("year_num", year_num); // 연도
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                            startActivityForResult(intent, MainActivity.REQUEST_CODE_QUIZ);

                            // 액티비티 이동시 페이드인아웃 연출
                            //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            finish();
                        }
                    }
                }, 600); //딜레이 타임 조절
            }
        });

        year_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_num > 0)
                {
                    View year_v = year_linear.getChildAt(button_num);

                    year_v.setSelected(false);

                    Button year_b = (Button)year_v;
                    year_b.setTextColor(getResources().getColor(R.color.white));
                    year_b.setTextSize(18);

                    button_num--;
                    year_v = year_linear.getChildAt(button_num);
                    year_v.setSelected(true);

                    year_b = (Button)year_v;
                    year_b.setTextColor(getResources().getColor(R.color.black));
                    year_b.setTextSize(24);

                    year_up.setEnabled(false);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(button_num == 0){
                                View year_v = year_linear.getChildAt(button_num);
                                year_scroll.smoothScrollTo(0, year_v.getTop());
                            }
                            else {
                                View year_v = year_linear.getChildAt(button_num - 1);
                                year_scroll.smoothScrollTo(0, year_v.getTop());
                            }

                            year_up.setEnabled(true);

                        }
                    }, 100);
                    Log.e("Button : " ," : " +button_num);
                }

            }
        });


    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.REQUEST_CODE_QUIZ){
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizMain.HIGH_SCORE, 0);
                Log.e("점수를 받았는가33","?" + score);
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

        Intent mainintent = new Intent(this,ModeActivity.class);
        startActivity(mainintent);
        finish();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        MainActivity.mediaplayer_main.pause();
    }

}