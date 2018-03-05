package com.chenexample.myshop16173;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SubmitOrderActivity extends Activity {

    private MyApplication application;
    private TextView textAllPrice;
    private EditText editAdd,editAddName,editAddTel;
    private ListView listView;
    private Button btn;
    private List<Map<String,Object>> list;
    private MyAdapterForOrderSubmit myAdapter;
    private List<Map<String,String>> resList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        application = (MyApplication)getApplication();
        resList = (List<Map<String,String>>)getIntent().getSerializableExtra("submitRes");
        getIntent().getFloatExtra("allPrice",2);
        listView = (ListView) findViewById(R.id.listView_submit_order);
        textAllPrice = (TextView) findViewById(R.id.textView_submit_order_all_price);
        btn = (Button) findViewById(R.id.button_submit_order);
        editAdd = (EditText) findViewById(R.id.editText_submit_order_add);
        editAddName = (EditText) findViewById(R.id.editText_submit_order_name);
        editAddTel = (EditText) findViewById(R.id.editText_submit_order_tel);
        textAllPrice.setText("总价：￥"+Float.toString(getIntent().getFloatExtra("allPrice",2)));
        initList();
        myAdapter = new MyAdapterForOrderSubmit(this.getBaseContext(),list);
        listView.setAdapter(myAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setEnabled(false);
                String add,addName,addTel;
                add = editAdd.getText().toString();
                addName = editAddName.getText().toString();
                addTel = editAddTel.getText().toString();
                if(addName.length()==0){
                    Toast.makeText(SubmitOrderActivity.this,"请填写收货人姓名",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                if(addTel.length()==0){
                    Toast.makeText(SubmitOrderActivity.this,"请填写收货人电话",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                if(add.length()==0){
                    Toast.makeText(SubmitOrderActivity.this,"请填写收货人地址",Toast.LENGTH_SHORT).show();
                    btn.setEnabled(true);
                    return;
                }
                String[] res = new String[3];
                res[0] = addName ;
                res[1] = addTel ;
                res[2] = add ;
                SubmitOrderAC ac = new SubmitOrderAC();
                if(!ac.isCancelled()){
                    ac.execute(res);
                }
            }
        });
        //----------
    }
    private void initList(){
        list = new ArrayList<>();
        Map<String,Object> map;
        for(Map<String,String> m:resList){
            map = new HashMap<>();
            map.put("img",R.drawable.wait);
            map.put("imgPath",m.get("imgPath"));
            map.put("name",m.get("good_name"));
            map.put("price",m.get("good_price"));
            map.put("number",m.get("goodNumber"));
            map.put("id",m.get("good_id"));
            if(m.get("detailId")!=null && m.get("detailId").length()!=0){
                map.put("detailId",m.get("detailId"));
            }
            list.add(map);
        }
    }

    class MyAdapterForOrderSubmit extends BaseAdapter {
        private List<Map<String,Object>> list;
        private Context context;

        public MyAdapterForOrderSubmit(Context context,List<Map<String,Object>> list){
            super();
            this.list=list;
            this.context=context;
        }
        public void setList(List<Map<String,Object>> list){
            this.list=list;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_a_order_submit,null);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView_i_a_order_img);
            TextView textName = (TextView) v.findViewById(R.id.textView_i_a_order_submit_name);
            TextView textNumber = (TextView) v.findViewById(R.id.textView_i_a_order_submit_number);
            TextView textPrice = (TextView) v.findViewById(R.id.textView_i_a_order_submit_price);
            imageView.setImageResource((int)list.get(position).get("img"));
            textName.setText((String)list.get(position).get("name"));
            textNumber.setText((String)list.get(position).get("number"));
            textPrice.setText((String)list.get(position).get("price"));
            if(!list.get(position).containsKey("imgPath") || list.get(position).get("imgPath")==null){
                imageView.setImageResource(R.drawable.wait);
            }else{
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize = 3;
                imageView.setImageBitmap(BitmapFactory.decodeFile((String)list.get(position).get("imgPath"),options));
            }
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


    class SubmitOrderAC extends AsyncTask<String, Object, Object> {

        private ProgressDialog pd;

        @Override
        protected Object doInBackground(String... params) {
            String[] res = {"no","noSubmit"};
            /**
             * &addName=阿萨德&addTel=15687&add=捱三顶五无无无若过&goodId_1=1&detailId_1=1&goodNumber_1=1&goodId_2=9&goodNumber_2=2
             */
            try {
                String data = "&sessionid=" + URLEncoder.encode(application.getSessionId(), "UTF-8");
                data += "&addName=" + URLEncoder.encode(params[0], "UTF-8") +
                        "&addTel=" + URLEncoder.encode(params[1], "UTF-8") +
                        "&add=" + URLEncoder.encode(params[2], "UTF-8") ;
                for(int i=0;i<list.size();i++){
                    data += "&goodId_"+(i+1)+"=" + URLEncoder.encode((String)list.get(i).get("id"), "UTF-8") +
                            "&goodNumber_"+(i+1)+"=" + URLEncoder.encode((String)list.get(i).get("number"), "UTF-8") ;
                    if(list.get(i).containsKey("detailId") && !((String)list.get(i).get("detailId")).equals("-1")){
                        data += "&detailId_"+(i+1)+"=" + URLEncoder.encode((String)list.get(i).get("detailId"), "UTF-8");
                    }
                }
                data = application.getWebURL() + "/orderdetailForMobile.action?submitaim=submit" + data;
                System.out.println("order的URL:"+data);
                JSONArray jsonArray = new JSONArray(String.valueOf(util.getHttpJsonByhttpclient(data)));
                String status = null;
                String msg = null;//submitFalse、订单ID
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
                status = jsonObject2.getString("status");
                msg = jsonObject2.getString("msg");
                res[0] = status;
                res[1] = msg;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("URL出问题了");
            }
            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SubmitOrderActivity.this);
            pd.setMessage("提交中...");
            if(!pd.isShowing()){
                pd.show();
            }
            pd.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String[] res = (String[])o;
            if(res[0].equals("ok")){
                Intent intent = new Intent(SubmitOrderActivity.this,OrderDetailActivity.class);
                intent.putExtra("orderId",res[1]);
                startActivity(intent);
                System.out.println("成功提交！"+res[1]);
                finish();
            }else{
                System.out.println("提交失败。。。。"+res[1]);
                Toast.makeText(SubmitOrderActivity.this,"提交失败，请重新尝试！"+res[1],Toast.LENGTH_LONG).show();
            }
            pd.dismiss();
            btn.setEnabled(true);
        }
    }

}
