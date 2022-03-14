package com.lsy.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 * 远程服务接口
 * Remote是一个标记接口，没有任何方法
 * 用于标识其方法可以从非本地虚拟机上调用的接口。任何远程对象都必须直接或间接实现此接口
 * 只有在远程接口（扩展java.rmi.Remote的接口）中指定的这些方法才可远程使用
 */

public interface FirstInterface  extends Remote {
    //rmi强制要求所有的远程服务方法必须抛出RemoteException
    String first(String name) throws RemoteException;


}
