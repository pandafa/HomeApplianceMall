package cn.stu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.stu.neu.util.DBUtil;

public class OrderService {
	private DBUtil db ;
	public OrderService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}
	
	/**
	 * 获取全部订单信息
	 * @return 订单信息
	 */
	public List<Map<String,String>> getAllOrders(){
		List<Map<String,String>> res = new ArrayList<>();
		String[] userIds = null;
		String sqlForUser = "select distinct user_id from t_order";
		//获取所有有订单的用户ID
		List<Map<String,String>> list = db.getList(sqlForUser);
		if(list!=null){
			userIds = new String[list.size()];
			for(int i=0;i<list.size();i++){
				userIds[i]=list.get(i).get("user_id");
			}
		}
		//获取每个用户的订单信息
		if(userIds!=null){
			//获取一个用户的订单信息
			for(int i=0;i<userIds.length;i++){
				List<Map<String,String>> list2 = getAllOrdersById(userIds[i]);
				String userName = db.getMap("select user_name from t_user where user_id="+userIds[i]).get("user_name");
				if(list2!=null && list2.size()!=0){
					for(int j=0;j<list2.size();j++){
						list2.get(j).put("user_id", userIds[i]);
						list2.get(j).put("user_name", userName);
					}
				}
				res.addAll(list2);
			}
		}
		return res;
	}
	
	/**
	 * 获取订单列表
	 * @param userId 用户ID
	 * @return List中的map有：name,status,allNum,allPrice
	 */
	public List<Map<String,String>> getAllOrdersById(String userId){
		List<Map<String,String>> list = null;
		list = db.getList("select order_id,good_id,order_status,good_num,detail_id from t_order where user_id="+userId);
		for(int i=0;i<list.size();i++){
			String[] ids = list.get(i).get("good_id").split(";");
			String[] nums = list.get(i).get("good_num").split(";");
			String[] details = list.get(i).get("detail_id").split(";");
			Map<String,String> goodMap = db.getMap("select good_name from t_goods where good_id="+ids[0]);
			//获取第一个名字
			String goodName = goodMap.get("good_name");
			//获取第一个详细名字
			String detailName = "";
			if(!details[0].equals("-1")){
				detailName = db.getMap("select detail_name from t_detail where good_id="+ids[0]+" and detail_id="+details[0]).get("detail_name");
				goodName += " ##【 " +detailName+"】##";
			}
			
			if(ids.length>1){
				goodName += " *等*";
			}
			float allPrice = 0;
			float allNum = 0;
			for(int j=0;j<nums.length;j++){
				float n = Float.parseFloat(nums[j]);
				String priceStr = db.getMap("select good_price from t_goods where good_id="+ids[j]).get("good_price");
				float price = Float.parseFloat(priceStr);
				allNum   += n;
				allPrice += n*price;
			}
			list.get(i).put("orderId", list.get(i).get("order_id"));
			list.get(i).put("name", goodName);
			list.get(i).put("allPrice", Float.toString(allPrice));
			list.get(i).put("allNum", Float.toString(allNum));
			list.get(i).put("id", ids[0]);
			list.get(i).put("status", list.get(i).get("order_status"));
			list.get(i).remove("order_id");
			list.get(i).remove("detail_id");
			list.get(i).remove("good_num");
			list.get(i).remove("order_status");
			list.get(i).remove("good_id");
		}
		
		
		return list;
	}
}
