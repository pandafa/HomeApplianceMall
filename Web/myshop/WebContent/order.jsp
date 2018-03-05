<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/MagicZoom.css" type="text/css" />
<script src="${pageContext.request.contextPath}/plugs/zoom/js/MagicZoom.js" type="text/javascript"></script>

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">我的订单</h3>
				</div>
				<div class="panel-body">





<table class="table table-striped table-hover">
	<thead>
		<tr>
			
			<th>序号</th>
		<c:if test="${userKind=='m' }">
			<th>用户名</th>
		</c:if>
			<th>图片</th>
			<th>商品名称</th>
			<th>总数量</th>
			<th>交易价格</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		
		<% 
			List<Map<String,String>> orderList = (List<Map<String,String>>)request.getAttribute("orderList");
			if(orderList!=null){
				for(int i=0;i<orderList.size();i++){
		%>
		
		<tr>
			<% 
				String userKind = (String)request.getAttribute("userKind");
				String status = orderList.get(i).get("status") ;
				String statusStr = null;
				String btnStr1 = null;
				String btnStr2 = null;
				String changeTo = null;
				String changeTo2 = null;
				if(userKind.equals("m")){
					if(status.equals("submit")){
						statusStr = "订单已提交";
						btnStr1 = "取消订单";
						changeTo = "cancel";
					}else if(status.equals("pay")){;
						statusStr = "订单已支付";
						btnStr1 = "商品发货";
						changeTo = "ship";
					}else if(status.equals("ship")){
						statusStr = "商品已发货";
					}else if(status.equals("finish")){
						statusStr = "订单已完成";
					}else if(status.equals("return")){
						statusStr = "商品正在退货中";
						btnStr1 = "退货收到<br/>订单完成";
						changeTo = "finish";
					}else if(status.equals("cancel")){
						statusStr = "订单已取消";
					}
				}else{
					if(status.equals("submit")){
						statusStr = "订单已提交";
						btnStr1 = "支付订单";
						btnStr2 = "取消订单";
						changeTo = "pay";
						changeTo2 = "cancel";
					}else if(status.equals("pay")){;
						statusStr = "订单已支付";
						btnStr1 = "取消订单";
						changeTo = "cancel";
					}else if(status.equals("ship")){
						statusStr = "商品已发货";
						btnStr1 = "确认收货";
						btnStr2 = "请求退货";
						changeTo = "finish";
						changeTo2 = "return";
					}else if(status.equals("finish")){
						statusStr = "订单已完成";
					}else if(status.equals("return")){
						statusStr = "商品正在退货中";
					}else if(status.equals("cancel")){
						statusStr = "订单已取消";
					}
				}
			%>
			<td><%=i+1 %></td>
		<c:if test="${userKind=='m' }">
			<td><%=orderList.get(i).get("user_name") %></td>
		</c:if>
			<td><img src="${pageContext.request.contextPath}/images/goods/<%=orderList.get(i).get("id") %>_01.jpg" width="150px"/></td>
			<td><p><%=orderList.get(i).get("name") %></p></td>
			<td><%=orderList.get(i).get("allNum") %></td>
			<td>&yen;<%=orderList.get(i).get("allPrice") %></td>
			<td><%=statusStr %></td>
			<td>
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/orderdetail.action?orderid=<%=orderList.get(i).get("orderId") %>" role="button">
					查看详情
				</a>
				<% if(btnStr1!=null){ %>
				<a class="btn btn-success" data-toggle="modal" data-target="#myOrderModal" data-wen="<%=btnStr1 %>" data-word="<%=changeTo %>" data-orderid="<%=orderList.get(i).get("orderId") %>">
					<%=btnStr1 %>
				</a>
				<% if(btnStr2!=null){ %>
				<a class="btn btn-danger" data-toggle="modal" data-target="#myOrderModal" data-wen="<%=btnStr2 %>" data-word="<%=changeTo2 %>" data-orderid="<%=orderList.get(i).get("orderId") %>">
					<%=btnStr2 %>
				</a>
				<% }} %>
			</td>
		</tr>
		<%
				}
			}
		%>
		
	</tbody>
</table>
<!-- 模态开始 -->
<div class="modal fade" id="myOrderModal" tabindex="-1" role="dialog" aria-labelledby="myOrderModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myOrderModalLabel">xxx</h4>
      </div>
      <form action="orderdetail.action" method="post">
      <div class="modal-body">
          	是否要xxx？
      </div>
      <div class="modal-footer">
      	<input type="hidden" class="in_orderid" name="orderid" value="order_id" />
		<input type="hidden" class="in_changestatus" name="changestatus" value="changeto" />
		<input type="hidden" name="source" value="list" />
		<input type="hidden" class="in_submitaim" name="submitaim" value="changeto" />
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="submit" class="btn btn-primary">确认</button>
      </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
$('#myOrderModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget)
  var wen  = button.data('wen') 
  var word = button.data('word')
  var id   = button.data('orderid')
  
  var modal = $(this)
  modal.find('h4').text(wen)
  modal.find('.modal-body').text('是否要' + wen+'？')
  modal.find('.in_orderid').val(id)
  modal.find('.in_changestatus').val(word)
  modal.find('.in_submitaim').val(word)

})
</script>
<!-- 模态结束 -->

				</div>
			</div>
		</div>
	</div>
</div>


<%@include  file="../footer.jsp"%>