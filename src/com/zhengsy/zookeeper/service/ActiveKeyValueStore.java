package com.zhengsy.zookeeper.service;

import java.nio.charset.Charset;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import com.zhengsy.zookeeper.simple.ConnectionWatcher;

public class ActiveKeyValueStore extends ConnectionWatcher {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 将一个关键字和值写到zookeeper
     * @param path
     * @param value
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void write(String path, String value) throws KeeperException, InterruptedException {

        //检查znode是否存在；
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            //创建znode节点
            zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            //更新节点数值
            zk.setData(path, value.getBytes(CHARSET), -1);
        }
    }

}
