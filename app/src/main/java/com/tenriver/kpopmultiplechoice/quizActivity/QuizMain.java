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

public class QuizMain extends YouTubeBaseActivity {
    public static final String HIGH_SCORE = "highScore";
    private static final long COUNTDOWN_IN_MILLIS = 30500;

    private static final String QUIZ_SHARED = "quizshared";
    private static final String BASIC_PLAY_TIME = "basicplaytime";

    YouTubePlayerView playerView;
    YouTubePlayer player;

    //static String API_KEY ="AIzaSyDImlmmX6mnicXNlzed8TH1cn5YN62hBN0"; // 구글 콘솔사이트에서 발급받는 키
    static String API_KEY ="AIzaSyCnt7CWC3z_t_OimQLUwJ5-yXf6C6F83-A";
    private static final String INTERSTITIAL_AD_ID = "ca-app-pub-3940256099942544/1033173712";
    static int score = 0;
    static int plus = 0;
    static int pointplus = 0;
    static int videoLength;// 이지 노말 하드에 따라서 바뀜
    
    private String question;
    private String korean_Answer;
    private String english_Answer;
    private String real_Answer;
    private String hint_all;

    InternetDialog internetDialog;

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
    private EditText answerText;
    private TextView hintText;
    
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

    private ImageView leftSpeaker;
    private ImageView rightSpeaker;

    boolean isStarted =true;


    private static int[] arr = {51000,61000,71000};


    private ProgressBar musicProgressbar;

    private TextView txt_answer;


    boolean isBackPressed = false;
    boolean isFinished = false;
    boolean isError = false;

    private int question_Num;


    // hint button
    private Button HintButton1; // 다시 듣기
    private Button HintButton2; // 뮤비 보기
    private Button HintButton3; // 초성 보기

    // point
    private static int hintPoint;
    private TextView txtHintPoint;

    // 플레이 횟수
    private int basic_playtime = 0;

    private ImageView endimage;

    private int randomAd;

    private boolean isLoaded = false;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        Log.d("start","quiz main activity start!");

        question_Num = 0;


        if(MainActivity.mediaplayer_main!=null)
        {
            MainActivity.mediaplayer_main.stop();
            MainActivity.mediaplayer_main.release();
            MainActivity.mediaplayer_main = null;
        }

        score = 0;


        Intent intent = getIntent();

        videoLength = intent.getIntExtra("difficulty_time",10000);


        try {
            GamesClient gamesClient = Games.getGamesClient(this,GoogleSignIn.getLastSignedInAccount(this));
            gamesClient.setViewForPopups(findViewById(R.id.content));
            gamesClient.setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

        } catch(Exception e){
            Log.d("로그", "알림 불러오기 실패");
        }



        if(videoLength == 10000){
            plus = 10;
        }

        if(videoLength == 5000){
            plus = 20;
        }

        if(videoLength == 3000){
            plus = 30;
        }



        int year_num = intent.getIntExtra("year_num",2020);


        confirmButton = findViewById(R.id.confirmButton);
        nextButton = findViewById(R.id.nextButton);
        scoreText = findViewById(R.id.scoreText);
        correctText = findViewById(R.id.correctText);
        correctText.setVisibility(View.GONE);

        playerView = findViewById(R.id.youtubeView);


        answerText = findViewById(R.id.editText);
        txt_answer = findViewById(R.id.txt_answer);

        txt_answer.setVisibility(View.GONE);

        answerText.getBackground().setColorFilter(null);
        //textColorDefaultCd = txtCountDown.getTextColors();

        txtQuestionCount = findViewById(R.id.txtQuestionCount);
        txtCountDown = findViewById(R.id.txtCountDown);

        // Hint Button
        HintButton1 = findViewById(R.id.Quiz_hint1);  // 다시 듣기
        HintButton2 = findViewById(R.id.Quiz_hint2);  // 뮤비 보기
        HintButton3 = findViewById(R.id.Quiz_hint3);  // 초성 보기

        hintText = findViewById(R.id.txt_hintall);
        txtHintPoint = findViewById(R.id.txt_HintPoint);

        // end 이미지
        endimage = findViewById(R.id.EndImage);

