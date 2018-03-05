package cn.stu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.stu.neu.util.ChangeToJSON;
import cn.stu.neu.util.Judge;
import cn.stu.service.HomeService;

/**
 * 首页的今日商品、所有商品、商品的所有分类
 * @author DELL
 *
 */
@WebServlet({"/index.action","/indexForMobile.action"})
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;

    public HomeServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();
		boolean isMobile = Judge.isForMobile(url);
		if(url.equals("/index.action") || url.equals("/indexForMobile.action")){
			HomeService service = new HomeService();
				
			if(isMobile){
				String k = request.getParameter("kind");
				List<Map<String, String>> list = null;
				if(k.equals("today")){
					list = service.getTodayGoods();
				}else if(k.equals("goods")){
					list = service.getAllGoods();
				}else if(k.equals("kinds")){
					list = service.getAllKinds();
				}
				ChangeToJSON.parseList("ok", "ok", list, request, response);
				service.close();
				return;
			}
			
			List<Map<String, String>> todayGoodsList = service.getTodayGoods();
			request.setAttribute("todayGoodsList", todayGoodsList);
			List<Map<String, String>> allGoodsList = service.getAllGoods();
			request.setAttribute("allGoodsList", allGoodsList);
			List<Map<String, String>> allKindsList = service.getAllKinds();
			request.setAttribute("allKindsList", allKindsList);
			service.close();
			
			request.setAttribute("headerNum", 1);
			request.getRequestDispatcher("/home.jsp").forward(request, response);		    
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
