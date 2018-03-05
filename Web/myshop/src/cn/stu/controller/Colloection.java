package cn.stu.controller;
import java.io.IOException;
import java.util.ArrayList;
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
import cn.stu.service.CollectionService;

/**
 * 查看、添加、移除收藏夹
 */
@WebServlet({"/collection.action","/collectionForMobile.action"})
public class Colloection extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Colloection() {
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
		String loginUserId = (String) session.getAttribute("loginUserId");
		if(loginUserId==null || loginUserId.length()==0){
			String urlStr = "index.action";
			String doStr = request.getParameter("do");
			String goodId = request.getParameter("id");
			if(doStr!=null && doStr.equals("add")){
				urlStr = request.getContextPath()+"/goods/goods_detail.action?id="+goodId;
			}
			request.setAttribute("msg", "请先登录");
			request.setAttribute("url", urlStr);
			if(isMobile){
				ChangeToJSON.parseMap("no", "noLogin", null, request, response);
				return;
			}
			request.getRequestDispatcher("/prompt.jsp").forward(request, response);
			return;
		}
		
		CollectionService service = new CollectionService();
		if(url.equals("/collection.action") || url.equals("/collectionForMobile.action")){
			String doStr = request.getParameter("do");
			request.setAttribute("headerNum", 1);
			if(doStr!=null && doStr.equals("add")){
				//添加
				String goodId = request.getParameter("id");
				int n = service.addGood(goodId, loginUserId);
				if(isMobile){
					String s = null;
					String m = null;
					if(n>0){
						s = "ok";
						m = "addOk";
					}else{
						s = "no";
						m = "addFalse";
					}
					ChangeToJSON.parseMap(s, m, null, request, response);
					return;
				}
				response.sendRedirect(request.getContextPath()+"/collection.action");
			}else if(doStr!=null && doStr.equals("del")){
				//移除
				String goodId = request.getParameter("id");
				int n = service.delGood(goodId, loginUserId);
				if(isMobile){
					String s = null;
					String m = null;
					if(n>0){
						s = "ok";
						m = "delOk";
					}else{
						s = "no";
						m = "delFalse";
					}
					ChangeToJSON.parseMap(s, m, null, request, response);
					return;
				}
				response.sendRedirect(request.getContextPath()+"/collection.action");
			}else{
				//查看
				List<Map<String,String>> collectGoodsList = service.getAllCollections(loginUserId);
				request.setAttribute("collectGoodsList", collectGoodsList);
				if(isMobile){
					List<Map<String,String>> listForM = new ArrayList<>();
					if(collectGoodsList!=null){
						listForM.addAll(collectGoodsList);
						ChangeToJSON.parseList("ok", "ok", listForM, request, response);
					}else{
						ChangeToJSON.parseList("ok", "null", listForM, request, response);
					}
					
					return;
				}
				request.getRequestDispatcher("/collection.jsp").forward(request, response);
			}
			
		}
		service.close();
	}

}
