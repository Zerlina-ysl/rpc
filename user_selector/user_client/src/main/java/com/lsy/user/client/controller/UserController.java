package com.lsy.user.client.controller;

import com.lsy.pojo.User;
import com.lsy.rpc.RpcFactory;
import com.lsy.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 */
@Controller
public class UserController {


    public UserService uservice;

    public UserController() {
        try {
            this.uservice= RpcFactory.getServiceProxy(UserService.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务"+UserService.class.getName()+"不存在");
        }

    }


    @ResponseBody
    @RequestMapping(value="/getUserByName")
    public List<User> getUserByName(String name)
    {
        try {
            List<User> users = uservice.getUserByName(name);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }


}
