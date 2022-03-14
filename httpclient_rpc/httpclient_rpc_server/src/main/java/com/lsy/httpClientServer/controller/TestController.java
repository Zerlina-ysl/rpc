package com.lsy.httpClientServer.controller;



import com.lsy.httpclient.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/12
 */
@Controller
public class TestController {


    //使用请求体传递请求参数
    @ResponseBody
    @RequestMapping(value="/bodyParams",produces = {"appliation/json;charset=UTF-8"})
    @CrossOrigin
    public String bodyParams(@RequestBody List<User> user){
        System.out.println(user);
        return user.toString();
    }


    @RequestMapping(value="/test",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String test(){
        return "{\"msg\":\"处理返回\"}";
    }


    @ResponseBody
    @RequestMapping(value="/params",produces = {"application/json;charset=UTF-8"})
    public String params(String name,String password){
        System.out.println("name-"+name+"password-"+password);
        return "{msg:登录成功,user:{name:"+name+",password:"+password+"}}";
    }

}
