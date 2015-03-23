package cn.com.cis.web.actions;

import cn.com.cis.domain.*;
import cn.com.cis.service.DataBaseService;
import cn.com.cis.service.JobEntityService;
import cn.com.cis.service.ParameterService;
import cn.com.cis.utils.SqlScriptUtil;
import cn.com.cis.web.bean.ParameterActionBean;
import cn.com.cis.web.bean.ParameterRefActionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/parameter")
public class ParameterController {

    @Autowired
    private ParameterService parameterService;
    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private JobEntityService jobEntityService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Parameter> list(int jobId) {
        return parameterService.selectParameterByJobId(jobId);
    }



    @RequestMapping(value = "/typeList", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<ParameterType> typeList() {
        return parameterService.selectAllParameterType();
    }

    @RequestMapping(value = "/save/{jobId}", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    String save(@PathVariable("jobId") final int jobId, @RequestBody List<ParameterActionBean> beanList) {
        List<Parameter> parameterList = new ArrayList<Parameter>();
        JobEntity job = jobEntityService.selectJobEntityById(jobId);
        String sql = job.getSqlScript();
        Set<String> paramNames = new HashSet<String>();
        SqlScriptUtil.getSqlParameter(paramNames, sql);
        Set<String> actionParamNames = new HashSet<String>();
        for (ParameterActionBean actionBean : beanList) {
            Parameter parameter = new Parameter();
            ParameterType parameterType = new ParameterType();
            if(actionBean.getScope().equals("GLOBAL")){
                parameter.setJobId(jobId);
                parameter.setParameterName(actionBean.getParameterRef());
                parameterType.setCode(actionBean.getParameterType());
                parameter.setParameterType(parameterType);
                parameter.setParameterRef(parameterService.selectParameterRefByName(actionBean.getParameterRef()));
                parameter.setScope(actionBean.getScope());
                actionParamNames.add(actionBean.getParameterRef());
                }else {
                if(null == actionBean.getParameterValue() || "".equals(actionBean.getParameterValue())){
                    return "LOCAL 类型参数必须赋值";
                }
                parameterType.setCode(actionBean.getParameterType());
                parameter.setJobId(jobId);
                parameter.setParameterRef(null);
                parameter.setParameterType(parameterType);
                parameter.setParameterName(actionBean.getParameterName());
                parameter.setParameterValue(actionBean.getParameterValue());
                parameter.setScope(actionBean.getScope());
                actionParamNames.add(actionBean.getParameterName());
            }
            parameterList.add(parameter);
        }
        if(paramNames.size() != actionParamNames.size() || paramNames.size() != parameterList.size()){
            return "参数个数与定义的sql脚本不一致,请检查！";
        }
        for(String s:actionParamNames){
            if(!paramNames.contains(s)){
                return "未找到名为："+s+" 的参数定义!";
            }
        }
        parameterService.insertParameterList(jobId,parameterList);
        return "参数保存成功!";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("parameter/add");
        mv.addObject("databaseList", dataBaseService.selectAllDatabase());
        mv.addObject("parameterTypeList", parameterService.selectAllParameterType());
        return mv;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(ParameterRefActionBean ref, RedirectAttributesModelMap modelMap) {
        ModelAndView mv = new ModelAndView();
        ParameterRef parameterRef;

        parameterRef = parameterService.selectParameterRefByName(ref.getRefName());
        if (parameterRef != null) {
            mv.setViewName("parameter/add");
            mv.addObject("databaseList", dataBaseService.selectAllDatabase());
            mv.addObject("parameterTypeList", parameterService.selectAllParameterType());
            mv.addObject("error", "已存在同名参数！");
            return mv;
        }
        if("".equals(ref.getDefaultVal()) || ref.getDefaultVal().length() <= 0){
            mv.setViewName("parameter/add");
            mv.addObject("databaseList", dataBaseService.selectAllDatabase());
            mv.addObject("parameterTypeList", parameterService.selectAllParameterType());
            mv.addObject("error", "全局参数必须指定缺省值！");
            return mv;
        }
        mv.setViewName("redirect:/parameter/glist");
        parameterRef = new ParameterRef();
        parameterRef.setRefName(ref.getRefName());
        Database database = dataBaseService.selectDatabaseById(ref.getDatabaseId());
        parameterRef.setDatabase(database);
        parameterRef.setDefaultVal(ref.getDefaultVal());
        parameterRef.setSqlScript(ref.getSqlScript());
        ParameterType parameterType = parameterService.selectParameterTypeById(ref.getReturnType());
        parameterRef.setReturnType(parameterType);
        parameterService.insertParameterRef(parameterRef);
        return mv;
    }

    @RequestMapping(value = "/glist", method = RequestMethod.GET)
    public ModelAndView glist() {
        ModelAndView mv = new ModelAndView("parameter/glist");
        mv.addObject("icon", "icon-list");
        mv.addObject("title", "全局参数");
        mv.addObject("desc", "参数维护");
        mv.addObject("parameterRefList", parameterService.selectAllParameterRef());
        return mv;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public ModelAndView edit(int id){
        ModelAndView mv = new ModelAndView();
        ParameterRef parameterRef = parameterService.selectParameterRefById(id);
        mv.addObject("databaseList", dataBaseService.selectAllDatabase());
        mv.addObject("parameterTypeList", parameterService.selectAllParameterType());
        mv.addObject("parameterRef",parameterRef);
        mv.setViewName("parameter/edit");
        return mv;
    }



    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(ParameterRefActionBean ref, RedirectAttributesModelMap modelMap) {
        ModelAndView mv = new ModelAndView();
        ParameterRef parameterRef;
        parameterRef = parameterService.selectParameterRefById(ref.getRefId());
        mv.setViewName("redirect:/parameter/glist");
        Database database = dataBaseService.selectDatabaseById(ref.getDatabaseId());
        parameterRef.setDatabase(database);
        parameterRef.setDefaultVal(ref.getDefaultVal());
        parameterRef.setSqlScript(ref.getSqlScript());
        ParameterType parameterType = parameterService.selectParameterTypeById(ref.getReturnType());
        parameterRef.setReturnType(parameterType);
        parameterService.updateParameterRef(parameterRef);
        return mv;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ModelAndView del(int id,RedirectAttributesModelMap modelMap) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/parameter/glist");
        List <Parameter> parameters = parameterService.selectParameterByRefId(id);
        if(parameters.isEmpty()){
            parameterService.deleteParameterRef(id);
        }else {
            modelMap.addFlashAttribute("error","该全局参数已经被作业引用，不能删除!");
        }

        return mv;
    }
    @RequestMapping(value = "/paramRefAjax", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<ParameterRef> getParamRefList() {
        return parameterService.selectAllParameterRef();
    }

    @RequestMapping(value = "/paramRefByNameAjax", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ParameterRef getParamRefByName(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }
        return parameterService.selectParameterRefByName(name);

    }

    @RequestMapping(value = "/UpdateParamByIDAjax", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String UpdateParamByIDAjax(int id,String defaultVal) {
        ParameterRef parameterRef;
        parameterRef = parameterService.selectParameterRefById(id);
        parameterRef.setDefaultVal(defaultVal);
        int updaterownum = parameterService.UpdateParamByIDAjax(parameterRef);
        return updaterownum+"";
    }

    @RequestMapping(value = "/UpdateParamCheckDateAjax", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String UpdateParamCheckDateAjax(String enddate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date nowSysDate = new Date();
            df.format(nowSysDate);

            Date defaultenddate = null;
            defaultenddate = df.parse(enddate);

            if(defaultenddate.getTime() > nowSysDate.getTime())
            {
                return "true";
            }
            else
            {
                return "false";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "false";
        }

    }
}
