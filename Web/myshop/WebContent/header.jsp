<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>  
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
  <title>DD商城</title>
  <!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/css/default.css">

<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carousel.css" > -->
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script	src="${pageContext.request.contextPath}/css/bootstrap/js/bootstrap.min.js"></script>
<!-- <script src="${pageContext.request.contextPath}/js/common.js"></script> -->

 </head>
 <body>

 <div class="container">

	<div class="row clearfix">
		<div class="col-md-12 ">
		


 			
			 <nav class="navbar navbar-default navbar-fixed-top">
			  <div class="container">
			    <ol class="breadcrumb text-right" id="info" style="background-color:#5bc0de">

					<li id="li1">
						<span>
							<c:if test="${empty loginUserId }">
								游客
							</c:if>
							<c:if test="${!empty loginUserId }">
								<c:if test="${loginUserKind=='m' }">
									管理员
								</c:if>
								【 ${loginUserName } 】
							</c:if>
							您好，欢迎来到DD商城！
						</span>
						<c:if test="${!empty loginUserId }">
							<a href="${pageContext.request.contextPath}/logout.action">[退出]</a>&nbsp;
							<c:if test="${loginUserKind=='u' }">
								<a href="${pageContext.request.contextPath}/order.action" >我的订单</a>
								<a href="${pageContext.request.contextPath}/collection.action" >我的收藏</a>
							</c:if>
							<c:if test="${loginUserKind=='m' }">
								<a href="${pageContext.request.contextPath}/order.action" >订单管理</a>
							</c:if>
						</c:if>
						<!-- 以下两个是登录之前的 -->
						<c:if test="${empty loginUserId }">
							<a href="#modal-login" data-toggle="modal">[登录]</a>&nbsp;
							<a href="#modal-zhuce" data-toggle="modal">[新用户注册]</a>
						</c:if>
					</li>
					<li>
						<c:if test="${!empty loginUserKind && loginUserKind!='m' }">
						
							<a href="${pageContext.request.contextPath}/shoppingcar.action" onclick="">购物车 <span class="badge" id="cartBadge">${shoppingcarNum }</span></a>
						</c:if>
					</li>
			 
			 
			 	</ol>
			  </div>
			</nav>

<!-- 登录模块开始 -->
<div class="modal fade" id="modal-login" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel">
					用户登录
				</h4>
			</div>
			<script type="text/javascript">
				function loginVer(){
					var username = document.getElementById("l_username").value;
					var pw = document.getElementById("l_password").value;
					if(username==""){
						alert("请输入用户名！");
						return false;
					}
					if(pw==""){
						alert("请输入密码！");
						return false;
					}
				}
				
			</script>
			<form class="form-horizontal" role="form" onsubmit="return loginVer()" action="${pageContext.request.contextPath}/login.action">
			<div class="modal-body">
				
				<div class="form-group">
					 <label for="l_username" class="col-sm-2 col-sm-offset-2 control-label">用户名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="l_username" name="username" />
					</div>
				</div>
				<div class="form-group">
					 <label for="l_password" class="col-sm-2 col-sm-offset-2 control-label">密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="l_password" name="password" />
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				 <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				 <button type="submit" class="btn btn-primary">登录</button>
			</div>
			</form>
		</div>
	</div>
</div>
<!-- 登录模块结束 -->
<!-- 注册模块开始 -->
<div class="modal fade" id="modal-zhuce" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel">
					新用户注册
				</h4>
			</div>
			<script type="text/javascript">
				function registVer(){
					var username = document.getElementById("r_username").value;
					var pw1 = document.getElementById("r_password1").value;
					var pw2 = document.getElementById("r_password2").value;
					if(username==""){
						alert("请输入用户名！");
						return false;
					}
					if(pw1==""){
						alert("请输入密码！");
						return false;
					}
					if(pw2==""){
						alert("请重复输入密码！");
						return false;
					}
					if(pw1!=pw2){
						alert("两次密码输入不同，请重复输入");
						return false;
					}
					
				}
				
			</script>
			<form class="form-horizontal" role="form" onsubmit="return registVer()" action="${pageContext.request.contextPath}/registUser.action">
			<div class="modal-body">
				
				<div class="form-group">
					 <label for="r_username" class="col-sm-2 col-sm-offset-2 control-label">用户名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="r_username" name="username" />
					</div>
				</div>
				<div class="form-group">
					 <label for="r_password1" class="col-sm-2 col-sm-offset-2 control-label">密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="r_password1" name="password1" />
					</div>
				</div>
				<div class="form-group">
					 <label for="r_password2" class="col-sm-2 col-sm-offset-2 control-label">重复密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="r_password2" name="password2" />
					</div>
				</div>
				
			</div>
			<div class="modal-footer">
				 <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				 <button type="submit" class="btn btn-success">快速注册</button>
			</div>
			</form>
		</div>
	</div>
</div>
<!-- 注册模块结束 -->
			 <br/><br/><br/>
			 
			<nav class="navbar navbar-default navbar-inverse" role="navigation">
				<div class="navbar-header">
					 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="${pageContext.request.contextPath}/index.action"><span class="logo">DD</span> 商城</a>
				</div>
				
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="${headerNum==1||empty headerNum  ? 'active':'' }">
							 <a href="${pageContext.request.contextPath}/index.action">首页</a>
						</li>
						<li class="${headerNum==2 ? 'active':'' }">
							 <a href="${pageContext.request.contextPath}/goods/sale_goods.action">热销商品</a>
						</li>
						<li class="${headerNum==3 ? 'active':'' }">
							 <a href="${pageContext.request.contextPath}/goods/new_goods.action">新到商品</a>
						</li>
					
						<li class="dropdown ${headerNum==4 ? 'active':'' }">
							 <a href="#" class="dropdown-toggle" data-toggle="dropdown">商品分类<strong class="caret"></strong></a>
							<ul class="dropdown-menu">
							  	<li><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=1">电视</a></li>
							  	<li><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=2">冰箱</a></li>
							  	<li><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=3">洗衣机</a></li>
							  	<li><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=4">空调</a></li>
							  	<li><a href="${pageContext.request.contextPath}/goods/goods_list.action?kind=5">热水器</a></li>
							</ul>
						</li>
					</ul>
					<form action="${pageContext.request.contextPath}/search.action" class="navbar-form navbar-left" role="search" accept-charset="utf-8" method="post">
						<div class="form-group">
							<input type="text" class="form-control" name="search" />
						</div>
						<button type="submit" class="btn btn-default">店内搜索</button>
					</form>
					
				</div>
				
			</nav>