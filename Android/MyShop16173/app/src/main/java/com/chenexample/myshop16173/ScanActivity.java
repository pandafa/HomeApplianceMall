package com.chenexample.myshop16173;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends Activity {

    private final String KEY_1 = "/myshop/goods/goods_detailForMobile.action?id=";
    private final String KEY_2 = "skkwkkkwdwdjjaswd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }



    @Override
    protected void onResume() {
        super.onResume();
        // 创建IntentIntegrator对象
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
        intentIntegrator.setPrompt("开始扫描");
        intentIntegrator.setTimeout(5*1000);
        intentIntegrator.setBeepEnabled(false);
        // 开始扫描
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                finish();
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                String goodId = null;
                String[] res = result.getContents().split("#");
                if(res!=null && res.length==3){
                    if(res[0].equals(KEY_1) && res[2].equals(KEY_2)){
                        goodId = res[1];
                    }
                }
                if(goodId!=null){
                    Intent intent = new Intent(ScanActivity.this,GoodDetailActivity.class);
                    intent.putExtra("goodId",goodId);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "请扫描本网站二维码！", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
