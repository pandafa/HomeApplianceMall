package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;

import com.chenexample.myshop16173.fragment.*;
import com.chenexample.myshop16173.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

import static com.chenexample.myshop16173.R.id.radioButton_main_fen;
import static com.chenexample.myshop16173.R.id.radioButton_main_home;
import static com.chenexample.myshop16173.R.id.radioButton_main_my;
import static com.chenexample.myshop16173.R.id.radioButton_main_order;
import static com.chenexample.myshop16173.R.id.radioButton_main_search;
import static com.chenexample.myshop16173.R.id.radioButton_main_shoppingcar;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.ViewPager_main);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup_main);
        application = (MyApplication)getApplication();

        //viewPage
        List<Fragment> fragmentList = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        SearchFragment searchFragment = new SearchFragment();
        ClassifyFragment classifyFragment = new ClassifyFragment();
        ShoppingCarFragment shoppingCarFragment = new ShoppingCarFragment();
        OrderFragment orderFragment = new OrderFragment();
        MyFragment myFragment = new MyFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(searchFragment);
        fragmentList.add(classifyFragment);
        fragmentList.add(shoppingCarFragment);
        fragmentList.add(orderFragment);
        fragmentList.add(myFragment);
        application.setViewPager(viewPager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyPageLister());

        //
        radioGroup.setOnCheckedChangeListener(new MyRadionGroupListen());
        radioGroup.check(R.id.radioButton_main_home);
        //
    }

    //初始化视图、组件
    private void initView(){}
    //初始化函数，fragment
    private void initViewPager(){}

    class MyRadionGroupListen implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId){
                case radioButton_main_home:
                    viewPager.setCurrentItem(0);
                    break;
                case radioButton_main_search:
                    viewPager.setCurrentItem(1);
                    break;
                case radioButton_main_fen:
                    viewPager.setCurrentItem(2);
                    break;
                case radioButton_main_shoppingcar:
                    viewPager.setCurrentItem(3);
                    break;
                case radioButton_main_order:
                    viewPager.setCurrentItem(4);
                    break;
                case radioButton_main_my:
                    viewPager.setCurrentItem(5);
                    break;
            }
        }
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragmentList;
        public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList=fragmentList;
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    class MyPageLister implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int current = viewPager.getCurrentItem();
            switch (current){
                case 0:
                    radioGroup.check(radioButton_main_home);
                    break;
                case 1:
                    radioGroup.check(radioButton_main_search);
                    break;
                case 2:
                    radioGroup.check(radioButton_main_fen);
                    break;
                case 3:
                    radioGroup.check(radioButton_main_shoppingcar);
                    break;
                case 4:
                    radioGroup.check(radioButton_main_order);
                    break;
                case 5:
                    radioGroup.check(radioButton_main_my);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //

}
