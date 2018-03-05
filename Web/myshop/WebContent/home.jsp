<%@include file="/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!-- 巨幕开始 -->
<div class="jumbotron">
  <h1>欢迎来到DD商城！</h1>
</div>
<!-- 巨幕结束 -->
<!-- 轮播开始 -->
<div class="row clearfix">
<div class="col-md-8 col-md-offset-2 column">
<div class="carousel slide" id="carousel-210314" data-ride="carousel" data-interval="2000">
	<ol class="carousel-indicators">
		<li class="active" data-slide-to="0" data-target="#carousel-210314">
		</li>
		<li data-slide-to="1" data-target="#carousel-210314">
		</li>
		<li data-slide-to="2" data-target="#carousel-210314">
		</li>
	</ol>
	<div class="carousel-inner">
		<div class="item active">
			<img alt="" src="${pageContext.request.contextPath}/images/adver/1.jpg" />

		</div>
		<div class="item">
			<img alt="" src="${pageContext.request.contextPath}/images/adver/2.jpg" />

		</div>
		<div class="item">
			<img alt="" src="${pageContext.request.contextPath}/images/adver/3.jpg" />
		</div>
	</div> <a class="left carousel-control" href="#carousel-210314" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> <a class="right carousel-control" href="#carousel-210314" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
</div>
</div>
</div>
<!-- 轮播结束 -->
<!-- 今日推荐 -->
<div class="row clearfix">
<div class="col-md-8 col-md-offset-2 column">
<div class="panel panel-primary">
	<div class="panel-heading">今日推荐</div>
	<div class="panel-body">
		<div class="row clearfix">

			<c:forEach items="${todayGoodsList }" var="c" varStatus="vs">
				<div class="col-md-2 column ">	
				    <a href="${pageContext.request.contextPath}/goods/goods_detail.action?id=${c.good_id }">
				    <div class="thumbnail todaygoods">
				     <img src="${pageContext.request.contextPath}/images/goods/${c.good_id }_01.jpg"/>
				     <div class="caption text-center">
				        <h4 class="font-red">&yen;${c.good_price }</h4>
				        <p>${c.good_name }</p>
				      </div>
				    </div>
				    </a>				
				</div>
               </c:forEach>
               
		</div>	
	</div>
</div>
</div>
</div>
<!-- 今日结束 -->


<div class="row clearfix">
<c:forEach items="${allKindsList }" var="c" varStatus="vs">
	<div class="col-md-6 column">
	<div class="navtitle">
		<a href="#${c.kind_id }"><span class="cate-title">${c.kind_name }</span></a>
	</div>	 						    
	<div class="row">
		<c:forEach items="${allGoodsList }" var="g" varStatus="gvs">
			<c:if test="${c.kind_id == g.good_kind }">
				<div class="col-md-4">
				   <a href="${pageContext.request.contextPath}/goods/goods_detail.action?id=${ g.good_id}">
					<div class="thumbnail homegoods goodsShowDiv">
						<img alt="暂无图片" src="${pageContext.request.contextPath}/images/goods/${ g.good_id}_01.jpg" />
						<div class="caption caption-style">
							<p>${g.good_name }</p>
							<p class="font-red">&yen; ${g.good_price }</p>
						</div>
					</div>
				   </a>	
				</div>
			</c:if>
		</c:forEach>
	</div>
	</div>
</c:forEach>
</div>

</div><!-- col-12 end -->	
</div><!-- row end -->
<%@include  file="footer.jsp"%>