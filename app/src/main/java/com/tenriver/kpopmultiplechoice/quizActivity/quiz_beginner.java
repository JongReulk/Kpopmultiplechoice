package com.tenriver.kpopmultiplechoice.quizActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tenriver.kpopmultiplechoice.InternetDialog;
import com.tenriver.kpopmultiplechoice.select.MainActivity;
import com.tenriver.kpopmultiplechoice.ProgressDialog;
import com.tenriver.kpopmultiplechoice.Question;
import com.tenriver.kpopmultiplechoice.QuizDbHelper;
import com.tenriver.kpopmultiplechoice.R;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.tenriver.kpopmultiplechoice.select.MainActivity.KEY_POINT;
import static com.tenriver.kpopmultiplechoice.select.MainActivity.SHARED_POINT;

public class quiz_beginner extends YouTubeBaseActivity {
    public static final String BEGINNERHIGH_SCORE = "beginnerhighScore";
    private long COUNTDOWN_IN_MILLIS = 30500;

    private static final String QUIZ_SHARED = "quizshared";
    private static final String SHARED_MUSIC = "sharedMusic";
    private String QuizHighscore;
    private static final String BABY_HIGH_SCORE = "babyhighscore";
    private static final String CLASSIC_HIGH_SCORE = "classichighscore";
    private static final String MASTER_HIGH_SCORE = "masterhighscore";

    // SharedPreferences 변수선언
    private static final String MODE_SHARED = "modeshared";
    private static final String GAMEMODE_SELECT = "gamemodeselect";
    private static final String YEAR_SELECT = "yearselect";
    private static final String WHICHMODE_SELECT = "whichmodeselect";
    private static final String SINGER_SELECT = "singerselect";

    YouTubePlayerView playerView;
    YouTubePlayer player;

    //static String API_KEY ="AIzaSyDImlmmX6mnicXNlzed8TH1cn5YN62hBN0"; // 구글 콘솔사이트에서 발급받는 키
    static String API_KEY ="AIzaSyCnt7CWC3z_t_OimQLUwJ5-yXf6C6F83-A";
    private String INTERSTITIAL_AD_ID;
    static int score = 0;
    static int plus = 0;
    static int videoLength;// 이지 노말 하드에 따라서 바뀜

    private String question;

    private InterstitialAd screenAd;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private TextView txtQuestionCount;
    private TextView txtCountDown;
    private TextView scoreText;
    private TextView correctText;

    private RadioButton op1;
    private RadioButton op2;
    private RadioButton op3;
    private RadioButton op4;
    private RadioGroup opGroup;
    private RadioButton opRemove;

    private Button confirmButton;
    private Button nextButton;
    private boolean isHandler = true;
    Handler handler;

    private long backPressedTime;

    ProgressDialog customProgressDialog;

    private int max_num_value = 3;
    private int min_num_value = 0;

    private static int randomTotal;
    private static int randomStart;

    private boolean isCountStart;
    private boolean isFirst = true;

    boolean isStarted =true;


    private static int[] arr = {51000,61000,71000};


    private ProgressBar musicProgressbar;


    boolean isBackPressed = false;
    boolean isFinished = false;
    boolean isError = false;

    private int question_Num;


    // hint button
    private Button HintButton1; // 다시 듣기
    private Button Hint_remove; // 보기 하나 지우기
    private Button Hint_pass; // 문제 패스
    
    private boolean isPassed = false; // 패스 힌트 사용 유무

    // point
    private int hintPoint;
    private TextView txtHintPoint;
    private static int pointplus;
    private int scorepointplus;

    private ImageView endimage;

    private int randomAd;
    private int lastrandomAd;

    private boolean isLoaded;

    private boolean isWrong;

    // 선택된 변수들
    private int modenum;
    private int yearnum;
    private String whichmode;
    private String singerselect;


    // 점수 비교용 변수
    private int currentHighScore;

