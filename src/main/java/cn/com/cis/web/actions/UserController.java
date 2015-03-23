package cn.com.cis.web.actions;

import cn.com.cis.domain.User;
import cn.com.cis.plugins.mybatis.PageInfo;
import cn.com.cis.service.UserService;
import cn.com.cis.web.bean.UserActionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(String username, String password, HttpServletRequest httpServletRequest, RedirectAttributesModelMap modelMap) {
        ModelAndView mv = new ModelAndView();
        if (this.checkParams(new String[]{username, password})) {
            User user = userService.selectUserByUsernameAndPassword(username, password);
            if (user != null) {
                mv.setViewName("redirect:/main");
                HttpSession session = httpServletRequest.getSession();
                session.setAttribute("user", user);
                modelMap.addFlashAttribute("icon", "icon-home");
                modelMap.addFlashAttribute("title", "主页");
                modelMap.addFlashAttribute("desc", "服务器状态、服务器日志、服务器负载");
            } else {
                mv.setViewName("login");
                mv.addObject("error","用户名或密码错误！");
            }
        } else {
            mv.setViewName("login");
            mv.addObject("error","用户名与密码不能为空！");
        }
        return mv;
    }



    private boolean checkParams(String[] params) {
        for (String param : params) {
            if (param == "" || param == null || param.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ModelAndView login() {
        return new ModelAndView("login");
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
        return new ModelAndView("redirect:/login");
    }


    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public ModelAndView addView() {

        ModelAndView mav = new ModelAndView("/user/add");
        return mav;
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ModelAndView save(UserActionBean userbean) {
        ModelAndView mav = new ModelAndView("redirect:/user/list/1");
        User user = new User();
        user.setUsername(userbean.getUsername());
        user.setPassword(userbean.getPassword());
        user.setEmail(userbean.getEmail());
        user.setPhone(userbean.getPhone());
        user.setRoleType(userbean.getRoleType());
        user.setEnabled(userbean.isEnabled());
        userService.insertUser(user);
        return mav;
    }

    @RequestMapping(value = "/user/list/{page}", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("page") int page) {
        ModelAndView mav = new ModelAndView("user/list");
        List<User> userList = userService.selectAllUser(page);
        PageInfo pageInfo = new PageInfo(userList,10);
        mav.addObject("pageInfo",pageInfo);
        mav.addObject("userList", userList);
        return mav;
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView editView(int id) {
        ModelAndView mav = new ModelAndView("user/edit");
        User user = userService.selectUserById(id);
        mav.addObject("user", user);
        return mav;
    }





    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public ModelAndView update(UserActionBean userbean) {

        ModelAndView mav ;
        User user = userService.selectUserById(userbean.getId());
        if(user.getRoleType() == "1")
        {
            user.setPassword(userbean.getPassword());
            user.setEmail(userbean.getEmail());
            user.setPhone(userbean.getPhone());
            user.setRoleType(userbean.getRoleType());
            user.setEnabled(userbean.isEnabled());
            mav= new ModelAndView("redirect:/user/list/1");
        }
        else
        {
            user.setEmail(userbean.getEmail());
            user.setPhone(userbean.getPhone());
            mav= new ModelAndView("redirect:/main");
        }
        userService.updateUser(user);
        return mav;
    }

    @RequestMapping(value = "/user/editpwd", method = RequestMethod.GET)
    public ModelAndView editpwdView(int id) {
        ModelAndView mav = new ModelAndView("/user/editpwd");
        return mav;
    }

    @RequestMapping(value = "/user/editpwd", method = RequestMethod.POST)
    public ModelAndView updatepwd(UserActionBean userbean) {
        ModelAndView mav = new ModelAndView("redirect:/main");
        User user = userService.selectUserById(userbean.getId());
        user.setPassword(userbean.getPassword());
        userService.updateUser(user);
        return mav;
    }


    @RequestMapping(value = "/user/del", method = RequestMethod.GET)
    public ModelAndView del(int id) {

        ModelAndView mav = new ModelAndView("redirect:/user/list/1");
        userService.deleteUser(id);
        return mav;
    }

    @RequestMapping(value = "/user/checkoldpassword", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String checkoldpassword(int id,String oldpassword) {
        User user = userService.selectUserById(id);
        return (user.getPassword().equals(oldpassword))+"";
    }

}
