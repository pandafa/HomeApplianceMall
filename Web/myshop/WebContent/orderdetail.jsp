<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/MagicZoom.css" type="text/css" />
<script src="${pageContext.request.contextPath}/plugs/zoom/js/MagicZoom.js" type="text/javascript"></script>




<c:if test="${type=='look' }">
<!-- 查看详情开始 -->
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<% Map<String,String> orderMap = (Map<String,String>)request.getAttribute("orderMap"); %>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						订单详情
					</h3>
				</div>
				<br/>
				<div class="row clearfix">
					<div class="col-md-10 col-md-offset-1 column">
						<div class="progress">
						<!-- 提交：25%；支付：50%；发货：75%；完成：100%；  退货：75%；取消：100%（只在发货前）； -->
						<% 
							String status = orderMap.get("order_status");
							String userKind = (String)request.getAttribute("userKind");
							int widthNum = 0;
							String barColor = "progress-bar-success";
							String barStr = null;
							String btnStr1 = null;
							String btnStr2 = null;
							String changeTo = null;
							String changeTo2 = null;
							if(userKind.equals("m")){
								if(status.equals("submit")){
									widthNum = 25;
									barColor = "progress-bar-success";
									barStr = "订单提交";
									btnStr1 = "取消订单";
									changeTo = "cancel";
								}else if(status.equals("pay")){
									widthNum = 50;
									barColor = "progress-bar-success";
									barStr = "订单已支付，等待发货";
									btnStr1 = "商品发货";
									changeTo = "ship";
								}else if(status.equals("ship")){
									widthNum = 75;
									barColor = "progress-bar-success";
									barStr = "商品已发货";
								}else if(status.equals("finish")){
									widthNum = 100;
									barColor = "progress-bar-success";
									barStr = "订单已完成";
								}else if(status.equals("return")){
									widthNum = 75;
									barColor = "progress-bar-info";
									barStr = "商品正在退货中";
									btnStr1 = "退货收到，完成订单";
									changeTo = "finish";
								}else if(status.equals("cancel")){
									widthNum = 100;
									barColor = "progress-bar-danger";
									barStr = "订单已取消";
								}
							}else{
								if(status.equals("submit")){
									widthNum = 25;
									barColor = "progress-bar-success";
									barStr = "订单提交，请支付";
									btnStr1 = "支付订单";
									btnStr2 = "取消订单";
									changeTo = "pay";
									changeTo2 = "cancel";
								}else if(status.equals("pay")){
									widthNum = 50;
									barColor = "progress-bar-success";
									barStr = "订单已支付，等待发货";
									btnStr1 = "取消订单";
									changeTo = "cancel";
								}else if(status.equals("ship")){
									widthNum = 75;
									barColor = "progress-bar-success";
									barStr = "商品已发货";
									btnStr1 = "确认收货，完成订单";
									btnStr2 = "请求退货";
									changeTo = "finish";
									changeTo2 = "return";
								}else if(status.equals("finish")){
									widthNum = 100;
									barColor = "progress-bar-success";
									barStr = "订单已完成";
								}else if(status.equals("return")){
									widthNum = 75;
									barColor = "progress-bar-info";
									barStr = "商品正在退货中";
								}else if(status.equals("cancel")){
									widthNum = 100;
									barColor = "progress-bar-danger";
									barStr = "订单已取消";
								}
							}
							
						%>
						  <div class="progress-bar <%=barColor %> progress-bar-striped" style="width: <%=widthNum %>%;">
						    	<%=barStr %>
						  </div>
						  	<div class="progress-bar" style="width: <%=100-widthNum %>%;background-color:#777"></div>
						</div>
					</div>
				</div>
				
				<div class="panel-body">
				
			<c:if test="${userKind=='m' }">
				<div class="list-group-item">
					订单用户名：${orderUserName }
				</div>
			</c:if>
				<div class="list-group-item">
					收件人信息：${orderMap.add_name }，${orderMap.add_tel }，${orderMap.addr }
				</div>
				<div class="list-group-item">
					订单流程：<br/>
						<%
							String str = "";
							String t = null;
							SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
							
							if(orderMap.get("time_submit")!=null && orderMap.get("time_submit").length()!=0){
								t = sd.format(new Date(Long.parseLong(orderMap.get("time_submit"))));
								str += t + " ———— 提交订单<br/>";
							}
							if(orderMap.get("time_pay")!=null && orderMap.get("time_pay").length()!=0){
								t = sd.format(new Date(Long.parseLong(orderMap.get("time_pay"))));
								str += t + " ———— 支付订单<br/>";
							}
							if(orderMap.get("time_cancel")!=null && orderMap.get("time_cancel").length()!=0){
								t = sd.format(new Date(Long.parseLong(orderMap.get("time_cancel"))));
								str += t + " ———— 取消订单<br/>";
							}
							if(orderMap.get("time_ship")!=null && orderMap.get("time_ship").length()!=0){
								t = sd.format(new Date(Long.parseLong(orderMap.get("time_ship"))));
								str += t + " ———— 商品发货<br/>";
							}
							if(orderMap.get("time_return")!=null && orderMap.get("time_return").length()!=0){
								t = sd.format(new Date(Long.parseLong(orderMap.get("time_return"))));
								str += t + " ———— 商品退货<br/>";
							}
							if(orderMap.get("time_finish")!=null && orderMap.get("time_finish").length()!=0){
								t = sd.format(new Date(Long.parseLong(orderMap.get("time_finish"))));
								str += t + " ———— 订单完成<br/>";
							}
							out.print(str);
						%>
				</div>
				<div class="list-group-item">
					商品清单
					<!-- 清单开始 -->
					<table class="table">
						<thead>
							<tr>
								<th>
									编号
								</th>
								<th>
									图片
								</th>
								<th>
									产品
								</th>
								<th>
									数量
								</th>
								<th>
									金额
								</th>
							</tr>
						</thead>
						<tbody>
							<%
								List<Map<String,String>> goodList = (List<Map<String,String>>)request.getAttribute("goodList");
								if(goodList!=null){
									for(int i=0;i<goodList.size();i++){
							%>
							
							<tr>
								<td>
									<%=(i+1) %>
								</td>
								<td>
									<img alt="暂无图片" src="${pageContext.request.contextPath}/images/goods/<%=goodList.get(i).get("id") %>_01.jpg" height="90px" />
								</td>
								<td>
									<%=goodList.get(i).get("name") %>
								</td>
								<td>
									<%=goodList.get(i).get("number") %>
								</td>
								<td>
									&yen;<%=goodList.get(i).get("price") %>
								</td>
							</tr>
							<%
									}
								}
							%>
							<tr>
								<td>
									总计
								</td>
								<td></td>
								<td></td>
								<td>
									${allNum }
								</td>
								<td>
									&yen;${allPrice }
								</td>
							</tr>
						</tbody>
					</table>
					<!-- 清单结束 -->
				</div>
				<% if(btnStr1!=null){ %>
				<!-- 取消订单开始 -->
				<div class="list-group-item text-center">
					
					<% if(status.equals("ship") || status.equals("submit")){ %>
						<a class="btn btn-info btn-lg" href="#modal-cancel2" data-toggle="modal"><%=btnStr2 %></a>
					<% } %>
					<a class="btn btn-danger btn-lg" href="#modal-cancel" data-toggle="modal"><%=btnStr1 %></a>
				</div>
				<!-- 取消订单结束 -->
				<% } %>
				<!-- 取消订单模块开始 -->
				<div class="modal fade" id="modal-cancel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title" id="myModalLabel">
									<%=btnStr1 %>
								</h4>
							</div>
							<div class="modal-body">
								<h3>是否要<%=btnStr1 %>？</h3>
							</div>
							<form action="${pageContext.request.contextPath}/orderdetail.action" method="post">
							<div class="modal-footer">
								<input type="hidden" name="orderid" value="<%=orderMap.get("order_id") %>" />
								<input type="hidden" name="changestatus" value="<%=changeTo %>" />
								<input type="hidden" name="source" value="detail" />
								<input type="hidden" name="submitaim" value="<%=changeTo %>" />
								 <button type="button" class="btn btn-success" data-dismiss="modal">取消</button>
								 <button type="submit" class="btn btn-danger">确认</button>
							</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal fade" id="modal-cancel2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title" id="myModalLabel">
									<%=btnStr2 %>
								</h4>
							</div>
							<div class="modal-body">
								<h3>是否要<%=btnStr2 %>？</h3>
							</div>
							<form action="${pageContext.request.contextPath}/orderdetail.action" method="post">
							<div class="modal-footer">
								<input type="hidden" name="orderid" value="<%=orderMap.get("order_id") %>" />
								<input type="hidden" name="changestatus" value="<%=changeTo2 %>" />
								<input type="hidden" name="source" value="detail" />
								<input type="hidden" name="submitaim" value="<%=changeTo2 %>" />
								 <button type="button" class="btn btn-success" data-dismiss="modal">取消</button>
								 <button type="submit" class="btn btn-danger">确认</button>
							</div>
							</form>
						</div>
					</div>
				</div>
				<!-- 取消订单模块结束 -->
			</div>



				</div>
				
			</div>
		</div>
	</div>
