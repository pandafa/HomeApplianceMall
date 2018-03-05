package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chenexample.myshop16173.util.GetImage;
import com.chenexample.myshop16173.util.MyApplication;
import com.chenexample.myshop16173.util.util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodDetailActivity extends Activity {

    private TextView textViewName,textViewPre,textViewPrice,textViewOver;
    private Button btnBuy,btnAddCollection,btnAddShoppingCar;
    private MyApplication myApplication;
    private Map<String,String> resGoodMap;
    private List<Map<String,String>> detailList;
    private Banner banner;
    private LinearLayout linearLayoutDispaly;
    private EditText editNum;
    private String goodId;
    private List<Bitmap> topImages;
    private List<Bitmap> detailImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        myApplication = (MyApplication)getApplication();

        textViewName = (TextView) findViewById(R.id.textView_good_detail_name);
        textViewPre = (TextView) findViewById(R.id.textView_good_detail_pre);
        textViewPrice = (TextView) findViewById(R.id.textView_good_detail_price);
        textViewOver = (TextView) findViewById(R.id.textView_good_detail_over);
        btnBuy = (Button) findViewById(R.id.button_good_detail_buy);
        btnAddCollection = (Button) findViewById(R.id.button_good_detail_add_collection);
        btnAddShoppingCar = (Button) findViewById(R.id.button_good_detail_add_shoppingcar);
        linearLayoutDispaly = (LinearLayout) findViewById(R.id.linearLayout_dispaly);
        editNum = (EditText) findViewById(R.id.editText_good_detail);

        topImages = new ArrayList<>();
        detailImages = new ArrayList<>();
        banner = (Banner) findViewById(R.id.goodDetailBanner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GoodsImageLoader());
        //设置图片集合
//        banner.setImages(topImages);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1100);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
//        banner.start();


        goodId = getIntent().getStringExtra("goodId");
        //TODO


        //
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myApplication.isB_login()){
                    Toast.makeText(GoodDetailActivity.this,"请先登录",Toast.LENGTH_LONG).show();
                    return;
                }
                String num = editNum.getText().toString();
                if(num.length()==0){
                    Toast.makeText(GoodDetailActivity.this,"请先填写购买数量",Toast.LENGTH_LONG).show();
                    return;
                }
                GoodDetailAC ac = new GoodDetailAC();
                String[] strings = new String[3];
                strings[0] = "buy";
                strings[1] = goodId;
                strings[2] = num;
