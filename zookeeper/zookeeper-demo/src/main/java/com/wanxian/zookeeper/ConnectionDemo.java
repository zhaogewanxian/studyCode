package com.wanxian.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * java操作zookeeperDemo
 */
public class ConnectionDemo {

    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("192.168.1.105:2181", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        //如果收到了服务端的响应事件，连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            System.out.println(zooKeeper.getState());//CONNECTED
            //新增节点
            zooKeeper.create("/wanxian-node", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            Thread.sleep(1000);
            Stat stat = new Stat();
            //得到当前节点的值
            byte[] bytes = zooKeeper.getData("/wanxian-node", null, stat);
            System.out.println(new String(bytes));
            //修改节点
            zooKeeper.setData("/wanxian-node", "2".getBytes(), stat.getVersion());

            byte[] bytes1 = zooKeeper.getData("/wanxian-node", null, stat);
            System.out.println(new String(bytes1));

            //删除节点
            zooKeeper.delete("/wanxian-node", stat.getVersion());
            zooKeeper.close();
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
