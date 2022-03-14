package com.lsy.service.user.impl;

import com.lsy.UserService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 *  用户服务实现类
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService,Remote {




    public UserServiceImpl() throws RemoteException {
        super();

    }

    public String getUser(String name) throws RemoteException {
        System.out.println("要查询的用户是："+name);
        return "{\"name\":\""+name+"\",\"age\":20,\"gender\",\"男\"}";

    }
}
