package cn.stu.service;

import java.util.List;
import java.util.Map;

import cn.stu.neu.util.DBUtil;

public class GoodDetailService {
	private DBUtil db ;
	public GoodDetailService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}
	
	/**
	 * 获取商品详情
	 * @param id 商品ID
	 * @return 商品详情
	 */
	public Map<String,String> getGoodById(String id){
		Map<String,String> map = null;
		map = db.getMap("select * from t_goods where good_id ="+id);
		return map;
	}
	
	/**
	 * 获取单个商品详细分类
	 * @param id 商品ID
	 * @return 单个商品详细分类
	 */
	public List<Map<String,String>> getGoodDetail(String id){
		List<Map<String,String>> list = null;
		list = db.getList("select * from t_detail where good_id="+id);
		return list;
	}
}
