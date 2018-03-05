package com.chenexample.myshop16173.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chenexample.myshop16173.GoodDetailActivity;

public class OnClickToGoodDetailListener implements View.OnClickListener {

    private Context context;
    private String goodId;

    public OnClickToGoodDetailListener(Context context,String goodId){
        super();
        this.context = context;
        this.goodId = goodId;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("goodId",goodId);
        context.startActivity(intent);
    }
}
