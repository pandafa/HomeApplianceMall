<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/MagicZoom.css" type="text/css" />
<script src="${pageContext.request.contextPath}/plugs/zoom/js/MagicZoom.js" type="text/javascript"></script>

<div class="row clearfix">
	<div class="col-md-10 col-md-offset-1 column">
		<div class="row">
			
			<!-- 没有商品提醒开始 -->
			<c:if test="${empty searchGoods }">
			<div class="alert alert-success alert-dismissable">
					 <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					<h4>抱歉!</h4>
					没有查到相关商品！/(ㄒoㄒ)/~~
			</div>
			</c:if>
			<!-- 没有商品提醒结束 -->
			
			<c:forEach items="${searchGoods }" var="g" varStatus="gvs">
				<div class="col-md-3">
				   <a href="${pageContext.request.contextPath}/goods/goods_detail.action?id=${g.good_id}">
					<div class="thumbnail homegoods goodsShowDiv">
						<img alt="暂无图片" src="${pageContext.request.contextPath}/images/goods/${g.good_id}_01.jpg" />
						<div class="caption caption-style">
							<strong>${g.good_name}</strong>
							<p>现售：<span class="label label-danger">&yen;${g.good_price}</span></p>
							<p>共售出${g.good_over}件</p>
						</div>
					</div>
				   </a>	
				</div>
			</c:forEach>
		</div>
	</div>
</div>


<%@include  file="../footer.jsp"%>		

