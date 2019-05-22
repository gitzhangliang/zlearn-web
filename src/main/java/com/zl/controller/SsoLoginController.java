package com.zl.controller;


import com.google.gson.Gson;
import com.zl.model.sso.SSOCallBack;
import com.zl.utils.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tzxx
 * @date 2019/3/6.
 */
@Controller
@RequestMapping("/sso")
public class SsoLoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/index")
    public String login(HttpServletRequest request){
        String sid = getCookie(request,"did");
        if(StringUtils.isNotBlank(sid)){
            return "success";
        }
        return "login";
    }




    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if("18612689497".equals(username) && "111111".equals(password)){
            Cookie cookie = new Cookie("did", "123");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "success";
        }else {
            Map<String, Object> uriVariables = new HashMap<String, Object>();
            uriVariables.put("mobile",username);
            uriVariables.put("password", password);
            boolean exist = restTemplate.getForObject("http://127.0.0.1:7083/exist",Boolean.class,uriVariables);
            if(exist){
                setCookie(response);
                return "success";
            }

        }
        return "error";
    }


    @RequestMapping("/isLogin")
    public String isLogin(HttpServletRequest request,@RequestParam("url") String url) throws IOException {
        String sid = getCookie(request,"did");
        boolean isLogin = false;
        String mobile ="";
        Long time = System.currentTimeMillis();
        if(sid != null && !"".equals(sid)){
            isLogin =true;
            mobile = "18612689497";
        }
        String content = "time="+time+"&mobile="+mobile;
        Map<String,Object> map = new HashMap<>(16);
        map.put("isLogin",isLogin);
        map.put("mobile",mobile);
        map.put("content",content);
        map.put("sign", RSAUtil.sign(content));
        map.put("time",time);
        String token = RSAUtil.urlEncoder(RSAUtil.encryptBASE64(new Gson().toJson(map).getBytes()).replaceAll("\r\n",""));
        Map<String, Object> map1 = new HashMap<>();
        map1.put("token",token);
        return "redirect:"+url+"?token="+token;

    }


    @RequestMapping("/callBack")
    public String ssoCallBack(HttpServletRequest request,HttpServletResponse response,@RequestParam("token") String token){
        if(token != null && !"".equals(token)){
            SSOCallBack callBack = new Gson().fromJson(new String(RSAUtil.decryptBASE64(RSAUtil.urlDecode(token))), SSOCallBack.class);
            if(System.currentTimeMillis() - callBack.getTime()<3*60*1000){
                if(RSAUtil.verify(callBack.getContent(),callBack.getSign())){
                    if(callBack.isLogin()){
                        Cookie cookie = new Cookie("did", "123");
                        cookie.setMaxAge(7 * 24 * 60 * 60);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        return "success";
                    }
                }
            }
        }
        return "login";
    }

    @RequestMapping("/exit")
    public String logout(HttpServletResponse response, HttpServletRequest request){
        deleteCookie(response,request);
        return "login";
    }

    /**
     * 培训系统退出时同时退出青距
     * @param request
     * @param response
     * @param url 培训系统退出url
     * @return
     */
    @RequestMapping("/logout")
    public String ssoLogout(HttpServletRequest request, HttpServletResponse response,@RequestParam("url") String url) {
        deleteCookie(response,request);
        return "redirect:"+url;
    }

    /**
     * 培训系统询问是否存在某个用户
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/exist")
    @ResponseBody
    public boolean exist(HttpServletResponse response, HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");
        if("18612689495".equals(mobile) && "111111".equals(password)){
            return true;
        }
        return false;
    }

    private void deleteCookie(HttpServletResponse response, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(int i = 0; i < cookies.length; ++i) {
                if("did".equalsIgnoreCase(cookies[i].getName())) {
                    Cookie ck = new Cookie(cookies[i].getName(), (String)null);
                    ck.setPath("/");
                    ck.setMaxAge(0);
                    response.addCookie(ck);

                }
            }
        }
    }

    public String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        String resValue = "";
        if(cookies != null && cookies.length > 0) {
            for(int i = 0; i < cookies.length; ++i) {
                if(key.equalsIgnoreCase(cookies[i].getName()) && StringUtils.isNotEmpty(cookies[i].getValue())) {
                    resValue = cookies[i].getValue();
                }
            }
        }
        return resValue;
    }

    public void setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("did", "123");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void main(String[] args) {
        String token = "eyJpc0xvZ2luIjoiZmFsc2UiLCJtb2JpbGUiOiIiLCJzaWduIjoicW9WRExzWjgzSzArdVk5TFNYM3lxU1VaNlA3UUR5R1hXNUwvUUhkVnpCVjdnRCtWaS9WV1g4OWx1QXFnekhoZGZBaXFHdkVKam5xZwowbFBhNk5kOVg2WFZWMWM0TGsrakVOdmg5VlovMlg2a0pMdTVqdkpuUTVOU3FsdStmT0dIekZVM1ZJY2VHdFphc3I1YmVDM0MrQTNCCjJOeDE1SElpczJORlBHOTkrd1E9CiIsInRpbWUiOiIxNTU4NDAyOTE0NDI5IiwiY29udGVudCI6InRpbWU9MTU1ODQwMjkxNDQyOSZtb2JpbGU9In0=";
        SSOCallBack callBack = new Gson().fromJson(new String(RSAUtil.decryptBASE64(RSAUtil.urlDecode(token))), SSOCallBack.class);
        if(RSAUtil.verify(callBack.getContent(),callBack.getSign())){
            if(callBack.isLogin()){
                System.out.println("success");
            }
        }
    }
}
