package com.tenriver.kpopmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Handler;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.tenriver.kpopmultiplechoice.select.MainActivity;

import me.relex.circleindicator.CircleIndicator3;

public class TipsActivity extends AppCompatActivity{

    // MediaPlayer 객체생성
    public static MediaPlayer mediaplayer_tips;

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 5;
    private CircleIndicator3 mIndicator;

    private Button close;
    private Button closeforever;

    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        // BGN 실행
        mediaplayer_tips = MediaPlayer.create(this, R.raw.tipsmusic);
        mediaplayer_tips.setLooping(true);
        mediaplayer_tips.start();

        SharedPreferences music = getSharedPreferences(MainActivity.SHARED_MUSIC,MODE_PRIVATE);
        Boolean bgmCb_tips = music.getBoolean("bgmCb",true);
        Boolean effectCb_tips = music.getBoolean("effectCb",true);

        // 버튼
        closeforever = findViewById(R.id.closeforever_Button);

        if(mediaplayer_tips!=null){
            if(!bgmCb_tips){
                mediaplayer_tips.setVolume(0,0);
            }

            else{
                mediaplayer_tips.setVolume(1,1);
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
                    if(mediaplayer_tips!=null) {
                        if (mediaplayer_tips.isPlaying()) {
                            // BGM 중지
                            mediaplayer_tips.pause();

                        }
                    }
                }

            }
        };

        registerReceiver(receiver, intentFilter);




        //가로 슬라이드 뷰 Fragment
        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,
                0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


        mPager.setCurrentItem(0); //시작 지점
        mPager.setOffscreenPageLimit(4); //최대 이미지 수
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });

        /*
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // bgm 끄기
                if(mediaplayer_tips!=null)
                {
                    mediaplayer_tips.stop();
                    mediaplayer_tips.release();
                    mediaplayer_tips = null;
                }

                close.setEnabled(false);
                close.setTextColor(Color.GRAY);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                        startActivity(intent);

                        // 액티비티 이동시 페이드인아웃 연출
                        //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                }, 600); //딜레이 타임 조절
            }
        });*/

        closeforever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다시보지않기
                SharedPreferences closef = getSharedPreferences(TitleActivity.SHARED_CLOSE,MODE_PRIVATE);
                SharedPreferences.Editor closeEditor = closef.edit();
                closeEditor.putBoolean("closeforever", false);
                closeEditor.apply();

                // bgm 끄기
                if(mediaplayer_tips!=null)
                {
                    mediaplayer_tips.stop();
                    mediaplayer_tips.release();
                    mediaplayer_tips = null;
                }

                closeforever.setEnabled(false);
                closeforever.setTextColor(Color.GRAY);

                // 핸들러
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                        startActivity(intent);

                        // 액티비티 이동시 페이드인아웃 연출
                        //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                }, 600); //딜레이 타임 조절
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!mediaplayer_tips.isPlaying()){
            SharedPreferences music = getSharedPreferences(MainActivity.SHARED_MUSIC,MODE_PRIVATE);
            Boolean bgmCb_tips = music.getBoolean("bgmCb",true);

            if(mediaplayer_tips!=null){
                if(!bgmCb_tips){
                    mediaplayer_tips.setVolume(0,0);
                }

                else{
                    mediaplayer_tips.setVolume(1,1);
                }
            }
            mediaplayer_tips.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mediaplayer_tips!=null)
        {
            mediaplayer_tips.stop();
            mediaplayer_tips.release();
            mediaplayer_tips = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaplayer_tips!=null) {

            if (!mediaplayer_tips.isPlaying()) {
                // BGN 실행
                mediaplayer_tips = MediaPlayer.create(this, R.raw.selectmusic_new);
                mediaplayer_tips.setLooping(true);
                mediaplayer_tips.start();

            }
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        if(mediaplayer_tips.isPlaying())
        {
            // BGM 중지
            mediaplayer_tips.pause();

        }
    }


    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //손가락으로 화면을 누르기 시작했을 때 할 일
                break;
            case MotionEvent.ACTION_MOVE:
                //터치 후 손가락을 움직일 때 할 일
                break;
            case MotionEvent.ACTION_UP:
                //손가락을 화면에서 뗄 때 할 일
                // 타이틀 음악 종료 후 액티비티 이동
                // BGM 종료
                if(mPager.getCurrentItem() == num_page - 1)
                {
                    if(mediaplayer_title!=null)
                    {
                        mediaplayer_title.stop();
                        mediaplayer_title.release();
                        mediaplayer_title = null;
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                    // 액티비티 이동시 페이드인아웃 연출
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                // 터치가 취소될 때 할 일
                break;
            default:
                break;
        }
        return true;
    }*/
}
