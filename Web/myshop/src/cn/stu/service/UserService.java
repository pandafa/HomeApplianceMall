package cn.stu.service;
import java.util.Map;
import cn.stu.model.User;
import cn.stu.neu.util.DBUtil;

public class UserService {
	private DBUtil db ;
	public UserService(){
		db = new DBUtil();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		db.close();
	}

	@SuppressWarnings("finally")
	public boolean registUser(String name,String pw){
		Map<String,String> map = null;
		try{
			map = db.getMap("select user_id from t_user where user_name='"+name+"'");
		}catch(Exception e){
		}finally {
			if(map==null){
				int n = db.update("insert into t_user (user_name,user_pw,user_kind) values('"+name+"','"+pw+"','"+User.USER+"')");
				if(n==0){
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}
	}
	
	/**
	 * 验证用户信息
	 * @param userName 用户名
	 * @param passWord 用户秘密
	 * @return  Map user_id,user_name,user_kind
	 */
	public Map<String,String> loginUser(String userName,String passWord){
		Map<String,String> map = db.getMap("select * from t_user where user_name='"+userName+"'");
		if(map.get("user_pw").equals(passWord)){
			map.remove("user_pw");
			Map<String,String> map2 = db.getMap("select COUNT(user_id) from t_shoppingcar where user_id="+map.get("user_id"));
			map.put("shoppingcarNum", map2.get("COUNT(user_id)"));
			System.out.println("+"+map.get("user_kind"));
			if(map.get("user_kind").equals(Integer.toString(User.MANAGER))){
				Map<String,String> map3= db.getMap("select COUNT(*) from t_order");
				map.put("orderNum", map3.get("COUNT(*)"));
			}else{
				Map<String,String> map3= db.getMap("select COUNT(user_id) from t_order where user_id="+map.get("user_id"));
				map.put("orderNum", map3.get("COUNT(user_id)"));
			}
			
		}else{
			map = null;
		}
		return map;
	}

	/**
	 * 修改密码
	 * @param userName
	 * @param pw
	 * @return
	 */
	public boolean changePassword(String userName, String pw) {
		if(db.update("update t_user set user_pw='"+pw+"' where user_name='"+userName+"'")>0){
			return true;
		}else{
			return false;
		}
	}
	
}
