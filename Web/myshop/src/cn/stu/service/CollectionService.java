package cn.stu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.stu.neu.util.DBUtil;

public class CollectionService {
	private DBUtil db ;
	public CollectionService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}

	/**
	 * 获取所有收藏的商品
	 * @param id 用户ID
	 * @return 商品List  id,name,price
	 */
	public List<Map<String,String>> getAllCollections(String id){
		List<Map<String,String>> list = null;
		List<Map<String,String>> res = null;
		list = db.getList("select good_id from t_collection where user_id="+id);
		if(list!=null && list.size()!=0){
			res = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Map<String,String> map = db.getMap("select good_id,good_name,good_price from t_goods where good_id="+list.get(i).get("good_id"));
				res.add(map);
			}
		}
		return res;
	}
	
	/**
	 * 移除收藏夹
	 * @param goodId 商品ID
	 * @param userId 用户ID
	 */
	public int delGood(String goodId,String userId){
		return db.update("delete from t_collection where user_id ="+userId+" and good_id="+goodId);
	}
	
	/**
	 * 添加到收藏夹
	 * @param goodId 商品ID
	 * @param userId 用户ID
	 */
	public int addGood(String goodId,String userId){
		return db.update("insert into t_collection (user_id,good_id)  values('"+userId+"','"+goodId+"')");
	}
}
