package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class RegisterActivity extends Activity {

    private MyApplication myApplication;
    private EditText editUserName,editPassWord1,editPassWord2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myApplication = (MyApplication)getApplication();
        editUserName = (EditText) findViewById(R.id.editText_reg_username);
        editPassWord1 = (EditText) findViewById(R.id.editText_reg_pw1);
        editPassWord2 = (EditText) findViewById(R.id.editText_reg_pw2);
        btn = (Button) findViewById(R.id.button_reg_reg);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setEnabled(false);
                String userName = editUserName.getText().toString();
                String pw1 = editPassWord1.getText().toString();
                String pw2 = editPassWord2.getText().toString();
                if(userName.length()==0){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                if(pw1.length()==0){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                if(pw2.length()==0){
                    Toast.makeText(RegisterActivity.this,"请输入再次密码",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                if(!pw1.equals(pw2)){
                    Toast.makeText(RegisterActivity.this,"两次密码不同，请重新输入",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                // 执行注册操作
                String data = null;
                try {
                    data = "username=" + URLEncoder.encode(userName, "UTF-8") +
                            "&password1=" + URLEncoder.encode(pw1, "UTF-8") +
                            "&password2=" + URLEncoder.encode(pw2, "UTF-8");
                    data = myApplication.getWebURL() + "/registUserForMobile.action?" + data;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("------------>"+data);
                if(data==null){
                    Toast.makeText(RegisterActivity.this,"登录异常！",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                RegistAC ac = new RegistAC();
                ac.execute(data);
            }
        });
    }

    class RegistAC extends AsyncTask<String, Object, Object> {

        private ProgressDialog pd;

        @Override
        protected Object doInBackground(String... params) {
            boolean res = false;
            try {
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(params[0])));
                String status = null;//ok、no
                String msg = null;//registFalse、registSuccess
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
                status = jsonObject2.getString("status");
                msg = jsonObject2.getString("msg");

                if(status.equals("ok")){
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
            pd = new ProgressDialog(RegisterActivity.this);
            pd.setMessage("正在注册...");
            pd.show();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pd.dismiss();
            btn.setEnabled(true);
            if((boolean)o){
                Toast.makeText(RegisterActivity.this,"注册成功！！！",Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
            }
        }
    }
}
