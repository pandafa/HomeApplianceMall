package com.chenexample.myshop16173.adapter;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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


/**
 * 订单发货(ship)、取消(cancel)、支付(pay)、退货(return)、完成(finish)
   submitaim要干啥
   orderid订单编号
   changestatus要变成什么
 */
public class OrderChangeAC extends AsyncTask<String, Object, Object> {

    public static final String SUBMIT_SHIP = "ship" ;
    public static final String SUBMIT_PAY = "pay" ;
    public static final String SUBMIT_CANCEL = "cancel" ;
    public static final String SUBMIT_RETURN = "return" ;
    public static final String SUBMIT_FINISH = "finish" ;
    private ProgressDialog pd;
    private Context context;
    private Activity activity;
    private MyApplication application;

    public OrderChangeAC(Context context,MyApplication application){
        super();
        this.context = context;
        this.application = application;
    }


    @Override
    protected Object doInBackground(String... params) {
//        Map<String, String> param = (HashMap<String, String>)params;
        boolean res = false;
        try {
            String data = "sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8") +
                    "&submitaim=" + URLEncoder.encode(params[0], "UTF-8") +  //订单发货(ship)、取消(cancel)、支付(pay)、退货(return)、完成(finish)
                    "&orderid=" + URLEncoder.encode(params[1], "UTF-8") +
                    "&source=mobile";
            data = application.getWebURL() + "/orderdetailForMobile.action?" + data;
            JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
            String status = null;//ok、no
            String msg = null;//changeSuccess、changeFalse
            float allPrice = -1;
            List<Map<String,String>> list = null;
            if(jsonArray.length()>1){
                list = new ArrayList<>();
            }
            JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
            status = jsonObject2.getString("status");
            msg = jsonObject2.getString("msg");

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
        pd = new ProgressDialog(context);
        pd.setMessage("处理中...");
        if(!pd.isShowing()){
            pd.show();
        }
        pd.show();

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        pd.dismiss();
    }
}