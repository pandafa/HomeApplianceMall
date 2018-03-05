<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/MagicZoom.css" type="text/css" />
<script src="${pageContext.request.contextPath}/plugs/zoom/js/MagicZoom.js" type="text/javascript"></script>


<div class="panel panel-primary">
  <!-- Default panel contents -->
  <div class="panel-heading">我的收藏</div>
  <!-- Table -->
  <table class="table table-hover table-striped table-bordered">
	<tr>
		<th class="">图片</th>
		<th class="">名称</th>
		<th class="">单价</th>
		<th class="">操作</th>
	</tr>
	<c:forEach items="${collectGoodsList }" var="good">
	<tr>
		<td class="">
			<img alt="暂无图片" src="${pageContext.request.contextPath}/images/goods/${good.good_id }_01.jpg" width="150px" />
		</td>
		<td class="">
			${good.good_name }
		</td>
		<td class="">
			&yen;${good.good_price }
		</td>
		<td class="">
			<a class="btn btn-success" href="${pageContext.request.contextPath}/goods/goods_detail.action?id=${good.good_id }">查看商品</a>
			<a class="btn btn-danger" href="${pageContext.request.contextPath}/collection.action?do=del&id=${good.good_id }">移除收藏夹</a>
		</td>
	</tr>
	</c:forEach>
  </table>
</div>


<%@include  file="../footer.jsp"%>