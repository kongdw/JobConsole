package cn.com.cis.web.actions;

import cn.com.cis.domain.*;
import cn.com.cis.enums.ETLMode;
import cn.com.cis.plugins.mybatis.PageInfo;
import cn.com.cis.service.*;
import cn.com.cis.utils.SqlScriptUtil;
import cn.com.cis.web.bean.JobActionBean;
import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/job")
public class JobController {

    private static final String pageList = "redirect:/job/list?page=1";
    @Autowired
    private LayerService layerService;

    @Autowired
    private ModeService modeService;

    @Autowired
    private DataBaseService databaseService;

    @Autowired
    private JobEntityService jobEntityService;
    @Autowired
    private ParameterService parameterService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        ModelAndView mv = new ModelAndView("job/list");
        List<JobEntity> list = jobEntityService.selectAllJobEntity(page, pageSize, false);
        PageInfo pageInfo = new PageInfo(list, pageSize);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("list", list);
        mv.addObject("icon", "icon-list");
        mv.addObject("title", "作业列表");
        mv.addObject("desc", "作业维护、参数维护、作业执行");
        mv.addObject("page",page);
        return mv;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editView(@RequestParam(value = "jobId", required = true) int jobId,
                                 @RequestParam(value = "sqlType", required = true) String sqlType,
                                 @RequestParam(value = "page",required = true,defaultValue = "1")int page) {
        ModelAndView mav = new ModelAndView("job/edit");
        JobEntity job = jobEntityService.selectJobEntityById(jobId);
        List<Database> databaseList = databaseService.selectAllDatabase();
        List<Layer> layerList = layerService.selectAllLayer();
        List<Mode> modeList = modeService.selectAllMode();
        mav.addObject("job", job);
        mav.addObject("databaseList", databaseList);
        mav.addObject("layerList", layerList);
        mav.addObject("modeList", modeList);
        mav.addObject("sqlType", sqlType);
        mav.addObject("page",page);
        return mav;
    }

    @ModelAttribute("jobActionBean")
    public JobActionBean createFormBean() {
        return new JobActionBean();
    }

    @RequestMapping(value = "/update")
    public ModelAndView update(JobActionBean bean, RedirectAttributesModelMap modelMap) {
        ModelAndView mv = new ModelAndView("redirect:/job/edit?jobId=" + bean.getJobId() + "&sqlType=" + bean.getSqlType());
        JobEntity jobEntity = jobEntityService.selectJobEntityById(bean.getJobId());
        //1 名称是否存在
        if (isExist(bean.getJobName())) {
            if (jobEntity.getId() != bean.getJobId()) {
                modelMap.addFlashAttribute("bean", bean);
                modelMap.addFlashAttribute("error", "作业" + bean.getJobName() + "已存在，请更换其他名称.");
                return mv;
            }
        }
        //2 顺序号是否存在
        if (vldSequence(bean.getLayerId(), bean.getSequence())) {
            JobEntity job1 = jobEntityService.selectJobEntityByLayerAndSequence(bean.getLayerId(), bean.getSequence());
            if (job1.getId() != bean.getJobId()) {
                modelMap.addFlashAttribute("bean", bean);
                modelMap.addFlashAttribute("error", "该层级已经存在顺序号为" + bean.getSequence() + "的作业");
                return mv;
            }
        }
        //3 脚本是否合法
        if (!"c".equals(bean.getSqlType())) {
            if (!vldSqlScript(bean.getSqlScript(), bean.getSqlType())) {
                modelMap.addFlashAttribute("bean", bean);
                modelMap.addFlashAttribute("error", "SQL脚本不正确或与作业类型不一致，请检查");
                return mv;
            }
        }
        //4 参数完整
        if (!vldEtlMode(ETLMode.valueOf(bean.getModeCode()), bean.getSqlScript())) {
            modelMap.addFlashAttribute("bean", bean);
            modelMap.addFlashAttribute("error", "抽取模式与Sql脚本不符，如：时间段并发任务必须定义BEGIN_DATE与END_DATE参数.");
            return mv;
        }
        jobEntity.setLayer(layerService.selectLayerById(bean.getLayerId()));
        jobEntity.setMode(modeService.selectModeById(bean.getModeCode()));
        jobEntity.setSourceDatabase(databaseService.selectDatabaseById(bean.getSourceDatabaseId()));
        jobEntity.setTargetDatabase(databaseService.selectDatabaseById(bean.getTargetDatabaseId()));
        jobEntity.setSequence(bean.getSequence());
        jobEntity.setSqlScript(bean.getSqlScript());

        jobEntity.setName(bean.getJobName());
        jobEntity.setTargetTable(bean.getTargetTable());
        jobEntity.setDescription(bean.getDescription());
        jobEntity.setSplitValue(bean.getSplitValue());
        jobEntity.setTruncateFlag(bean.isTruncateFlag());
        // 更新脚本以后自动重置为禁用状态。
        jobEntity.setEnabled(false);
        jobEntityService.updateJobEntity(jobEntity);
        mv.setViewName("redirect:/job/list");
        return mv;
    }

