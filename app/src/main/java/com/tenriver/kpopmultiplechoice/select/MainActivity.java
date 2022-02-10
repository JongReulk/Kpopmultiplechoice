package com.tenriver.kpopmultiplechoice.select;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
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

    // 각 모드 별 퀴즈 점수
    private static final String QUIZ_SHARED = "quizshared";
    private static final String BABY_HIGH_SCORE = "babyhighscore";
    private static final String CLASSIC_HIGH_SCORE = "classichighscore";
    private static final String MASTER_HIGH_SCORE = "masterhighscore";
    private static final String CHALLENGE_HIGH_SCORE = "challengehighScore";
    private static final String TOTAL_HIGH_SCORE = "totalhighscore";
    private int totalHighScore;

    private int baby_highscore;
    private int classic_highscore;
    private int master_highscore;
    private int challengehighscore;

    private String REWARD_AD_ID;
    private String BANNER_AD_ID;

    //로그인 관련
    private static final int RC_SIGN_IN=9001;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_LEADERBOARD_UI = 9004;
    static GoogleSignInAccount googleSignInAccount=null;

    private TextView kpop1;
    private TextView kpop2;
    private TextView kpop3;
    private TextView mvquiz;

    private TextView multiplechoice;


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

    // 전원 버튼 감지
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;

    // 인앱 업데이트
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;

    // 앱 리뷰 작성
    private ReviewInfo reviewInfo;
    private ReviewManager reviewManager;
    private ImageButton ratingButton;

    // 리워드 광고
    private Button rewardButton;

    // 로그인 및 랭킹 관련
    private ImageButton loginButton;
    private ImageButton logoutButton;
    private ImageButton rankButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        BANNER_AD_ID = getString(R.string.BANNER_TEST);
        REWARD_AD_ID = getString(R.string.REWARD_TEST);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //텍스트 페이드인
        kpop1 = (TextView) findViewById((R.id.txtTitle1));
        kpop2 = (TextView) findViewById((R.id.txtTitle2));
        kpop3 = (TextView) findViewById((R.id.txtTitle3));
        mvquiz = (TextView) findViewById((R.id.txtMvQuiz));
        multiplechoice = findViewById(R.id.Mainmultiplechoice);
        txtpoint = findViewById(R.id.txtPoint);

        // 로그인 및 랭킹 관련
        loginButton = findViewById(R.id.loginBtn);
        logoutButton = findViewById(R.id.logoutBtn);
        rankButton = findViewById(R.id.scoreRank);

        Animation textfadein = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_text);
        kpop1.startAnimation(textfadein);
        kpop2.startAnimation(textfadein);
        kpop3.startAnimation(textfadein);
        mvquiz.startAnimation(textfadein);
        multiplechoice.startAnimation(textfadein);
        txtpoint.startAnimation(textfadein);
        loginButton.startAnimation(textfadein);
        logoutButton.startAnimation(textfadein);
        rankButton.startAnimation(textfadein);

        rewardButton = findViewById(R.id.Reward_Button);

        ratingButton = findViewById(R.id.rating_Button);
        
        loginButton.setVisibility(View.VISIBLE);
        logoutButton.setVisibility(View.GONE);
        rankButton.setVisibility(View.GONE);

        // 인앱 업데이트
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this,
                                RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //mAppUpdateManager.registerListener(installStateUpdatedListener);

        // 리뷰 활성화
        activateReviewInfo();

        // 퀴즈 별 하이스코어 확인 후 토탈 점수 갱신
        updateQuizHighscore();

        // 토탈점수 submit
        submitTotalScore();
        
        // 자동 로그인
        signInSilently();



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        // 광고 부분

        mAdview = findViewById(R.id.mainadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" + BANNER_AD_ID);



        // BGN 실행
        if(mediaplayer_main ==null){
            mediaplayer_main = MediaPlayer.create(this, R.raw.loveaside_bgm);
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

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewFlow();
            }
        });

        rewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewardedAd();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinIntent();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });

        rankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rank();
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

        Intent intent = getIntent();

        updatePoint();


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

    private void updateQuizHighscore(){
        SharedPreferences quizshared = getSharedPreferences(QUIZ_SHARED,MODE_PRIVATE);
        baby_highscore = quizshared.getInt(BABY_HIGH_SCORE,0);
        classic_highscore = quizshared.getInt(CLASSIC_HIGH_SCORE,0);
        master_highscore = quizshared.getInt(MASTER_HIGH_SCORE,0);
        challengehighscore = quizshared.getInt(CHALLENGE_HIGH_SCORE,0);

        totalHighScore = baby_highscore + classic_highscore
                + master_highscore + challengehighscore;
        SharedPreferences.Editor editor = quizshared.edit();
        Log.d("베이비", " 점수"+ baby_highscore);
        Log.d("클래식", " 점수"+ classic_highscore);
        Log.d("마스터", " 점수"+ master_highscore);
        Log.d("챌린지", " 점수"+ challengehighscore);
        Log.d("토탈", " 점수"+ totalHighScore);
        editor.putInt(TOTAL_HIGH_SCORE,totalHighScore);
        editor.apply();
    }



    private void loadPoint() {
        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        totalPoint = point.getInt(KEY_POINT,100);
        txtpoint.setText(""+totalPoint +" POINT");

    }

    private void updatePoint() {
        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

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
            adPoint = 50;
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

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if(result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this,
                                RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if(mediaplayer_main!=null) {

            if (!mediaplayer_main.isPlaying()) {
                // BGN 실행
                mediaplayer_main = MediaPlayer.create(this, R.raw.selectmusic_new);
                mediaplayer_main.setLooping(true);
                mediaplayer_main.start();

            }
        }
    }

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState state) {
            if(state.installStatus() == InstallStatus.DOWNLOADED){
                showCompletedUpdate();
            }
        }
    };

    @Override
    protected void onStop() {
        //if(mAppUpdateManager != null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }

    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New app is Ready!",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RC_APP_UPDATE && resultCode != RESULT_OK){
        }

        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
                // 로그인 성공시
                loginButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);
                rankButton.setVisibility(View.VISIBLE);
                Toast.makeText(this, getString(R.string.loginsucceed), Toast.LENGTH_SHORT);
            } else {
                String message = result.getStatus().getStatusMessage();
                Toast.makeText(this, getString(R.string.loginFail), Toast.LENGTH_SHORT);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void activateReviewInfo() {
        reviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> managerInfoTask = reviewManager.requestReviewFlow();
        managerInfoTask.addOnCompleteListener((task)-> {
            if(task.isSuccessful()){
                reviewInfo = task.getResult();
            }
            else
            {
                //Toast.makeText(this, "Review Failed to start", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void startReviewFlow() {
        if(reviewInfo != null)
        {
            Task<Void> flow = reviewManager.launchReviewFlow(this,reviewInfo);
            flow.addOnCompleteListener(task -> {
                //Toast.makeText(this, "Rating is completed", Toast.LENGTH_SHORT).show();
            });
        }
    }


    private void submitTotalScore() {
        SharedPreferences quizshared = getSharedPreferences(QUIZ_SHARED,MODE_PRIVATE);
        int newTotalScore;
        newTotalScore = quizshared.getInt(TOTAL_HIGH_SCORE,0);


        try {
            Games.getLeaderboardsClient(this,GoogleSignIn.getLastSignedInAccount(this)).submitScore(getString(R.string.leaderboard_kpop_mv_quiz__multiple_choice_ranking), newTotalScore);
            Toast.makeText(this, getString(R.string.updateSuccess), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    private void rank() {
        Games.getLeaderboardsClient(this,GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_kpop_mv_quiz__multiple_choice_ranking))
                .addOnSuccessListener(intent -> {
                    startActivityForResult(intent,RC_LEADERBOARD_UI);
                });
    }

    private void signinIntent() {
        GoogleSignInClient signInclient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent signIntent = signInclient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void signout() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this, task -> {
            // 로그아웃 성공시
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            rankButton.setVisibility(View.GONE);

        });
    }

    private void signInSilently() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(GoogleSignIn.hasPermissions(account,signInOptions.getScopeArray())) {
            // 이미 로그인됨
            googleSignInAccount=account;
            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            rankButton.setVisibility(View.VISIBLE);
            Toast.makeText(this, getString(R.string.loginsucceed), Toast.LENGTH_SHORT).show();

        } else {
            // 로그인 안됨
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            rankButton.setVisibility(View.GONE);
            Toast.makeText(this,getString(R.string.offline),Toast.LENGTH_SHORT).show();
        }

    }


}