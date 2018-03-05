package cn.stu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.stu.neu.util.DBUtil;

public class OrderDetailService {
	private DBUtil db ;
	public OrderDetailService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}

	/**
	 * 获取该商品的所有东西
	 * @param goodId 商品ID
	 * @return 商品Map
	 */
	public Map<String,String> getGoodById(String goodId){
		Map<String,String> map = null;
		map = db.getMap("select * from t_goods where good_id="+goodId);
		return map;
	}
	
	/**
	 * 获取单个商品详细的名字
	 * @param goodId 商品ID
	 * @param detailId 单个商品详细ID
	 * @return 单个商品详细的名字
	 */
	public String getDetailName(String goodId,String detailId){
		String name = null;
		name = db.getMap("select detail_name from t_detail where good_id="+goodId+" and detail_id="+detailId).get("detail_name");
		return name;
	}
	
	/**
	 * 提交一个订单
	 * @param map 订单信息
	 * @return  //订单ID。-1为失败
	 */
	public int addOrder(Map<String,String> map){
		String sql = "";
		sql+="insert into t_order (user_id,order_status,good_num,good_id,detail_id,addr,add_name,add_tel,time_submit) values('"
				+map.get("user_id")+"','"
				+"submit','"
				+map.get("numbers")+"','"
				+map.get("goods")+"','"
				+map.get("deatils")+"','"
				+map.get("add")+"','"
				+map.get("addName")+"','"
				+map.get("addTel")+"','"
				+map.get("time")+"')";
		int res = db.update(sql);
		if(res==1){
			res = Integer.parseInt(db.getMap("select order_id from t_order where time_submit="+map.get("time")).get("order_id"));
		}else{
			res = -1;
		}
		return res;
	}
	
	/**
	 * 获取订单详细
	 * @param id 订单ID
	 * @return 订单详细
	 */
	public Map<String,String> getOrder(String id){
		Map<String,String> map = null;
		map = db.getMap("select * from t_order where order_id="+id);
		return map;
	}
	
	/**
	 * 获取物品简单详细
	 * @param goodId 物品ID
	 * @param detailId 详细ID
	 * @return map。id,name,price。
	 */
	public Map<String,String> getGoodSim(String goodId,String detailId){
		Map<String,String> map = new HashMap<>();
		String name = "";
		Map<String,String> mapTemp = db.getMap("select good_name,good_price from t_goods where good_id="+goodId);
		name = mapTemp.get("good_name");
		if(!detailId.equals("-1")){
			String sql = "select detail_name from t_detail where good_id="+goodId+" and detail_id="+detailId;
			name += " ##【" + db.getMap(sql).get("detail_name")+"】##";
//			System.out.println("sql:"+sql);
		}
		map.put("id", goodId);
		map.put("name", name);
		map.put("price", mapTemp.get("good_price"));
		return map;
	}
	
	/**
	 * 根据订单ID更新状态
	 * @param orderId 订单ID
	 * @param status  要变成的状态
	 */
	public boolean orderChangeStatus(String orderId,String status){
		boolean res = false;
		Long time = new Date().getTime();
		String sql = "update t_order set order_status='"+status+"',time_"+status+"='"+time+"' where order_id="+orderId;
		String nowStatus = db.getMap("select order_status from t_order where order_id="+orderId).get("order_status");
		int nowNum = getStatusNum(nowStatus);
		int newNum = getStatusNum(status);
		if(newNum <= nowNum){
			return false;
		}else{
			System.out.println("sql:"+sql);
			int n = db.update(sql);
			if(n>0){
				res = true;
			}
			return res;
		}
	}
	
	/**
	 * 获取订单状态数字
	 * @param status 订单状态
	 * @return 状态数字
	 */
	private int getStatusNum(String status){
		int num = 255;
		if(status.equals("submit")){
			num = 1;
		}else if(status.equals("pay")){
			num = 2;
		}else if(status.equals("cancel")){
			num = 3;
		}else if(status.equals("ship")){
			num = 4;
		}else if(status.equals("return")){
			num = 5;
		}else if(status.equals("finish")){
			num = 6;
		}
		return num;
	}
	
	/**
	 * 获取用户名
	 * @param id 用户ID
	 * @return 用户名 String
	 */
	public String getUserName(String id){
		String name = null;
		name = db.getMap("select user_name from t_user where user_id="+id).get("user_name");
		return name;
	}
}
