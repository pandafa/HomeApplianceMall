package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chenexample.myshop16173.util.MyApplication;
import com.chenexample.myshop16173.util.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends Activity {

    private MyApplication myApplication;
    private EditText editUserName,editPassWord;
    private Button btnLogin,btnReg;
    private TextView textForger;
    private boolean canAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myApplication = (MyApplication)getApplication();
        canAC = true;
        editUserName = (EditText) findViewById(R.id.editText_login_username);
        editPassWord = (EditText) findViewById(R.id.editText_login_password);
        btnLogin = (Button) findViewById(R.id.button_login_login);
        btnReg = (Button) findViewById(R.id.button_login_reg);
        textForger = (TextView) findViewById(R.id.textView_login_forget);

        //忘记密码
        textForger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgerActivity.class);
                startActivity(intent);
            }
        });
        //注册
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我要注册！");
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                String userName = editUserName.getText().toString();
                String passWord = editPassWord.getText().toString();
                if(userName.length()==0){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                    return;
                }
                if(passWord.length()==0){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                    return;
                }
                // 验证密码
                String data = null;
                try {
                    data = "username=" + URLEncoder.encode(userName, "UTF-8") +
                            "&password=" + URLEncoder.encode(passWord, "UTF-8");
                    data = myApplication.getWebURL() + "/loginForMobile.action?" + data;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("登录URL:------------>"+data);
                if(data==null){
                    Toast.makeText(LoginActivity.this,"登录异常！",Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                    return;
                }
                LoginAC ac = new LoginAC();
                if(canAC){
                    canAC = false;
                    ac.execute(data);
                }

            }
        });
        //
    }

    class LoginAC extends AsyncTask<String, Object, Object>{

        private ProgressDialog pd;

        @Override
        protected Object doInBackground(String... params) {
            boolean res = false;
            try {
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(params[0])));
                String status = null;
                String msg = null;//loginFalse、u、m
                String loginUserId = null;
                String loginUserName = null;
                String shoppingcarNum = null;
                String orderNumber = null;
                String sessionId = null;
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                    if(i==0){
                        status = jsonObject2.getString("status");
                        msg = jsonObject2.getString("msg");
                    }else{
                        loginUserId = jsonObject2.getString("loginUserId");
                        loginUserName = jsonObject2.getString("loginUserName");
                        orderNumber = jsonObject2.getString("orderNum");
                        sessionId = jsonObject2.getString("sessionId");
                        if(msg.equals("u")){
                            shoppingcarNum = jsonObject2.getString("shoppingcarNum");
                        }
                    }
                }
                if(shoppingcarNum==null || shoppingcarNum.equals("null")){
                    shoppingcarNum = "0";
                }
                if(orderNumber==null){
                    orderNumber = "0";
                }

                if(status!=null && status.equals("ok")){
                    myApplication.setB_login(true);
                    myApplication.setS_loginUserId(loginUserId);
                    myApplication.setS_loginUserKind(msg);
                    myApplication.setS_loginUserName(loginUserName);
                    myApplication.setNumberOrder(Integer.parseInt(orderNumber));
                    myApplication.setNumberShoppingCar(Integer.parseInt(shoppingcarNum));
                    myApplication.setSessionId(sessionId);
                    res = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("正在登录...");
            if(!pd.isShowing()){
                pd.show();
            }
            pd.show();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            pd.dismiss();

            if((boolean)o){
                Toast.makeText(LoginActivity.this,"登录成功！！！",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_LONG).show();
                btnLogin.setEnabled(true);
                canAC = true;
            }
        }
    }

}
