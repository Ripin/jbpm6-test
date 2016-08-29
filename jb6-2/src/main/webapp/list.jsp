<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>工作流列表</title>
    <link rel="stylesheet" href="css/jquery-ui-1.10.4.custom.css"/>
    <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>
</head>
<body>
<a href="taskList.do">查看任务</a>
<table border="1">
    <thead>
    <tr>
        <th>流程ID</th>
        <th>流程名称</th>
        <th>流程版本</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="process" items="${processList}">
        <tr>
            <td>${process.processId}</td>
            <td>${process.proName }</td>
            <td>${process.proVersion }</td>
            <td><a href="startProcess.do?proId=${process.processId}">启动</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>