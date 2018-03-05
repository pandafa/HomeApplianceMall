package com.chenexample.myshop16173.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenexample.myshop16173.CollectionActivity;
import com.chenexample.myshop16173.GlideImageLoader;
import com.chenexample.myshop16173.GoodDetailActivity;
import com.chenexample.myshop16173.LoginActivity;
import com.chenexample.myshop16173.R;
import com.chenexample.myshop16173.ScanActivity;
import com.chenexample.myshop16173.myview.MyGridView;
import com.chenexample.myshop16173.util.GetImage;
import com.chenexample.myshop16173.util.MyApplication;
import com.chenexample.myshop16173.util.util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private MyApplication application;
    private MyGridView gridView;
    private MyGridView gridViewToday;
    private MyGridView gridViewSale;
    private MyGridView gridViewNew;
    private MyAdapterForGoods myAdapterForToday ;
    private MyAdapterForGoods myAdapterForSale ;
    private MyAdapterForGoods myAdapterForNew ;
    private List<Map<String,Object>> menuList;
    private List<Map<String,Object>> todayList;
    private List<Map<String,Object>> saleList;
    private List<Map<String,Object>> newList;
    private final int KIND_TODAY = 1;
    private final int KIND_SALES = 2;
    private final int KIND_NEW = 3;
    private final int KIND_KIND = 4;
    private LoginAC ac;
    private boolean first;
    private boolean canRefresh;
    public HomeFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        canRefresh = true;
        first = true;
        application = (MyApplication)getActivity().getApplication();
        gridView = (MyGridView) v.findViewById(R.id.GridView_fhome);
        gridViewToday = (MyGridView) v.findViewById(R.id.GridView_fhome_today);
        gridViewSale = (MyGridView) v.findViewById(R.id.GridView_fhome_sale);
        gridViewNew = (MyGridView) v.findViewById(R.id.GridView_fhome_new);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.adv_1);
        images.add(R.drawable.adv_2);
        images.add(R.drawable.adv_3);
        List<String> titles = new ArrayList<>();
        titles.add("地一个");
        titles.add("第二个");
        titles.add("第三个");
        Banner banner = (Banner) v.findViewById(R.id.homeBanner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Accordion);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1100);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        initMenuList();
        todayList = new ArrayList<>();
        saleList = new ArrayList<>();
        newList = new ArrayList<>();
        MyAdapter myAdapter = new MyAdapter(this.getContext(),menuList);
        myAdapterForToday = new MyAdapterForGoods(this.getContext(),todayList);
        myAdapterForSale = new MyAdapterForGoods(this.getContext(),saleList);
        myAdapterForNew = new MyAdapterForGoods(this.getContext(),newList);
        gridView.setAdapter(myAdapter);
        gridViewToday.setAdapter(myAdapterForToday);
        gridViewSale.setAdapter(myAdapterForSale);
        gridViewNew.setAdapter(myAdapterForNew);
        ac = new LoginAC();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("---------------------------开始了----------------------------------------");
        if(first && canRefresh){
            canRefresh = false;
            try{
                ac.execute(123);
                first = false;
            }catch (Exception e){
                System.out.println("这行了二次homeAC");
            }
        }
    }

    private void initMenuList(){
        menuList = new ArrayList<>();
        Map<String,Object> mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_tv);
        mapTemp.put("text","电视");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_fridge);
        mapTemp.put("text","冰箱");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_washer);
        mapTemp.put("text","洗衣机");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_airconditioning);
        mapTemp.put("text","空调");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_geyser);
        mapTemp.put("text","热水器");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_scan);
        mapTemp.put("text","扫一扫");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_collect);
        mapTemp.put("text","收藏");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_sort);
        mapTemp.put("text","分类");
        menuList.add(mapTemp);
        mapTemp = new HashMap<>();
        mapTemp.put("img",R.drawable.f_home_order);
        mapTemp.put("text","订单");
        menuList.add(mapTemp);
    }




    class MyAdapter extends BaseAdapter {

        private List<Map<String,Object>> list;
        private Context context;

        public MyAdapter(Context context,List<Map<String,Object>> list){
            super();
            this.list=list;
            this.context=context;
        }
        public void setList(List<Map<String,Object>> list){this.list=list;}
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_f_gridview,null);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView_i_f_gridview);
            TextView textView = (TextView) v.findViewById(R.id.textView_i_f_gridview);
            ConstraintLayout layout = (ConstraintLayout) v.findViewById(R.id.Layout_i_f_gridview);
            String textStr = (String)list.get(position).get("text");
            imageView.setImageResource((int)list.get(position).get("img"));
            textView.setText(textStr);
            v.setContentDescription(textStr);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String s = v.getContentDescription().toString();
                    if(s.equals("电视")){
                        application.setCurrentKind(Integer.parseInt(application.getKindMap().get("电视")));
                        application.getViewPager().setCurrentItem(2);
                    }else if(s.equals("冰箱")){
                        application.setCurrentKind(Integer.parseInt(application.getKindMap().get("冰箱")));
                        application.getViewPager().setCurrentItem(2);
                    }else if(s.equals("洗衣机")){
                        application.setCurrentKind(Integer.parseInt(application.getKindMap().get("洗衣机")));
                        application.getViewPager().setCurrentItem(2);
                    }else if(s.equals("空调")){
                        application.setCurrentKind(Integer.parseInt(application.getKindMap().get("空调")));
                        application.getViewPager().setCurrentItem(2);
                    }else if(s.equals("热水器")){
                        application.setCurrentKind(Integer.parseInt(application.getKindMap().get("热水器")));
                        application.getViewPager().setCurrentItem(2);
                    }else if(s.equals("扫一扫")){
                        Intent intent = new Intent(getContext(), ScanActivity.class);
                        startActivity(intent);
                    }else if(s.equals("收藏")){
                        if(application.isB_login()){
                            Intent intent = new Intent(getContext(), CollectionActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_SHORT).show();
                        }
                    }else if(s.equals("分类")){
                        application.getViewPager().setCurrentItem(2);
                    }else if(s.equals("订单")){
                        application.getViewPager().setCurrentItem(4);
                    }
                    //-------
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

    class MyAdapterForGoods extends BaseAdapter {

        private List<Map<String,Object>> list;
        private Context context;

        public MyAdapterForGoods(Context context,List<Map<String,Object>> list){
            super();
            this.list=list;
            this.context=context;
        }

        public void setList(List<Map<String,Object>> list){this.list=list;}


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_f_goods,null);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView_f_goods);
            TextView textViewName = (TextView) v.findViewById(R.id.textView_f_goods_name);
            TextView textViewPre = (TextView) v.findViewById(R.id.textView_f_goods_pre_price);
            TextView textViewPrice = (TextView) v.findViewById(R.id.textView_f_goods_price);
            TextView textViewOver = (TextView) v.findViewById(R.id.textView_f_goods_over);
            if(!list.get(position).containsKey("imgBit") || list.get(position).get("imgBit")==null){
                imageView.setImageResource(R.drawable.wait);
            }else{
                imageView.setImageBitmap((Bitmap)list.get(position).get("imgBit"));
            }

            textViewName.setText((String)list.get(position).get("name"));
            v.setContentDescription((String)list.get(position).get("id"));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 到商品详情
                    Intent intent = new Intent(getContext(), GoodDetailActivity.class);
                    intent.putExtra("goodId",v.getContentDescription());
                    startActivity(intent);
                }
            });
            if(list.get(position).get("pre")==null || ((String)list.get(position).get("pre")).length()==0){
                textViewPre.setVisibility(View.INVISIBLE);
            }else{
                textViewPre.setText("商品原价："+(String)list.get(position).get("pre")+" 元");
            }
            if(list.get(position).get("over")==null || ((String)list.get(position).get("over")).length()==0){
                textViewPre.setVisibility(View.GONE);
            }else{
                textViewOver.setText("已售出"+(String)list.get(position).get("over")+"件");
            }
            textViewPrice.setText("现售价："+(String)list.get(position).get("price")+" 元");

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

    class LoginAC extends AsyncTask<Integer, Object, Object> {

        private ProgressDialog pd;
        private String imgUrlHead;

        @Override
        protected Object doInBackground(Integer... params) {
            int res = 0;
            imgUrlHead = application.getWebURL() +"/images/goods";
            int[] kind = new int[]{KIND_TODAY,KIND_SALES,KIND_NEW,KIND_KIND};
            String uri = null;
            for(int index=0;index<kind.length;index++){
                switch(kind[index]){
                    case KIND_TODAY :
                        uri = "/indexForMobile.action?kind=today";
                        break;
                    case KIND_SALES :
                        uri = "/goods/sale_goodsForMobile.action";
                        break;
                    case KIND_NEW :
                        uri = "/goods/new_goodsForMobile.action";
                        break;
                    case KIND_KIND :
                        uri = "/indexForMobile.action?kind=kinds";
                        break;
                }
                try {
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient( application.getWebURL()+uri)));
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
                            if(kind[index]==KIND_KIND){
                                Map<String,String> map = new HashMap<>();
                                map.put("kind_id",jsonObject2.getString("kind_id"));
                                map.put("kind_name",jsonObject2.getString("kind_name"));
                                list.add(map);
                            }else{
                                Map<String,String> map = new HashMap<>();
                                map.put("good_name",jsonObject2.getString("good_name"));
                                map.put("good_price",jsonObject2.getString("good_price"));
                                if(kind[index]==KIND_SALES){
                                    map.put("good_pre",jsonObject2.getString("good_pre"));
                                }
                                if(kind[index]==KIND_TODAY){
                                    map.put("good_mid",jsonObject2.getString("good_mid"));
                                }else{
                                    map.put("good_over",jsonObject2.getString("good_over"));
                                }
                                map.put("good_id",jsonObject2.getString("good_id"));
                                list.add(map);
                            }

                        }
                    }
                    if(status.equals("ok") && list!=null){
                        res++;
                        Map<String,Object> mapTemp = null;
                        switch(kind[index]){
                            case KIND_TODAY :
                                for(Map<String,String> map:list){
                                    mapTemp = new HashMap<>();
//                                    mapTemp.put("img",R.drawable.wait);
                                    mapTemp.put("imgBit", GetImage.getInternetPicture(getActivity(),imgUrlHead+"/"+map.get("good_id")+"_01.jpg"));
                                    mapTemp.put("name",map.get("good_name"));
                                    mapTemp.put("price",map.get("good_price"));
                                    mapTemp.put("id", map.get("good_id"));
                                    mapTemp.put("mid", map.get("good_mid"));
                                    todayList.add(mapTemp);
                                }
                                break;
                            case KIND_SALES :
                                for(Map<String,String> map:list){
                                    mapTemp = new HashMap<>();
//                                    mapTemp.put("img", R.drawable.wait);
                                    mapTemp.put("imgBit", GetImage.getInternetPicture(getActivity(),imgUrlHead+"/"+map.get("good_id")+"_01.jpg"));
                                    mapTemp.put("name", map.get("good_name"));
                                    mapTemp.put("price", map.get("good_price"));
                                    mapTemp.put("over", map.get("good_over"));
                                    mapTemp.put("pre", map.get("good_pre"));
                                    mapTemp.put("id", map.get("good_id"));
                                    saleList.add(mapTemp);
                                }
                                break;
                            case KIND_NEW :
                                for(Map<String,String> map:list){
                                    mapTemp = new HashMap<>();
//                                    mapTemp.put("img", R.drawable.wait);
                                    mapTemp.put("imgBit", GetImage.getInternetPicture(getActivity(),imgUrlHead+"/"+map.get("good_id")+"_01.jpg"));
                                    mapTemp.put("name", map.get("good_name"));
                                    mapTemp.put("price", map.get("good_price"));
                                    mapTemp.put("over", map.get("good_over"));
                                    mapTemp.put("id", map.get("good_id"));
                                    newList.add(mapTemp);
                                }
                                break;
                            case KIND_KIND :
                                Map<String,String> mapTemp2 = new HashMap<>();
                                for(Map<String,String> map:list){
                                    mapTemp2.put(map.get("kind_name"), map.get("kind_id"));
                                }
                                application.setKindMap(mapTemp2);
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getContext());
            pd.setMessage("加载中...");
            if(!pd.isShowing()){
                pd.show();
            }

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int res = (int)o;
            if(res!=0){
                myAdapterForToday.setList(todayList);
                myAdapterForToday.notifyDataSetChanged();
                myAdapterForSale.setList(saleList);
                myAdapterForSale.notifyDataSetChanged();
                myAdapterForNew.setList(newList);
                myAdapterForNew.notifyDataSetChanged();
                if(res!=3){
                    Toast.makeText(getContext(),"加载失败！（"+(3-res)+"个）",Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(getContext(),"加载成功",Toast.LENGTH_LONG);
                }
            }else{
                Toast.makeText(getContext(),"加载失败！",Toast.LENGTH_LONG);
            }
            pd.dismiss();
            canRefresh = true;
        }
    }

}
