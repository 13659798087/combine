package lzgene.newscreening.util;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class SSO {


    public static String getSuperRole() {
        return "超级管理员";
    }

    public static String getGeneralManager() {
        return "产前中心管理员";
    }

    /**
     * 公用的方法，获取缓存session属性admin的值
     * @return
     */
    public static String getLoginRole() {
        //得到request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();

        return (String)request.getSession().getAttribute("admin");
    }


}
