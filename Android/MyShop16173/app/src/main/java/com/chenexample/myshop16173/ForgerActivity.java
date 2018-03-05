package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chenexample.myshop16173.util.MyApplication;
import com.chenexample.myshop16173.util.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgerActivity extends Activity {

    private EditText editUserName,editPW1,editPW2,editCode;
    private Button btnCode,btnForger;
    private String codeReal;
    private boolean getCode;
    private Handler handler;
    private int waitTime;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forger);
        application = (MyApplication)getApplication();
        editUserName = (EditText) findViewById(R.id.editText_forger_username);
        editPW1 = (EditText) findViewById(R.id.editText_forger_pw1);
        editPW2 = (EditText) findViewById(R.id.editText_forger_pw2);
        editCode = (EditText) findViewById(R.id.editText_forger_code);
        btnCode = (Button) findViewById(R.id.button_forger_code);
        btnForger = (Button) findViewById(R.id.button_forger_forger);

        codeReal = null;
        waitTime = 0;
        getCode = true;
        handler = new Handler();


        btnForger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUserName.getText().toString();
                String pw1 = editPW1.getText().toString();
                String pw2 = editPW2.getText().toString();
                String code = editCode.getText().toString();
                if(username.length()==0){
                    Toast.makeText(ForgerActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw1.length()==0){
                    Toast.makeText(ForgerActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw2.length()==0){
                    Toast.makeText(ForgerActivity.this,"请再次输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(codeReal==null){
                    Toast.makeText(ForgerActivity.this,"请先获取验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(code.length()==0){
                    Toast.makeText(ForgerActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pw1.equals(pw2)){
                    Toast.makeText(ForgerActivity.this,"两次密码输入不同，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!codeReal.equals(code)){
                    Toast.makeText(ForgerActivity.this,"验证码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 更改密码
                String[] str = new String[]{username,pw1};
                ForgetAC ac = new ForgetAC();
                if(!ac.isCancelled()){
                    ac.execute(str);
                }
            }
        });
        //
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取验证码操作
                if(getCode){
                    codeReal = createCode(4);
                    Toast.makeText(ForgerActivity.this,"验证码为："+codeReal,Toast.LENGTH_LONG).show();
                    getCode = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final int needTime = 60;
                            waitTime = 0;
                            while(waitTime<=needTime){
                                if(waitTime==needTime){
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnCode.setText("获取验证码");
                                            btnCode.setTextColor(Color.rgb(0,0,0));
                                            btnCode.setBackgroundColor(Color.argb(204,255,255,255));
                                        }
                                    });
                                    break;
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        btnCode.setText((needTime-waitTime+1)+"秒后获取验证码");
                                        btnCode.setTextColor(Color.rgb(190,190,190));
                                        btnCode.setBackgroundColor(Color.argb(100, 255, 255, 255));
                                    }
                                });
                                waitTime++;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            getCode = true;
                        }
                    }).start();
                }
            }
        });
        //-------
    }

    private String createCode(int n){
        String[] codes = new String[]{"0","1","2","3","4","5","6","7","8","9","s","v","w","e","m"};
        String res = "";
        if(n<=0){
            res = null;
        }else{
            for(int i=0;i<n;i++){
                int index = (int)(Math.random()*codes.length);
                res += codes[index];
            }
        }
        return res;
    }

    //开始
    class ForgetAC extends AsyncTask<String, Object, Object> {
        private ProgressDialog pd;
        @Override
        protected Object doInBackground(String... params) {
            String res = "no";
            try {
                String data = "username=" + URLEncoder.encode(params[0], "UTF-8") +
                        "&password=" + URLEncoder.encode(params[1], "UTF-8");
                data = application.getWebURL() + "/forgetPasswordForMobile.action?" + data;
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                String status = null;
                String msg = null;
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
                status = jsonObject2.getString("status");
                msg = jsonObject2.getString("msg");
                res = status;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ForgerActivity.this);
            pd.setMessage("处理中...");
            if(!pd.isShowing()){
                pd.show();
            }
            pd.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String res = (String)o;
            pd.dismiss();
            if(res.equals("ok")){
                Toast.makeText(ForgerActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(ForgerActivity.this,"修改失败",Toast.LENGTH_LONG).show();
            }
        }
    }
    //结束

}