//                strings[3] = goodId;//di
                ac.execute(strings);
            }
        });
        btnAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myApplication.isB_login()){
                    Toast.makeText(GoodDetailActivity.this,"请先登录",Toast.LENGTH_LONG).show();
                    return;
                }
                GoodDetailAC ac = new GoodDetailAC();
                String[] strings = new String[2];
                strings[0] = "collection";
                strings[1] = goodId;
                ac.execute(strings);
            }
        });
        btnAddShoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myApplication.isB_login()){
                    Toast.makeText(GoodDetailActivity.this,"请先登录",Toast.LENGTH_LONG).show();
                    return;
                }
                GoodDetailAC ac = new GoodDetailAC();
                String[] strings = new String[2];
                strings[0] = "shoppingcar";
                strings[1] = goodId;
                ac.execute(strings);
            }
        });
        GoodDetailAC ac = new GoodDetailAC();
        String[] strings = new String[2];
        strings[0] = "init";
        strings[1] = goodId;
        ac.execute(strings);
    }

    class GoodDetailAC extends AsyncTask<String, Object, Object> {
        private ProgressDialog pd;
        private String imgUrlHead;
        @Override
        protected Object doInBackground(String... params) {
            String res = null;
            imgUrlHead = myApplication.getWebURL() +"/images/goods";
            try {
                if(params[0].equals("init")){
                    //初始化
                    String data = "id=" + URLEncoder.encode(params[1], "UTF-8");
                    data = myApplication.getWebURL() + "/goods/goods_detailForMobile.action?" + data;
                    System.out.println("物品详情初始化URL------>"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;//ok
                    List<Map<String,String>> list = null;
                    Map<String,String> goodMap = null;
                    if(jsonArray.length()>2){
                        list = new ArrayList<>();
                    }
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        if(i==0){
                            status = jsonObject2.getString("status");
                            msg = jsonObject2.getString("msg");
                        }else if(i==1){
                            detailImages.clear();
                            topImages.clear();
                            goodMap = new HashMap();
                            goodMap.put("good_name",jsonObject2.getString("good_name"));
                            goodMap.put("good_mid",jsonObject2.getString("good_mid"));
                            goodMap.put("good_price",jsonObject2.getString("good_price"));
                            goodMap.put("istoday",jsonObject2.getString("istoday"));
                            goodMap.put("good_dis",jsonObject2.getString("good_dis"));
                            goodMap.put("good_kind",jsonObject2.getString("good_kind"));
                            goodMap.put("good_pre",jsonObject2.getString("good_pre"));
                            goodMap.put("good_over",jsonObject2.getString("good_over"));
                            goodMap.put("good_id",jsonObject2.getString("good_id"));
                            String imgUrlHead = myApplication.getWebURL() +"/images/goods";
                            for(int k=1;k<=Integer.parseInt(jsonObject2.getString("good_mid"));k++){
                                Bitmap b =  GetImage.getInternetPicture(GoodDetailActivity.this,imgUrlHead+"/"+goodMap.get("good_id")+"_0"+k+".jpg");
                                if(b!=null){
                                    topImages.add(b);
                                }
                            }
                            for(int k=1;k<=Integer.parseInt(jsonObject2.getString("good_dis"));k++){
                                Bitmap b =  GetImage.getInternetPicture(GoodDetailActivity.this,imgUrlHead+"/"+goodMap.get("good_id")+"_"+k+".png");
                                if(b!=null){
                                    detailImages.add(b);
                                }
                            }
                            resGoodMap = goodMap;
                        }else{
                            Map<String,String> map = new HashMap<>();
                            map.put("detail_id",jsonObject2.getString("detail_id"));
                            map.put("detail_name",jsonObject2.getString("detail_name"));
                            list.add(map);
                        }
                    }
                    if(status.equals("ok")){
                        //                        topImages
//                        detailImages
                        detailList = list;
                        res = "initOk";
                    }else{
                        res = msg;
                    }
                    //初始化结束
                }else if(params[0].equals("shoppingcar")){
                    //加入购物车
                    String data = "sessionid=" + URLEncoder.encode(myApplication.getSessionId(), "UTF-8") +
                                "&goodId=" + URLEncoder.encode(params[1], "UTF-8");
                    if(params.length==3){
                        data += "&detailId=" + URLEncoder.encode(params[2], "UTF-8");
                    }
                    data = myApplication.getWebURL() + "/shoppingcarForMobile.action?do=add&" + data;
                    System.out.println("物品详情加入购物车URL------>"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;//noAdd、add
                    String shoppingcarNum = null;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        if(i==0){
                            status = jsonObject2.getString("status");
                            msg = jsonObject2.getString("msg");
                            if(status.equals("no")){
                                break;
                            }
                        }else{
                            shoppingcarNum = jsonObject2.getString("shoppingcarNum");
                        }
                    }
                    if(status.equals("ok")){
                        res = "carOk";
                    }else{
                        res = msg;
                    }
                    //加入购物车结束
                }else if(params[0].equals("collection")){
                    //加入收藏夹
                    String data = "sessionid=" + URLEncoder.encode(myApplication.getSessionId(), "UTF-8") +
                                "&id=" + URLEncoder.encode(params[1], "UTF-8");
                    data = myApplication.getWebURL() + "/collectionForMobile.action?do=add&" + data;
                    System.out.println("物品详情加入收藏夹URL------>"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;//addOk、addFalse
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        if(i==0){
                            status = jsonObject2.getString("status");
                            msg = jsonObject2.getString("msg");
                        }
                    }
                    if(status.equals("ok")){
                        res = "collectOk";
                    }else{
                        res = msg;
                    }
                    //加入收藏夹结束
                }else if(params[0].equals("buy")){
                    //立即购买
                    String data = "sessionid=" + URLEncoder.encode(myApplication.getSessionId(), "UTF-8") +
                            "&goodId_1=" + URLEncoder.encode(params[1], "UTF-8") +
                            "&goodNumber_1=" + URLEncoder.encode(params[2], "UTF-8") ;
                    if(params.length==4){
                        data += "&detailId_1=" + URLEncoder.encode(params[3], "UTF-8");
                    }
                    data = myApplication.getWebURL() + "/orderdetailForMobile.action?submitaim=create&" + data;
                    System.out.println("物品详情立即购买URL------>"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;
                    float allPrice = -1;
                    ArrayList<Map<String,String>> list = null;
                    if(jsonArray.length()>1){
                        list = new ArrayList<>();
                    }
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        if(i==0){
                            status = jsonObject2.getString("status");
                            msg = jsonObject2.getString("msg");
                        }else if(i==1){
                            allPrice = Float.parseFloat(jsonObject2.getString("allPrice"));
                        }else{
		                    Map<String,String> map = new HashMap<>();
                            map.put("good_name",jsonObject2.getString("good_name"));
                            map.put("good_mid",jsonObject2.getString("good_mid"));
                            map.put("goodNumber",jsonObject2.getString("goodNumber"));
                            map.put("good_price",jsonObject2.getString("good_price"));
                            map.put("istoday",jsonObject2.getString("istoday"));
                            map.put("good_dis",jsonObject2.getString("good_dis"));
                            map.put("good_kind",jsonObject2.getString("good_kind"));
                            map.put("detailName",jsonObject2.getString("detailName"));
                            map.put("good_pre",jsonObject2.getString("good_pre"));
                            map.put("good_over",jsonObject2.getString("good_over"));
                            map.put("good_id",jsonObject2.getString("good_id"));
                            map.put("imgPath", GetImage.getInternetPicturePath(GoodDetailActivity.this,imgUrlHead+"/"+jsonObject2.getString("good_id").split(";")[0]+"_01.jpg"));
                            map.put("detailId",jsonObject2.getString("detailId"));
                            list.add(map);
                        }
                    }
                    if(status.equals("ok")){
                        res = "buyOk";
                        Intent intent = new Intent(GoodDetailActivity.this, SubmitOrderActivity.class);
                        intent.putExtra("submitRes", list);
                        intent.putExtra("allPrice", allPrice);
                        System.out.println("-------------------+++++++++++++++++");
                        startActivity(intent);
                    }else{
                        res = msg;
                    }
                    //立即购买结束
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
            pd = new ProgressDialog(GoodDetailActivity.this);
            pd.setMessage("加载中...");
            if(!pd.isShowing()){
                pd.show();
            }
            pd.show();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String res = (String)o;
            if(res.equals("initOk")){
                if(resGoodMap!=null){
                    textViewName.setText(resGoodMap.get("good_name"));
                    textViewPre.setText("原价：￥"+resGoodMap.get("good_pre"));
                    textViewPrice.setText("￥"+resGoodMap.get("good_price"));
                    textViewOver.setText("已售出"+resGoodMap.get("good_over")+"个");
//                    topImages
                    //TODO
                    banner.setImages(topImages);
                    banner.start();
                    for(int i=0;i<detailImages.size();i++){
                        ImageView v = new ImageView(GoodDetailActivity.this);
//                        v.setImageResource(R.drawable.box_null);
                        v.setImageBitmap(detailImages.get(i));
                        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayoutDispaly.addView(v);
                    }
                    Toast.makeText(GoodDetailActivity.this,"加载完成",Toast.LENGTH_LONG).show();
                }
            }else if(res.equals("carOk")){
                Toast.makeText(GoodDetailActivity.this,"添加购物车成功",Toast.LENGTH_LONG).show();
            }else if(res.equals("collectOk")){
                Toast.makeText(GoodDetailActivity.this,"添加收藏夹成功",Toast.LENGTH_LONG).show();
            }else if(res.equals("buyOk")){
                Toast.makeText(GoodDetailActivity.this,"购买",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(GoodDetailActivity.this,"处理异常。"+res,Toast.LENGTH_LONG).show();
            }
            pd.dismiss();
//            canAC = true;
        }
    }
}
