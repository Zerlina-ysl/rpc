package com.lsy.service.user.service;

import com.lsy.pojo.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 */
public interface UserService  extends Remote {


    List<User> getUserByName(String name) throws RemoteException;



}
