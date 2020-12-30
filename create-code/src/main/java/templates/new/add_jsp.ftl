<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="com.some.web.url.RD" %>

<html>
<head>
	<script>
		var __global_cdnPath = '<%=RD.getStaticPath("")%>';
		var __global_serverPath = '';
		var __global_ctx = '<%=RD.getCtx()%>';
	</script>
	<meta http-equiv="Content-Language" content="zh-cn"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	<meta name="author" content="some"/>
	<meta name="Copyright" content="some.com"/>
	<meta name="viewport"
		  content="width=device-width, maximum-scale=1.0, initial-scale=1.0,initial-scale=1.0,user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes"/>
	<title>${table.comment}</title>
	<link href="<%=RD.getStaticPath("thirdlibs/layui/css/layui.css")%>" rel="stylesheet"/>
	<link href="<%=RD.getStaticPath("css/mg/mg.css")%>" rel="stylesheet"/>
	<script src="<%=RD.getStaticPath("thirdlibs/jquery/jquery-1.11.3.min.js")%>"></script>
	<script src="<%=RD.getStaticPath("thirdlibs/layui/layui.js")%>"></script>
	<script src="<%=RD.getStaticPath("js/pub/googTool.js")%>"></script>
	<script src="<%=RD.getStaticPath("js/${requestmapping_pre}/add${entity}.js")%>"></script>
	<style>
		.layui-form{
			padding: 40px;
			width: 400px;
			margin: 0 auto;
		}

		.layui-form textarea{
			height: 150px;
		}
	</style>
</head>
<body>
<div class="right-side">
	<div class="title-bar">
		<div class="layui-input-inline">${table.comment}</div>
	</div>
	<form class="layui-form" lay-filter="form1">
		<#-- ----------  BEGIN 字段循环遍历  ---------->
		<#list jspFields as field>
		<#if field.propertyName?contains("time")>
		<div class="layui-form-item" ${field.hiddenStr}>
				<#if field.comment!?length gt 0>
					<label class="layui-form-label">${field.comment}</label>
				<#else>
					<label class="layui-form-label">${field.propertyName}</label>
				</#if>
			<div class="layui-input-inline">
				<input type="text" name="${field.propertyName}" id="${field.propertyName}" lay-verify="date" placeholder="请选择日期" autocomplete="off" class="layui-input">
			</div>
		</div>
		<#else>
		<div class="layui-form-item"  ${field.hiddenStr}>
			<#if field.comment!?length gt 0>
			<label class="layui-form-label">${field.comment}</label>
			<#else>
			<label class="layui-form-label">${field.propertyName}</label>
			</#if>
			<div class="layui-input-block">
				<input type="text" name="${field.propertyName}" autocomplete="off" class="layui-input">
			</div>
		</div>
		</#if>
		</#list>


		<div class="layui-form-item">
			<div class="layui-input-block">
				<button type="submit" class="layui-btn" lay-submit="" lay-filter="save">立即提交</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>
