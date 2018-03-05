<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugs/zoom/css/MagicZoom.css" type="text/css" />
<script src="${pageContext.request.contextPath}/plugs/zoom/js/MagicZoom.js" type="text/javascript"></script>

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">购物车</h3>
				</div>
				<div class="panel-body">




<form action="${pageContext.request.contextPath}/orderdetail.action" method="post">
<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th></th>
			<th>序号</th>
			<th>图片</th>
			<th>商品名称</th>
			<th>单价</th>
			<th>数量</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${goodsList }" var="g" varStatus="gvs">
		<tr>
			<td><input type="checkbox" name="selected" value="${gvs.count }" /></td>
			<td>${gvs.count }</td>
			<td><img src="${pageContext.request.contextPath}/images/goods/${g.good_id }_01.jpg" width="150px"/></td>
			<td>
				<a href="${pageContext.request.contextPath}/goods/goods_detail.action?id=${g.good_id }">${g.name }</a>
			</td>
			<td>${g.price }</td>
			<td>
				<input name="goodNumber_${gvs.count }" value="1" type="number" min="1" class="form-control">
				<input type="hidden" name="detailId_${gvs.count }" value="${g.detail_id }" />
				<input type="hidden" name="goodId_${gvs.count }" value="${g.good_id }" />
			</td>
			<td>
				<a class="btn btn-danger " href="${pageContext.request.contextPath}/shoppingcar.action?do=del&goodId=${g.good_id }">从购物车中删除</a>
			</td>
			
		</tr>
		</c:forEach>
		<tr>
			<td><input type="checkbox" onclick="swapCheck()" /><br/>全选</td>
			<td>
				<input type="hidden" name="submitaim" value="creates" />
				<input type="hidden" name="" value="" />
				<input type="hidden" name="" value="" />
			</td>
			<td></td>
			<td></td>
			<td><!-- <font size="5">总价：</font><font class="allprice">55555</font> --></td>
			<td><input class="btn btn-primary" type="submit" value="结算" /></td>
		</tr>
	</tbody>
</table>
</form>
    <script type="text/javascript">  
        //checkbox 全选/取消全选  
        var isCheckAll = false;  
        function swapCheck() {  
            if (isCheckAll) {  
                $("input[type='checkbox']").each(function() {  
                    this.checked = false;  
                });  
                isCheckAll = false;  
            } else {  
                $("input[type='checkbox']").each(function() {  
                    this.checked = true;  
                });  
                isCheckAll = true;  
            }  
        }  
    </script> 

				</div>
			</div>
		</div>
	</div>
</div>


<%@include  file="../footer.jsp"%>