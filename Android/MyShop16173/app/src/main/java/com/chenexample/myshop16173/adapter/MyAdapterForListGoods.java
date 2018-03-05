package com.chenexample.myshop16173.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenexample.myshop16173.GoodDetailActivity;
import com.chenexample.myshop16173.R;
import com.chenexample.myshop16173.listener.OnClickToGoodDetailListener;

import java.util.List;
import java.util.Map;

public class MyAdapterForListGoods extends BaseAdapter {

    private List<Map<String,Object>> list;
    private Context context;

    public MyAdapterForListGoods(Context context,List<Map<String,Object>> list){
        super();
        this.list=list;
        this.context=context;
    }
    public void setList(List<Map<String,Object>> list){
        this.list=list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_f_list_goods,null);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView_i_list_good);
        TextView textName = (TextView) v.findViewById(R.id.textView_i_list_good_name);
        TextView textPre = (TextView) v.findViewById(R.id.textView_i_list_good_pre);
        TextView textPrice = (TextView) v.findViewById(R.id.textView_i_list_good_price);
        TextView textOver = (TextView) v.findViewById(R.id.textView_i_list_good_over);
        if(!list.get(position).containsKey("imgBit") || list.get(position).get("imgBit")==null){
            imageView.setImageResource(R.drawable.wait);
        }else{
            imageView.setImageBitmap((Bitmap)list.get(position).get("imgBit"));
        }
        textName.setText((String)list.get(position).get("name"));
        if(list.get(position).get("pre")==null || ((String)list.get(position).get("pre")).length()==0){
            textPre.setVisibility(View.GONE);
        }else{
            textPre.setText("原价："+(String)list.get(position).get("pre")+"元");
        }
        textPrice.setText("现价："+(String)list.get(position).get("price")+"元");
        textOver.setText("已出售"+(String)list.get(position).get("over")+"件");
        v.setOnClickListener(new OnClickToGoodDetailListener(context,(String)list.get(position).get("id")));
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