        // DB 관련 선언
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        if(year_num == 101){
            questionList = dbHelper.getAllQuestions();
        }
        else {
            String year_num_string = String.valueOf(year_num);
            Log.e("year_QUIZMAIN", " : " +year_num_string);

            questionList = dbHelper.getQuestions(year_num_string);
            Log.e("Question", " : "+year_num_string);
        }

        questionCountTotal = 10;



        Collections.shuffle(questionList);

        // 스피커 애니메이션
        leftSpeaker = findViewById(R.id.imageView_speakerleft);
        rightSpeaker = findViewById(R.id.imageView_speakerright);

        Animation speakerleft_anim = AnimationUtils.loadAnimation(getApplication(), R.anim.speaker_leftanim);

        leftSpeaker.startAnimation(speakerleft_anim);
        Animation speakerright_anim = AnimationUtils.loadAnimation(getApplication(), R.anim.speaker_rightanim);
        rightSpeaker.startAnimation(speakerright_anim);

        // 다음 버튼 비활성화
        nextButton.setEnabled(false);
        nextButton.setTextColor(Color.GRAY);

        isHandler = true;

        ImageView remote_quiz = (ImageView) findViewById(R.id.Remote_quizmain);
        ConstraintLayout remotebutton_quiz = (ConstraintLayout) findViewById(R.id.Remote_button_quizmain);

