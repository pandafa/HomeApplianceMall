package cn.stu.service;

import java.util.List;
import java.util.Map;
import cn.stu.neu.util.DBUtil;

public class HomeService {
	
	private DBUtil db ;
	public HomeService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}
	
	/**
	 * 获取今日推荐商品
	 * @return 今日推荐商品列表。ID good_id，名称 good_name，价格 good_price，图片 good_mid。
	 */
	public List<Map<String,String>> getTodayGoods(){
		List<Map<String,String>> list = null;
		list = db.getList("select good_id,good_name,good_price,good_mid from t_goods where istoday=1");
		return list;
	}
	
	/**
	 * 获取所有商品
	 * @return 商品列表。ID good_id，名称 good_name，价格 good_price，图片 good_mid，类别 good_kind。
	 */
	public List<Map<String,String>> getAllGoods(){
		List<Map<String,String>> list = null;
		list = db.getList("select good_id,good_name,good_price,good_mid,good_kind from t_goods");
		return list;
	}
	
	/**
	 * 获取所有类别
	 * @return 类别。ID kind_id，名称 kind_name。
	 */
	public List<Map<String,String>> getAllKinds(){
		List<Map<String,String>> list = null;
		list = db.getList("select * from t_kind");
		return list;
	}
	
	
}