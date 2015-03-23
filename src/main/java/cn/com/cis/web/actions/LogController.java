package cn.com.cis.web.actions;

import cn.com.cis.domain.JobLog;
import cn.com.cis.job.ETLServer;
import cn.com.cis.plugins.mybatis.PageInfo;
import cn.com.cis.service.JobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/log")
public class LogController {

    @Autowired
    private ETLServer etlServer;

    @Autowired
    private JobLogService logService;

//    @RequestMapping(value = "/jobLog", method = RequestMethod.GET, produces = "application/json")
//    public
//    @ResponseBody
//    String getJobLog() {
//        return etlServer.getCurrentJobLog();
//    }

    @RequestMapping(value = "/power", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    double getPower() {
        //return etlServer.getPower();
        return etlServer.getPower() ;
    }

//    @RequestMapping(value = "/taskStatus", method = RequestMethod.GET, produces = "application/json")
//    public
//    @ResponseBody
//    List getTaskStatus() {
//        return etlServer.getTasks();
//    }

//    @RequestMapping(value = "/etlServerStatus", method = RequestMethod.GET, produces = "application/json")
//    public
//    @ResponseBody
//    String getEtlServerStatus() {
//        return etlServer.getStatus().toString();
//    }

    @RequestMapping(value = "/termination", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    boolean termination() throws InterruptedException {
        etlServer.shutdownNow();
        return true;
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("page") int page) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("log/list");
        List<JobLog> list = logService.selectAllJobLog(page,10);
        PageInfo pageInfo = new PageInfo(list,10);
        mav.addObject("pageInfo",pageInfo);
        mav.addObject("list", list);
        return mav;
    }


    @RequestMapping(value = "/ajaxlist", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo ajaxlist(@RequestParam(value = "page",defaultValue = "1",required = false) int page) {
        List<JobLog> list = logService.selectAllJobLog(page,7);
        PageInfo pageInfo = new PageInfo(list,7);
        return pageInfo;
    }
}
