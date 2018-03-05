package com.chenexample.myshop16173.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chenexample.myshop16173.LoginActivity;
import com.chenexample.myshop16173.R;
import com.chenexample.myshop16173.RegisterActivity;
import com.chenexample.myshop16173.SubmitOrderActivity;
import com.chenexample.myshop16173.util.GetImage;
import com.chenexample.myshop16173.util.MyApplication;
import com.chenexample.myshop16173.util.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCarFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private MyApplication application;
    private LinearLayout layoutNull,layoutNoLogin,layoutMain,layoutNo;
    private Button btnLogin,btnReg,btnSubmit;
    private CheckBox checkBoxAll;
    private ListView listView;
    private List<Map<String,Object>> resList;
    private MyAdapterForCarGoods myAdapter;
    private boolean canAC;
    private int checkedAll;
    private final int CHECK_ALL = 0;
    private final int CHECK_NOALL = 1;
    private final int CHECK_TONO = 2;
    private final int CHECK_TOALL = 3;
    private List<CheckBox> checkBoxs;
    private List<EditText> editTextList;

    public ShoppingCarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_car, container, false);
        canAC = true;
        checkedAll = CHECK_NOALL;
        checkBoxs = new ArrayList<>();
        editTextList = new ArrayList<>();
        application = (MyApplication)getActivity().getApplication();
        listView = (ListView) v.findViewById(R.id.listView_fcar);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout_f_car);
        layoutNull = (LinearLayout) v.findViewById(R.id.linearLayout_fcar_null);
        layoutNoLogin = (LinearLayout) v.findViewById(R.id.linearLayout_fcar_noLogin);
        layoutNo = (LinearLayout) v.findViewById(R.id.linearLayout_fcar_no);
        layoutMain = (LinearLayout) v.findViewById(R.id.linearLayout_fcar_main);
        btnLogin = (Button) v.findViewById(R.id.button_fclass_login);
        btnReg = (Button) v.findViewById(R.id.button_fclass_reg);
        btnSubmit = (Button) v.findViewById(R.id.button_f_shoppingcar_submit);
        checkBoxAll = (CheckBox) v.findViewById(R.id.radioButton_f_shoppingcar_all);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交订单
                List<String> tempList = new ArrayList<String>();
                for(int i=0;i<checkBoxs.size();i++){
                    if(checkBoxs.get(i).isChecked()){
                        tempList.add(checkBoxs.get(i).getContentDescription().toString());
                        tempList.add(editTextList.get(i).getText().toString());
                    }
                }
                String[] resStr = new String[tempList.size()+1];
                resStr[0] = "submit";
                for(int i=0;i<tempList.size();i++){
                    resStr[i+1] = tempList.get(i);
                }
                //for  为测试
                for(int i=0;i<resStr.length;i++){
                    System.out.println(i+"---"+resStr[i]);
                }
                ShoppingCarAC ac = new ShoppingCarAC();
                if(canAC){
                    canAC = false;
                    ac.execute(resStr);
                }
            }
        });

        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 全选/全不选
                switch (checkedAll){
                    case CHECK_TONO:
                        break;
                    case CHECK_TOALL:
                        break;
                    default:
                        for(CheckBox c:checkBoxs){
                            c.setChecked(isChecked);
                        }
                        if(isChecked){
                            checkedAll = CHECK_ALL;
                        }else{
                            checkedAll = CHECK_NOALL;
                        }
                        break;
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(application.isB_login()){
                    initData();
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

        resList = new ArrayList<>();
        myAdapter = new MyAdapterForCarGoods(this.getContext(),resList);
        listView.setAdapter(myAdapter);
        initData();

        //显示方式
        layoutNoLogin.setVisibility(View.INVISIBLE);
        layoutNull.setVisibility(View.INVISIBLE);
        layoutMain.setVisibility(View.INVISIBLE);
        layoutNo.setVisibility(View.INVISIBLE);
        if(application.isB_login()){
            if(application.getS_loginUserKind().equals("m")){
                //管理员
                layoutNo.setVisibility(View.VISIBLE);
            }else{
                if(application.getNumberShoppingCar()==0){
                    //没东西
                    layoutNull.setVisibility(View.VISIBLE);
                }else{
                    //有东西
                    layoutMain.setVisibility(View.VISIBLE);
                }
            }
        }else{
            //没登录
            layoutNoLogin.setVisibility(View.VISIBLE);
        }
    }

    private void initData(){
        ShoppingCarAC ac = new ShoppingCarAC();
        if(canAC){
            canAC = false;
            ac.execute("look");
        }
    }

    class MyAdapterForCarGoods extends BaseAdapter{
        private List<Map<String,Object>> list;
        private Context context;
        public MyAdapterForCarGoods(Context context,List<Map<String,Object>> list){
            this.context=context;
            this.list=list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_f_car_goods,null);
            final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox_fcar);
            ImageView image = (ImageView) v.findViewById(R.id.imageView_fcar);
            TextView textName = (TextView) v.findViewById(R.id.textView_fcar_name);
            TextView textPrice = (TextView) v.findViewById(R.id.textView_fcar_price);
            EditText editNum = (EditText) v.findViewById(R.id.editText_fcar_num);
            Button button = (Button) v.findViewById(R.id.button_fcar_del);
            if(!list.get(position).containsKey("imgBit") || list.get(position).get("imgBit")==null){
                image.setImageResource(R.drawable.wait);
            }else{
                image.setImageBitmap((Bitmap)list.get(position).get("imgBit"));
            };
            textName.setText((String)resList.get(position).get("name"));
            textPrice.setText((String)resList.get(position).get("price"));
            editNum.setText("1");
            button.setContentDescription((String)resList.get(position).get("goodId"));
            checkBox.setContentDescription((String)resList.get(position).get("goodId"));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boolean is = true;
                    for(int i=0;i<checkBoxs.size();i++){
                        if(!checkBoxs.get(i).isChecked()){
                            is = false;
                            break;
                        }
                    }
                    if(is){
                        checkedAll = CHECK_TOALL;
                    }else{
                        checkedAll = CHECK_TONO;
                    }
                    checkBoxAll.setChecked(is);
                }
            });

            checkBoxs.add(checkBox);
            editTextList.add(editNum);
            //移除购物车
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoppingCarAC ac = new ShoppingCarAC();
                    if(canAC){
                        canAC = false;
                        String[] str = new String[3];
                        str[0] = "del";
                        str[1] = v.getContentDescription().toString();
                        str[2] = "";
                        ac.execute(str);
                    }
                }
            });

            return v;
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

    class ShoppingCarAC extends AsyncTask<String, Object, Object> {
        private ProgressDialog pd;
        private String imgUrlHead;
        @Override
        protected Object doInBackground(String... params) {
            imgUrlHead = application.getWebURL() +"/images/goods";
            String res = "f";
            if(params[0].equals("look")){
                try {
                    String data = "sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8");
                    data = application.getWebURL() + "/shoppingcarForMobile.action?" + data;
                    System.out.println("order的URL:"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;//look、noLogin
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
                            map.put("good_id",jsonObject2.getString("good_id"));
                            map.put("price",jsonObject2.getString("price"));
                            map.put("name",jsonObject2.getString("name"));
                            list.add(map);
                        }
                    }
                    if(status.equals("ok") && list!=null && list.size()>0){
                        res = "t";
                        checkBoxs.clear();
                        editTextList.clear();
                        resList.clear();
                        Map<String,Object> map;
                        for(Map<String,String> m:list){
                            map = new HashMap<>();
                            map.put("imgBit", GetImage.getInternetPicture(getActivity(),imgUrlHead+"/"+m.get("good_id")+"_01.jpg"));
                            map.put("name",m.get("name"));
                            map.put("price",m.get("price"));
                            map.put("goodId",m.get("good_id"));
                            resList.add(map);
                        }
                        application.setNumberShoppingCar(list.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    System.out.println("URL出问题了");
                }
            }else if(params[0].equals("del")){
                try {
                    if(params[2]==null){
                        params[2] = "";
                    }
                    String data = "&sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8") +
                                "&goodId=" + URLEncoder.encode(params[1], "UTF-8") +
                                "&detailId=" + URLEncoder.encode(params[2], "UTF-8");
                    data = application.getWebURL() + "/shoppingcarForMobile.action?do=del" + data;
                    System.out.println("order的URL:"+data);
                    JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                    String status = null;
                    String msg = null;//noDel、del
                    JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
                    status = jsonObject2.getString("status");
                    msg = jsonObject2.getString("msg");
                    if(status.equals("ok")){
                        res = "okDel";
                    }else{
                        res = "noDel";
                    }
                    doInBackground("look");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    System.out.println("URL出问题了");
                }
            }else if(params[0].equals("submit")){
                try {
                    if(params[2]==null){
                        params[2] = "";
                    }
                    String data = "&sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8");
                    // 优化
                    for(int j=1;j<(params.length+1)/2;j++){
                        data += "&goodId_"+j+"=" + URLEncoder.encode(params[2*j-1], "UTF-8") +
                                "&goodNumber_"+j+"=" + URLEncoder.encode(params[2*j], "UTF-8");
                    }
                    data = application.getWebURL() + "/orderdetailForMobile.action?submitaim=create" + data;
                    System.out.println("order的URL:"+data);
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
                            map.put("imgPath", GetImage.getInternetPicturePath(getActivity(),imgUrlHead+"/"+jsonObject2.getString("good_id").split(";")[0]+"_01.jpg"));
                            map.put("detailId",jsonObject2.getString("detailId"));
                            list.add(map);
                        }
                    }
                    if(status.equals("ok")){
                        // 成功提交
                        Intent intent = new Intent(getContext(), SubmitOrderActivity.class);
                        intent.putExtra("submitRes", list);
                        intent.putExtra("allPrice", allPrice);
                        getActivity().startActivity(intent);
                    }else{
                        // 提交失败
                        res = "submitFalse";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    System.out.println("URL出问题了");
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
            pd.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pd.dismiss();
            String res = (String)o;
            if(res.equals("t")){
                myAdapter.setList(resList);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"加载完成",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(res.equals("f")){
//                Toast.makeText(getContext(),"加载失败",Toast.LENGTH_LONG).show();
            }else if(res.equals("noDel")){
                Toast.makeText(getContext(),"删除失败",Toast.LENGTH_LONG).show();
            }else if(res.equals("okDel")){
                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_LONG).show();
            }
            else if(res.equals("submitFalse")){
                Toast.makeText(getContext(),"订单提交失败",Toast.LENGTH_LONG).show();
            }

            refreshLayout.setRefreshing(false);
            canAC = true;
        }
    }

}
