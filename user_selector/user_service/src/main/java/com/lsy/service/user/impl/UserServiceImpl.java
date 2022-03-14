package com.lsy.service.user.impl;

import com.lsy.pojo.User;
import com.lsy.rpc.RpcFactory;
import com.lsy.service.user.mapper.UserMapper;
import com.lsy.service.user.service.UserService;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 * 服务对象需要spring管理 现在缺少注册过程
 * 调用自定义框架把当前对象和实现的接口信息通过RPcfatory注册到zk中
 *
 * 如何创建spring容器管理的bean对象？
 * 调用构造方法创建
 */
@Service
public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    public UserServiceImpl() throws RemoteException {
//        super();super
        System.out.println("UserServiceImpl对象创建...");
        try {
            RpcFactory.registerService(UserService.class,this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

        System.out.println("注册服务：UserServiceImpl..");
    }

    @Autowired
    private UserMapper umapper;
    @Override
    public List<User> getUserByName(String name) throws RemoteException {
        return umapper.getUser(name);
    }
}
