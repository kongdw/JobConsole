package cn.com.cis.web.actions;

import cn.com.cis.common.Constants;
import cn.com.cis.domain.*;
import cn.com.cis.job.ETLServer;
import cn.com.cis.service.*;
import cn.com.cis.utils.SqlScriptUtil;
import com.foundationdb.sql.StandardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;

@Controller
@RequestMapping(value = "/queue")
public class ExecutorController {

    @Autowired
    private JobEntityService jobEntityService;
    @Autowired
    private JobLogService logService;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private ETLServer etlServer;
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private LayerService layerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("queue/list");
        mav.addObject("queue", etlServer.getBlockingDeque());
        return mav;
    }

    @RequestMapping(value = "/MainAjaxlist", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public  Collection<JobInfo> MainAjaxlist() {
        return etlServer.getBlockingDeque();
    }

    @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<JobLog> ajaxList() {
        return logService.selectAllJobLog(1,10);
    }


    @RequestMapping(value = "/executeAll", method = RequestMethod.GET)
    public ModelAndView executeAll(HttpServletRequest request, RedirectAttributesModelMap modelMap) throws StandardException, ParseException {
        ModelAndView mav = new ModelAndView("redirect:/main");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }
        List<Layer> layerList = layerService.selectAllLayer();
        for (Layer layer : layerList) {
            List<JobEntity> jobList = jobEntityService.selectJobEntityByLayer(layer.getId(), true);
            for (JobEntity job : jobList) {
                JobInfo jobInfo = new JobInfo();
                jobInfo.setJobId(job.getId());
                if(etlServer.getBlockingDeque().contains(jobInfo)){
                    modelMap.addFlashAttribute("error","不能重复提交作业。");
                    mav.setViewName("redirect:/layer/list");
                    return mav;
                }
                Set<String> params = new HashSet<String>();
                SqlScriptUtil.getSqlParameter(params, job.getSqlScript());
                List<Parameter> parameterList = parameterService.selectParameterByJobId(job.getId());
                if (params.size() != parameterList.size()) {
                    modelMap.addFlashAttribute("error", "作业参数与sql定义不否!");
                    return mav;
                }
                for (Parameter parameter : parameterList) {
                    if (Constants.GLOBAL.equals(parameter.getScope())) {
                        if ("".equals(parameter.getParameterRef().getDefaultVal()) || parameter.getParameterRef().getDefaultVal().length() <= 0) {
                            modelMap.addFlashAttribute("error", "全局参数必须赋初始值!");
                            return mav;
                        }
                    } else {
                        if (parameter.getParameterValue() == null) {
                            modelMap.addFlashAttribute("error", "本地参数必须赋初始值!");
                            return mav;
                        }
                    }
                }
            }
        }
        executorService.execute(user);
        return mav;
    }


    @RequestMapping(value = "/start/{layerId}", method = RequestMethod.GET)
    public ModelAndView start(@PathVariable(value = "layerId") int layerId, RedirectAttributesModelMap modelMap, HttpServletRequest request) throws ParseException, StandardException {
        ModelAndView mav = new ModelAndView("redirect:/layer/list");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }
        List<JobEntity> jobList = jobEntityService.selectJobEntityByLayer(layerId, true);
        for (JobEntity job : jobList) {
            Set<String> params = new HashSet<String>();
            SqlScriptUtil.getSqlParameter(params, job.getSqlScript());
            List<Parameter> parameterList = parameterService.selectParameterByJobId(job.getId());
            JobInfo jobInfo = new JobInfo();
            jobInfo.setJobId(job.getId());
            if(etlServer.getBlockingDeque().contains(jobInfo)){
                modelMap.addFlashAttribute("error","不能重复提交作业。");
                mav.setViewName("redirect:/layer/list");
                return mav;
            }
            if (params.size() != parameterList.size()) {
                modelMap.addFlashAttribute("error", "作业参数与sql定义不否!");
                mav.setViewName("redirect:/layer/list");
                return mav;
            }
            for (Parameter parameter : parameterList) {
                if (Constants.GLOBAL.equals(parameter.getScope())) {
                    if ("".equals(parameter.getParameterRef().getDefaultVal()) || parameter.getParameterRef().getDefaultVal().length() <= 0) {
                        modelMap.addFlashAttribute("error", "全局参数必须赋初始值!");
                        mav.setViewName("redirect:/layer/list");
                        return mav;
                    }
                } else {
                    if (parameter.getParameterValue() == null) {
                        modelMap.addFlashAttribute("error", "本地参数必须赋初始值!");
                        mav.setViewName("redirect:/layer/list");
                        return mav;
                    }
                }
            }
        }
        Layer layer = layerService.selectLayerById(layerId);
        executorService.execute(layer, user);
        mav.setViewName("redirect:/layer/list");
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(int jobId,
                            RedirectAttributesModelMap modelMap,
                            HttpServletRequest request,
                            @RequestParam(value = "page",required = true,defaultValue = "1")int page
    ) throws ParseException, StandardException {
        ModelAndView mav = new ModelAndView("redirect:/job/list?page="+page);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobId(jobId);
        if(etlServer.getBlockingDeque().contains(jobInfo)){
            modelMap.addFlashAttribute("error","不能重复提交作业。");
            mav.setViewName("redirect:/job/list?page="+page);
            return mav;
        }
        if (checkJobParameter(jobId)) {
            modelMap.addFlashAttribute("error", "作业参数校验失败，请检查作业参数");
            mav.setViewName("redirect:/job/list?page="+page);
            return mav;
        }
        JobEntity jobEntity = jobEntityService.selectJobEntityById(jobId);
        executorService.execute(jobEntity, user);
        return mav;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ModelAndView delete(HttpServletRequest request, int jobId) {
        ModelAndView mav = new ModelAndView("redirect:/queue/list");
        BlockingDeque<JobInfo> deque = etlServer.getBlockingDeque();
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobId(jobId);
        deque.remove(jobInfo);
        return mav;
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public ModelAndView stop() throws InterruptedException {
        ModelAndView mav = new ModelAndView("redirect:/main");
        etlServer.shutdownNow();
        return mav;
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView startEtlService() {
        ModelAndView mav = new ModelAndView("redirect:/main");
        etlServer.start();
        return mav;
    }

    private boolean checkJobParameter(int jobId) {
        JobEntity jobEntity = jobEntityService.selectJobEntityById(jobId);
        List<Parameter> jobParameterList = parameterService.selectParameterByJobId(jobId);
        Set<String> params = new HashSet<String>();
        SqlScriptUtil.getSqlParameter(params, jobEntity.getSqlScript());
        if (params.size() != jobParameterList.size()) {
            return true;
        }
        for (Parameter parameter : jobParameterList) {
            if (Constants.GLOBAL.equals(parameter.getScope())) {
                if ("".equals(parameter.getParameterRef().getDefaultVal()) || parameter.getParameterRef().getDefaultVal().length() <= 0) {
                    return true;
                }
            } else {
                if (parameter.getParameterValue() == null) {
                    return true;
                }
            }
        }
        return false;
    }

}
