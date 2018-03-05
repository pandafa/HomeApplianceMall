package cn.stu.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import cn.stu.service.OrderDetailService;

/**
 * 订单详情
 * 
 */
@WebServlet({"/orderdetail.action","/orderdetailForMobile.action"})
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 5L;
    public OrderDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String url = request.getServletPath();	
		boolean isMobile = Judge.isForMobile(url);
		request.setAttribute("headerNum", 1);
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
		String userKind;
		if(loginUserId==null || loginUserId.length()==0){
			if(isMobile){
				ChangeToJSON.parseMap("no", "noLogin", null, request, response);
				return;
			}
			String urlStr = "index.action";
			String doStr = request.getParameter("submitaim");
			String goodId = request.getParameter("goodId_1");
			if(doStr!=null && doStr.equals("create")){
				urlStr = request.getContextPath()+"/goods/goods_detail.action?id="+goodId;
			}
			request.setAttribute("msg", "请先登录");
			request.setAttribute("url", urlStr);
			request.getRequestDispatcher("/prompt.jsp").forward(request, response);
			return;
		}else{
			userKind = (String) session.getAttribute("loginUserKind");
		}
		if(url.equals("/orderdetail.action") || url.equals("/orderdetailForMobile.action")){
			String aim = null;
			aim = (String) request.getAttribute("submitaim");
			if(aim==null || aim.length()==0){
				aim = request.getParameter("submitaim");
			}
			if(aim==null || aim.length()==0 || aim.equals("look")){
				//查看订单
				System.out.println("查看订单");
				String orderId = request.getParameter("orderid");
				if(orderId==null || orderId.length()==0){
					//缺少订单编号，到订单列表
					if(isMobile){
						ChangeToJSON.parseMap("no", "noOrderId", null, request, response);
						return;
					}
					response.sendRedirect(request.getContextPath()+"/order.action");
				}
				OrderDetailService service = new  OrderDetailService();
				if(userKind.equals("m")){
					//管理员
					Map<String,String> orderMap = service.getOrder(orderId);
					String goods1 = orderMap.get("good_id");
					String nums1 = orderMap.get("good_num");
					String details1 = orderMap.get("detail_id");
					String orderUserId = orderMap.get("user_id");
					String orderUserName = service.getUserName(orderUserId);
					orderMap.remove("good_id");
					orderMap.remove("good_num");
					orderMap.remove("detail_id");
					String[] goods   = goods1.split(";");
					String[] numbers = nums1.split(";");
					String[] details = details1.split(";");
					
					List<Map<String,String>> goodList = new ArrayList<>();
					float allPrice = 0;
					int allNum = 0;
					for(int i=0;i<goods.length;i++){
						Map<String,String> map = service.getGoodSim(goods[i], details[i]);
						map.put("number", numbers[i]);
						goodList.add(map);
						allNum += Integer.parseInt(numbers[i]);
						allPrice += Float.parseFloat(map.get("price"));
					}
					if(isMobile){
						List<Map<String,String>> listForM = new ArrayList<>();
						orderMap.put("allPrice", Float.toString(allPrice));
						orderMap.put("allNum", Integer.toString(allNum));
						orderMap.put("orderUserName", orderUserName);
						listForM.add(orderMap);
						listForM.addAll(goodList);
						ChangeToJSON.parseList("ok", "m", listForM, request, response);
						return;
					}
					request.setAttribute("orderMap", orderMap);
					request.setAttribute("goodList", goodList);
					request.setAttribute("allPrice", allPrice);
					request.setAttribute("allNum", allNum);
					request.setAttribute("orderUserName", orderUserName);
					request.setAttribute("type", "look");//类型
					request.setAttribute("userKind", "m");
				}else if(userKind.equals("u")){
					//普通用户
					Map<String,String> orderMap = service.getOrder(orderId);
					
					String goods1 = orderMap.get("good_id");
					String nums1 = orderMap.get("good_num");
					String details1 = orderMap.get("detail_id");
					orderMap.remove("good_id");
					orderMap.remove("good_num");
					orderMap.remove("detail_id");
					String[] goods   = goods1.split(";");
					String[] numbers = nums1.split(";");
					String[] details = details1.split(";");
					
					List<Map<String,String>> goodList = new ArrayList<>();
					float allPrice = 0;
					int allNum = 0;
					for(int i=0;i<goods.length;i++){
						Map<String,String> map = service.getGoodSim(goods[i], details[i]);
						map.put("number", numbers[i]);
						goodList.add(map);
						allNum += Integer.parseInt(numbers[i]);
						allPrice += Float.parseFloat(map.get("price"));
					}
					if(isMobile){
						List<Map<String,String>> listForM = new ArrayList<>();
						orderMap.put("allPrice", Float.toString(allPrice));
						orderMap.put("allNum", Integer.toString(allNum));
						listForM.add(orderMap);
						listForM.addAll(goodList);
						ChangeToJSON.parseList("ok", "u", listForM, request, response);
						return;
					}
					request.setAttribute("orderMap", orderMap);
					request.setAttribute("goodList", goodList);
					request.setAttribute("allPrice", allPrice);
					request.setAttribute("allNum", allNum);
					request.setAttribute("type", "look");//类型
					request.setAttribute("userKind", "u");
				}
				service.close();
				request.getRequestDispatcher("/orderdetail.jsp").forward(request, response);
			}else{
				OrderDetailService service = new  OrderDetailService();
				if(aim.equals("create") || aim.equals("creates")){
					//创建订单
					System.out.println("创建订单");
					List<Map<String,String>> goodList = new ArrayList<>();
					float allPrice = 0;
					String[] selected = request.getParameterValues("selected");
					if(selected!=null && selected.length!=0){
						for(int i=0;i<selected.length;i++){
							int n = Integer.parseInt(selected[i]);
							String goodId = request.getParameter("goodId_"+n);
							String detailId = request.getParameter("detailId_"+n);
							String goodNumber = request.getParameter("goodNumber_"+n);
							if(goodId==null || goodId.length()==0){
								break;
							}
							if(goodNumber==null || goodNumber.length()==0){
								goodNumber = "1";
							}
							String detailName = null;
							if(detailId!=null && detailId.length()!=0){
								detailName = service.getDetailName(goodId, detailId);
								System.out.println("detailName:"+detailName);
							}else{
								detailId = "-1";
							}
							Map<String,String> goodMap = service.getGoodById(goodId);
							allPrice += Float.parseFloat(goodMap.get("good_price"))*Integer.parseInt(goodNumber);
							goodMap.put("detailName", detailName);
							goodMap.put("detailId", detailId);
							goodMap.put("goodNumber", goodNumber);
							goodList.add(goodMap);
						}
					}else{
						int i = 1;
						while(true){
							String goodId = request.getParameter("goodId_"+i);
							String detailId = request.getParameter("detailId_"+i);
							String goodNumber = request.getParameter("goodNumber_"+i);
							System.out.println("goodId:"+goodId);
							System.out.println("detailId:"+detailId);
							System.out.println("goodNumber:"+goodNumber);
							if(goodId==null || goodId.length()==0){
								break;
							}
							if(goodNumber==null || goodNumber.length()==0){
								goodNumber = "1";
							}
							String detailName = null;
							if(detailId!=null && detailId.length()!=0){
								detailName = service.getDetailName(goodId, detailId);
								System.out.println("detailName:"+detailName);
							}else{
								detailId = "-1";
							}
							Map<String,String> goodMap = service.getGoodById(goodId);
							allPrice += Float.parseFloat(goodMap.get("good_price"))*Integer.parseInt(goodNumber);
							goodMap.put("detailName", detailName);
							goodMap.put("detailId", detailId);
							goodMap.put("goodNumber", goodNumber);
							goodList.add(goodMap);
							i++;
						}
					}
					System.out.println("goodList:"+ goodList);
					if(isMobile){
						List<Map<String,String>> listForM = new ArrayList<>();
						Map<String,String> mapTemp = new HashMap<>();
						mapTemp.put("allPrice", Float.toString(allPrice));
						listForM.add(mapTemp);
						listForM.addAll(goodList);
						ChangeToJSON.parseList("ok", "ok", listForM, request, response);
						return;
					}
					request.setAttribute("goodList", goodList);//商品所有内容
					request.setAttribute("allPrice", allPrice);//总价
					request.setAttribute("type", "create");//类型
					request.getRequestDispatcher("/orderdetail.jsp").forward(request, response);
				}else if(aim.equals("submit")){
					//订单确认提交
					//获取订单信息
					System.out.println("确认提交");
					Map<String,String> map = new HashMap<>();
					String goods = "";
					String deatils = "";
					String numbers = "";
					int i = 1;
					while(true){
						String goodId = request.getParameter("goodId_"+i);
						String detailId = request.getParameter("detailId_"+i);
						String goodNumber = request.getParameter("goodNumber_"+i);
						if(goodId==null || goodId.length()==0){
							break;
						}
						goods   += goodId + ";";
						if(detailId!=null && detailId.length()!=0){
							deatils += detailId + ";";
						}else{
							deatils += "-1;";
						}
						numbers += goodNumber + ";";
						i++;
					}
					map.put("goods", goods);
					map.put("deatils", deatils);
					map.put("numbers", numbers);
					//获取订单地址
					String addName = request.getParameter("addName");
					String addTel = request.getParameter("addTel");
					String add = request.getParameter("add");
					map.put("addName", addName);
					map.put("addTel", addTel);
					map.put("add", add);
					
					// 获取用户ID
					map.put("user_id", loginUserId);
					map.put("time", Long.toString(new Date().getTime()));
					//保存到数据库
					int res = service.addOrder(map);
					if(res!=-1){
						session.setAttribute("type", "look");
						if(isMobile){
							ChangeToJSON.parseMap("ok", Integer.toString(res), null, request, response);
							return;
						}
						response.sendRedirect(request.getContextPath()+"/orderdetail.action?submitaim=look&orderid="+res);
					}else{
						if(isMobile){
							ChangeToJSON.parseMap("no", "submitFalse", null, request, response);
							return;
						}
						response.sendRedirect(request.getContextPath()+"/order.action");
					}
				}else if(aim.equals("ship")){
					//订单发货
					System.out.println("订单发货");
					changeStatus(request, response, service, isMobile);
				}else if(aim.equals("cancel")){
					//订单取消
					System.out.println("订单取消");
					changeStatus(request, response, service, isMobile);
				}else if(aim.equals("pay")){
					//订单支付
					System.out.println("订单支付");
					changeStatus(request, response, service, isMobile);
				}else if(aim.equals("return")){
					//订单退货
					System.out.println("订单退货");
					changeStatus(request, response, service, isMobile);
				}else if(aim.equals("finish")){
					//订单完成
					System.out.println("订单完成");
					changeStatus(request, response, service, isMobile);
				}
				service.close();
			}
		}
		//--最外层if
	}
	
	private void changeStatus(HttpServletRequest request, HttpServletResponse response,OrderDetailService service,boolean isMobile) throws ServletException, IOException {
		String orderId = request.getParameter("orderid");
		String changeTo = request.getParameter("submitaim");
		String source = request.getParameter("source");
		boolean res = service.orderChangeStatus(orderId, changeTo);
		if(source.equals("detail")){
			request.getRequestDispatcher("/orderdetail.action?submitaim=look&orderid="+orderId).forward(request, response);
		}else if(source.equals("list")){
			response.sendRedirect(request.getContextPath()+"/order.action");
		}else if(isMobile){
			if(res){
				ChangeToJSON.parseMap("ok", "changeSuccess", null, request, response);
			}else{
				ChangeToJSON.parseMap("no", "changeFalse", null, request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
