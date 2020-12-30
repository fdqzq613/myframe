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
    <script src="<%=RD.getStaticPath("js/${requestmapping_pre}/${lowEntity}.js")%>"></script>
    <style>
        tbody .layui-table-cell input{
            width: 80%;
            line-height: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="right-side">
    <div class="title-bar">
        <div class="layui-input-inline">${table.comment}</div>
        <div class="layui-input-inline">
            <button class="layui-btn" id="addBtn">添加${table.comment}</button>
        </div>
        <div class="right">
            <div class="layui-input-inline">
                <input id="search_name" class="layui-input" type="text" name="" placeholder="可通过用户名称进行搜索">
            </div>
            <button id="js_search" type="button" class="layui-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
        </div>
    </div>
    <table class="layui-table" id="list" lay-filter="list"></table>
</div>
</body>
</html>
