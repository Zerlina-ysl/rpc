package com.lsy.service.user.impl;

import com.lsy.CustomerService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 */
public class CustomerServiceImpl extends UnicastRemoteObject implements CustomerService, Remote {

    public CustomerServiceImpl() throws RemoteException {
        super();
    }

    public String getCustomer(String name) throws RemoteException {
        return "查询客户："+name;
    }

    public int addCustomer(String name)throws RemoteException {

        System.out.println("增加用户："+name);
        return 1;
    }
}
