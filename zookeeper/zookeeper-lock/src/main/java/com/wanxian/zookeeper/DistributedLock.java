package com.wanxian.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * zookeeper分布式锁实现
 */
public class DistributedLock implements Lock, Watcher {
    private ZooKeeper zooKeeper = null;
    private String ROOT_LOCK = "/locks";
    private String WAIT_LOCK;//等待当前的一个锁
    private String CURRENT_LOCK;//表示当前的锁
    private CountDownLatch countDownLatch;

    public DistributedLock() {//zk连接创建
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 5000, this);
            try {
                Stat stat = zooKeeper.exists(ROOT_LOCK, false);
                //判断根节点是否存在
                if (stat == null) {
                    zooKeeper.create(ROOT_LOCK, "0".getBytes(),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean tryLock() {
        //创建临时有序节点
        try {
            CURRENT_LOCK = zooKeeper.create(ROOT_LOCK + "/", "0".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "->" + CURRENT_LOCK + "尝试获取锁");
            List<String> childrenList = zooKeeper.getChildren(ROOT_LOCK, false);//获取根节点下所有节点
            SortedSet<String> sortedSet = new TreeSet<>(); //定义集合进行排序
            childrenList.forEach(list -> {
                sortedSet.add(ROOT_LOCK + "/" + list);
            });
            String firstNode = sortedSet.first(); //获得子节点中最小的节点
            SortedSet<String> lessThenMe = ((TreeSet<String>) sortedSet).headSet(CURRENT_LOCK);
            if (firstNode.equals(CURRENT_LOCK)) { //如果当前节点与最小的节点相等，证明获取锁成功
                return true;
            }
            if (!lessThenMe.isEmpty()) {
                WAIT_LOCK = lessThenMe.last();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


    public void lock() {
        if (this.tryLock()) {//获得锁成功
            System.out.println(Thread.currentThread().getName() + "->" + CURRENT_LOCK + "成功获得锁");
            return;
        }
        try {
            waitForLock(WAIT_LOCK); //没有获得锁，继续等待
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void waitForLock(String prev) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(prev, true);//监听当前时间的上一个节点
        if (stat != null) {//如果存在
            System.out.println(Thread.currentThread().getName() + "->" + "等待锁" + CURRENT_LOCK + "释放");
            countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + "->" + CURRENT_LOCK + "成功获得锁");
        }

    }

    public void lockInterruptibly() throws InterruptedException {

    }


    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        System.out.println(Thread.currentThread().getName() + "->释放锁" + CURRENT_LOCK);
        try {
            zooKeeper.delete(CURRENT_LOCK, -1);
            CURRENT_LOCK = null;
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (this.countDownLatch != null) {
            countDownLatch.countDown();
        }

    }
}
