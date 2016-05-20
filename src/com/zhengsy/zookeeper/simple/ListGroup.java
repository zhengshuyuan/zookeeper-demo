package com.zhengsy.zookeeper.simple;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

/**
 * 列出组成员的程序
 * @author zhengsy
 * 2016-5-17
 */
public class ListGroup extends ConnectionWatcher {


    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;

        try {
            List<String> children = zk.getChildren(path, false);
            if (children.isEmpty()) {
                System.out.printf("No members in group %s\n", groupName);
                System.exit(1);
            }
            for (String child : children) {
                System.out.println(child);
            }
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("No members in group %s\n", groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ListGroup listGroup = new ListGroup();
        listGroup.connect(args[0]);
        listGroup.list(args[1]);
        listGroup.close();
    }

}
