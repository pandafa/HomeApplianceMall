<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

function reg(baseurl){
	$.post(baseurl+"/reg.action",$('#regForm').serialize(),function(result){
		$('#regFormModal').modal('hide');
		if(result.reg==true){
			$("#msgTitle").html("注册成功");
			$("#msgBody").html("恭喜您，注册成功");
			$("#msgModal").modal();
			
			$("#info").html("<li id='li1'><span>"+result.username+" 您好，欢迎来到随意购商城！</span>"+	
					"<li><a href='#' onclick='showCart()'>购物车 <span class='badge' id='cartBadge'>${fn:length(sessionScope.goodslist)}</span></a></li>"+
	 				"<li><a href='${pageContext.request.contextPath}/order/getMyOrders'>我的订单</a></li>"+
	 				"<li><a href='${pageContext.request.contextPath}/usercenter/index'>个人中心</a></li>"+
	 				"<li><a href='#' onclick='logout()'>退出登录</a></li>");

		}
		else{
			$("#msgTitle").html("注册失败");
			$("#msgBody").html("对不起，注册失败");
			$("#msgModal").modal();
		}
	});
}

</script>    
<div class="modal fade" id="regFormModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
				<h4 class="modal-title" id="myModalLabel">新用户注册</h4>
			</div>
			<div class="modal-body">
				<form role="form" id="regForm"  method="post">
					<div class="form-group">
						<label for="userName"> 用户名 </label> 
						<input class="form-control" name="userName" id="userName" type="text" placeholder="用户名" required/>
						<span id="checkNameResult"></span>
					</div>
					<div class="form-group">
						<label for="userPass"> 密码 </label> 
						<input class="form-control" name="userPass" id="userPass" type="password" placeholder="密码"  required/>
					</div>
					<div class="form-group">
						<label for="userPass1"> 密码确认 </label> 
						<input class="form-control" id="userPass1" type="password" placeholder="密码确认"  required/>
					</div>	
					<div class="form-group"> 
						<label for="userAge"> 年龄</label>
						<input class="form-control" name="userAge" id="userAge" type="number" placeholder="年龄" />
					</div>				
					<div class="form-group">
						<label for="userSex"> 性别 </label> 
						<div class="radio">
						  <label>
						    <input type="radio" name="userSex" id="sex1" value="0" checked> 男
						  </label>
						  <label>
						    <input type="radio" name="userSex" id="sex2" value="1"> 女
						  </label>
						</div>						
					</div>					
					<div class="form-group"> 
						<label for="userEmail">	邮箱</label>
						<input class="form-control" name="userEmail" id="userEmail" type="email" placeholder="Email" />
					</div>				
				</form>

			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" type="button" onclick="reg('${pageContext.request.contextPath}')">注册</button>
				<button class="btn btn-default" type="button"
					data-dismiss="modal">关闭窗口</button>
				
			</div>
		</div>

	</div>

</div>