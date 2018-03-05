package cn.stu.controller;
import java.io.IOException;
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
import cn.stu.service.OrderService;

/**
 * 获取所有订单列表
 */
@WebServlet({"/order.action","/orderForMobile.action"})
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 6L;
    public OrderServlet() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();	
		String userKind;
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
		String loginUserId = (String) session.getAttribute("loginUserId");
		if(loginUserId==null || loginUserId.length()==0){
			if(isMobile){
				ChangeToJSON.parseMap("no", "noLogin", null, request, response);
				return;
			}
			String urlStr = "index.action";
			String doStr = request.getParameter("do");
			String goodId = request.getParameter("id");
			if(doStr!=null && doStr.equals("add")){
				urlStr = request.getContextPath()+"/goods/goods_detail.action?id="+goodId;
			}
			request.setAttribute("msg", "请先登录");
			request.setAttribute("url", urlStr);
			request.getRequestDispatcher("/prompt.jsp").forward(request, response);
			return;
		}else{
			userKind = (String) session.getAttribute("loginUserKind");
		}
		request.setAttribute("headerNum", 1);
		OrderService service = new OrderService();
		if(url.equals("/order.action") || url.equals("/orderForMobile.action")){
			if(userKind.equals("m")){
				//管理员
				List<Map<String,String>> orderList = service.getAllOrders();
				if(isMobile){
					ChangeToJSON.parseList("ok", "m", orderList, request, response);
					return;
				}
				request.setAttribute("orderList", orderList);
				request.setAttribute("userKind", "m");
			}else if(userKind.equals("u")){
				//普通用户
				List<Map<String,String>> orderList = service.getAllOrdersById(loginUserId);
				if(isMobile){
					ChangeToJSON.parseList("ok", "u", orderList, request, response);
					return;
				}
				request.setAttribute("orderList", orderList);
				request.setAttribute("userKind", "u");
			}
			request.getRequestDispatcher("/order.jsp").forward(request, response);
		}
		service.close();
	}
}
