package com.pandy.controller;

import com.google.common.collect.Maps;
import com.pandy.JSONUtils;
import com.pandy.service.Jbpm6Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
public class Jbpm6Controller {


    @Autowired
    private Jbpm6Service jbpm6Service;

    @RequestMapping("/index.do")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }

    @RequestMapping("/listProcess.do")
    public ModelAndView listProcess() {
        List<Map<String, Object>> list = jbpm6Service.listProcess();
        ModelAndView view = new ModelAndView("list");
        view.addObject("processList", list);
        view.addObject("TestStr", "This is a test message.");
        return view;
    }

    @RequestMapping("/taskList.do")
    public ModelAndView taskList(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        List<Map<String, Object>> list = jbpm6Service.listUserAllTask(userId);
        ModelAndView view = new ModelAndView("taskList");
        view.addObject("tasks", list);

        List<Map<String, Object>> userList = jbpm6Service.listUsers();
        view.addObject("userList", userList);
        view.addObject("userId", userId);
        return view;
    }

    @RequestMapping("/claimTask.do")
    public ModelAndView claimTask(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String userId = request.getParameter("userId");
        jbpm6Service.claimTask(taskId, userId);
        return taskList(request);
    }

    @RequestMapping("/viewTask.do")
    public ModelAndView viewTask(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String userId = request.getParameter("userId");
        Map<String, Object> paramterMapping = jbpm6Service.getTaskParamterMapping(taskId, userId);

        ModelAndView view = taskList(request);
        paramterMapping.put("taskId",taskId);
        paramterMapping.put("userId",userId);
        view.addObject("formParamterMapping", JSONUtils.map2str(paramterMapping));
        return view;
    }

    @RequestMapping("/startTask.do")
    public ModelAndView startTask(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String userId = request.getParameter("userId");
        jbpm6Service.startTask(taskId, userId);

        return viewTask(request);
    }

    @RequestMapping("/completeTask.do")
    public ModelAndView completeTask(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        String userId = request.getParameter("userId");

        Map<String,Object> resultMapping = Maps.newHashMap();

        Enumeration enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()){
            String key = enumeration.nextElement().toString();
            resultMapping.put("output_"+key,request.getParameter(key));
        }
        jbpm6Service.completeTask(taskId, userId,resultMapping);
        return taskList(request);
    }

    @RequestMapping("/startProcess.do")
    public ModelAndView startProcess(HttpServletRequest request) {
        String proId = request.getParameter("proId");
        if(proId.startsWith("com.sample.bpmn.ruleTask")){
            jbpm6Service.startRuleTaskProcess(proId);
        }else{
            jbpm6Service.startProcess(proId);
        }
        return listProcess();
    }

    @RequestMapping("/evaluation.do")
    public ModelAndView evaluation() {
        jbpm6Service.evaluation();
        return listProcess();
    }
}
