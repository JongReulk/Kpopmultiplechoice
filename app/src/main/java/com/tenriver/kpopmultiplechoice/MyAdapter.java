package com.tenriver.kpopmultiplechoice;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;
    public MyAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        if(index==0) return new tips_1();
        else if(index==1) return new tips_2();
        else if(index==2) return new tips_3();
        else if(index==3) return new tips_4();
        else return new tips_5();
    }
    @Override
    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition(int position) { return position % mCount; }
}
