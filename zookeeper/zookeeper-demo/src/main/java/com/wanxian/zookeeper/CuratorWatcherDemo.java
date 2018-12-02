package com.wanxian.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 *
 */
public class CuratorWatcherDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString("192.168.1.105:2181,192.168.1.106:2181,192.168.1.107:2181").sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("curator").build();
        curatorFramework.start();
        //当前节点的创建、删除、更新监听
        //addListenerWithNodeCache(curatorFramework,"/wanxian-curator");
        //子节点的创建、删除、更新监听
        //addListenerWithPathChildCache(curatorFramework, "/wanxian-curator");
        //当前节点、子节点的创建、删除、更新监听
        addListenerWithTreeCache(curatorFramework, "/wanxian-curator");
        System.in.read();
    }

    /**
     * TreeCache  综合PatchChildCache和NodeCache的特性
     * @param curatorFramework
     * @param path
     * @throws Exception
     */
    public static void addListenerWithTreeCache(CuratorFramework curatorFramework, String path) throws Exception {
        TreeCache  treeCache=new TreeCache(curatorFramework,path);
        TreeCacheListener treeCacheListener= new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println(treeCacheEvent.getType()+"->"+treeCacheEvent.getData().getPath());
            }
        };
        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }


    /**
     * NodeCache  监听一个节点的更新和创建事件
     * @param curatorFramework
     * @param path
     * @throws Exception
     */
    public static void addListenerWithNodeCache(CuratorFramework curatorFramework, String path) throws Exception {
        final NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("收到事件变化:" + nodeCache.getCurrentData().getPath());
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    /**
     * PathChildCache 监听一个节点下子节点的创建、删除、更新
     * @param curatorFramework
     * @param path
     * @throws Exception
     */
    public static void addListenerWithPathChildCache(CuratorFramework curatorFramework, String path) throws Exception {
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("收到事件变化" + pathChildrenCacheEvent.getType());
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start();
    }


}
