package cn.stu.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.stu.model.MySessionContext;
import cn.stu.neu.util.ChangeToJSON;
import cn.stu.neu.util.Judge;
import cn.stu.service.ShoppingCarService;

/**
 * 购物车的添加、删除、查看
 */
@WebServlet({"/shoppingcar.action","/shoppingcarForMobile.action"})
public class ShoppingCarServlet extends HttpServlet {
	private static final long serialVersionUID = 7L;
    public ShoppingCarServlet() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();
		boolean isMobile = Judge.isForMobile(url);
		HttpSession session = null;
		if(isMobile){
			session = MySessionContext.getSession(request.getParameter("sessionid"));
			if(session==null){
				ChangeToJSON.parseMap("no", "relog", null, request, response);
				return;
			}
		}else{
			session = request.getSession();
		}
		String userId = (String) session.getAttribute("loginUserId");
		if(userId==null || userId.length()==0){
			if(isMobile){
				ChangeToJSON.parseMap("no", "noLogin", null, request, response);
				return;
			}
			String urlStr = "index.action";
			String doStr = request.getParameter("do");
			String goodId = request.getParameter("goodId");
			if(doStr!=null && doStr.equals("add")){
				urlStr = request.getContextPath()+"/goods/goods_detail.action?id="+goodId;
			}
			request.setAttribute("msg", "请先登录");
			request.setAttribute("url", urlStr);
			request.getRequestDispatcher("/prompt.jsp").forward(request, response);
			return;
		}
		ShoppingCarService service = new ShoppingCarService();
		if(url.equals("/shoppingcar.action") || url.equals("/shoppingcarForMobile.action")){
			String doStr = request.getParameter("do");
			if(doStr==null || doStr.length()==0){
				List<Map<String,String>> goodsList = service.getCarGoods(userId);
				if(isMobile){
					if(goodsList!=null && goodsList.size()==0){
						goodsList = null;
					}
					ChangeToJSON.parseList("ok", "look", goodsList, request, response);
					return;
				}
				request.setAttribute("goodsList", goodsList);
				request.getRequestDispatcher("/shoppingcar.jsp?").forward(request, response);
			}else if(doStr.equals("del")){
				//从购物车中删除
				String goodId = request.getParameter("goodId");
				int n = service.delCar(userId, goodId);
				int num = -1;
				String s = null;
				String m = null;
				if(n>0){
					num = Integer.parseInt((String) session.getAttribute("shoppingcarNum"));
					num--;
					session.setAttribute("shoppingcarNum", Integer.toString(num));
					s="ok";
					m="del";
				}else{
					s="no";
					m="noDel";
				}
				if(isMobile){
					Map<String,String> map = new HashMap<String, String>();
					map.put("shoppingcarNum", Integer.toString(num));
					ChangeToJSON.parseMap(s, m, map, request, response);
					return;
				}
				response.sendRedirect(request.getContextPath()+"/shoppingcar.action");
			}else if(doStr.equals("add")){
				//添加到购物车
				String goodId = request.getParameter("goodId");
				String detailId = request.getParameter("detailId");
				int n = service.addCar(userId, goodId, detailId);
				int num = -1;
				String s = null;
				String m = null;
				if(n>0){
					num = Integer.parseInt((String) session.getAttribute("shoppingcarNum"));
					num++;
					session.setAttribute("shoppingcarNum", Integer.toString(num));
					s="ok";
					m="add";
				}else{
					s="no";
					m="noAdd";
				}
				if(isMobile){
					Map<String,String> map = new HashMap<String, String>();
					map.put("shoppingcarNum", Integer.toString(num));
					ChangeToJSON.parseMap(s, m, map, request, response);
					return;
				}
				response.sendRedirect(request.getContextPath()+"/shoppingcar.action");
			}
		}
		service.close();
	}
}
