package com.lsy.rmi.impl;

import com.lsy.rmi.api.FirstInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 *  实现远程服务接口
 *  所有远程服务实现必须是Remote接口直接或间接实现类
 *  如果不会创建基于RMI的服务标准实现，可以继承UnicastRemoteObject类型
 * UnicastRemoteObject是sun公司定义的Remote接口方法
 *  所有方法必须抛出RMIException 包括构造方法
 */


public class FirstRMIImpl extends UnicastRemoteObject implements FirstInterface, Remote {

    public FirstRMIImpl() throws RemoteException {
        super();
    }

    /**
     * 服务实现
     * @param name
     * @return
     * @throws RemoteException
     */
    public String first(String name) throws RemoteException {
        System.out.println("客户端请求参数是"+name);
        return "你好，"+name;
    }
}
