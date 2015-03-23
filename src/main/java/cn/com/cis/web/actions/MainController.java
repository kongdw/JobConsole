package cn.com.cis.web.actions;

import cn.com.cis.domain.User;
import cn.com.cis.job.ETLServer;
import cn.com.cis.service.JobEntityService;
import cn.com.cis.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private JobEntityService jobEntityService;
    @Autowired
    private ParameterService parameterService;

    @Autowired
    private ETLServer etlServer;


    @RequestMapping(value = "/",method ={RequestMethod.GET, RequestMethod.HEAD})
    public ModelAndView defaultView(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            return new ModelAndView("main");
        } else {
            return new ModelAndView("login");
        }
    }

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView("main");
        mav.addObject("parameterRefList", parameterService.selectAllParameterRef());
        mav.addObject("serverStatus",etlServer.getEtlServerStatus());
        //mav.addObject("queue", etlServer.getBlockingDeque());
        //List<JobLog> list = logService.selectAllJobLogMain();
        //List<JobEntity> JobEntityList = jobEntityService.selectAllJobEntityForMain(true);
       // mav.addObject("list", list);
        //mav.addObject("JobEntityList",JobEntityList);
        //long currentTime = System.currentTimeMillis();

        //new Date(currentTime)
        return mav;
    }

}
