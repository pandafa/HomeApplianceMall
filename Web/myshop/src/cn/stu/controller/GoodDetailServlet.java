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
import cn.stu.neu.util.ChangeToJSON;
import cn.stu.neu.util.Judge;
import cn.stu.service.GoodDetailService;

/**
 * 查看物品详细
 */
@WebServlet({"/goods/goods_detail.action","/goods/goods_detailForMobile.action"})
public class GoodDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;

    public GoodDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();	
		boolean isMobile = Judge.isForMobile(url);
		if(url.equals("/goods/goods_detail.action") || url.equals("/goods/goods_detailForMobile.action")){
			String id = request.getParameter("id");
			GoodDetailService service = new GoodDetailService();
			Map<String,String> good = service.getGoodById(id);
			List<Map<String,String>> detail = service.getGoodDetail(id);
			service.close();
			if(isMobile){
				List<Map<String,String>> listForM = new ArrayList<>();
				listForM.add(good);
				if(detail!=null && detail.size()>0){
					listForM.addAll(detail);
				}
				ChangeToJSON.parseList("ok", "ok", listForM, request, response);
				return;
			}
			request.setAttribute("good", good);
			request.setAttribute("detail", detail);
			request.setAttribute("headerNum", 1);
			request.getRequestDispatcher("/goods/goods_detail.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
