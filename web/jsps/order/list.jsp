<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	* {
		font-size: 11pt;
	}
	div {
		border: solid 2px gray;
		width: 75px;
		height: 75px;
		text-align: center;
	}
	li {
		margin: 10px;
	}
	
	#buy {
		background: url(<c:url value='/images/all.png'/>) no-repeat;
		display: inline-block;
		
		background-position: 0 -902px;
		margin-left: 30px;
		height: 36px;
		width: 146px;
	}
	#buy:HOVER {
		background: url(<c:url value='/images/all.png'/>) no-repeat;
		display: inline-block;
		
		background-position: 0 -938px;
		margin-left: 30px;
		height: 36px;
		width: 146px;
	}
</style>
  </head>
  
  <body>
  <h1>我的订单</h1>

  <table border="1" width="100%" cellspacing="0" background="black">

	  <c:forEach var="order" items="${order }">
		  <tr bgcolor="gray" bordercolor="gray">
			  <td colspan="6">
				  订单编号：<a href="${pageContext.request.contextPath }/order?method=findOrderItemByOid&oid=${order.oid }">${order.oid }</a>　

				  成交时间：${order.ordertime }　
				  金额：<font color="red"><b>${order.total }元</b></font>　
				  <c:if test="${order.state==1 }">
					  未付款
				  </c:if>
				  <c:if test="${order.state==2 }">
					  未发货
				  </c:if>
				  <c:if test="${order.state==3 }">
					  未确认收货
				  </c:if>
				  <c:if test="${order.state==4 }">
					  收货已结束
				  </c:if>
			  </td>
		  </tr>
	  </c:forEach>



	  <c:forEach var="orderitem" items="${orderitem }">
		  <tr bordercolor="gray" align="center">
			  <td width="15%">
				  <div><img src="${pageContext.request.contextPath }/${orderitem.image }" height="75"/></div>
			  </td>
			  <td>书名：${orderitem.bname }</td>
			  <td>单价：${orderitem.price }元</td>
			  <td>作者：${orderitem.author }</td>
			  <td>数量：${orderitem.count }</td>
			  <td>小计：${orderitem.subtotal }元</td>
		  </tr>
	  </c:forEach>

  </table>
  </body>
</html>
