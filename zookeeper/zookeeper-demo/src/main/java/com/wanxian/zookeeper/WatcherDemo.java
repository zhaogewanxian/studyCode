package com.wanxian.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class WatcherDemo {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ZooKeeper zooKeeper = new ZooKeeper("192.168.1.105:2181,192.168.1.106:2181,192.168.1.107:2181", 4000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    System.out.println("默认事件"+watchedEvent.getType());
                    //如果收到了服务端的响应事件，连接成功
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        //新增节点
        zooKeeper.create("/wanxian-watcher", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //通过exists绑定事件，只会监听一次
       Stat stat = zooKeeper.exists("/wanxian-watcher",new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType()+"->"+watchedEvent.getPath());
                try {
                    zooKeeper.exists("/wanxian-watcher",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
       //通过修改的事务类型触发监听
        stat=zooKeeper.setData("/wanxian-watcher","2".getBytes(),stat.getVersion());

        zooKeeper.delete("/wanxian-watcher",stat.getVersion());
        System.in.read();

    }
}
