package cn.stu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.stu.neu.util.ChangeToJSON;
import cn.stu.neu.util.Judge;
import cn.stu.service.GoodListService;

/**
 * 商品分类、新到、热销、搜索商品
 */
@WebServlet({ "/goods/goods_list.action", "/goods/new_goods.action","/goods/sale_goods.action","/search.action",
	"/goods/goods_listForMobile.action", "/goods/new_goodsForMobile.action","/goods/sale_goodsForMobile.action","/searchForMobile.action"})
public class GoodsListServlet extends HttpServlet {
	private static final long serialVersionUID = 3L;
    public GoodsListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();
		boolean isMobile = Judge.isForMobile(url);
		GoodListService service = new GoodListService();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if(url.equals("/goods/goods_list.action") || url.equals("/goods/goods_listForMobile.action")){
			//商品分类
			String kind = request.getParameter("kind");
			if(kind==null){
				kind = "1";
			}
			List<Map<String,String>> goodsList = service.getGoodsByKind(kind);
			if(isMobile){
				service.close();
				List<Map<String,String>> list = new ArrayList<>();
				Map<String,String> map = new HashMap<>();
				map.put("kind", kind);
				list.add(map);
				if(goodsList!=null && goodsList.size()>0){
					list.addAll(goodsList);
				}
				ChangeToJSON.parseList("ok", "ok", list, request, response);
				return;
			}
			request.setAttribute("goodsList", goodsList);
			request.setAttribute("kind", kind);
			request.setAttribute("headerNum", 4);
			request.getRequestDispatcher("/goods/goods_list.jsp").forward(request, response);
		}else if(url.equals("/goods/new_goods.action") || url.equals("/goods/new_goodsForMobile.action")){
			//新到商品
			List<Map<String,String>> newGoods = service.getNewGoods();
			if(isMobile){
				service.close();
				ChangeToJSON.parseList("ok", "ok", newGoods, request, response);
				return;
			}
			request.setAttribute("newGoods", newGoods);
			request.setAttribute("headerNum", 3);
			request.getRequestDispatcher("/goods/new_goods.jsp").forward(request, response);
		}else if(url.equals("/goods/sale_goods.action") || url.equals("/goods/sale_goodsForMobile.action")){
			//热销商品
			List<Map<String,String>> hotGoods = service.getHotGoods();
			if(isMobile){
				service.close();
				ChangeToJSON.parseList("ok", "ok", hotGoods, request, response);
				return;
			}
			request.setAttribute("hotGoods", hotGoods);
			request.setAttribute("headerNum", 2);
			request.getRequestDispatcher("/goods/sale_goods.jsp").forward(request, response);
		}else if(url.equals("/search.action") || url.equals("/searchForMobile.action")){
			//搜索商品
			String str = request.getParameter("search");
			List<Map<String,String>> searchGoods = service.searchGoods(str);
			if(isMobile){
				service.close();
				ChangeToJSON.parseList("ok", "ok", searchGoods, request, response);
				return;
			}
			request.setAttribute("searchGoods", searchGoods);
			request.setAttribute("headerNum", 1);
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		}
		service.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
