package cn.com.cis.web.actions;

import cn.com.cis.domain.JobConfig;
import cn.com.cis.service.JobConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JobController {
    @Autowired
    private JobConfigService service;

    @RequestMapping(value = "/job/query.do", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("job/list");
        mav.addObject("list", service.queryAllJobs());
        return mav;
    }

    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/job/add.do", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView("/job/list");
        JobConfig j = new JobConfig();
        j.setAutostart(true);
        j.setRemark("111");
        j.setCronExpression("sss");
        j.setEtlFileUrl("ffff");
        j.setJobName("ssss.xml");
        j.setJobGroup("ssss.xml");
        j.setJobStatus(1);
        service.insertJobConfig(j);
        return mav;
    }
}