    @RequestMapping(value = "/enable")
    public ModelAndView enable(@RequestParam(required = true) int jobId,
                               @RequestParam(required = false, defaultValue = "1") int page,
                               RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/job/list?page="+page);
        JobEntity jobEntity = jobEntityService.selectJobEntityById(jobId);
        // 未启动状态
        if (!jobEntity.isEnabled()) {
            if (vldParam(jobId, jobEntity.getSqlScript())) {
                jobEntityService.enableJobEntity(jobId);
            } else {
                modelMap.addFlashAttribute("error", "参数不完整");
            }
        } else {
            jobEntityService.disableJobEntity(jobId);

        }
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addView(String sqlType) {
        ModelAndView mav = new ModelAndView("job/add");
        List<Database> databaseList = databaseService.selectAllDatabase();
        List<Layer> layerList = layerService.selectAllLayer();
        List<Mode> modeList = modeService.selectAllMode();
        mav.addObject("databaseList", databaseList);
        mav.addObject("layerList", layerList);
        mav.addObject("modeList", modeList);
        mav.addObject("sqlType", sqlType);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(JobActionBean bean, RedirectAttributesModelMap modelMap) {
        ModelAndView mv = new ModelAndView(pageList);

        //1 名称是否存在

        if (isExist(bean.getJobName())) {
            modelMap.addFlashAttribute("bean", bean);
            modelMap.addFlashAttribute("error", "作业" + bean.getJobName() + "已存在，请更换其他名称.");
            mv.setViewName("redirect:/job/add?sqlType=" + bean.getSqlType());
            return mv;
        }
        //2 顺序号是否存在
        if (vldSequence(bean.getLayerId(), bean.getSequence())) {
            modelMap.addFlashAttribute("bean", bean);
            modelMap.addFlashAttribute("error", "该层级已经存在顺序号为" + bean.getSequence() + "的作业");
            mv.setViewName("redirect:/job/add?sqlType=" + bean.getSqlType());
            return mv;
        }
        //3 脚本是否合法
        if (!"c".equals(bean.getSqlType())) {
            if (!vldSqlScript(bean.getSqlScript(), bean.getSqlType())) {
                modelMap.addFlashAttribute("bean", bean);
                modelMap.addFlashAttribute("error", "SQL脚本不正确或与作业类型不一致，请检查");
                mv.setViewName("redirect:/job/add?sqlType=" + bean.getSqlType());
                return mv;
            }
        }
        //4 参数完整
        if (!vldEtlMode(ETLMode.valueOf(bean.getModeCode()), bean.getSqlScript())) {
            modelMap.addFlashAttribute("bean", bean);
            modelMap.addFlashAttribute("error", "抽取模式与Sql脚本不符，如：时间段并发任务必须定义BEGIN_DATE与END_DATE参数.");
            mv.setViewName("redirect:/job/add?sqlType=" + bean.getSqlType());
            return mv;
        }

        JobEntity jobEntity = new JobEntity();
        jobEntity.setLayer(layerService.selectLayerById(bean.getLayerId()));
        jobEntity.setMode(modeService.selectModeById(bean.getModeCode()));
        jobEntity.setSourceDatabase(databaseService.selectDatabaseById(bean.getSourceDatabaseId()));
        jobEntity.setTargetDatabase(databaseService.selectDatabaseById(bean.getTargetDatabaseId()));
        jobEntity.setSequence(bean.getSequence());
        jobEntity.setSqlScript(bean.getSqlScript());
        jobEntity.setName(bean.getJobName());
        jobEntity.setTargetTable(bean.getTargetTable());
        jobEntity.setDescription(bean.getDescription());
        jobEntity.setSplitValue(bean.getSplitValue());
        jobEntity.setTruncateFlag(bean.isTruncateFlag());
        jobEntity.setSqlType(bean.getSqlType());
        jobEntityService.insertJobEntity(jobEntity);
        return new ModelAndView(pageList);
    }

    @RequestMapping(value = "/del")
    public ModelAndView del(int jobId,
                            @RequestParam(value = "page",required = true,defaultValue = "1")int page,
                            RedirectAttributesModelMap modelMap) {
        ModelAndView mv = new ModelAndView("redirect:/job/list?page="+page);
        jobEntityService.deleteJobEntity(jobId);
        modelMap.addFlashAttribute("msg", "删除成功!");
        return mv;
    }

    // 名称是否存在
    private boolean isExist(String jobName) {
        JobEntity jobEntity = jobEntityService.selectJobEntityByJobName(jobName);
        return jobEntity != null;
    }

    private boolean vldSequence(int layerId, int sequence) {
        JobEntity jobEntity = jobEntityService.selectJobEntityByLayerAndSequence(layerId, sequence);
        return jobEntity != null;
    }

    private boolean vldSqlScript(String sqlScript, String type) {
        SQLParser sqlParser = new SQLParser();
        String sql = SqlScriptUtil.replaceSql(sqlScript);
        try {
            StatementNode statementNode = sqlParser.parseStatement(sql);
            if (vldStatementType(statementNode, getStatementType(type))) {
                return true;
            }
        } catch (StandardException e) {
            return false;
        }
        return false;
    }

    private boolean vldStatementType(StatementNode statementNode, String type) {
        String cmd = statementNode != null ? statementNode.statementToString() : "";
        return cmd.equals(type);
    }

    private String getStatementType(String type) {
        if (type.equals("i")) {
            return "SELECT";
        } else if (type.equals("u")) {
            return "UPDATE";
        } else if (type.equals("d")) {
            return "DELETE";
        } else {
            return "";
        }
    }

    /**
     * 验证sql脚本参数与数据库参数是否正确
     *
     * @param jobId     作业id
     * @param sqlScript SQL
     * @return 通过返回true，失败返回false
     */
    private boolean vldParam(int jobId, String sqlScript) {
        List<Parameter> dbParams = parameterService.selectParameterByJobId(jobId);
        List<String> sqlParams = SqlScriptUtil.getSqlParameter(sqlScript);
        if (dbParams.size() != sqlParams.size()) {
            return false;
        }
        for (String s : sqlParams) {
            for (Parameter parameter : dbParams) {
                if (s.equals(parameter.getParameterName())) {
                    break;
                }

            }
        }
        return true;
    }

    private boolean vldEtlMode(ETLMode etlMode, String sqlScript) {
        Set<String> params = new HashSet<String>();
        SqlScriptUtil.getSqlParameter(params, sqlScript);

        switch (etlMode) {
            case TIME_PERIOD:
                boolean beginB = params.contains("BEGIN_DATE");
                boolean endB = params.contains("END_DATE");
                return beginB && endB;
            case NORMAL:
                return true;
            default:
                return false;
        }
    }

    @RequestMapping(value = "/ajaxlistMain", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo ajaxlistMain(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                 @RequestParam(value = "pageSize", defaultValue = "7", required = false) int pageSize) {
        List<JobEntity> list = jobEntityService.selectAllJobEntity(page, pageSize, true);
        PageInfo pageInfo = new PageInfo(list, pageSize);
        return pageInfo;
    }

    @RequestMapping(value = "/getjobparams", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<String> getjobparams(int jobId) {
        //根据jobid获取sql脚本
        JobEntity job = jobEntityService.selectJobEntityById(jobId);
        String SqlScript = job.getSqlScript();
        List<String> resultlist = new ArrayList<String>();
        if (SqlScript != null && SqlScript != "") {
            resultlist = SqlScriptUtil.getSqlParameter(SqlScript);
        }
        return resultlist;
    }
}
