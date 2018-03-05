package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.chenexample.myshop16173.util.GetImage;
import com.chenexample.myshop16173.util.MyApplication;
import com.chenexample.myshop16173.util.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends Activity {

    private MyApplication application;

    private ProgressBar progressBar;
    private TextView textBar,textAllNumber,textAllPrice;
    private TextView textAddInfo,emsInfo;
    private LinearLayout layoutGoodsList;
    private Button btnCancel,btnPay,btnReturn,btnShip,btnFinish;

    private String orderStatus ;
//    private String msg ;//noLogin、u、m、noOrderId
    private Map<String,String> resMap ;
    private List<Map<String,String>> goodsList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        application = (MyApplication)getApplication();
        progressBar = (ProgressBar) findViewById(R.id.progressBar_order_detail);
        textBar = (TextView) findViewById(R.id.textView_order_detail_text);
        textAddInfo = (TextView) findViewById(R.id.textView_order_detail_people_info);
        emsInfo = (TextView) findViewById(R.id.textView_order_detail_ems_info);
        layoutGoodsList = (LinearLayout) findViewById(R.id.linearLayout_order_detail_good_list);
        textAllNumber = (TextView) findViewById(R.id.textView_order_detail_all_number);
        textAllPrice = (TextView) findViewById(R.id.textView_order_detail_all_price);
        btnCancel = (Button) findViewById(R.id.button_order_detail_cancel);
        btnPay = (Button) findViewById(R.id.button_order_detail_pay);
        btnReturn = (Button) findViewById(R.id.button_order_detail_return);
        btnShip = (Button) findViewById(R.id.button_order_detail_ship);
        btnFinish = (Button) findViewById(R.id.button_order_detail_finisth);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了取消++++++--->");
                new AlertDialog.Builder(OrderDetailActivity.this)
                        .setTitle("确认")
                        .setMessage("确定要取消订单吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                                String[] str = new String[2];
                                str[0] = OrderChangeAC.SUBMIT_CANCEL;
                                str[1] = resMap.get("order_id");
                                OrderChangeAC a = new OrderChangeAC();
                                if(!a.isCancelled()){
                                    a.execute(str);
                                }
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了支付++++++--->");
                new AlertDialog.Builder(OrderDetailActivity.this)
                        .setTitle("确认")
                        .setMessage("确定要支付订单吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                                String[] str = new String[2];
                                str[0] = OrderChangeAC.SUBMIT_PAY;
                                str[1] = resMap.get("order_id");
                                OrderChangeAC a = new OrderChangeAC();
                                if(!a.isCancelled()){
                                    a.execute(str);
                                }
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了退货++++++--->");
                new AlertDialog.Builder(OrderDetailActivity.this)
                        .setTitle("确认")
                        .setMessage("确定要退货吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                                String[] str = new String[2];
                                str[0] = OrderChangeAC.SUBMIT_RETURN;
                                str[1] = resMap.get("order_id");
                                OrderChangeAC a = new OrderChangeAC();
                                if(!a.isCancelled()){
                                    a.execute(str);
                                }
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });
        btnShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了发货++++++--->");
                new AlertDialog.Builder(OrderDetailActivity.this)
                        .setTitle("确认")
                        .setMessage("确定要发货吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                                String[] str = new String[2];
                                str[0] = OrderChangeAC.SUBMIT_SHIP;
                                str[1] = resMap.get("order_id");
                                OrderChangeAC a = new OrderChangeAC();
                                if(!a.isCancelled()){
                                    a.execute(str);
                                }
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了完成++++++--->");
                new AlertDialog.Builder(OrderDetailActivity.this)
                        .setTitle("确认")
                        .setMessage("确定要完成订单吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                                String[] str = new String[2];
                                str[0] = OrderChangeAC.SUBMIT_FINISH;
                                str[1] = resMap.get("order_id");
                                OrderChangeAC a = new OrderChangeAC();
                                if(!a.isCancelled()){
                                    a.execute(str);
                                }
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnCancel.setVisibility(View.GONE);
        btnPay.setVisibility(View.GONE);
        btnReturn.setVisibility(View.GONE);
        btnShip.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        String orderId = getIntent().getStringExtra("orderId");
        if(orderId!=null && orderId.length()>0){
            OrderDetailAC ac = new OrderDetailAC();
            if(!ac.isCancelled()){
                ac.execute(orderId);
            }
        }

    }

    private void initUI(){
        String status = resMap.get("order_status");
        String userKind = application.getS_loginUserKind();
        int widthNum = 0;
        int barColor = R.drawable.progressbar_horizontal_success;
        String barStr = null;
        boolean bShip=false,bCancel=false,bReturn=false,bFinish=false,bPay=false;
        if(userKind.equals("m")){
            if(status.equals("submit")){
                widthNum = 25;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "订单提交";
                bCancel = true;
            }else if(status.equals("pay")){
                widthNum = 50;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "订单已支付，等待发货";
                bShip = true;
            }else if(status.equals("ship")){
                widthNum = 75;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "商品已发货";
            }else if(status.equals("finish")){
                widthNum = 100;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "订单已完成";
            }else if(status.equals("return")){
                widthNum = 75;
                barColor = R.drawable.progressbar_horizontal_info;
                barStr = "商品正在退货中";
                bFinish = true;
            }else if(status.equals("cancel")){
                widthNum = 100;
                barColor = R.drawable.progressbar_horizontal_danger;
                barStr = "订单已取消";
            }
        }else{
            if(status.equals("submit")){
                widthNum = 25;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "订单提交，请支付";
                bCancel = true;
                bPay = true;
            }else if(status.equals("pay")){
                widthNum = 50;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "订单已支付，等待发货";
                bCancel = true;
            }else if(status.equals("ship")){
                widthNum = 75;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "商品已发货";
                bFinish = true;
                bReturn = true;
            }else if(status.equals("finish")){
                widthNum = 100;
                barColor = R.drawable.progressbar_horizontal_success;
                barStr = "订单已完成";
            }else if(status.equals("return")){
                widthNum = 75;
                barColor = R.drawable.progressbar_horizontal_info;
                barStr = "商品正在退货中";
            }else if(status.equals("cancel")){
                widthNum = 100;
                barColor = R.drawable.progressbar_horizontal_danger;
                barStr = "订单已取消";
            }
        }

        String emsInfoStr = "";
        String temp = null;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        if(resMap.get("time_submit")!=null && resMap.get("time_submit").length()!=0){
            temp = sd.format(new Date(Long.parseLong(resMap.get("time_submit"))));
            emsInfoStr += temp + " ———— 提交订单\n";
        }
        if(resMap.get("time_pay")!=null && resMap.get("time_pay").length()!=0){
            temp = sd.format(new Date(Long.parseLong(resMap.get("time_pay"))));
            emsInfoStr += temp + " ———— 支付订单\n";
        }
        if(resMap.get("time_cancel")!=null && resMap.get("time_cancel").length()!=0){
            temp = sd.format(new Date(Long.parseLong(resMap.get("time_cancel"))));
            emsInfoStr += temp + " ———— 取消订单\n";
        }
        if(resMap.get("time_ship")!=null && resMap.get("time_ship").length()!=0){
            temp = sd.format(new Date(Long.parseLong(resMap.get("time_ship"))));
            emsInfoStr += temp + " ———— 商品发货\n";
        }
        if(resMap.get("time_return")!=null && resMap.get("time_return").length()!=0){
            temp = sd.format(new Date(Long.parseLong(resMap.get("time_return"))));
            emsInfoStr += temp + " ———— 商品退货\n";
        }
        if(resMap.get("time_finish")!=null && resMap.get("time_finish").length()!=0){
            temp = sd.format(new Date(Long.parseLong(resMap.get("time_finish"))));
            emsInfoStr += temp + " ———— 订单完成\n";
        }
        System.out.println("坎坎坷坷扩扩扩-->"+goodsList.size());
        System.out.println(goodsList);
        System.out.println("---------------------------------");
        for(int i=0;i<goodsList.size();i++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView textCount = new TextView(this);
            textCount.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));
            textCount.setGravity(Gravity.CENTER);
            textCount.setPadding(5,0,5,0);
            textCount.setText(""+(i+1));

            ImageView img = new ImageView(this);
            img.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));
            if(!goodsList.get(i).containsKey("imgPath") || goodsList.get(i).get("imgPath")==null){
                img.setImageResource(R.drawable.wait);
            }else{
                img.setImageBitmap(BitmapFactory.decodeFile(goodsList.get(i).get("imgPath")));
            }

            TextView textName = new TextView(this);
            textName.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,4));
            textName.setGravity(Gravity.CENTER);
            textName.setPadding(5,0,5,0);
            textName.setText(goodsList.get(i).get("name"));

            TextView textNumber = new TextView(this);
            textNumber.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));
            textNumber.setGravity(Gravity.CENTER);
            textNumber.setText(goodsList.get(i).get("number"));

            TextView textPrice = new TextView(this);
            textPrice.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,2));
            textPrice.setGravity(Gravity.CENTER);
            textPrice.setText("￥"+goodsList.get(i).get("price"));

            row.addView(textCount);
            row.addView(img);
            row.addView(textName);
            row.addView(textNumber);
            row.addView(textPrice);

            layoutGoodsList.addView(row);
        }
        if(bCancel){
            btnCancel.setVisibility(View.VISIBLE);
        }
        if(bFinish){
            btnFinish.setVisibility(View.VISIBLE);
        }
        if(bReturn){
            btnReturn.setVisibility(View.VISIBLE);
        }
        if(bPay){
            btnPay.setVisibility(View.VISIBLE);
        }
        if(bShip){
            btnShip.setVisibility(View.VISIBLE);
        }


        String addInfo = resMap.get("add_name")+"，"
                        +resMap.get("add_tel")+"，"
                        +resMap.get("addr");
        //---------
        progressBar.setProgress(widthNum);
        progressBar.setProgressDrawable(getResources().getDrawable(barColor));
        textBar.setText(barStr);
        textAddInfo.setText(addInfo);
        emsInfo.setText(emsInfoStr);
        textAllNumber.setText(resMap.get("allNum"));
        textAllPrice.setText("￥"+resMap.get("allPrice"));

        View v = new View(this);
    }


    //获取开始

    class OrderDetailAC extends AsyncTask<String, Object, Object> {
        private ProgressDialog pd;
        private String imgUrlHead;
        @Override
        protected Object doInBackground(String... params) {
            boolean res = false;
            imgUrlHead = application.getWebURL() +"/images/goods";
            try {
                String data = "&sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8") +
                            "&orderid=" + URLEncoder.encode(params[0], "UTF-8");
                data = application.getWebURL() + "/orderdetailForMobile.action?" + data;
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                String status = null;
                String msg = null;//noLogin、u、m、noOrderId
                Map<String,String> map = null;
                List<Map<String,String>> list = null;
                if(jsonArray.length()>2){
                    list = new ArrayList<>();
                }
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                    if(i==0) {
                        status = jsonObject2.getString("status");
                        msg = jsonObject2.getString("msg");
                    }else if(i==1){
                        map = new HashMap<>();


                        map.put("allPrice",jsonObject2.getString("allPrice"));
                        map.put("allNum",jsonObject2.getString("allNum"));
                        map.put("addr",jsonObject2.getString("addr"));
                        map.put("add_tel",jsonObject2.getString("add_tel"));
                        map.put("add_name",jsonObject2.getString("add_name"));
                        map.put("order_id",jsonObject2.getString("order_id"));
                        map.put("order_status",jsonObject2.getString("order_status"));
                        map.put("time_submit",jsonObject2.getString("time_submit"));
                        if(jsonObject2.has("time_pay")){
                            map.put("time_pay",jsonObject2.getString("time_pay"));
                        }
                        if(jsonObject2.has("time_cancel")){
                            map.put("time_cancel",jsonObject2.getString("time_cancel"));
                        }
                        if(jsonObject2.has("time_ship")){
                            map.put("time_ship",jsonObject2.getString("time_ship"));
                        }
                        if(jsonObject2.has("time_return")){
                            map.put("time_return",jsonObject2.getString("time_return"));
                        }
                        if(jsonObject2.has("time_finish")){
                            map.put("time_finish",jsonObject2.getString("time_finish"));
                        }
                        if(msg.equals("m")){
                            map.put("user_id",jsonObject2.getString("user_id"));
                        }
                    }else{
		                Map<String,String> mapTemp = new HashMap<>();
                        mapTemp.put("id",jsonObject2.getString("id"));
                        mapTemp.put("price",jsonObject2.getString("price"));
                        mapTemp.put("name",jsonObject2.getString("name"));
                        mapTemp.put("number",jsonObject2.getString("number"));
                        mapTemp.put("imgPath", GetImage.getInternetPicturePath(OrderDetailActivity.this,imgUrlHead+"/"+mapTemp.get("id")+"_01.jpg"));
                        list.add(mapTemp);
                    }
                }
                if(status.equals("ok")){
                    //
                    resMap = map ;
                    goodsList = list ;
                }
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
            pd = new ProgressDialog(OrderDetailActivity.this);
            pd.setMessage("搜索中...");
            if(!pd.isShowing()){
                pd.show();
            }
            pd.show();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            initUI();
            pd.dismiss();
            //
        }
    }

    //获取结束

    //开始

    class OrderChangeAC extends AsyncTask<String, Object, Object> {

        public static final String SUBMIT_SHIP = "ship" ;
        public static final String SUBMIT_PAY = "pay" ;
        public static final String SUBMIT_CANCEL = "cancel" ;
        public static final String SUBMIT_RETURN = "return" ;
        public static final String SUBMIT_FINISH = "finish" ;
        private ProgressDialog pd;

        @Override
        protected Object doInBackground(String... params) {
            String res = "false";
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
            pd = new ProgressDialog(OrderDetailActivity.this);
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
            if(res.equals("ok")){
                onResume();
            }
            pd.dismiss();
        }
    }
    //结束
}
