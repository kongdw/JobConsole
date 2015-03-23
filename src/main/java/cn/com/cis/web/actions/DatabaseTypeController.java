package cn.com.cis.web.actions;

import cn.com.cis.domain.DatabaseType;
import cn.com.cis.service.DataBaseService;
import cn.com.cis.service.JobEntityService;
import cn.com.cis.web.bean.DatabaseTypeActionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

@Controller
@RequestMapping(value = "/databasetype")
public class DatabaseTypeController {

    @Autowired
    private DataBaseService databaseService;
    @Autowired
    private JobEntityService jobEntityService;

    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("databasetype/list");
        List<DatabaseType> databastypeList = databaseService.selectAllDatabaseType();
        mav.addObject("databasetypeList", databastypeList);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addView() {
        ModelAndView mav = new ModelAndView("databasetype/add");
        //List<DatabaseType> databaseTypeList = databaseService.selectAllDatabaseType();
        //mav.addObject("databaseTypeList", databaseTypeList);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView save(DatabaseTypeActionBean bean, RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/databasetype/list");
        DatabaseType d = databaseService.selectDatabaseTypeById(bean.getType());
        if (d != null) {
            mav.setViewName("redirect:/databasetype/add");
            modelMap.addFlashAttribute("error", "已存在名称为" + bean.getType() + "的数据源类型.");
            return mav;
        }
        DatabaseType databaseType = new DatabaseType();
        databaseType.setType(bean.getType());
        databaseType.setDescription(bean.getDescription());
        databaseType.setUrl(bean.getUrl());
        databaseType.setDriver(bean.getDriver());
        databaseService.insertDatabaseType(databaseType);
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editView(String type) {
        ModelAndView mav = new ModelAndView("databasetype/edit");

        DatabaseType databasetype = databaseService.selectDatabaseTypeById(type);

        mav.addObject("databasetype", databasetype);
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView update(DatabaseTypeActionBean bean, RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/databasetype/list");
//        if(bean.getOldtype() != bean.getType()) {
//            DatabaseType d = databaseService.selectDatabaseTypeById(bean.getType());
//            if (d != null) {
//                mav.setViewName("redirect:/databasetype/edit?type=" + bean.getOldtype());
//                modelMap.addFlashAttribute("error", "已存在名称为" + bean.getType() + "的数据源类型.");
//                return mav;
//            }
//        }
        DatabaseType databaseType = new DatabaseType();
        databaseType.setType(bean.getType());
        databaseType.setDescription(bean.getDescription());
        databaseType.setUrl(bean.getUrl());
        databaseType.setDriver(bean.getDriver());

        databaseService.updateDatabaseType(databaseType);
        return mav;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ModelAndView del(String type, RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/databasetype/list");
        int c = databaseService.selectDatabaseByType(type);
        if (c > 0) {
            modelMap.addFlashAttribute("error", "数据源类型已被引用，不能删除。");
        } else {
            databaseService.deleteDatabaseType(type);
        }
        return mav;
    }

}
