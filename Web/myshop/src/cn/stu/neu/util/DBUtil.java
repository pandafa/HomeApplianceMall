package cn.stu.neu.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
	private String driver;
	private String url;
	private String username;
	private String password;
	private Connection con;
	private PreparedStatement pstmt;
	public static final long PAGE_REC_NUM = 4;	 
	 
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//构造方法，定义驱动程序连接用户名和密码信息
    public DBUtil(){
	  driver="com.mysql.jdbc.Driver";
	  url="jdbc:mysql://localhost:3306/myshop?useUnicode=true&characterEncoding=UTF-8";
	  username="root";
	  password="cxx123";
    }
    
	//加载驱动程序，得到连接对象
	public void init(){
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//关闭操作
	public void close(){
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    //给sql语句设置参数
    private void setParams(String sql,String[] params){
    	if(params!=null){
    		for(int i=0;i<params.length;i++){
    			try {
					pstmt.setString(i+1, params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
    			
    		}
    		
    	}
    }
    
    //执行更新
    public int update(String sql,String[] params){
    	int result=0;
    	init();
    	try {
			pstmt=con.prepareStatement(sql);
			setParams(sql,params);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close();
		}
    	return result;
    }
    
    public int update(String sql){
    	return update(sql, null);
    }
    
   //查询获取List对象
 	public List<Map<String,String>> getList(String sql, String[] params) {
 		List<Map<String,String>> list = null;// 定义保存查询结果的集合对象
 		init();
 		try {
 			pstmt=con.prepareStatement(sql);
 			setParams(sql, params);
 			ResultSet rs = pstmt.executeQuery();
 			list = getListFromRS(rs);// 根据RS得到list
 			rs.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		} finally {
 			close();
 		}
 		return list;
 	}
 	
 	public List<Map<String,String>> getList(String sql){
 		return getList(sql, null);
 	}
 	
 	//查询获取Map对象
 	public Map<String,String> getMap(String sql, String[] params) {
 		Map<String,String> m = null;
 		List<Map<String,String>> l = getList(sql, params);
 		if (l != null){
 			m = (Map<String,String>) (l.get(0));
 		}
 		return m;
 	}
   
 	public Map<String,String> getMap(String sql){
 		return getMap(sql, null);
 	}
 	
 	//将结果集封装成一个List
	private List<Map<String,String>> getListFromRS(ResultSet rs) throws SQLException {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		// 获取元数据
		ResultSetMetaData rsmd = rs.getMetaData();
		while (rs.next()) {
			Map<String,String> m = new HashMap<String,String>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				// 获取当前行第i列的列名
				String colName = rsmd.getColumnName(i);
				String s = rs.getString(colName);
				if (s != null) {
						m.put(colName, s);
				}
			}
			list.add(m);
		}
		return list;
	}
 	
	//将Map对象转换为Object数组
	@SuppressWarnings("rawtypes")
	public Object[] getObjectArrayFromMap(Map map, String key) {
		String[] keys = key.split(",");
		Object[] tmp = new Object[keys.length];
		for (int i = 0; i < keys.length; i++) {
			tmp[i] = map.get(keys[i].trim());
		}
		return tmp;
	}
	
	// 分页显示，获取页号为curPage的本页所有记录
	// 返回的Map对象中存放两个元素：
	// key="list"的元素是存放了本页所有记录的List对象，
	// key="totalPage"的元素是代表总页数的Integer对象
	// key="recNum"的元素是代表每页记录数
	public Map<String,Object> getPage(String sql, String[] params, String curPage) {
		Map<String,Object> page = new HashMap<String,Object>();
		String newSql = sql + " limit " + (Long.parseLong(curPage) - 1)
				* PAGE_REC_NUM + "," + PAGE_REC_NUM;
		List<Map<String,String>> pageList = getList(newSql, params);// 根据getList方法得到list
		// 计算总页数
		sql = sql.toLowerCase();
		String countSql = "";
		if(sql.indexOf("group")>=0){
			countSql = "select count(*) from ("+sql+") as tempNum";
		}else{
			countSql = "select count(*) as tempNum "+ sql.substring(sql.indexOf("from"));
		}	
		// count中存放总记录数
		String count_s = (String)getMap(countSql,params).get("tempNum");
		long count = Long.parseLong(count_s); // 根据getLong方法得到记录数
		// 利用总记录数（count）和每页记录个数（PAGE_REC_NUM）计算总页数
		long totalPage = 0;
		if (count % PAGE_REC_NUM == 0)
			totalPage = count / PAGE_REC_NUM;
		else
			totalPage = count / PAGE_REC_NUM + 1;
		// 返回的Map对象page中，存放当前分页所有记录的List对象pageList
		page.put("list", pageList);
		page.put("totalPage", new Long(totalPage));
		page.put("recNum", new Long(PAGE_REC_NUM));
		return page;
	}

	public Map<String,Object> getPage(String sql, String curPage) {
		return getPage(sql, null, curPage);
	}
}