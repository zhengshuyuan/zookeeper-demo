package com.zhengsy.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 加入组
 * 
 * @author zhengsy 2016年5月16日
 */
public class JoinGroup extends ConnectionWatcher {

	public void join(String groupName, String memerName)
			throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memerName;
		// 创建短暂znode作为组znode的子节点，通过休眠来模拟正在做的某种工作，直到该进程被强行终止
		String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		System.out.println("create path " + createPath);
	}

	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		JoinGroup joinGroup = new JoinGroup();
		joinGroup.connect(args[0]);
		joinGroup.join(args[1], args[2]);

		Thread.sleep(Long.MAX_VALUE);
	}

}
