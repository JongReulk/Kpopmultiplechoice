package com.tenriver.kpopmultiplechoice.quizActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class QuizChallenge extends YouTubeBaseActivity {
    public static final String HIGH_SCORE = "highScore";

    private static final long COUNTDOWN_IN_MILLIS = 30500;
    private static final String INTERSTITIAL_AD_ID = "ca-app-pub-3940256099942544/1033173712";

    private static final String QUIZ_SHARED = "quizshared";
    private static final String CHALLENGE_HIGH_SCORE = "challengehighScore";


    YouTubePlayerView playerView;
    YouTubePlayer player;

    //static String API_KEY ="AIzaSyDImlmmX6mnicXNlzed8TH1cn5YN62hBN0"; // 구글 콘솔사이트에서 발급받는 키
    static String API_KEY ="AIzaSyCnt7CWC3z_t_OimQLUwJ5-yXf6C6F83-A";
    static int score_challenge = 0;
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

    boolean isChallengefinish = false;
    boolean isBackPressed = false;
    boolean isFinished = false;
    boolean isError = false;

    private int question_Num;


    // hint button
    private Button HintButton1; // 다시 듣기
    private Button Hint_remove; // 보기 하나 지우기
    private Button Hint_pass; // 문제 패스

    // point
    private static int hintPoint;
    private TextView txtHintPoint;


    // 플레이 횟수
    private int challenge_playtime = 0;

    private ImageView endimage;

    private int randomAd;

    private boolean isLoaded = false;

    private boolean isPassed = false;

    private int currentChallengeHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_challenge);

        Log.d("start","quiz challenge activity start!");

        question_Num = 0;


        if(MainActivity.mediaplayer_main!=null)
        {
            MainActivity.mediaplayer_main.stop();
            MainActivity.mediaplayer_main.release();
            MainActivity.mediaplayer_main = null;
        }

        score_challenge = 0;


        Intent intent = getIntent();

        videoLength = intent.getIntExtra("difficulty_time",10000);

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

        endimage = findViewById(R.id.EndImage);

        // DB 관련 선언
        QuizDbHelper dbHelper = new QuizDbHelper(this);

        // challenge 모드 전용 선언
        plus = 100;
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = 100;


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

        // 힌트 버튼 활성화
        HintButton1.setEnabled(false);


        // 포인트 가져오기
        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        hintPoint = point.getInt(KEY_POINT,100);

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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        Random Adrandom = new Random();
        randomAd = Adrandom.nextInt(3);

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
                    Log.v("포인트부족","포인트가 부족함");
                }
                else {
                    hintPoint = hintPoint - 10;

                    txtHintPoint.setText(""+hintPoint);
                    Toast.makeText(getApplicationContext(), getString(R.string.currentPoint) + hintPoint, Toast.LENGTH_SHORT).show();
                    Log.v("다시 듣기", "다시듣기 버튼 클릭");

                    musicProgressbar.setAlpha(0);

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
                    Toast.makeText(getApplicationContext(), getString(R.string.currentPoint) + hintPoint, Toast.LENGTH_SHORT).show();
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
            @Override
            public void onClick(View v) {
                if (hintPoint < 100){
                    Toast.makeText(getApplicationContext(), getString(R.string.lessPoint), Toast.LENGTH_SHORT).show();

                }
                else {
                    hintPoint = hintPoint - 100;

                    txtHintPoint.setText(""+hintPoint);
                    Toast.makeText(getApplicationContext(), getString(R.string.currentPoint) + hintPoint, Toast.LENGTH_SHORT).show();
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

                if(isChallengefinish){
                    //AdRequest adRequest = new AdRequest.Builder().build();
                    //finishQuiz();
                    isFinished=true;
                    endimage.setVisibility(View.VISIBLE);
                    Animation end_anim = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in);
                    endimage.startAnimation(end_anim);
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showInterstitial();
                        }
                    },2000);
                }

                else {
                    if (questionCounter >= questionCountTotal) {
                        isFinished=true;
                        endimage.setVisibility(View.VISIBLE);
                        Animation end_anim = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in);
                        endimage.startAnimation(end_anim);
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showInterstitial();
                            }
                        },2000);
                    } else {
                        showNextQuestion();

                        playVideo();

                    }
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
                        QuizChallenge.this.screenAd = interstitialAd;

                        if(isFirst){
                            showInterstitial();
                            isFirst = false;
                        }


                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        QuizChallenge.this.screenAd = null;
                        Log.d("로그", loadAdError+" 에러발생");
                        Toast.makeText(getApplicationContext(), getString(R.string.fatalerror), Toast.LENGTH_SHORT).show();
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
                        Log.d("로그", "init player 에러발생"+errorReason);
                        Toast.makeText(getApplicationContext(), getString(R.string.waitasecond), Toast.LENGTH_SHORT).show();

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
        if (questionCounter < questionCountTotal) {
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

            questionCounter ++;
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
            txtCountDown.setTextColor(getResources().getColor(R.color.teal_200));
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
            correctText.setVisibility(View.VISIBLE);
            opSelected.setTextColor(Color.GREEN);
            score_challenge = score_challenge+plus; // 점수 책정 방식
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
                opSelected.setTextColor(Color.RED);
            }
            nextButton.setText(getString(R.string.Finish));
            isChallengefinish = true;
            if(randomAd == 0){

            }
            else{
                LoadAD();
            }

        }

        scoreText.setText(""+score_challenge); // 점수 텍스트 변경
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
        if (questionCounter < questionCountTotal) {
            nextButton.setText(getString(R.string.Next));
        }
        else{
            //LoadAD();
            isFinished=true;
            nextButton.setText(getString(R.string.Finish));
        }
    }


    private void finishQuiz() {
        isHandler = false;

        challenge_playtime++;


        if (isBackPressed) {
            score_challenge = 0;
            challenge_playtime--;
        }

        Intent resultIntent = new Intent(this, MainActivity.class);

        updateHintPoint();
        updateChallengeHighScore();
        startActivity(resultIntent);

        Log.e("최고 점수", ":" + score_challenge);
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
                    QuizChallenge.this.screenAd = null;
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
            screenAd.show(QuizChallenge.this);
            screenAd = null;
        } else {
            Toast.makeText(this, getString(R.string.waitasecond), Toast.LENGTH_SHORT).show();
            Log.e("TAG","NO SHOW!");
            if(!isLoaded) {
                LoadAD();
                showInterstitial();
                isLoaded = true;
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

    private void updateChallengeHighScore() {
        SharedPreferences quizshared = getSharedPreferences(QUIZ_SHARED,MODE_PRIVATE);

        currentChallengeHighscore = quizshared.getInt(CHALLENGE_HIGH_SCORE,0);

        if(score_challenge > currentChallengeHighscore) {
            SharedPreferences.Editor quizEditor = quizshared.edit();
            quizEditor.putInt(CHALLENGE_HIGH_SCORE,score_challenge);
            quizEditor.apply();
        }


    }

}







