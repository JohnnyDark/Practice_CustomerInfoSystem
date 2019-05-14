<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: works-0326
  Date: 2019/5/6
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
    <script>
        <%--删除单个用户--%>
        var deleteSingleUser=function (id) {
            if (confirm("确定要删除吗？")){
               location.href="${pageContext.request.contextPath}/delSingleUserServlet?id="+id;
            }
        }
        //批量删除用户
        window.onload=function () {
            document.getElementById("delSelected").onclick=function () {
                if (confirm("确定要删除选择项吗？")){
                    var flag=false;
                    //判断是否有选中条目
                    var cbs=document.getElementsByName("ids");
                    for (var i=0;i<cbs.length;i++){
                        if (cbs[i].checked){ //至少有一条被勾选，表明有选中项
                            flag=true;
                            break;
                        }
                    }
                    if (flag){//如果有条目被选中时则提交
                        document.getElementById("form").submit();
                    }
                }
            }

            document.getElementById("firstCb").onclick=function () {
                var cbs=document.getElementsByName("ids");
                for (var i=0;i<cbs.length;i++){
                    cbs[i].checked=this.checked;
                }
            }
        }


    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>
    <!--

    复杂查询操作 div

    -->
    <div style="float: left;margin: 10px">
        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" class="form-control" name="name" id="exampleInputName2" value="${conditions.name[0]}" >
            </div>
            <div class="form-group">
                <label for="exampleInputEmail2">籍贯</label>
                <input type="text" class="form-control" name="address" id="exampleInputEmail2" value="${conditions.address[0]}">
            </div>
            <div class="form-group">
                <label for="exampleInputAddressed2">Email</label>
                <input type="text" class="form-control" name="email" id="exampleInputAddressed2" value="${conditions.email[0]}">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>
    <div style="float: right; margin: 10px">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
        <a class="btn btn-primary" href="javascript:void(0);" id="delSelected" >删除选中</a>
    </div>
    <form action="${pageContext.request.contextPath}/delMultipleUserServlet" method="post" id="form">
    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th><input type="checkbox" id="firstCb"></th>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${pb.list}" var="user" varStatus="s">
            <c:if test="${s.count%2==0}">
                <tr bgcolor="#b0e0e6">
                    <td><input  type="checkbox" name="ids" value="${user.id}"></td>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteSingleUser(${user.id});">删除</a>
                    </td>
                </tr>
            </c:if>
            <c:if test="${s.count%2!=0}">
                <tr bgcolor="#fff0f5">
                    <td><input type="checkbox" name="ids" value="${user.id}"></td>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteSingleUser(${user.id});">删除</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
    </form>
    <!--

    分页div

    -->
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                        <c:if test="${pb.currentPage==1}"><li class="disabled"></c:if>
                        <c:if test="${pb.currentPage!=1}"><li></c:if>
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage-1}&rows=5&name=${conditions.name[0]}&address=${conditions.address[0]}&email=${conditions.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        </a>
                </li>
                <c:forEach begin="1"  var="s" end="${pb.totalPage}">
                    <c:if test="${s==pb.currentPage}">
                        <li class="active"><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${s}&rows=5&name=${conditions.name[0]}&address=${conditions.address[0]}&email=${conditions.email[0]}">${s}</a></li>
                    </c:if>
                    <c:if test="${s!=pb.currentPage}">
                        <li><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${s}&rows=5&name=${conditions.name[0]}&address=${conditions.address[0]}&email=${conditions.email[0]}">${s}</a></li>
                    </c:if>
                </c:forEach>
                        <c:if test="${pb.currentPage==pb.totalPage}"><li class="disabled"></c:if>
                        <c:if test="${pb.currentPage!=pb.totalPage}"><li></c:if>
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage+1}&rows=5&name=${conditions.name[0]}&address=${conditions.address[0]}&email=${conditions.email[0]}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        </a>
                </li>
                <span style="font-size: 25px; margin: 10px;">
                    共${pb.totalCount}条记录, 共${pb.totalPage}页
                </span>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>

