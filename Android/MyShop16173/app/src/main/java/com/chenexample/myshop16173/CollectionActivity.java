package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class CollectionActivity extends Activity {

    private Toolbar toolbar;
    private LinearLayout layoutNull,layoutFull;
    private ListView listView;
    private Button btnBack;
    private List<Map<String,Object>> resList;
    private MyAdapterForCollList myAdapter;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        application = (MyApplication)getApplication();
        layoutNull = (LinearLayout) findViewById(R.id.linearLayout_collection_null);
        layoutFull = (LinearLayout) findViewById(R.id.linearLayout_collection_full);
        listView = (ListView) findViewById(R.id.listView_collection);
        toolbar = (Toolbar) findViewById(R.id.toolbar_collection);
        btnBack = (Button) findViewById(R.id.button_collection);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("我的收藏夹");
        toolbar.setNavigationIcon(R.drawable.path_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resList = new ArrayList<>();
        myAdapter = new MyAdapterForCollList(this.getBaseContext(),resList);
        listView.setAdapter(myAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        layoutNull.setVisibility(View.GONE);
        layoutFull.setVisibility(View.GONE);
        CollectionAC ac = new CollectionAC();
        if(!ac.isCancelled()){
            ac.execute("init");
        }
    }

    class MyAdapterForCollList extends BaseAdapter{
        private List<Map<String,Object>> list;
        private Context context;

        public MyAdapterForCollList(Context context,List<Map<String,Object>> list){
            super();
            this.list=list;
            this.context=context;
        }
        public void setList(List<Map<String,Object>> list){
            this.list=list;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_a_collection_goods,null);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView_i_a);
            TextView textX = (TextView) v.findViewById(R.id.textView_i_a_x);
            TextView textName = (TextView) v.findViewById(R.id.textView_i_a_name);
            TextView textPrice = (TextView) v.findViewById(R.id.textView_i_a_price);
            Button buttonLook = (Button) v.findViewById(R.id.button_i_a_collection_look);
            Button buttonDel = (Button) v.findViewById(R.id.button_i_a_collection_del);
            if(!list.get(position).containsKey("imgBit") || list.get(position).get("imgBit")==null){
                imageView.setImageResource(R.drawable.wait);
            }else{
                imageView.setImageBitmap((Bitmap)list.get(position).get("imgBit"));
            }
            textX.setText(""+(position+1));
            textName.setText((String)list.get(position).get("name"));
            textPrice.setText((String)list.get(position).get("price"));
            buttonLook.setContentDescription((String)list.get(position).get("id"));
            buttonDel.setContentDescription((String)list.get(position).get("id"));
            buttonLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 查看
                    String goodId = v.getContentDescription().toString();
                    Intent intent = new Intent(CollectionActivity.this, GoodDetailActivity.class);
                    intent.putExtra("goodId",goodId);
                    startActivity(intent);
                }
            });
            buttonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 删除
                    String[] str = new String[2];
                    str[0] = "del";
                    str[1] = v.getContentDescription().toString();
                    CollectionAC ac = new CollectionAC();
                    if(!ac.isCancelled()){
                        ac.execute(str);
                    }
                }
            });
            return v;
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
    //

    //获取开始

    class CollectionAC extends AsyncTask<String, Object, Object> {
        private ProgressDialog pd;
        private String imgUrlHead;
        @Override
        protected Object doInBackground(String... params) {
            String res = "no";
            imgUrlHead = application.getWebURL() +"/images/goods";
            try {
                String data = "sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8");

                if(params[0].equals("init")){
                    //初始化
                    data = application.getWebURL() + "/collectionForMobile.action?" + data;
                    System.out.println("初始化收藏夹URL--->"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;//noLogin、ok
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
                            map.put("good_name",jsonObject2.getString("good_name"));
                            map.put("good_price",jsonObject2.getString("good_price"));
                            map.put("good_id",jsonObject2.getString("good_id"));
                            list.add(map);
                        }
                    }
                    if(status.equals("ok")){
                        resList.clear();
                        if(list!=null && list.size()>0){
                            Map<String,Object> map;
                            for(Map<String,String> m:list){
                                map = new HashMap<>();
                                map.put("imgBit", GetImage.getInternetPicture(CollectionActivity.this,imgUrlHead+"/"+m.get("good_id")+"_01.jpg"));
                                map.put("name",m.get("good_name"));
                                map.put("price",m.get("good_price"));
                                map.put("id",m.get("good_id"));
                                resList.add(map);
                            }
                        }
                        res = "initOk";
                    }else{
                        res = msg;
                    }
                    //初始化结束
                }else if(params[0].equals("del")) {
                    //移除收藏夹
                    data += "&id=" + URLEncoder.encode(params[1], "UTF-8");
                    data = application.getWebURL() + "/collectionForMobile.action?do=del&" + data;
                    System.out.println("移除收藏夹URL---------->"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;//delOk、delFalse
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        if(i==0){
                            status = jsonObject2.getString("status");
                            msg = jsonObject2.getString("msg");
                        }
                    }
                    if(status.equals("ok")){
                        res = "delOk";
                    }else{
                        res = msg;//delFalse
                    }
                    //移除收藏夹结束
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
            pd = new ProgressDialog(CollectionActivity.this);
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
            if(res.equals("no")){
                System.out.println("异常");
            }else if(res.equals("initOk")){
                myAdapter.setList(resList);
                myAdapter.notifyDataSetChanged();
                if(resList.size()==0){
                    layoutNull.setVisibility(View.VISIBLE);
                }else{
                    layoutFull.setVisibility(View.VISIBLE);
                }
                System.out.println("加载成功");
            }else if(res.equals("delOk")){
                onResume();
                System.out.println("删除成功");
            }else if(res.equals("")){
            }else if(res.equals("")){
            }
            pd.dismiss();
        }
    }

    //获取结束
}
