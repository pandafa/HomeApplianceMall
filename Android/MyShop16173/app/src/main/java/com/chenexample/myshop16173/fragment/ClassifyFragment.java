package com.chenexample.myshop16173.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.AppLaunchChecker;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.chenexample.myshop16173.R;
import com.chenexample.myshop16173.adapter.MyAdapterForListGoods;
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


public class ClassifyFragment extends Fragment implements View.OnClickListener {

    private MyApplication myApplication;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private LinearLayout layoutTv,layoutFridge,layoutAir,layoutGeyser,layoutWasher;
    private List<Map<String,Object>> listList;
    private Map<Integer,String> kindMap;
    private MyAdapterForListGoods myAdape;
    private boolean canAC;

    public ClassifyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_classify, container, false);
        canAC = true;
        myApplication = (MyApplication)getActivity().getApplication();
        listView = (ListView) v.findViewById(R.id.listView_fclass);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout_f_class);
        layoutTv = (LinearLayout) v.findViewById(R.id.linearLayout_f_class_tv);
        layoutFridge = (LinearLayout) v.findViewById(R.id.linearLayout_f_class_fridge);
        layoutAir = (LinearLayout) v.findViewById(R.id.linearLayout_f_class_air);
        layoutGeyser = (LinearLayout) v.findViewById(R.id.linearLayout_f_class_geyser);
        layoutWasher = (LinearLayout) v.findViewById(R.id.linearLayout_f_class_washer);
        kindMap = new HashMap<>();
        listList = new ArrayList<>();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新操作
                int kind = myApplication.getCurrentKind();
                if(kind<0){
                    kind = -1*kind;
                    updateData(kind+"");
                }
            }
        });

        layoutTv.setOnClickListener(this);
        layoutFridge.setOnClickListener(this);
        layoutAir.setOnClickListener(this);
        layoutGeyser.setOnClickListener(this);
        layoutWasher.setOnClickListener(this);
        myAdape = new MyAdapterForListGoods(container.getContext(),listList);
        listView.setAdapter(myAdape);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        initKindMap();
        int kind = myApplication.getCurrentKind();
        System.out.println("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊-->"+kind);
        if(kind>0){
            updateData(kind+"");
            myApplication.setCurrentKind(0-kind);
        }else if(kind==0){
            kind = 1;
            updateData(kind+"");
            myApplication.setCurrentKind(0-kind);
        }
    }

    /**
     * 初始化分类ID
     */
    private void initKindMap(){
        kindMap = new HashMap<>();
        if(myApplication.getKindMap()==null || myApplication.getKindMap().size()==0){
            kindMap.put(layoutTv.getId(),"1");
            kindMap.put(layoutFridge.getId(),"2");
            kindMap.put(layoutAir.getId(),"3");
            kindMap.put(layoutGeyser.getId(),"4");
            kindMap.put(layoutWasher.getId(),"5");
        }else{
            kindMap.put(layoutTv.getId(),myApplication.getKindMap().get("电视"));
            kindMap.put(layoutFridge.getId(),myApplication.getKindMap().get("冰箱"));
            kindMap.put(layoutAir.getId(),myApplication.getKindMap().get("空调"));
            kindMap.put(layoutGeyser.getId(),myApplication.getKindMap().get("热水器"));
            kindMap.put(layoutWasher.getId(),myApplication.getKindMap().get("洗衣机"));
        }

    }


    /**
     * 点击后更新商品列表
     * @param v
     */
    @Override
    public void onClick(View v) {
        String s = kindMap.get(v.getId());
        myApplication.setCurrentKind(0-Integer.parseInt(s));
        updateData(s);
    }

    public void updateData(String kind){
        String data = null;
        try {
            data = "kind=" + URLEncoder.encode(kind, "UTF-8");
            data = myApplication.getWebURL() + "/goods/goods_listForMobile.action?" + data;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("分类URL:------------>"+data);
        if(data==null){
            Toast.makeText(getContext(),"分类异常！",Toast.LENGTH_SHORT).show();
            return;
        }
        ClassAC ac = new ClassAC();
        if(canAC){
            canAC = false;
            ac.execute(data);
        }
    }


    class ClassAC extends AsyncTask<String, Object, Object> {
        private String imgUrlHead;
        @Override
        protected Object doInBackground(String... params) {
            imgUrlHead = myApplication.getWebURL() +"/images/goods";
            boolean res = false;
            try {
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(params[0])));
                String status = null;
                String msg = null;
                String kind = null;
                List<Map<String,String>> list = null;
                if(jsonArray.length()>2){
                    list = new ArrayList<>();
                }
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                    if(i==0){
                        status = jsonObject2.getString("status");
                        msg = jsonObject2.getString("msg");
                    }else if(i==1){
                        kind = jsonObject2.getString("kind");
                    }else{
		                Map<String,String> map = new HashMap<>();
                        map.put("good_name",jsonObject2.getString("good_name"));
                        map.put("good_price",jsonObject2.getString("good_price"));
                        map.put("good_pre",jsonObject2.getString("good_pre"));
                        map.put("good_id",jsonObject2.getString("good_id"));
                        map.put("good_over",jsonObject2.getString("good_over"));
                        list.add(map);
                    }
                }
                if(list!=null && list.size()>0){
                    listList.clear();
                    res = true;
                    Map<String,Object> map;
                    for(Map<String,String> m:list){
                        map = new HashMap<>();
                        String t = imgUrlHead+"/"+map.get("good_id")+"_01.jpg";
                        System.out.println("=======》》》"+t);
                        map.put("imgBit", GetImage.getInternetPicture(getActivity(),imgUrlHead+"/"+m.get("good_id")+"_01.jpg"));
                        map.put("name",m.get("good_name"));
                        map.put("pre",m.get("good_pre"));
                        map.put("price",m.get("good_price"));
                        map.put("over",m.get("good_over"));
                        map.put("id",m.get("good_id"));
                        listList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
            if((boolean)o){
                myAdape.setList(listList);
                myAdape.notifyDataSetChanged();
                Toast.makeText(getContext(),"加载完成！！！",Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getContext(),"加载失败！",Toast.LENGTH_SHORT).show();
            }
            canAC = true;
            refreshLayout.setRefreshing(false);
        }
    }//--class end

}
