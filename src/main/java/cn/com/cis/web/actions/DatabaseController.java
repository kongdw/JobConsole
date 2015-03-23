package cn.com.cis.web.actions;

import cn.com.cis.domain.Database;
import cn.com.cis.domain.DatabaseType;
import cn.com.cis.service.DataBaseService;
import cn.com.cis.service.JobEntityService;
import cn.com.cis.web.bean.DatabaseActionBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

@Controller
@RequestMapping(value = "/database")
public class DatabaseController {

    @Autowired
    private DataBaseService databaseService;
    @Autowired
    private JobEntityService jobEntityService;

    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("database/list");
        List<Database> databaseList = databaseService.selectAllDatabase();
        mav.addObject("databaseList", databaseList);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addView() {
        ModelAndView mav = new ModelAndView("database/add");
        List<DatabaseType> databaseTypeList = databaseService.selectAllDatabaseType();
        mav.addObject("databaseTypeList", databaseTypeList);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView save(DatabaseActionBean bean, RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/database/list");
        Database d = databaseService.selectDatabaseByName(bean.getName());
        if (d != null) {
            mav.setViewName("redirect:/database/add");
            modelMap.addFlashAttribute("error", "已存在名称为" + bean.getName() + "的数据源.");
            return mav;
        }
        Database database = new Database();
        DatabaseType databaseType = databaseService.selectDatabaseTypeById(bean.getType());
        BeanUtils.copyProperties(bean, database);
        database.setDatabaseType(databaseType);
        databaseService.insertDatabase(database);
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editView(int id) {
        ModelAndView mav = new ModelAndView("database/edit");
        Database database = databaseService.selectDatabaseById(id);
        List<DatabaseType> databaseTypeList = databaseService.selectAllDatabaseType();
        mav.addObject("databaseTypeList", databaseTypeList);
        mav.addObject("database", database);
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView update(DatabaseActionBean bean,RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/database/list");
//        Database d=databaseService.selectDatabaseByName(bean.getName());
//        if (d != null) {mav.setViewName("redirect:/database/edit?id="+bean.getId());
//           modelMap.addFlashAttribute("error", "已存在名称为" + bean.getName() + "的数据源.");
//            modelMap.addFlashAttribute(bean);
//           return mav;
//         }
        Database database = databaseService.selectDatabaseById(bean.getId());
        DatabaseType databaseType = databaseService.selectDatabaseTypeById(bean.getType());
        BeanUtils.copyProperties(bean, database);
        database.setDatabaseType(databaseType);
        databaseService.updateDatabase(database);
        return mav;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ModelAndView del(int id, RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/database/list");
        int c = jobEntityService.selectAllJobByDatabaseId(id);
        if (c > 0) {
            modelMap.addFlashAttribute("error", "数据源已被引用，不能删除。");
        } else {
            databaseService.deleteDatabase(id);
        }
        return mav;
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String test(int id) {
        boolean result = databaseService.testDatabase(id);
        if (result) {
            return "[{\"flag\":\"success\"}]";
        } else
            return "[{\"flag\":\"error\"}]";
    }

}
