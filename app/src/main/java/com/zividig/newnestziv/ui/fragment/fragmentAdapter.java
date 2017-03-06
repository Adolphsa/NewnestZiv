package com.zividig.newnestziv.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zividig.newnestziv.ui.fragment.message.MessageFragment;
import com.zividig.newnestziv.ui.fragment.my.MyFragment;
import com.zividig.newnestziv.ui.fragment.mycar.MyCarFragment;
import com.zividig.newnestziv.ui.fragment.setting.SettingFragment;

/**
 * Created by adolph
 * on 2017-03-04.
 */

public class fragmentAdapter extends FragmentPagerAdapter {

    private MyCarFragment mMyCarFragment = null; //我的车
    private MessageFragment mMessageFragment = null; //消息
    private SettingFragment mSettingFragment = null; //设置
    private MyFragment mMyFragment = null; //我

    private int size;

    public fragmentAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= 0 && position < size){
            switch (position){
                case  0:
                    if(null == mMyCarFragment)
                        mMyCarFragment = MyCarFragment.newInstance();
                    return mMyCarFragment;

                case 1:
                    if(null == mMessageFragment)
                        mMessageFragment = MessageFragment.newInstance();
                    return mMessageFragment;

                case 2:
                    if(null == mSettingFragment)
                        mSettingFragment = SettingFragment.newInstance();
                    return mSettingFragment;

                case 3:
                    if(null == mMyFragment)
                        mMyFragment = MyFragment.newInstance();
                    return mMyFragment;
                default:
                    break;
            }
        }
        return mMyCarFragment;
    }

    @Override
    public int getCount() {
        return size;
    }
}
