package cn.com.cis.web.interceptor;

import cn.com.cis.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityInterceptor implements HandlerInterceptor {

    private static final String LOGIN_URL = "/login";
    private static final String MAIN_URL = "/main";
    private static final String ADMIN = "1";
    private static final String USER = "2";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        HttpSession session = req.getSession(true);
        // 从session 里面获取用户名的信息

        Object obj = session.getAttribute("user");
        // 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆
        if (obj == null || "".equals(obj.toString())) {
            res.sendRedirect(LOGIN_URL);
        } else {
            User user = (User) obj;
            return isAdmin(user) || isView(req);
        }
        return true;
    }

    private boolean isAdmin(User user) {
        return ADMIN.equals(user.getRoleType());
    }

    private boolean isView(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/main")
                || uri.startsWith("/logout")
                || uri.startsWith("/parameter/typeList")
                || uri.startsWith("/job/ajaxlistMain")
                || uri.startsWith("/queue/MainAjaxlist")
                || uri.startsWith("/log/ajaxlist")
                || uri.startsWith("/queue/start")
                || uri.startsWith("/queue/stop")
                || uri.startsWith("/queue/executeAll")
                || uri.startsWith("/user/editpwd")
                || uri.startsWith("/user/edit")
                || uri.startsWith("/user/checkoldpassword");

    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {
    }


}
