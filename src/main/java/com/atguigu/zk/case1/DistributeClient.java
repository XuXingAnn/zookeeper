package com.atguigu.zk.case1;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {

    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        final DistributeClient client = new DistributeClient();
        // 1. 获取连接
        client.getConnect();

        // 2. 获取服务器列表
        client.getServerList();
        // 3. 执行逻辑
        client.business();

    }
    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
    private void getServerList() throws KeeperException, InterruptedException {
        final List<String> childrens = zooKeeper.getChildren("/servers", true);
        final ArrayList<String> servers = new ArrayList<>();
        for (String children : childrens) {
            final byte[] data = zooKeeper.getData("/servers/" + children, false, null);
            servers.add( "serverNode" +"/servers/" + children + " data : " +new String(data));
        }
//        for (String server : servers) {
//            System.out.println(server);
//        }
        System.out.println(servers);
    }

    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper("192.168.1.19", 20000000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServerList();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
