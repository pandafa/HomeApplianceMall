package cn.stu.neu.util;

public class Judge {
	/**
	 * 判断是否是移动端的
	 * @param url request.getServletPath()得到的字符串
	 * @return boolean形式
	 */
	public static boolean isForMobile(String url){
		boolean res = false;
		if(url.endsWith("ForMobile.action")){
			res = true;
		}
		return res;
	}
}
