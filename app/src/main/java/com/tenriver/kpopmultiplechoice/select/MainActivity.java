package com.tenriver.kpopmultiplechoice.select;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Handler;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.tenriver.kpopmultiplechoice.R;
import com.tenriver.kpopmultiplechoice.SettingDialog;
import com.tenriver.kpopmultiplechoice.quizActivity.QuizChallenge;
import com.tenriver.kpopmultiplechoice.quizActivity.QuizMain;
import com.tenriver.kpopmultiplechoice.quizActivity.quiz_beginner;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_QUIZ = 101;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_MUSIC = "sharedMusic";
    public static final String SHARED_POINT = "sharedPoint";
    public static final String KEY_HIGHSCORE = "keyhighscore";
    public static final String KEY_CHALLENGEHIGHSCORE = "keychallengehighscore";
    public static final String KEY_POINT = "keypoint";
    public static final String KEY_AD_TIME = "adtime";

    public static final String BASICMODE_HIGHSCORE = "basichighscore";
    public static final String CHALLENGEMODE_HIGHSCORE = "challengehighscore";

    private static final String REWARD_AD_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111";

    //로그인 관련
    private static final int RC_SIGN_IN=9001;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_LEADERBOARD_UI = 9004;
    static GoogleSignInAccount googleSignInAccount=null;

    private int beginnerscore;
    private int basichighscore;
    private int challengehighscore;
    private TextView txtbasicHighscore;
    private TextView txtchallengeHighscore;
    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;
    private TextView mvquiz;


    private ImageView imageview_lp;

    private long backPressedTime;

    private ImageButton startButton;
    private ImageButton settingOpen;

    private AdView mAdview;
    private RewardedAd mRewardedAd;
    private boolean isEarned = false;

    SettingDialog settingDialog;
    private float soundPoolVolume;


    AudioManager audioManager;

    //Sound
    SoundPool soundPool;	//작성
    int soundID;		    //작성

    // MediaPlayer 객체생성
    public static MediaPlayer mediaplayer_main;

    //point 관련
    private TextView txtpoint;
    private int pointNow;
    private int pointBeginner;
    private int totalPoint;
    private int ad_time;

    private Toast adToast;

    // 로그인 및 랭킹 관련
    private Button loginbtn;
    private Button submitbtn;
    private Button boardbtn;
    private Button logoutbtn;
    private Button achievementbtn;

    // 전원 버튼 감지
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //텍스트 페이드인
        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));
        mvquiz = (TextView) findViewById((R.id.txtMvQuiz));
        txtpoint = findViewById(R.id.txtPoint);

        Animation textfadein = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_text);
        kpop1.startAnimation(textfadein);
        kpop2.startAnimation(textfadein);
        kpop3.startAnimation(textfadein);
        mvquiz.startAnimation(textfadein);
        txtpoint.startAnimation(textfadein);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        mAdview = findViewById(R.id.mainadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" + BANNER_AD_ID);


        // BGN 실행
        if(mediaplayer_main ==null){
            mediaplayer_main = MediaPlayer.create(this, R.raw.selectmusic_new);
            mediaplayer_main.setLooping(true);
            mediaplayer_main.start();
        }

        SharedPreferences music = getSharedPreferences(SHARED_MUSIC,MODE_PRIVATE);

        Boolean bgmCb_main = music.getBoolean("bgmCb",true);
        Boolean effectCb_main = music.getBoolean("effectCb",true);

        imageview_lp = findViewById(R.id.imageView_lp);
        Animation lpLotate = AnimationUtils.loadAnimation(getApplication(), R.anim.rotate_lp);
        imageview_lp.startAnimation(textfadein);


        if(mediaplayer_main!=null){
            if(!bgmCb_main){
                imageview_lp.clearAnimation();
                mediaplayer_main.setVolume(0,0);
            }

            else{
                mediaplayer_main.setVolume(1,1);
                imageview_lp.startAnimation(lpLotate);
            }
        }

        // 전원 버튼 감지
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    if(mediaplayer_main!=null) {
                        if (mediaplayer_main.isPlaying()) {
                            // BGM 중지
                            mediaplayer_main.pause();
                        }
                    }

                }

            }
        };

        registerReceiver(receiver, intentFilter);


        startButton = findViewById(R.id.Main_start);
        settingOpen = findViewById(R.id.setting_Button);
        txtbasicHighscore = findViewById(R.id.txtbasicbestScore);
        txtchallengeHighscore = findViewById(R.id.txtChallengebestScore);


        txtbasicHighscore.startAnimation(textfadein);
        txtchallengeHighscore.startAnimation(textfadein);


        //Sound
        soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);	//작성
        soundID = soundPool.load(this,R.raw.buttonsound1,1);

        if(soundPool!=null){
            if(!effectCb_main){
                soundPoolVolume=0.0f;
            }

            else{
                soundPoolVolume=0.4f;
            }
        }

        try {
            GamesClient gamesClient = Games.getGamesClient(this,GoogleSignIn.getLastSignedInAccount(this));
            gamesClient.setViewForPopups(findViewById(R.id.content));
            gamesClient.setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

        } catch(Exception e){
            Log.d("로그", "알림 불러오기 실패");
        }



        pointNow = 0;
        pointBeginner = 0;




        // 포인트 받아오기
        loadPoint();

        loadRewardedAd();



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);

                startButton.setEnabled(false);
                settingOpen.setEnabled(false);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), ModeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                        startActivityForResult(intent,REQUEST_CODE_QUIZ);

                        // 액티비티 이동시 페이드인아웃 연출
                        //overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        finish();

                    }
                }, 100); //딜레이 타임 조절
            }
        });


        settingOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                dial();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!mediaplayer_main.isPlaying())
        {
            // BGN 실행
            mediaplayer_main = MediaPlayer.create(this, R.raw.selectmusic_new);
            mediaplayer_main.setLooping(true);
            mediaplayer_main.start();

        }

        loadHighscore();
        loadchallengeHighscore();
        Intent intent = getIntent();
        int score = intent.getIntExtra(QuizMain.HIGH_SCORE, 0);
        int challengescore = intent.getIntExtra(QuizChallenge.CHALLENGE_HIGH_SCORE, 0);

        updatePoint();

        Log.e("점수를 받았는가","?" + score);
        if (score > basichighscore){
            updateHighscore(score);
        }

        if (challengescore > challengehighscore) {
            updatechallengeHighscore(challengescore);
        }


        SharedPreferences music = getSharedPreferences(SHARED_MUSIC,MODE_PRIVATE);

        Boolean bgmCb_main = music.getBoolean("bgmCb",true);
        Boolean effectCb_main = music.getBoolean("effectCb",true);


        if(mediaplayer_main!=null){
            if(!bgmCb_main){
                mediaplayer_main.setVolume(0,0);
            }

            else{
                mediaplayer_main.setVolume(1,1);
            }
        }

        soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);	//작성
        soundID = soundPool.load(this,R.raw.buttonsound1,1);

        if(soundPool!=null){
            if(!effectCb_main){
                soundPoolVolume=0.0f;
            }

            else{
                soundPoolVolume=0.4f;
            }
        }


        startButton.setEnabled(true);
        settingOpen.setEnabled(true);
    }

    private void updateHighscore(int highscoreNew){
        basichighscore = highscoreNew;
        txtbasicHighscore.setText("" + basichighscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE,basichighscore);
        editor.apply();
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        basichighscore = prefs.getInt(KEY_HIGHSCORE, 0);
        if(basichighscore <= 0) {
            txtbasicHighscore.setText("Basic");
        }
        else{
            txtbasicHighscore.setText("" + basichighscore);
        }
    }

    private void loadchallengeHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        challengehighscore = prefs.getInt(KEY_CHALLENGEHIGHSCORE, 0);
        if(challengehighscore <= 0) {
            txtchallengeHighscore.setText("Chall");
        }
        else{
            txtchallengeHighscore.setText("" + challengehighscore);
        }
    }

    private void updatechallengeHighscore(int challengeNew){
        challengehighscore = challengeNew;
        txtchallengeHighscore.setText("" + challengehighscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_CHALLENGEHIGHSCORE,challengehighscore);
        editor.apply();
    }



    private void loadPoint() {
        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        totalPoint = point.getInt(KEY_POINT,100);
        txtpoint.setText(""+totalPoint);

    }

    private void updatePoint() {
        Intent intent = getIntent();
        int score = intent.getIntExtra(QuizMain.HIGH_SCORE, 0);
        int beginnerscore = intent.getIntExtra(quiz_beginner.BEGINNERHIGH_SCORE, 0);
        if(score > 0) {
            pointNow = score / 5;
        }
        else if(beginnerscore > 0){
            pointBeginner = beginnerscore / 5;
        }

        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        totalPoint = totalPoint + pointNow + pointBeginner;
        //totalPoint = 1000;

        SharedPreferences.Editor pointEditor = point.edit();
        pointEditor.putInt(KEY_POINT,totalPoint);
        pointEditor.apply();

        loadPoint();
    }

    private void getAdPoint() {

        Random random = new Random();
        Integer adPoint;
        Integer adRandom = random.nextInt(4);

        if(adRandom == 0){
            adPoint = 60;
            adToast = Toast.makeText(this, getString(R.string.congratulation), Toast.LENGTH_SHORT);
        }

        else{
            adPoint = 30;
            adToast = Toast.makeText(this, getString(R.string.point_30), Toast.LENGTH_SHORT);
        }

        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        totalPoint = totalPoint + adPoint;

        SharedPreferences.Editor pointEditor = point.edit();
        pointEditor.putInt(KEY_POINT,totalPoint);
        pointEditor.apply();

        SharedPreferences adtimes = getSharedPreferences("sharedad",MODE_PRIVATE);

        ad_time = adtimes.getInt(KEY_AD_TIME,0);
        ad_time ++;


        SharedPreferences.Editor adEditor = adtimes.edit();
        adEditor.putInt(KEY_AD_TIME,ad_time);
        adEditor.apply();



        loadPoint();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finish();
            if(mediaplayer_main!=null)
            {
                mediaplayer_main.stop();
                mediaplayer_main.release();
                mediaplayer_main = null;
            }
        }else{
            Toast.makeText(this, getString(R.string.BackQuit), Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }

    private void dial(){
        settingDialog = new SettingDialog(this);
        settingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button cancelBtn = settingDialog.findViewById(R.id.btn_cancel);
        Button confirmBtn = settingDialog.findViewById(R.id.btn_confirm);
        CheckBox bgmCb = settingDialog.findViewById(R.id.backgroundMusicCb);
        CheckBox effectCb = settingDialog.findViewById(R.id.effectCb);

        SharedPreferences music = getSharedPreferences(SHARED_MUSIC,MODE_PRIVATE);

        Boolean bgm_B = music.getBoolean("bgmCb",true);
        Boolean effect_B = music.getBoolean("effectCb",true);

        bgmCb.setChecked(bgm_B);
        effectCb.setChecked(effect_B);

        settingDialog.setCancelable(false); // 밖에 선택해도 창이 안꺼짐
        settingDialog.show();


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                settingDialog.dismiss();

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor musicEditor = music.edit();
                musicEditor.putBoolean("bgmCb", (bgmCb.isChecked()));
                musicEditor.putBoolean("effectCb", (effectCb.isChecked()));
                musicEditor.apply();

                Animation lpLotate = AnimationUtils.loadAnimation(getApplication(), R.anim.rotate_lp);


                if(mediaplayer_main!=null){
                    if(!bgmCb.isChecked()){
                        mediaplayer_main.setVolume(0,0);
                        imageview_lp.clearAnimation();
                    }

                    else{
                        mediaplayer_main.setVolume(1,1);
                        imageview_lp.startAnimation(lpLotate);
                    }
                }

                if(mediaplayer_main!=null){
                    if(!effectCb.isChecked()){
                        soundPoolVolume=0.0f;
                    }

                    else{
                        soundPoolVolume=1.0f;
                    }
                }

                soundPool.play(soundID,soundPoolVolume,soundPoolVolume,0,0,1f);
                settingDialog.dismiss();

            }
        });

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        if(mediaplayer_main!=null) {

            if (mediaplayer_main.isPlaying()) {
                // BGM 중지
                mediaplayer_main.pause();

            }
        }

    }

    // 보상형 광고
    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mRewardedAd.load(this, REWARD_AD_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
                Log.d("로그", "광고 load 완료");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mRewardedAd = null;
                loadRewardedAd();
                Log.d("로그", ""+loadAdError);
            }
        });

    }

    private void showRewardedAd() {
        if (mRewardedAd != null) {

            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    Log.d("로그", "Ad 보여주기 실패");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d("로그", "Ad 보여주기");
                    mRewardedAd = null;
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d("로그", "Ad 닫기");

                    if(isEarned){
                        adToast.show();
                        isEarned = false;
                    }
                    loadRewardedAd();
                }

            });

            mRewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Log.d("로그", "유저가 보상을 받았습니다.");
                    isEarned = true;
                    getAdPoint();
                }
            });

        }
        else {
            Toast.makeText(this, getString(R.string.tryAgain), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaplayer_main!=null) {

            if (!mediaplayer_main.isPlaying()) {
                // BGN 실행
                mediaplayer_main = MediaPlayer.create(this, R.raw.selectmusic_new);
                mediaplayer_main.setLooping(true);
                mediaplayer_main.start();

            }
        }
    }


}