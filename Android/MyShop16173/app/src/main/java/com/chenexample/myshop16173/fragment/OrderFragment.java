package com.chenexample.myshop16173.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenexample.myshop16173.LoginActivity;
import com.chenexample.myshop16173.OrderDetailActivity;
import com.chenexample.myshop16173.R;
import com.chenexample.myshop16173.RegisterActivity;
import com.chenexample.myshop16173.util.GetImage;
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

public class OrderFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private MyApplication application;
    private Button btnLogin,btnReg;
    private LinearLayout layoutNull,layoutNoLogin,layoutMain;
    private ListView listView;
    private MyAdapterForOrder myAdapter;
    private TextView textTopUserName;
    private List<Map<String,Object>> resList;
    private boolean canAC;

    public OrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        application = (MyApplication)getActivity().getApplication();
        listView = (ListView) v.findViewById(R.id.listView_forder);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout_f_order);
        textTopUserName = (TextView) v.findViewById(R.id.textView_forder_top_username);
        layoutNull = (LinearLayout) v.findViewById(R.id.linearLayout_forder_null);
        layoutNoLogin = (LinearLayout) v.findViewById(R.id.linearLayout_forder_noLogin);
        layoutMain = (LinearLayout) v.findViewById(R.id.linearLayout_forder_main);
        btnLogin = (Button) v.findViewById(R.id.button_fclass_login);
        btnReg = (Button) v.findViewById(R.id.button_fclass_reg);
        canAC = true;

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新操作
                if(application.isB_login()){
                    logingData();
                }
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


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        application = (MyApplication)getActivity().getApplication();
        //显示方式
        layoutNull.setVisibility(View.INVISIBLE);
        layoutNoLogin.setVisibility(View.INVISIBLE);
        layoutMain.setVisibility(View.INVISIBLE);
        if(application.isB_login()){
            if(application.getS_loginUserKind().equals("u")){
                textTopUserName.setVisibility(View.GONE);
            }
            if(application.getNumberOrder()==0){
                layoutNull.setVisibility(View.VISIBLE);
            }else{
                layoutMain.setVisibility(View.VISIBLE);
            }
        }else{
            layoutNoLogin.setVisibility(View.VISIBLE);
        }

        resList = new ArrayList<>();
        myAdapter = new MyAdapterForOrder(this.getContext(),resList);
        listView.setAdapter(myAdapter);
        logingData();
    }

    class MyAdapterForOrder extends BaseAdapter{

        private List<Map<String,Object>> list;
        private Context context;
        private int x = 1;

        public MyAdapterForOrder(Context context,List<Map<String,Object>> list){
            super();
            this.list=list;
            this.context=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_f_order,null);
            if(position%2!=0){
                LinearLayout l = (LinearLayout) v.findViewById(R.id.linearLayout_forder);
                l.setBackgroundColor(Color.rgb(217,237,247));
            }
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView_forder);
            TextView textX = (TextView) v.findViewById(R.id.textView_forder_x);
            TextView textId = (TextView) v.findViewById(R.id.textView_forder_id);
            TextView textUserName = (TextView) v.findViewById(R.id.textView_forder_username);
            TextView textName = (TextView) v.findViewById(R.id.textView_forder_name);
            TextView textNumber = (TextView) v.findViewById(R.id.textView_forder_number);
            TextView textPrice = (TextView) v.findViewById(R.id.textView_forder_price);
            TextView textStatus = (TextView) v.findViewById(R.id.textView_forder_status);
            Button buttonLook = (Button) v.findViewById(R.id.button_forder_look);
            Button buttonCancel = (Button) v.findViewById(R.id.button_forder_cancel);
            Button buttonPay = (Button) v.findViewById(R.id.button_forder_pay);
            Button buttonShip = (Button) v.findViewById(R.id.button_forder_ship);
            Button buttonFinishuU = (Button) v.findViewById(R.id.button_forder_finish_u);
            Button buttonReturn = (Button) v.findViewById(R.id.button_forder_return);
            Button buttonFinishuM = (Button) v.findViewById(R.id.button_forder_finish_m);
            String status = (String)list.get(position).get("status");
            String kind = (String)list.get(position).get("kind");
            if(kind!=null && kind.equals("u")){
                textUserName.setVisibility(View.GONE);
            }else if(kind!=null){
                textUserName.setText((String)list.get(position).get("userName"));
            }
            buttonCancel.setContentDescription((String)list.get(position).get("orderId"));
            buttonPay.setContentDescription((String)list.get(position).get("orderId"));
            buttonShip.setContentDescription((String)list.get(position).get("orderId"));
            buttonFinishuU.setContentDescription((String)list.get(position).get("orderId"));
            buttonReturn.setContentDescription((String)list.get(position).get("orderId"));
            buttonFinishuM.setContentDescription((String)list.get(position).get("orderId"));
            //给按钮加监听
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String s = v.getContentDescription().toString();
                    new AlertDialog.Builder(getContext())
                            .setTitle("确认")
                            .setMessage("确定要取消订单吗？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    String[] str = new String[2];
                                    str[0] = OrderChangeAC.SUBMIT_CANCEL;
                                    str[1] = s;
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
            buttonPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String s = v.getContentDescription().toString();
                    new AlertDialog.Builder(getContext())
                            .setTitle("确认")
                            .setMessage("确定要支付订单吗？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    String[] str = new String[2];
                                    str[0] = OrderChangeAC.SUBMIT_PAY;
                                    str[1] = s;
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
            buttonShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String s = v.getContentDescription().toString();
                    new AlertDialog.Builder(getContext())
                            .setTitle("确认")
                            .setMessage("确定要发货吗？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    String[] str = new String[2];
                                    str[0] = OrderChangeAC.SUBMIT_SHIP;
                                    str[1] = s;
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
            buttonFinishuU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String s = v.getContentDescription().toString();
                    new AlertDialog.Builder(getContext())
                            .setTitle("确认")
                            .setMessage("确定要完成订单吗？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    String[] str = new String[2];
                                    str[0] = OrderChangeAC.SUBMIT_FINISH;
                                    str[1] = s;
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
            buttonReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String s = v.getContentDescription().toString();
                    new AlertDialog.Builder(getContext())
                            .setTitle("确认")
                            .setMessage("确定要退货吗？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    String[] str = new String[2];
                                    str[0] = OrderChangeAC.SUBMIT_RETURN;
                                    str[1] = s;
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
            buttonFinishuM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String s = v.getContentDescription().toString();
                    new AlertDialog.Builder(getContext())
                            .setTitle("确认")
                            .setMessage("确定要完成订单吗？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    String[] str = new String[2];
                                    str[0] = OrderChangeAC.SUBMIT_FINISH;
                                    str[1] = s;
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
            //判断显示谁
            if(status.equals("submit")){
                buttonCancel.setVisibility(View.VISIBLE);
                if(kind.equals("u")){
                    buttonPay.setVisibility(View.VISIBLE);
                }
                textStatus.setText("订单已提交");
            }else if(status.equals("pay")){
                if(kind.equals("m")){
                    buttonShip.setVisibility(View.VISIBLE);
                }
                textStatus.setText("订单已支付");
            }else if(status.equals("ship")){
                if(kind.equals("u")){
                    buttonReturn.setVisibility(View.VISIBLE);
                    buttonFinishuU.setVisibility(View.VISIBLE);
                }
                textStatus.setText("商品已发货");
            }else if(status.equals("return")){
                if(kind.equals("m")){
                    buttonFinishuM.setVisibility(View.VISIBLE);
                }
                textStatus.setText("商品正在退货中");
            }else if(status.equals("finish")){
                textStatus.setText("订单已完成");
            }else if(status.equals("cancel")){
                textStatus.setText("订单已取消");
            }
            textX.setText(""+(position+1));
            x++;
            textId.setText((String)list.get(position).get("orderId"));
            textName.setText((String)list.get(position).get("name"));
            textNumber.setText((String)list.get(position).get("number"));
            textPrice.setText((String)list.get(position).get("price"));
            if(!list.get(position).containsKey("imgBit") || list.get(position).get("imgBit")==null){
                imageView.setImageResource(R.drawable.wait);
            }else{
                imageView.setImageBitmap((Bitmap)list.get(position).get("imgBit"));
            }
            buttonLook.setContentDescription((String)list.get(position).get("orderId"));
            buttonLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("---啊啊啊啊啊啊啊啊啊啊一月又一月晕晕晕晕晕晕晕晕");
                    Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                    intent.putExtra("orderId",v.getContentDescription());
                    startActivity(intent);
                }
            });

            return v;
        }

        public void initX(){
            x = 1;
        }

        public void setList(List<Map<String,Object>> list){
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    private void logingData(){
        OrderAC ac = new OrderAC();
        if(canAC){
            canAC = false;
            ac.execute("ok");
        }
    }


    //class begin
    class OrderAC extends AsyncTask<String, Object, Object> {
        private String imgUrlHead;
        @Override
        protected Object doInBackground(String... params) {
            imgUrlHead = application.getWebURL() +"/images/goods";
            String res = "f";
            try {
                String data = "sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8");
                data = application.getWebURL() + "/orderForMobile.action?" + data;
                System.out.println("order的URL:"+data);
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                String status = null;
                String msg = null;//u、m、onLogin
                List<Map<String,String>> list = null;
                if(jsonArray.length()>1){
                    list = new ArrayList<>();
                }
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                    if(i==0){
                        status = jsonObject2.getString("status");
                        msg = jsonObject2.getString("msg");
                    }else{
		                Map<String,String> map = new HashMap<>();
                        map.put("id",jsonObject2.getString("id"));
                        map.put("allPrice",jsonObject2.getString("allPrice"));
                        map.put("status",jsonObject2.getString("status"));
                        map.put("name",jsonObject2.getString("name"));
                        map.put("allNum",jsonObject2.getString("allNum"));
                        map.put("orderId",jsonObject2.getString("orderId"));
                        if(msg.equals("m")){
                            map.put("user_id",jsonObject2.getString("user_id"));
                            map.put("user_name",jsonObject2.getString("user_name"));
                        }
                        list.add(map);
                    }
                }
                if(status.equals("ok") && list!=null && list.size()!=0){
                    res = "t";
                    resList.clear();
                    //  处理数据
                    Map<String,Object> map;
                    for(Map<String,String> m:list){
                        map = new HashMap<>();
                        map.put("imgBit", GetImage.getInternetPicture(getActivity(),imgUrlHead+"/"+m.get("id").split(";")[0]+"_01.jpg"));
                        map.put("kind",msg);
                        map.put("status",m.get("status"));
                        map.put("orderId",m.get("orderId"));
                        map.put("name",m.get("name"));
                        map.put("number",m.get("allNum"));
                        map.put("price",m.get("allPrice"));
                        if(msg.equals("m")){
                            map.put("user_id",m.get("user_id"));
                            map.put("userName",m.get("user_name"));
                        }
                        resList.add(map);
                    }
                }else if(status.equals("no") && msg.equals("relog")){
                    res = "r";
                }//if end

            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("网络问题");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("URL出问题了");
            }
            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(((String)o).equals("t")){
                myAdapter.setList(resList);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"加载完成",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(((String)o).equals("r")){
                Toast.makeText(getContext(),"请重新登录",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"加载失败",Toast.LENGTH_LONG).show();
            }
            canAC = true;
            refreshLayout.setRefreshing(false);
        }
    }//class end


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
            pd = new ProgressDialog(getContext());
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
                logingData();
            }else if(res.equals("no")){
            }else if(res.equals("false")){
            }
            pd.dismiss();
        }
    }

    //结束

}