<!-- 查看详情结束 -->
</c:if>










<c:if test="${type=='create' }">
<!-- 提交订单开始 -->
<div class="container">
	<div class="row clearfix">
		<div class="col-md-8 col-md-offset-2 column">
			<form class="form-horizontal" method="post" role="form" accept-charset="utf-8" action="${pageContext.request.contextPath}/orderdetail.action">
			<div class="list-group">
				<div class="list-group-item active">
					提交订单
				</div>
				<c:forEach items="${goodList }" var="g" varStatus="gvs">
				<!-- 一个商品开始 -->
				<div class="list-group-item">
					<input type="hidden" name="goodId_${gvs.count }" value="${g.good_id }" />
					<input type="hidden" name="detailId_${gvs.count }" value="${g.detailId }" />
					<input type="hidden" name="goodNumber_${gvs.count }" value="${g.goodNumber }" />
					<input type="hidden" name="submitaim" value="submit" />
					<input type="hidden" name="" value="" />
				<div class="form-group">
					<label class="col-sm-2 control-label">商品名称</label>
					<div class="col-sm-10">
						<h3>${g.good_name } ${g.detailName }</h3>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商品图片</label>
					<div class="col-sm-10">
						<img src="${pageContext.request.contextPath}/images/goods/${g.good_id }_01.jpg" width="50%" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商品数量</label>
					<div class="col-sm-10">
						<p>${g.goodNumber }</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商品价格</label>
					<div class="col-sm-10">
						<p>&yen;${g.good_price }</p>
					</div>
				</div>
				</div>
				<!-- 一个商品结束 -->
				</c:forEach>
				<div class="list-group-item">
					<div class="form-group">
						<label for="addname" class="col-sm-2 control-label">收货人姓名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="addname" name="addName" />
						</div>
					</div>
					<div class="form-group">
						<label for="addtel" class="col-sm-2 control-label">收货人电话</label>
						<div class="col-sm-10">
							<input type="tel" class="form-control" id="addtel" name="addTel" />
						</div>
					</div>
					<div class="form-group">
						<label for="add" class="col-sm-2 control-label">收货地址</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="add" name="add" />
						</div>
					</div>
				</div>
				<div class="list-group-item active">
					<div class="row">
					<div class="col-sm-10">
						<p>总价：&yen;${allPrice }</p>
					</div>
					<div class="col-sm-2">
						<input type="submit" value="确认提交" class="btn btn-info" />
					</div>
					</div>
				</div>
				
			</div>
			</form>
		</div>
	</div>
</div>
<!-- 提交订单结束 -->
</c:if>

<%@include  file="../footer.jsp"%>