    // 답 효과음
    SoundPool soundPool;	//작성
    int correctSound;		    //작성
    int wrongSound;
    private float soundPoolVolume;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_beginner);

        INTERSTITIAL_AD_ID = getString(R.string.INTERSTITIAL_TEST);

        Log.d("start","quiz main activity start!");

        question_Num = 0;
        pointplus = 0;

        getModeandYear();

        SharedPreferences music = getSharedPreferences(SHARED_MUSIC,MODE_PRIVATE);

        Boolean bgmCb_main = music.getBoolean("bgmCb",true);
        Boolean effectCb_main = music.getBoolean("effectCb",true);

        //Sound
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);	//작성
        wrongSound = soundPool.load(this,R.raw.wrongsound,1);
        correctSound = soundPool.load(this,R.raw.correctsound,1);

        if(soundPool!=null){
            if(!effectCb_main){
                soundPoolVolume=0.0f;
            }

            else{
                soundPoolVolume=1f;
            }
        }


        if(MainActivity.mediaplayer_main!=null)
        {
            MainActivity.mediaplayer_main.stop();
            MainActivity.mediaplayer_main.release();
            MainActivity.mediaplayer_main = null;
        }

        score = 0;


        Intent intent = getIntent();

        videoLength = modenum; // 플레이 시간


        int year_num = yearnum; // 연도

        confirmButton = findViewById(R.id.confirmButton);
        nextButton = findViewById(R.id.nextButton);
        scoreText = findViewById(R.id.scoreText);
        correctText = findViewById(R.id.correctText);
        correctText.setVisibility(View.GONE);

        playerView = findViewById(R.id.youtubeView);


        //textColorDefaultCd = txtCountDown.getTextColors();

        txtQuestionCount = findViewById(R.id.txtQuestionCount);
        txtCountDown = findViewById(R.id.txtCountDown);

        // Hint Button
        HintButton1 = findViewById(R.id.Quiz_hint1);  // 다시 듣기
        Hint_remove = findViewById(R.id.hint_remove);
        Hint_pass = findViewById(R.id.hint_pass);

        op1 = findViewById(R.id.option1);
        op2 = findViewById(R.id.option2);
        op3 = findViewById(R.id.option3);
        op4 = findViewById(R.id.option4);
        opGroup = findViewById(R.id.radio_group);
        textColorDefaultRb = op1.getTextColors();

        txtHintPoint = findViewById(R.id.txt_HintPoint);

        questionCountTotal = 10;

        // DB 관련 선언
        QuizDbHelper dbHelper = new QuizDbHelper(this);

        if(whichmode.equals("year")) {
            if (year_num == 101) {
                questionList = dbHelper.getAllQuestions();
                Random Adrandom = new Random();
                randomAd = Adrandom.nextInt(5);
                lastrandomAd = Adrandom.nextInt(4);
            } else {
                String year_num_string = String.valueOf(year_num);
                Log.e("year_QUIZMAIN", " : " + year_num_string);

                questionList = dbHelper.getQuestions(year_num_string);
                Log.e("Question", " : " + year_num_string);
                Random Adrandom = new Random();
                randomAd = Adrandom.nextInt(5);
                lastrandomAd = Adrandom.nextInt(4);
            }
        }
        else {
            questionList = dbHelper.getSingerQuestion(singerselect);
            questionCountTotal = 5;
            Random Adrandom = new Random();
            randomAd = 1;
            lastrandomAd = Adrandom.nextInt(3);
        }



        Collections.shuffle(questionList);

        // 다음 버튼 비활성화
        nextButton.setEnabled(false);
        nextButton.setTextColor(Color.GRAY);

        isHandler = true;


        // 음악 로딩 다이얼로그 불러오기
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        Random random = new Random();
        int randomInt = random.nextInt(max_num_value - min_num_value);

        randomTotal = arr[randomInt] + videoLength;
        randomStart = arr[randomInt];


        musicProgressbar = findViewById(R.id.musicProgressBar);

        musicProgressbar.setMax(videoLength);

        confirmButton.setEnabled(false);
        confirmButton.setTextColor(Color.GRAY);

        // 힌트 버튼 비활성화
        HintButton1.setEnabled(false);
        Hint_remove.setEnabled(false);
        Hint_pass.setEnabled(false);

        // end Image
        endimage = findViewById(R.id.EndImage);

        // 포인트 가져오기
        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);
        hintPoint = point.getInt(KEY_POINT,100);

        if(videoLength == 10000){
            if(whichmode.equals("year")) {
                plus = 10;
                QuizHighscore = BABY_HIGH_SCORE;
            }
            else {
                plus = 10;
            }
        }

        if(videoLength == 5000){
            plus = 30;
            QuizHighscore = CLASSIC_HIGH_SCORE;
        }

        if(videoLength == 3000){
            plus = 50;
            COUNTDOWN_IN_MILLIS = 15500;
            QuizHighscore = MASTER_HIGH_SCORE;
            hintPoint = hintPoint - 10;
            Toast.makeText(getApplicationContext(), getString(R.string.deducted10points), Toast.LENGTH_SHORT).show();
            updateHintPoint();
        }

        txtHintPoint.setText(""+hintPoint);

        SharedPreferences quiz_shared = getSharedPreferences(QUIZ_SHARED,MODE_PRIVATE);


        InternetDialog internetDialog;
        // 인터넷 연결 확인
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        // 인터넷이 연결되어있을 때
        if(!isConnected){

            internetDialog = new InternetDialog(this);
            internetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Button internetOk = internetDialog.findViewById(R.id.btn_Internetconfirm);

            internetDialog.setCancelable(false); // 밖에 선택해도 창이 안꺼짐
            internetDialog.show();

            internetOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                }
            });
        }


        isLoaded = false;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });



        // 광고 부분

        if(randomAd == 0){
            LoadAD();
        }
        else{
            isFirst = false;
            initPlayer();
            showNextQuestion();
        }


        op1.setSingleLine(true);
        op1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        op2.setSingleLine(true);
        op2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        op3.setSingleLine(true);
        op3.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        op4.setSingleLine(true);
        op4.setEllipsize(TextUtils.TruncateAt.MARQUEE);



        // 텍스트 길이가 길 경우 선택된 보기만 MARQUEE 나머지는 NOTMARQUEE
        opGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // MARQUEE 효과 초기화
                op1.setSelected(false);
                op2.setSelected(false);
                op3.setSelected(false);
                op4.setSelected(false);
                switch (checkedId) {
                    case R.id.option1:
                        Log.d("옵션1", " 선택 !");
                        op1.setSelected(true);
                        op2.setSelected(false);
                        op3.setSelected(false);
                        op4.setSelected(false);
                        break;
                    case R.id.option2:
                        Log.d("옵션2", " 선택 !");
                        op1.setSelected(false);
                        op2.setSelected(true);
                        op3.setSelected(false);
                        op4.setSelected(false);
                        break;
                    case R.id.option3:
                        Log.d("옵션3", " 선택 !");
                        op1.setSelected(false);
                        op2.setSelected(false);
                        op3.setSelected(true);
                        op4.setSelected(false);
                        break;
                    case R.id.option4:
                        Log.d("옵션4", " 선택 !");
                        op1.setSelected(false);
                        op2.setSelected(false);
                        op3.setSelected(false);
                        op4.setSelected(true);
                        break;
                }


            }
        });



        // 다시 듣기
        HintButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintPoint < 10){
                    Toast.makeText(getApplicationContext(), getString(R.string.lessPoint), Toast.LENGTH_SHORT).show();

                }
                else {
                    hintPoint = hintPoint - 10;

                    txtHintPoint.setText(""+hintPoint);
                    Toast.makeText(getApplicationContext(), getString(R.string.listenagain), Toast.LENGTH_SHORT).show();
                    Log.v("다시 듣기", "다시듣기 버튼 클릭");

                    musicProgressbar.setAlpha(0.0f);

                    isStarted = true;

                    if (player.isPlaying()) {
                        player.pause();
                    }
                    HintButton1.setEnabled(false);


                    playVideo();
                }

            }
        });

        // 보기 지우기 힌트
        Hint_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintPoint < 50){
                    Toast.makeText(getApplicationContext(), getString(R.string.lessPoint), Toast.LENGTH_SHORT).show();

                }
                else {
                    hintPoint = hintPoint - 50;

                    txtHintPoint.setText(""+hintPoint);

                    Toast.makeText(getApplicationContext(), getString(R.string.Removeoption), Toast.LENGTH_SHORT).show();
                    Log.v("보기 지우기", "보기 지우기 버튼 클릭");

                    int randomRemove;

                    while(true) {
                        Random Adrandom = new Random();
                        randomRemove = Adrandom.nextInt(4) + 1;

                        int hintAnswer = currentQuestion.getAnswerNr();
                        if (hintAnswer == randomRemove) {
                            Log.d("로그", " 숫자가 같습니다. ");
                        }
                        else {
                            Log.d("로그", " remove 힌트 : " + randomRemove);
                            break;
                        }
                    }

                    if (randomRemove == 1) {
                        opRemove = op1;
                    }
                    else if (randomRemove ==2 ) {
                        opRemove = op2;
                    }
                    else if (randomRemove ==3 ) {
                        opRemove = op3;
                    }
                    else {
                        opRemove = op4;
                    }

                    opRemove.setEnabled(false);
                    opRemove.setTextColor(Color.GRAY);
                }

                Hint_remove.setEnabled(false);

            }
        });

        // 보기 지우기 힌트
        Hint_pass.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (hintPoint < 100){
                    Toast.makeText(getApplicationContext(), getString(R.string.lessPoint), Toast.LENGTH_SHORT).show();

                }
                else {
                    hintPoint = hintPoint - 100;

                    txtHintPoint.setText(""+hintPoint);
                    Toast.makeText(getApplicationContext(), getString(R.string.passquestion), Toast.LENGTH_SHORT).show();
                    Log.v("문제 패스", "패스 버튼 클릭");

                    isPassed = true;

                    isStarted = true;
                    musicProgressbar.setAlpha(0.0f);

                    if (player.isPlaying()) {
                        player.pause();
                    }

                    countDownTimer.cancel();
                    showNextQuestion();
                    playVideo();
                }

                Hint_pass.setEnabled(false);

            }
        });

        // 정답 확인 버튼 checkAnswer 함수 불러오기
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (op1.isChecked() || op2.isChecked() || op3.isChecked() || op4.isChecked()) {

                    musicProgressbar.setAlpha(0.0f);

                    //musicProgressbar.clearAnimation();
                    //musicProgressbar.setProgress(musicProgressbar.getMax());
                    checkAnswer();
                }
                else {
                    Toast.makeText(getApplicationContext(),getString(R.string.chooseAnswer), Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()){
                    player.pause();
                }
                // 터치 가능하게 변경
                op1.setEnabled(true);
                op2.setEnabled(true);
                op3.setEnabled(true);
                op4.setEnabled(true);

                // 선택 초기화 ( MARQUEE 방지)
                op1.setSelected(false);
                op2.setSelected(false);
                op3.setSelected(false);
                op4.setSelected(false);
                op1.setChecked(false);
                op2.setChecked(false);
                op3.setChecked(false);
                op4.setChecked(false);

                timeLeftInMillis = COUNTDOWN_IN_MILLIS;
                txtHintPoint.setText(""+hintPoint);

                playerView.setAlpha(1.0f);

                nextButton.setEnabled(false);
                nextButton.setTextColor(Color.GRAY);


                if(question_Num >= questionCountTotal)
                {
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.pause();
                        }
                    }

                    endimage.setVisibility(View.VISIBLE);
                    Animation end_anim = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in);
                    endimage.startAnimation(end_anim);

                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("로그", "랜덤 값" + lastrandomAd);
                            if(lastrandomAd == 0) {
                                finishQuiz();
                            }
                            // 광고 부분
                            else {
                                showInterstitial();
                            }
                            //finishQuiz();
                        }
                    },2000);

                }
                else {
                    showNextQuestion();

                    playVideo();

                }
            }

        });
    } // onCreate

    private void LoadAD() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,INTERSTITIAL_AD_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        quiz_beginner.this.screenAd = interstitialAd;

                        if(isFirst){
                            showInterstitial();
                            isFirst = false;
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        quiz_beginner.this.screenAd = null;

                        if (isFinished){
                            finishQuiz();
                        }
                        initPlayer();
                        showNextQuestion();
                    }

                });
    }

    public void initPlayer() {

        // YouTubePlayerView 초기화하기
        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {
                        // 로딩창 구현
                        customProgressDialog.show();
                    }

                    @Override
                    public void onLoaded(String s) {
                        Log.e("PlayerView", "onLoaded 호출됨: " + s);

                    }

                    @Override
                    public void onAdStarted() {}

                    @Override
                    public void onVideoStarted() {
                        customProgressDialog.dismiss();
                    }

                    @Override
                    public void onVideoEnded() {}

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {
                        customProgressDialog.dismiss();
                        Log.d("로그", "init player 에러발생"+errorReason);
                        Toast.makeText(getApplicationContext(), getString(R.string.mverror), Toast.LENGTH_LONG).show();

                        isError = true;

                        showNextQuestion();
                        playVideo();

                    }
                });

                playVideo();


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // 못 불러왔을 때
                Log.e("Fail!!","");
            }
        });
    }

    public void playVideo() {
        if (player != null) {
            if (player.isPlaying()) {
                player.pause();

//                player.cueVideo(videoId); // 여기에 있으면 동영상 재생이 안됨.
            }

            // 인터넷 연결 확인
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            // 인터넷이 연결되어있을 때
            if(!isConnected){
                Toast.makeText(this,getString(R.string.videointernet),Toast.LENGTH_SHORT).show();
            }

            player.loadVideo(question, randomStart);


            ObjectAnimator progressAnimation = ObjectAnimator.ofInt(musicProgressbar, "progress", 0,musicProgressbar.getMax() );
            progressAnimation.setDuration(musicProgressbar.getMax());
            progressAnimation.setInterpolator(new LinearInterpolator());

            // 지정 시간동안 동영상 재생하기
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(isHandler)
                    {
                        if (player.getCurrentTimeMillis() <= randomTotal ) {
                            if(player.isPlaying()){
                                //musicProgressbar.incrementProgressBy(1000);
                                startCountDown();
                                if(isStarted){
                                    Log.e("START", " ANI ");
                                    musicProgressbar.setAlpha(1);
                                    progressAnimation.start();
                                    isStarted=false;
                                }
                            }
                            handler.postDelayed(this, 1000);

                        } else {
                            handler.removeCallbacks(this);
                            player.pause();
                            musicProgressbar.setProgress(musicProgressbar.getMax());
                            musicProgressbar.clearAnimation();
                        }
                    }

                    else{
                        handler.removeCallbacks(this);
                    }

                }
            }, 1000);

        }


    }


    private void showNextQuestion() {
        if (question_Num <= questionCountTotal) {
            op1.setTextColor(textColorDefaultRb);
            op2.setTextColor(textColorDefaultRb);
            op3.setTextColor(textColorDefaultRb);
            op4.setTextColor(textColorDefaultRb);
            opGroup.clearCheck();

            currentQuestion = questionList.get(questionCounter);
            question = currentQuestion.getQuestion(); // 유튜브 url
            op1.setText(currentQuestion.getOption1()); // 1번 보기
            op2.setText(currentQuestion.getOption2());
            op3.setText(currentQuestion.getOption3());
            op4.setText(currentQuestion.getOption4());

            if(!isError){
                question_Num++;
            }
            else{
                isError = false;
            }

            if(isPassed) {
                question_Num--;
                isPassed = false;
            }
            txtQuestionCount.setText(question_Num + " / " + questionCountTotal); // 문제 수 확인
            confirmButton.setText(getString(R.string.Confirm));


            //카운트 다운 시작
            isCountStart = true;

            musicProgressbar.setProgress(0);
            isStarted = true;

            checkLast();
            questionCounter ++;
        }

    }

    private void startCountDown() {

        if(isCountStart) {
            // 힌트 버튼 활성화
            HintButton1.setEnabled(true);
            Hint_remove.setEnabled(true);
            Hint_pass.setEnabled(true);

            // 포인트 부족할 시 힌트 버튼 비활성화
            if (hintPoint < 100 ) {
                Hint_pass.setEnabled(false);
                if (hintPoint < 50) {
                    Hint_remove.setEnabled(false);
                }
                if (hintPoint < 10) {
                    HintButton1.setEnabled(false);
                }
            }

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // confirm 버튼 누를 수 있음
                    confirmButton.setEnabled(true);
                    confirmButton.setTextColor(Color.WHITE);
                }
            },1000);

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    timeLeftInMillis = 0;
                    updateCountDownText();
                    checkAnswer();
                }
            }.start();
        }
        else{
        }
        isCountStart=false;
    }

    private void updateCountDownText(){
        int minutes = (int)(timeLeftInMillis / 1000 ) / 60;
        int seconds = (int)(timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.KOREA, "%02d", seconds);

        txtCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            txtCountDown.setTextColor(getResources().getColor(R.color.hard_color));
        }
        else{
            txtCountDown.setTextColor(getResources().getColor(R.color.greencombination));
        }
    }

    // 영어인지 한글인지 확인
    // 영어면 소문자로 변환하고 검사
    // 한글이면 바로 검사
    private void checkAnswer() {
        if(player.isPlaying()){
            player.pause();
        }
        countDownTimer.cancel(); // 타이머 종료
        txtHintPoint.setText("POINT");

        // 힌트 버튼 비활성화
        HintButton1.setEnabled(false);
        Hint_remove.setEnabled(false);
        Hint_pass.setEnabled(false);

        playerView.setAlpha(1.0f);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // next 버튼 누를 수 있음
                nextButton.setEnabled(true);
                nextButton.setTextColor(Color.WHITE);
            }
        },1000);

        confirmButton.setEnabled(false);
        confirmButton.setTextColor(Color.GRAY);

        RadioButton opSelected = findViewById(opGroup.getCheckedRadioButtonId());
        int answerNr = opGroup.indexOfChild(opSelected) + 1;

        // 보기 안에 있는 내용 보고 정답 확인
        if (answerNr == currentQuestion.getAnswerNr()) {
            soundPool.play(correctSound,soundPoolVolume,soundPoolVolume,0,0,1f);
            correctText.setVisibility(View.VISIBLE);
            opSelected.setTextColor(Color.GREEN);
            score = score+plus; // 점수 책정 방식
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    correctText.setVisibility(View.GONE);

                }
            }, 1000);
        }
        

        else {
            if(opSelected !=null) {
                soundPool.play(wrongSound,soundPoolVolume,soundPoolVolume,0,0,1f);
                opSelected.setTextColor(Color.RED);
                isWrong = true;
            }

        }

        scoreText.setText(""+score); // 점수 텍스트 변경
        //confirmButton.setVisibility(View.GONE);

        playVideo(); // 비디오 재생
        //nextButton.setVisibility(View.VISIBLE);
        showSolution();
    }

    private void showSolution() {

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                op1.setTextColor(Color.GREEN);
                op1.setSelected(true);
                break;
            case 2:
                op2.setTextColor(Color.GREEN);
                op2.setSelected(true);
                break;
            case 3:
                op3.setTextColor(Color.GREEN);
                op3.setSelected(true);
                break;
            case 4:
                op4.setTextColor(Color.GREEN);
                op4.setSelected(true);
                break;
        }
        op1.setEnabled(false);
        op2.setEnabled(false);
        op3.setEnabled(false);
        op4.setEnabled(false);
        checkLast();
    }

    // 마지막 문제인지 체크
    private void checkLast(){
        if (question_Num < questionCountTotal) {
            nextButton.setText(getString(R.string.Next));
        }
        else{
            // 광고 부분

            if(randomAd == 0){

            }
            else{
                LoadAD();
            }

            isFinished=true;
            nextButton.setText(getString(R.string.Finish));
        }
    }


    private void finishQuiz() {
        isHandler = false;
        pointplus = 0;

        if (plus == 10) {
            if(whichmode.equals("year")) {
                pointplus = 20;
            }
            else {
                pointplus = 0;
            }
        }
        else if (plus == 30) {
            pointplus = 30;
        }

        if (isBackPressed) {
            score = 0;
            pointplus = 0;
        }

        Intent resultIntent = new Intent(this, MainActivity.class);

        updateQuizHighScore();
        updateHintPoint();
        startActivity(resultIntent);

        Log.e("최고 점수", ":" + score);
        //setResult(RESULT_OK, resultIntent);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    private void updateHintPoint() {

        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        hintPoint = hintPoint + pointplus;
        SharedPreferences.Editor pointEditor = point.edit();
        pointEditor.putInt(KEY_POINT,hintPoint);
        pointEditor.apply();
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            isBackPressed = true;
            finishQuiz();

        }else{
            Toast.makeText(this, getString(R.string.BackQuit), Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }



    private void showInterstitial() {
        if (screenAd != null) {
            screenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    quiz_beginner.this.screenAd = null;
                    if (isFinished){
                        finishQuiz();
                    }

                    else{
                        initPlayer();
                        showNextQuestion();
                    }
                    LoadAD();
                }
            });
            screenAd.show(quiz_beginner.this);
            screenAd = null;
        } else {
            Log.e("TAG","NO SHOW!");
            if(!isLoaded) {
                isLoaded = true;
                LoadAD();
                showInterstitial();

            }
            else {
                if (isFinished){
                    finishQuiz();
                }

                else{
                    initPlayer();
                    showNextQuestion();
                }
                LoadAD();
            }
        }

    }

    private void getModeandYear() {
        // 모드 및 연도 가져오기
        SharedPreferences modeandyear = getSharedPreferences(MODE_SHARED,MODE_PRIVATE);

        modenum = modeandyear.getInt(GAMEMODE_SELECT,10000);

        yearnum = modeandyear.getInt(YEAR_SELECT,2020);

        whichmode = modeandyear.getString(WHICHMODE_SELECT, "year");

        singerselect = modeandyear.getString(SINGER_SELECT,"BLACKPINK");
    }

    private void updateQuizHighScore() {
        SharedPreferences quizshared = getSharedPreferences(QUIZ_SHARED,MODE_PRIVATE);

        currentHighScore = quizshared.getInt(QuizHighscore,0);

        if(score > currentHighScore) {
            SharedPreferences.Editor quizEditor = quizshared.edit();
            quizEditor.putInt(QuizHighscore,score);
            quizEditor.apply();
        }


    }

}







