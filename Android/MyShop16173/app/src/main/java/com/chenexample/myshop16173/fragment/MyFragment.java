package com.chenexample.myshop16173.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.chenexample.myshop16173.CollectionActivity;
import com.chenexample.myshop16173.ForgerActivity;
import com.chenexample.myshop16173.LoginActivity;
import com.chenexample.myshop16173.MainActivity;
import com.chenexample.myshop16173.R;
import com.chenexample.myshop16173.RegisterActivity;
import com.chenexample.myshop16173.util.MyApplication;

public class MyFragment extends Fragment {

    private MyApplication myApplication;
    private TextView textViewName,textViewKind;
    private TableRow tablePasswd,tableOrder,tableCollection,tableShoppingCar;
    private TableRow tableUpdate,tableAbout,tableYi,tableTel;
    private Button buttonLogOut,btnLogin,btnReg;
    private LinearLayout layoutLogout,layoutNoLogin,layoutLoged;
    private LayoutInflater inflater;

    public MyFragment() {
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.layoutLogout=layoutLogout;
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        myApplication = (MyApplication)getActivity().getApplication();
        textViewName = (TextView) v.findViewById(R.id.textView_fmy_loginUserName);
        textViewKind = (TextView) v.findViewById(R.id.textView_fmy_kind);
        layoutLogout = (LinearLayout) v.findViewById(R.id.linearLayout_fmy_logout);
        layoutNoLogin = (LinearLayout) v.findViewById(R.id.linearLayout_fmy_noLogin);
        layoutLoged = (LinearLayout) v.findViewById(R.id.linearLayout_fmy_login);
        buttonLogOut = (Button) v.findViewById(R.id.button_fmy_logout);
        btnLogin = (Button) v.findViewById(R.id.button_fmy_login);
        btnReg = (Button) v.findViewById(R.id.button_fmy_reg);
        tablePasswd = (TableRow) v.findViewById(R.id.table_fmy_change_passwd);
        tableOrder = (TableRow) v.findViewById(R.id.table_fmy_order);
        tableCollection = (TableRow) v.findViewById(R.id.table_fmy_collect);
        tableShoppingCar = (TableRow) v.findViewById(R.id.table_fmy_shoppingCar);
        tableUpdate = (TableRow) v.findViewById(R.id.table_fmy_update);
        tableAbout = (TableRow) v.findViewById(R.id.table_fmy_about);
        tableYi = (TableRow) v.findViewById(R.id.table_fmy_yi);
        tableTel = (TableRow) v.findViewById(R.id.table_fmy_tel);

        //到收藏夹
        tableCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.isB_login()){
                    Intent intent = new Intent(getActivity(), CollectionActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_LONG).show();
                }

            }
        });
        //到忘记密码
        tablePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.isB_login()){
                    Intent intent = new Intent(getActivity(), ForgerActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_LONG).show();
                }

            }
        });
        //到我的订单
        tableOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.isB_login()){
                    myApplication.getViewPager().setCurrentItem(4);
                }else{
                    Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_LONG).show();
                }

            }
        });
        //到我的购物车
        tableShoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.isB_login()){
                    myApplication.getViewPager().setCurrentItem(3);
                }else{
                    Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_LONG).show();
                }

            }
        });
        //到更新
        tableUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"此版本为最新版，无需更新",Toast.LENGTH_LONG).show();
            }
        });
        //到关于
        tableAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"作者：软件15001班 陈锡鑫",Toast.LENGTH_LONG).show();
            }
        });
        //到意见
        tableYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"本服务暂时关闭",Toast.LENGTH_LONG).show();
            }
        });
        //到电话
        tableTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("客服电话");
//                builder.setIcon(R.drawable.main_user);
                builder.setMessage("拨打13688888888");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String data = "tel:" + "13688888888";
                        Uri uri = Uri.parse(data);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
        //登录按钮
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //注册按钮
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        //退出按钮
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 执行退出操作
                myApplication.setB_login(false);
                myApplication.setS_loginUserId(null);
                myApplication.setS_loginUserKind(null);
                myApplication.setS_loginUserName(null);
                myApplication.setNumberShoppingCar(-1);
                myApplication.setNumberOrder(-1);
                myApplication.setSessionId(null);
                Toast.makeText(getContext(),"执行退出操作",Toast.LENGTH_SHORT).show();
                onResume();
            }
        });
        //

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        myApplication = (MyApplication)getActivity().getApplication();
        //如何显示
        layoutNoLogin.setVisibility(View.INVISIBLE);
        layoutLoged.setVisibility(View.INVISIBLE);
        layoutLogout.setVisibility(View.GONE);
        if(myApplication.isB_login()){
            if(myApplication.getS_loginUserKind().equals("m")){
                textViewKind.setText("管理员");
            }else{
                textViewKind.setText("普通用户");
            }
            textViewName.setText("【"+myApplication.getS_loginUserName()+"】");
            layoutLoged.setVisibility(View.VISIBLE);
            layoutLogout.setVisibility(View.VISIBLE);
        }else{
            layoutNoLogin.setVisibility(View.VISIBLE);
        }
    }
}
