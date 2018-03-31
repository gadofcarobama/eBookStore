<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <h1>登录</h1>
<p style="color: red; font-weight: 900" id="msg">${msg }</p>
<form action="${pageContext.request.contextPath }/user" method="post" target="_parent">
    <input type="hidden" name="method" value="login"/>
	<table>
		<tr>
			<td>用户名：</td>
			<td><input type="text" name="username" onfocus="msg()"/></td>
		</tr>
		<tr>
			<td>密　码：</td>
			<td><input type="password" name="password"/></td>
		</tr>
		<tr>
			<td>验证码：</td>
			<td>	<input type="text" name="code"/><br/>
			<img id="img1" src="${pageContext.request.contextPath }/code"/>
			<a href="#" onclick="loadCode();">看不清，换一张</a></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" value="登录"/></td>
		</tr>
		
	</table>
	
</form>
  </body>
  <script type="text/javascript">
  	function loadCode() {
  		var img1 = document.getElementById("img1");
  		img1.src = "/estore/code?"+new Date().getTime();
  	}
  	//验证错误让上面的提示字消失
  	function msg() {
		var display=document.getElementById("msg")
		display.style.display="none"
    }
  </script>
</html>










