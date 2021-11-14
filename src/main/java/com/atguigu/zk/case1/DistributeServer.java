package com.atguigu.zk.case1;


import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {

    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        final DistributeServer server = new DistributeServer();
        // 1. 获取连接
        server.getConnect();

        // 2. 注册服务
        server.regis(args[0]);
        // 3. 执行业务逻辑

        server.business();
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void regis(String hostname) throws KeeperException, InterruptedException {
        final String s = zooKeeper.create("/servers/" + hostname, hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + "   已经上线了   ");
    }

    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper("192.168.1.19", 20000000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
