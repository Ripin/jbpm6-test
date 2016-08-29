/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */


function onSelectedUser() {
    var jbpmUserId = $("#userJbpmId").val();
    if (jbpmUserId != null && jbpmUserId != "") {
        window.location = "taskList.do?userId=" + jbpmUserId;
    }
}

function parseMapping2Form(formParamterMapping, action) {
    if (typeof(formParamterMapping) == "string" && (formParamterMapping == null || formParamterMapping == "")){
        //没有需要构造From的字段, 放会完成任务到表格
        $("#taskTable span[_type=complete][_status=InProgress][_taskId="+formParamterMapping["taskId"]+"]").each(function () {
            $(this).html('<a href="completeTask.do?taskId=' + $(this).attr("_taskId") + '&userId=' + $(this).attr("_actualOwnerId") + '">完成任务</a>');
        });
        return;
    }

    var foundInput=false;
    $("#taskFormDiv").append("<form method='post' action='" + action + "'></form>");
    $.each(formParamterMapping, function (key, val) {
        if (key.indexOf("input_") == 0) {
            foundInput=true;
            var name = key.substring("input_".length);
            $("#taskFormDiv form").append(name + "<input type='text' name='" + name + "' value='" + val + "' size='50'/><br>");
        } else {
            var name = key;
            $("#taskFormDiv form").append("<input type='hidden' name='" + name + "' value='" + val + "'/>");
        }
    });
    if(foundInput){
        $("#taskFormDiv form").append("<button type='submit'>完成任务</button>");
        $("#taskTable span[_type=complete][_status=InProgress][_taskId="+formParamterMapping["taskId"]+"]").each(function () {
            $(this).html('');
        });
    }else{
        //没有需要构造From的字段, 放会完成任务到表格
        $("#taskTable span[_type=complete][_status=InProgress][_taskId="+formParamterMapping["taskId"]+"]").each(function () {
            $(this).html('<a href="completeTask.do?taskId=' + $(this).attr("_taskId") + '&userId=' + $(this).attr("_actualOwnerId") + '">完成任务</a>');
        });
    }
}

$(document).ready(function () {
    $("#userJbpmId").val(userId);

    $("#taskTable span[_type=claim][_status=Ready]").each(function () {
        $(this).html('<a href="claimTask.do?taskId=' + $(this).attr("_taskId") + '&userId=' + $(this).attr("_actualOwnerId") + '">获取任务</a>');
    });
    $("#taskTable span[_type=start][_status=Reserved]").each(function () {
        $(this).html('<a href="startTask.do?taskId=' + $(this).attr("_taskId") + '&userId=' + $(this).attr("_actualOwnerId") + '">启动任务</a>');
    });
    $("#taskTable span[_type=complete][_status=InProgress]").each(function () {
        //$(this).html('<a href="completeTask.do?taskId=' + $(this).attr("_taskId") + '&userId=' + $(this).attr("_actualOwnerId") + '">完成任务</a>');
        var _taskId = $(this).attr("_taskId");
        var _actualOwnerId = $(this).attr("_actualOwnerId");
        $("#taskTable span[_type=start][_taskId=" + _taskId + "]").html('<a href="viewTask.do?taskId=' + _taskId + '&userId=' + _actualOwnerId + '">查看任务</a>');
    });
    parseMapping2Form(formParamterMapping, "completeTask.do");
})