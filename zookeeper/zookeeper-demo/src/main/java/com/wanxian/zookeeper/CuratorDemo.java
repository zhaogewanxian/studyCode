package com.wanxian.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString("192.168.1.105:2181,192.168.1.106:2181,192.168.1.107:2181").sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("curator").build();
        curatorFramework.start();
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/wanxian-curator/node","1".getBytes());
        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/wanxian-curator/node");
        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/wanxian-curator/node","sdfsldf".getBytes());


        //curatorFramework.delete().deletingChildrenIfNeeded().forPath("/wanxian-curator/node");


    }
}
