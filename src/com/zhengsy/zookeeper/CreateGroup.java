package com.zhengsy.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateGroup implements Watcher {

    private static final int SESSION_TIMEOUT = 5000;

    private ZooKeeper zk;
    //锁存器 ；1表示在它释放所有的等待线程之前需要发生的事件数；每调用一次countDown()，自减1；
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        /*
         * 阻止新建zookeeper对象，等待对象创建成功；
         * KeeperState 连接事件
         */
        if (event.getState() == KeeperState.SyncConnected) {
            connectedSignal.countDown();
        }
    }

    /**
     * 实例化zookeeper类，该类用于维护客户端与zookeeper服务之间的连接
     * @param hosts 
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect(String hosts) throws IOException, InterruptedException {
        /**
         * hosts 服务器地址，可指定端口;
         * sessionTimeout 会话超时时间（ms）
         * weatcher weatcher对象实例
         */
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        //当锁存器值变为-0后，则await()方法返回；
        connectedSignal.await();
    }

    /**
     * 创建新znode
     * @param groupName
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        //param:路径(String),znode的内容(字节数组)，访问控制列表(简称ACL，本例使用完全开放的ACL，允许任何客户端对znode进行对象)，创建znode的类型；
        //return:zookeeper创建的路径；
        String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created " + createPath);
    }

    public void close() throws InterruptedException {
        zk.close();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect(args[0]);
        createGroup.create(args[1]);
        createGroup.close();
        
    }

}
