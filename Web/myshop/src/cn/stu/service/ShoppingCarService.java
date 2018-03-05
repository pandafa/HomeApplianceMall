package cn.stu.service;
import java.util.List;
import java.util.Map;
import cn.stu.neu.util.DBUtil;

public class ShoppingCarService {
	private DBUtil db ;
	public ShoppingCarService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}

	/**
	 * 获取用户购物车商品
	 * @param userId 用户ID
	 * @return 商品List。Map:good_id,name,price,detail_id。
	 */
	public List<Map<String,String>> getCarGoods(String userId){
		List<Map<String,String>> list = null;
		list = db.getList("select good_id,detail_id from t_shoppingcar where user_id="+userId);
		if(list!=null && list.size()!=0){
			for(int i=0;i<list.size();i++){
				Map<String,String> map = db.getMap("select good_name,good_price from t_goods where good_id="+list.get(i).get("good_id"));
				String name = map.get("good_name");
				if(list.get(i).get("detail_id")!=null){
					name += "##【"+db.getMap("select detail_name from t_detail where detail_id="
							+list.get(i).get("detail_id")+
							" and good_id="+list.get(i).get("good_id")).get("detail_name")+ "】##";
				}
				list.get(i).put("name", name);
				list.get(i).put("price", map.get("good_price"));
			}
		}
		return list;
	}
	
	/**
	 * 从购物车中删除
	 * @param userId 用户ID
	 * @param goodId 商品ID
	 * @return SQL运行结果
	 */
	public int delCar(String userId,String goodId){
		String sql = "delete from t_shoppingcar where user_id="+userId+" and good_id="+goodId;
		return db.update(sql);
	}
	
	/**
	 * 添加到购物车
	 * @param userId 用户ID
	 * @param goodId 商品ID
	 * @param detailId 商品详细ID
	 * @return SQL运行结果
	 */
	public int addCar(String userId,String goodId,String detailId){
		String sql = null;
		if(detailId!=null && !detailId.equals("-1") && detailId.length()!=0){
			sql = "insert into t_shoppingcar (user_id,good_id,detail_id) values('"+userId+"','"+goodId+"','"+detailId+"')";
		}else{
			sql = "insert into t_shoppingcar (user_id,good_id) values('"+userId+"','"+goodId+"')";
		} 
		return db.update(sql);
	}
}
