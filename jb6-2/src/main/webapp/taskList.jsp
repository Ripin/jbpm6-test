<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>工作流列表</title>
    <link rel="stylesheet" href="css/jquery-ui-1.10.4.custom.css"/>
    <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>
    <script type="text/javascript" src="js/jquery.json.js"></script>
    <script type="text/javascript">
        var userId = "${userId}";
        var formParamterMapping = '${formParamterMapping}';
        formParamterMapping=$.parseJSON(formParamterMapping);
    </script>
    <script type="text/javascript" src="taskList.js"></script>
</head>
<body>
<a href="listProcess.do">返回查看工作流</a>
查看个人任务：
<select onchange="onSelectedUser()" id="userJbpmId">
    <c:forEach var="user" items="${userList}">
        <option value="${user.userJbpmId}">${user.userName}</option>
    </c:forEach>
</select>
<table border="1" id="taskTable" style="width: 90%;">
    <thead>
    <tr>
        <th>任务ID</th>
        <th>所属用户</th>
        <th>任务名称</th>
        <th>状态</th>
        <th>获取</th>
        <th>启动</th>
        <th>完成</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td>${task.getId}</td>
            <td>${task.getActualOwner }</td>
            <td>${task.getName }</td>
            <td>${task.getStatus }</td>
            <td><span _actualOwnerId="${task.getActualOwner.getId()}" _taskId="${task.getId}" _type="claim" _status="${task.getStatus }"></span></td>
            <td><span _actualOwnerId="${task.getActualOwner.getId()}" _taskId="${task.getId}" _type="start" _status="${task.getStatus }"></span></td>
            <td><span _actualOwnerId="${task.getActualOwner.getId()}" _taskId="${task.getId}" _type="complete" _status="${task.getStatus }"></span></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div id="taskFormDiv"></div>

</body>
</html>