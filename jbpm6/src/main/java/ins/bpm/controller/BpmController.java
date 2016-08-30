package ins.bpm.controller;

import ins.bpm.service.Jbpm6Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工作流Controller
 *
 * @author Ripin
 * @version $Id: BpmController.java 2016/8/30 19:47 $
 */
@Controller
@RequestMapping(value = "/bpm")
public class BpmController {

    private static final Logger logger = LoggerFactory.getLogger(BpmController.class);

    @Autowired
    private Jbpm6Service jbpm6Service;


    /**
     * 查询理赔
     *
     * @param request 订单号
     * @param orderNo
     * @return url
     */
    @RequestMapping(value = "/start", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String startProcess(String processId) {
        logger.info("===启动流程===开始===");
        jbpm6Service.startProcess(processId);
        logger.info("===启动流程===结束===");
        return "启动完成";
    }

}
