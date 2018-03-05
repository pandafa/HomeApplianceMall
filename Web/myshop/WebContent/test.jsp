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
<h1>测试</h1>


<div class="form-group has-error">
  <input type="text" class="form-control" id="inputError24">
</div>
-----------------------<br/>
<div class="form-group ">
  <input type="text" class="form-control" id="inputError24">
</div>
-----------------------<br/>
<div class="form-group has-error has-feedback">
  <input type="text" class="form-control" id="inputError24">
  <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
</div>

 </body>
</html>