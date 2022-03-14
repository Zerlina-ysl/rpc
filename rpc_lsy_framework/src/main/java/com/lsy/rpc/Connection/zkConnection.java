package com.lsy.rpc.Connection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 *  提供与zk的连接
 */
public class zkConnection {

    private String zkServer;
    private int sessionTimeout;

    public zkConnection(){
        super();
        this.zkServer="121.40.248.12:2181";
        this.sessionTimeout=10000;
    }
    public zkConnection(String zkServer,int sessionTimeout){
        super();
        this.zkServer=zkServer;
        this.sessionTimeout=sessionTimeout;
    }

    public ZooKeeper getConnection() throws IOException {
        return new ZooKeeper(zkServer, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }



}
