package com.chenexample.myshop16173.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.chenexample.myshop16173.R;
import com.chenexample.myshop16173.ScanActivity;
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


public class SearchFragment extends Fragment {

    private SearchView searchView;
    private ImageView imageViewScan;
    private ListView listView;
    private List<Map<String,Object>> resList;
    private MyAdapterForListGoods myAdapter;
    private boolean canAC;
    private MyApplication myApplication;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        canAC = true;
        myApplication = (MyApplication)getActivity().getApplication();
        searchView = (SearchView) v.findViewById(R.id.searchView_fsearch);
        listView = (ListView) v.findViewById(R.id.listView_fsearch);
        imageViewScan = (ImageView) v.findViewById(R.id.imageView_fsearch);
        resList = new ArrayList<>();
        myAdapter = new MyAdapterForListGoods(getContext(),resList);
        listView.setAdapter(myAdapter);

        imageViewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("提交！");
                getNewRes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("改变");
                return false;
            }
        });
        //

        return v;
    }

    /**
     * 更新列表内容
     * @param search 新的字符串
     */
    private void getNewRes(String search){
        if(search==null || search.length()==0){
            return;
        }
        System.out.println("要进行搜索了！"+search);
        String data = null;
        try {
            data = "search=" + URLEncoder.encode(search, "UTF-8");
            data = myApplication.getWebURL() + "/searchForMobile.action?" + data;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("登录URL:------------>"+data);
        if(data==null){
            Toast.makeText(getContext(),"搜索异常！",Toast.LENGTH_SHORT).show();
            return;
        }
        SearchAC ac = new SearchAC();
        if(canAC){
            canAC = false;
            ac.execute(data);
        }
    }

    class SearchAC extends AsyncTask<String, Object, Object> {
        private ProgressDialog pd;
        private String imgUrlHead;
        @Override
        protected Object doInBackground(String... params) {
            imgUrlHead = myApplication.getWebURL() +"/images/goods";
            boolean res = false;
            try {
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(params[0])));
                String status = null;
                String msg = null;
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
                        map.put("good_over",jsonObject2.getString("good_over"));
                        list.add(map);
                    }
                }
                if(list!=null && list.size()>0){
                    res = true;
                    resList.clear();
                    Map<String,Object> map;
                    for(Map<String,String> m:list){
                        map = new HashMap<>();
                        map.put("imgBit", GetImage.getInternetPicture(getActivity(),imgUrlHead+"/"+m.get("good_id")+"_01.jpg"));
                        map.put("name",m.get("good_name"));
                        map.put("price",m.get("good_price"));
                        map.put("over",m.get("good_over"));
                        map.put("id",m.get("good_id"));
                        resList.add(map);
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
            pd = new ProgressDialog(getContext());
            pd.setMessage("搜索中...");
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
                myAdapter.setList(resList);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"搜索完成",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getContext(),"搜索失败",Toast.LENGTH_LONG).show();
                canAC = true;
            }
        }
    }


}
