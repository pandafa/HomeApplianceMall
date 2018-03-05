package cn.stu.service;

import java.util.List;
import java.util.Map;

import cn.stu.neu.util.DBUtil;

public class GoodListService {
	private DBUtil db ;
	public GoodListService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}
	
	/**
	 * 获取某一个种类的商品
	 * @param kind 种类ID
	 * @return 商品列表。ID good_id，名称 good_name，价格 good_price，已售 good_over，原价 good_pre。
	 */
	public List<Map<String,String>> getGoodsByKind(String kind){
		List<Map<String,String>> list = null;
		list = db.getList("select good_over,good_pre,good_id,good_name,good_price from t_goods where good_kind="+kind);
		return list;
	}
	
	/**
	 * 获取热销商品
	 * @return 商品列表。ID good_id，名称 good_name，价格 good_price，已售 good_over，原价 good_pre。
	 */
	public List<Map<String,String>> getHotGoods(){
		List<Map<String,String>> list = null;
		list = db.getList("select good_over,good_pre,good_id,good_name,good_price from t_goods order by good_over desc limit 24");
		return list;
	}
	
	/**
	 * 获取新到商品
	 * @return 商品列表。ID good_id，名称 good_name，价格 good_price，已售 good_over，原价 good_pre。
	 */
	public List<Map<String,String>> getNewGoods(){
		List<Map<String,String>> list = null;
		list = db.getList("select good_over,good_id,good_name,good_price from t_goods order by good_over asc limit 24");
		return list;
	}
	
	/**
	 * 搜索商品
	 * @param str 搜索的内容
	 * @return 商品列表。ID good_id，名称 good_name，价格 good_price，已售 good_over，原价 good_pre。
	 */
	public List<Map<String,String>> searchGoods(String str){
		List<Map<String,String>> list = null;
		str = "%"+str+"%";
		String sql = "select good_over,good_id,good_name,good_price from t_goods where good_name like \""+str+"\"";
		System.out.println("sql:"+sql);
		list = db.getList(sql);
		System.out.println("list:"+list);
		return list;
	}
}
