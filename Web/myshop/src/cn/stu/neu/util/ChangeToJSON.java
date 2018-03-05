package cn.stu.neu.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ChangeToJSON {
	/**
	 *  List转换成JSON给移动端
	 * @param status
	 * @param msg
	 * @param list
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void parseList(String status,String msg,List list,
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper(); 
		
		ArrayList<Map> newList = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		map.put("status", status);
		map.put("msg", msg);
		newList.add(map);
		if(list!=null){
			newList.addAll(list);
		}
		
        String jsonlist = mapper.writeValueAsString(newList);  
        System.out.println(jsonlist);  
		out.print(jsonlist);
		out.flush();
	}
	
	/**
	 * Map转换成JSON给移动端
	 * @param status
	 * @param msg
	 * @param map
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void parseMap(String status,String msg,Map map,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ArrayList<Map> list = new ArrayList<>();
		if(map!=null){
			list.add(map);
		}
		parseList(status,msg,list,request, response);
	}
}
