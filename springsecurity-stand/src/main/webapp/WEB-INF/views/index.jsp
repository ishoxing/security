<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="common/head.jsp"/>
<body>

<div class="layui-layout-admin">
    <div class="layui-header">
        <div class="layui-inline" style="padding: 10px 0 0 10px;font-size: 30px;font-family: '微软雅黑 Light'">
            INTALLB•后台管理系统
        </div>
        <div style="float: right;width: 200px">
            <div style="height: 50%;padding: 20px 2px 2px 0">
                <div class="layui-inline">欢迎使用，<sec:authentication property="principal.username"/>！</div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-primary layui-btn-mini" href="${contextPath}/logout">离开</a>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">

            <ul class="layui-nav layui-nav-tree site-demo-nav">

                <li class="layui-nav-item" style="height: 5px; text-align: center">
                    <a title="纸飞机，为你护航">
                        <i class="layui-icon" style=" font-size: 16px;"></i>
                    </a>
                </li>

                <li class="layui-nav-item layui-this">
                    <a href="/demo/">
                        <i class="layui-icon" style="top: 1px; font-size: 18px;">&#xe612;</i>
                        <cite> 用户管理</cite>
                    </a>
                </li>

                <li class="layui-nav-item layui-nav-title">
                    <a> <i class="layui-icon" style="top: 1px; font-size: 18px;">&#xe616;</i>
                        <cite>权限管理</cite></a>
                </li>

                <li class="layui-nav-item ">
                    <a href="/demo/button.html">
                        <i class="layui-icon" style="top: 1px; font-size: 18px;">&#xe64c;</i>
                        <cite>权限分配</cite>
                    </a>
                </li>
                <span class="layui-nav-bar" style="top: 65px; height: 0px; opacity: 0;"></span></ul>

        </div>
    </div>
</div>

</body>