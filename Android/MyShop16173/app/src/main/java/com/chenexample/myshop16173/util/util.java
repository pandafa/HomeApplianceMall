package com.chenexample.myshop16173.util;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class util {

	public static String getHttpJsonByhttpclient(String fromurl)
	{
		System.out.print("------getHttpJsonByhttpclient------3-------");
		try{
			Log.v("zms","使用httget");
			HttpGet geturl=new HttpGet(fromurl);
			DefaultHttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(geturl);
			Log.v("zms","响应码"+response.getStatusLine().getStatusCode());

			if (response.getStatusLine().getStatusCode()==200)
			{

				String returnStr= EntityUtils.toString(response.getEntity(),"utf-8");
				Log.v("zms","返回值"+returnStr);
				return returnStr;
			} else
			{
				Log.v("zms","访问网络返回数据失败，错误码:"+response.getStatusLine().getStatusCode());
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;

	}

	public static String getHttpJsonByurlconnection(String fromurl)
	{
		try
		{
			Log.v("zms","使用httpurlconnection");
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			byte[] data =new byte[1024];
			int len=0;
			URL url=new URL(fromurl);
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			InputStream in=conn.getInputStream();
			while ((len=in.read(data))!=-1)
			{
				os.write(data,0,len);
			}
			in.close();
			return new String(os.toByteArray());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

}