        Animation RemoteUp = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_up);
        remote_quiz.startAnimation(RemoteUp);

        Animation RemoteButtonUp = AnimationUtils.loadAnimation(getApplication(), R.anim.remotemove_up);
        remotebutton_quiz.startAnimation(RemoteButtonUp);

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
        HintButton2.setEnabled(false);
        HintButton3.setEnabled(false);

        hintText.setVisibility(View.GONE);
        
        // 포인트 가져오기
        SharedPreferences point = getSharedPreferences(SHARED_POINT,MODE_PRIVATE);

        hintPoint = point.getInt(KEY_POINT,100);

        txtHintPoint.setText(""+hintPoint);

        SharedPreferences quiz_shared = getSharedPreferences(QUIZ_SHARED,MODE_PRIVATE);

        basic_playtime = quiz_shared.getInt(BASIC_PLAY_TIME,0);


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
                    Toast.makeText(getApplicationContext(), getString(R.string.currentPoint) + hintPoint, Toast.LENGTH_SHORT).show();
                    Log.v("다시 듣기", "다시듣기 버튼 클릭");

                    musicProgressbar.setAlpha(0);

                    isStarted = true;

                    if (player.isPlaying()) {
                        player.pause();
                    }
                    HintButton1.setEnabled(false);

                    leftSpeaker.startAnimation(speakerleft_anim);
                    rightSpeaker.startAnimation(speakerright_anim);

                    playVideo();
                }

            }
        });

        // 뮤비 보기
        HintButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintPoint < 20){
                    Toast.makeText(getApplicationContext(), getString(R.string.lessPoint), Toast.LENGTH_SHORT).show();

                }

                else {
                    hintPoint = hintPoint - 20;

                    txtHintPoint.setText(""+hintPoint);
                    Toast.makeText(getApplicationContext(), getString(R.string.currentPoint) + hintPoint, Toast.LENGTH_SHORT).show();
                    Log.v("뮤비 보기", "뮤비보기 버튼 클릭");

                    playerView.setAlpha(1.0f);

                    HintButton2.setEnabled(false);
                    HintButton3.setEnabled(false);
                }

            }
        });

        // 초성 보기
        HintButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintPoint < 30) {
                    Toast.makeText(getApplicationContext(), getString(R.string.lessPoint), Toast.LENGTH_SHORT).show();
                    Log.v("초성 보기", "초성보기 버튼 클릭");
                }

                else {
                    hintPoint = hintPoint - 30;

                    txtHintPoint.setText(""+hintPoint);

                    Toast.makeText(getApplicationContext(), getString(R.string.currentPoint) + hintPoint, Toast.LENGTH_SHORT).show();

                    hintText.setVisibility(View.VISIBLE);
                    hintText.setText(hint_all);

                    hintText.setSingleLine(true);
                    hintText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    hintText.setSelected(true);

                    HintButton2.setEnabled(false);
                    HintButton3.setEnabled(false);
                }

            }
        });





        // 정답 확인 버튼 checkAnswer 함수 불러오기
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                musicProgressbar.setAlpha(0.0f);
                checkAnswer();
                leftSpeaker.startAnimation(speakerleft_anim);
                rightSpeaker.startAnimation(speakerright_anim);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()){
                    player.pause();
                }
                timeLeftInMillis = COUNTDOWN_IN_MILLIS;


                hintText.setVisibility(View.GONE);



                playerView.setAlpha(0.0f);

                txt_answer.setBackground(null);

                nextButton.setEnabled(false);
                nextButton.setTextColor(Color.GRAY);
                answerText.setText("");
                answerText.setVisibility(View.VISIBLE);
                txt_answer.setVisibility(View.GONE);




                if(questionCounter >= questionCountTotal)
                {
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

                    showNextQuestion();
                    leftSpeaker.startAnimation(speakerleft_anim);
                    rightSpeaker.startAnimation(speakerright_anim);

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
                        QuizMain.this.screenAd = interstitialAd;

                        if(isFirst){
                            showInterstitial();
                            isFirst = false;
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        QuizMain.this.screenAd = null;
                        Toast.makeText(getApplicationContext(), getString(R.string.waitasecond), Toast.LENGTH_SHORT).show();
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
                            rightSpeaker.clearAnimation();
                            leftSpeaker.clearAnimation();
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
            currentQuestion = questionList.get(questionCounter);
            question = currentQuestion.getQuestion(); // 유튜브 url


            questionCounter ++;
            if(!isError){
                question_Num++;
            }
            else{
                isError = false;
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
            HintButton2.setEnabled(true);
            HintButton3.setEnabled(true);

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

        // 힌트 버튼 비활성화
        HintButton1.setEnabled(false);
        HintButton2.setEnabled(false);
        HintButton3.setEnabled(false);

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
        
        //좌우 공백 및 띄어쓰기 공백 없애기
        String answer = answerText.getText().toString().trim().replace(" ","");
        //answer.replaceAll(" ","");
        answer = answer.toLowerCase();
        Log.e("쓴 답"," : "+answer);
        Log.e("답"," : "+korean_Answer);
        Log.e("답"," : "+english_Answer);

        // 내용 비교 위해서 equals 사용
        if(answer.equals(korean_Answer) || answer.equals(english_Answer)){
            correctText.setVisibility(View.VISIBLE);
            txt_answer.setBackground(getResources().getDrawable(R.drawable.border_button_green));
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
            txt_answer.setBackground(getResources().getDrawable(R.drawable.border_button_red));
        }
        
        scoreText.setText(""+score); // 점수 텍스트 변경
        //confirmButton.setVisibility(View.GONE);

        playVideo(); // 비디오 재생
        //nextButton.setVisibility(View.VISIBLE);
        answerText.setVisibility(View.INVISIBLE);
        txt_answer.setVisibility(View.VISIBLE);
        txt_answer.setText(real_Answer);
        txt_answer.setSingleLine(true);
        txt_answer.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txt_answer.setSelected(true);

    }


    // 마지막 문제인지 체크
    private void checkLast(){
        if (questionCounter < questionCountTotal) {
            nextButton.setText(getString(R.string.Next));
        }
        else{
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


        basic_playtime++;

        pointplus = 10;


        if (isBackPressed) {
            score = 0;
            pointplus = 0;
            basic_playtime--;
        }

        Intent resultIntent = new Intent(this, MainActivity.class);

        resultIntent.putExtra(HIGH_SCORE, score);

        updateHintPoint();
        updateBasicPlayTime();
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

    private void updateBasicPlayTime() {
        SharedPreferences quiz_shared = getSharedPreferences(QUIZ_SHARED,MODE_PRIVATE);

        SharedPreferences.Editor quizeditor = quiz_shared.edit();
        quizeditor.putInt(BASIC_PLAY_TIME,basic_playtime);
        quizeditor.apply();
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
                    QuizMain.this.screenAd = null;
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
            screenAd.show(QuizMain.this);
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

}







