package com.lsy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 * 定义服务接口
 */
public interface UserService extends Remote {
    /**
     * 根据用户名查询用户，返回json格式的字符串，用于描述用户对象
     * @param name
     * @return
     * @throws RemoteException
     */
    String getUser(String name) throws RemoteException;
}
