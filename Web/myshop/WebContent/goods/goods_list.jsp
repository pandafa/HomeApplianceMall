<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/MagicZoom.css" type="text/css" />
<script src="${pageContext.request.contextPath}/plugs/zoom/js/MagicZoom.js" type="text/javascript"></script>






<div class="container">
<div class="row clearfix">
	<div class="col-md-10 col-md-offset-1 column">
			<ul class="nav nav-tabs">
			  <li role="presentation" class="${kind=='1' ? 'active':'' }"><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=1">电视</a></li>
			  <li role="presentation" class="${kind=='2' ? 'active':'' }"><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=2">冰箱</a></li>
			  <li role="presentation" class="${kind=='3' ? 'active':'' }"><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=3">洗衣机</a></li>
			  <li role="presentation" class="${kind=='4' ? 'active':'' }"><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=4">空调</a></li>
			  <li role="presentation" class="${kind=='5' ? 'active':'' }"><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=5">热水器</a></li>
			  <li role="presentation" class="${kind=='6' ? 'active':'' }"><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=6">太阳能</a></li>
			</ul>

<c:if test="${empty goodsList }">
<!-- 没有商品提醒开始 -->
<div class="alert alert-success alert-dismissable">
		 <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
		<h4>抱歉!</h4>
		暂时没有此类商品！/(ㄒoㄒ)/~~
</div>
<!-- 没有商品提醒结束 -->
</c:if>
<c:if test="${!empty goodsList }">
<div class="row">
	<c:forEach items="${goodsList }" var="g" varStatus="gvs">
		<div class="col-md-3">
		   <a href="${pageContext.request.contextPath}/goods/goods_detail.action?id=${ g.good_id}">
			<div class="thumbnail homegoods goodsShowDiv">
				<img alt="暂无图片" src="${pageContext.request.contextPath}/images/goods/${ g.good_id}_01.jpg" />
				<div class="caption caption-style">
					<strong>${g.good_name }</strong>
					<p>原价：&yen; ${ g.good_pre}</p>
					<p>现售：<span class="label label-danger">&yen;${g.good_price }</span></p>
					<p>共售出${ g.good_over}件</p>
				</div>
			</div>
		   </a>	
		</div>
	</c:forEach>
</div>
</c:if>


		</div>
	</div>
</div>

		    




<%@include  file="../footer.jsp"%>		

