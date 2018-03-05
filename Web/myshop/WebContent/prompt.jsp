<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<script type="text/javascript">
	var message = "${msg }";
	var url = "${url }";
	function f1(){
		alert(message);
	}
	function f2(){
		location.href=url;
	}
	f1();
	f2();
</script>



</body>
</html>