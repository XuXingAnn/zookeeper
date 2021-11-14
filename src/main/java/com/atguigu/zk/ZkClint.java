package com.atguigu.zk;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkClint {

    private  ZooKeeper zooKeeper = null;
    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper("192.168.1.19 ", 20000000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 持久化监听
                List<String> children = null;
                try {
                    children = zooKeeper.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String child : children) {
                    System.out.println("watch 中：" + child);
                }

            }
        });
    }

    @Test
    public void createNode() throws KeeperException, InterruptedException {
//        final String s = zooKeeper.create("/atguigu", "ss.avi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println(s);
        String nodeCreated = zooKeeper.create("/atguigu1",
                "shuaige".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }
    @Test
    public void watchOnlyOnce() throws KeeperException, InterruptedException {
        final List<String> children = zooKeeper.getChildren("/", true);
        children.forEach(System.out::println);
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void nodeIsExits() throws KeeperException, InterruptedException {
        final Stat aiguigt = zooKeeper.exists("/atguigu", false);
        System.out.println("watch == falise "+aiguigt);
        final Stat aiguigt1 = zooKeeper.exists("/atguigu", true);
        System.out.println("watch == falise "+aiguigt1);
    }
}
