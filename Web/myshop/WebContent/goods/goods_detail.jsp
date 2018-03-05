<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/MagicZoom.css" type="text/css" />
<script src="${pageContext.request.contextPath}/plugs/zoom/js/MagicZoom.js" type="text/javascript"></script>

	<ul class="breadcrumb">
		<li>
			 <a href="${pageContext.request.contextPath}/index.action">首页</a>
		</li>
		<li>
			 <a href="${pageContext.request.contextPath}/goods/goods_list.action">商品分类</a>
		</li>
		<li class="active">
			商品详情
		</li>
	</ul>

	<div class="row">
		<div class="col-md-4">
				
				<!-- 代码开始 -->
				<div id="tsShopContainer">
					<div id="tsImgS"><a href="${pageContext.request.contextPath}/images/goods/${good.good_id }_01.jpg" title="Images" class="MagicZoom" id="MagicZoom"><img src="${pageContext.request.contextPath}/images/goods/${good.good_id }_01.jpg" /></a></div> 
					<div id="tsPicContainer">
						<div id="tsImgSArrL" onclick="tsScrollArrLeft()"></div>
						<div id="tsImgSCon">
							<ul>
								<c:forEach begin="1" end="${good.good_mid }" var="g">	   
								    <li onclick="showPic(0)" rel="MagicZoom"><img height="42" width="42" src="${pageContext.request.contextPath}/images/goods/${good.good_id }_0${g}.jpg" tsImgS="${pageContext.request.contextPath}/images/goods/${good.good_id }_0${g}.jpg" /></li>
								</c:forEach>
							</ul>
						</div>
						<div id="tsImgSArrR" onclick="tsScrollArrRight()"></div>
					</div>
					<img class="MagicZoomLoading" width="16" height="16" src="${pageContext.request.contextPath}/plugs/zoom/images/loading.gif" alt="Loading..." />
				</div>
				<!-- 引入放大镜效果脚本 -->
				<script src="${pageContext.request.contextPath}/plugs/zoom/js/ShopShow.js"></script>
				<!-- 代码结束 -->				
				
		</div>
		<div class="col-md-8">
			<h3>${good.good_name }</h3>
			<div class="panel panel-default">
			  <div class="panel-body bg_goodsdetail">
			    <p>促销价：<span class="price_red "><small>&yen;</small>${good.good_price }</span></p>
			    <p>原价：<span class="price"><small>&yen;</small>${good.good_pre }</span></p>
			    <p>已售出 <span style="color:gray">${good.good_over } </span>件</p>
			  </div>
			</div>
	    <form class="form-inline" role="form"  method="post" action="${pageContext.request.contextPath}/orderdetail.action">	
			<input type="hidden" name="submitaim" value="create" />
			<input type="hidden" name="goodId_1" value="${good.good_id }" />
            <c:if test="${!empty detail }">
			<div class="row row_style">
			  <div class="col-md-12">
	            <label>套装：</label>
	            	<select name="detailId_1" class="form-control">
	            		<c:forEach items="${detail }" var="d">
	            			<option value="${d.detail_id }">${d.detail_name }</option>
	            		</c:forEach>
	            	</select>
	          </div>  
            </div>
            </c:if>
             <div class="row row_style">
	             <div class="col-md-12">
	            	<div class="form-group">	
					    <label for="num">数量：</label>		 								
						<div class="input-group input-group-sm col-xs-4">
						<input class="form-control" id="goodsSales" name="goodNumber_1" type="number" value="1" min="1"/>
						</div>
					</div>
				  </div>	
             </div>
            
		     <div class="row row_style">
		        <div class="col-md-12">

			        <p class="p_height">运费：<small>&yen;</small>0</p>			        
					<p>
						<button class="btn btn-danger btn-sm" type="submit">
							立即购买
						</button>
						<a class="btn btn-primary btn-sm" type="button" href="${pageContext.request.contextPath}/shoppingcar.action?do=add&goodId=${good.good_id }&detailId=" >
							加入购物车
						</a>
						<a class="btn btn-success btn-sm" type="button" href="${pageContext.request.contextPath}/collection.action?do=add&id=${good.good_id }" >
							加入收藏夹
						</a>
					</p>
				 </div>	
        
		     </div>
		    </form> 
		    
		</div>	
				
 
			</div>								
		 </div>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script>	
	<a class="btn btn-info btn-sm" type="button" data-toggle="modal" data-target=".bs-example-modal-sm" >使用手机查看</a>
	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	      	请用对应的手机APP扫描该二维码
	      </div>
	      <div class="modal-body">
	        <div id="qrcode"></div>
	        <script>
			    jQuery(function(){
			    jQuery('#qrcode').qrcode("/myshop/goods/goods_detailForMobile.action?id=#${good.good_id }#skkwkkkwdwdjjaswd");
			})
			</script>
	      </div>
	    </div>
	  </div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">

			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">商品详情</h3>
				</div>
				<div class="panel-body">
			<dl class="dl-horizontal">
				<dt>产地</dt>
				<dd>中国大陆</dd>
				<dt>宝贝详情</dt>
			</dl>				
            <hr>
            
			<div style="margin-top:20px" class="text-center">
				<c:if test="${good.good_dis==0 }">
	            	<img alt="暂无图片" />
	            </c:if>
				<c:forEach begin="1" end="${good.good_dis }" var="g">
			      <div>
				  <img alt="暂无图片" src="${pageContext.request.contextPath}/images/goods/${good.good_id }_${g }.png" />
			      </div>
			    </c:forEach>
			</div>
					
				</div>
			</div>
		</div>
	</div>

<%@include  file="../footer.jsp"%>		

