package com.lsy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 */
public interface CustomerService extends Remote {

    public String getCustomer(String name) throws RemoteException;

    public int  addCustomer(String name) throws  RemoteException;
}
