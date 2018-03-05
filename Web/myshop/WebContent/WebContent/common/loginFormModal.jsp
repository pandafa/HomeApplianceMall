<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
function login(redirUrl,baseurl){
	var userName=$("#username").val();
	var userPass=$("#password").val();

	$.post(baseurl+"/login.action",{userName:userName,userPass:userPass},
			function(result){
		
				if(result.login==true){
		   
					$('#loginFormModal').modal('hide');
					
					$("#info").html("<li id='li1'><span>"+result.username+" 您好，欢迎来到随意购商城！</span>"+	
							"<li><a href='#' onclick='showCart()'>购物车 <span class='badge' id='cartBadge'>${fn:length(sessionScope.goodslist)}</span></a></li>"+
			 				"<li><a href='${pageContext.request.contextPath}/order/getMyOrders'>我的订单</a></li>"+
			 				"<li><a href='${pageContext.request.contextPath}/usercenter/index'>个人中心</a></li>"+
			 				"<li><a href='#' onclick='logout()'>退出登录</a></li>");
								
				}
				else{
					//alert("false");
					
					$("#msgTitle").html("登录失败");
					$("#msgBody").html("用户名或密码错误");
					$("#msgModal").modal();
	
				}
			},"json");
}

function closeLogForm(backUrl){
	location.href=backUrl;
}

function logout(){
    //获取主机地址之后的目录，如： eshop/home.jsp  
    var pathName=window.document.location.pathname;  
    //获取带"/"的项目名，如：/eshop  
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
	$.post(projectName+"/logout.action",null,function(){
		$("#info").html("<li id='li1'><span>游客您好，欢迎来到随意购商城！</span>"+	
						"<a href='#loginFormModal' data-toggle='modal'>[登录]</a>&nbsp;<a href='#regFormModal' data-toggle='modal'>[新用户注册]</a></li>"+
		 				"<li><a href='#' onclick='showCart()'>购物车 <span class='badge' id='cartBadge'>${fn:length(sessionScope.goodslist)}</span></a></li>");
		//显示购物车中商品数量
		//$("#cartBadge").html(cart.getGoodsList().length);
	});
}
</script>
<div class="modal fade" id="loginFormModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
	<form role="form" action="${pageContext.request.contextPath}/login.action" method="post">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button"
					data-dismiss="modal">×</button>
				<h4 class="modal-title" id="myModalLabel">用户登录</h4>
			</div>
			<div class="modal-body">
			
				<div class="form-group">
					 <label for="exampleInputEmail1">用户名</label><input type="text" class="form-control" id="username" />
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword1">密码</label><input type="password" class="form-control" id="password" />
				</div>
			

			</div>
			<div class="modal-footer">
			    
				<button class="btn btn-primary" type="button" onclick="login('${redirUrl}','${pageContext.request.contextPath}')">登录</button>
				<button class="btn btn-default" type="button"
					data-dismiss="modal" 
					<c:if test="${!empty backUrl}">onclick=closeLogForm('${backUrl}')</c:if>   >关闭窗口</button>
				
			</div>
		</form>	
		</div>

	</div>

</div>


<div class="modal fade" id="msgModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">					 
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal">
					×
				</button>
				<h4 class="modal-title" id="msgTitle">
					
				</h4>
			</div>
			<div class="modal-body" id="msgBody">
				
			</div>
			<div class="modal-footer">	 
				<button class="btn btn-default" type="button" data-dismiss="modal">关闭窗口</button>	
			</div>
		</div>
		
	</div>
	
</div>
				
