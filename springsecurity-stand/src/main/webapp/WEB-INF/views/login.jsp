<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="common/head.jsp"/>
<body>

<blockquote class="layui-elem-quote" style="font-size: 25px;font-family: 微软雅黑;font-weight: bold">INTALLB•管理系统</blockquote>
<div>
    <div style="margin-left:30%; float: left">
        <form class="layui-form" action="${contextPath}/login" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                    <input type="text" name="username" lay-verify="required" autofocus autocomplete="off" placeholder="请输入用户名" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                    <input type="password" name="password" lay-verify="password"  autocomplete="off" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">记住我</label>
                <div class="layui-input-block">
                    <input type="checkbox" name="remember" lay-skin="switch" title="开关">
                </div>
            </div>

            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>

</div>
<script>
    layui.use(['form'], function () {
        var form = layui.form()
                , layer = layui.layer;
        //自定义验证规则
        form.verify({
            password: [/(.+){6,12}$/, '密码必须6到12位']
        });
    });
</script>
</body>
