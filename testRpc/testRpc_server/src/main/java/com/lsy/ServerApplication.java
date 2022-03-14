package com.lsy;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 */
public class ServerApplication {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        //创建服务对象
//        UserService service = new UserServiceImpl();
//
//        RpcFactory.registerService(UserService.class,service);

        try {
            Class.forName("com.lsy.rpc.RpcFactory");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
