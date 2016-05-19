package com.zhengsy.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

/**
 * 用于删除一个组以及其所有的成员的程序
 * 
 * @author zhengsy 2016-5-19
 */
public class DeleteGroup extends ConnectionWatcher {

    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;

        try {
            List<String> children = zk.getChildren(path, false);
            // 删除所有子成员
            for (String child : children) {
                // version -1，无视版本号作用，直接删除
                zk.delete(path + "/" + child, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1); // 非正常关闭
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(args[0]);
        deleteGroup.delete(args[1]);
        deleteGroup.close();
    }

}
