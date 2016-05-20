package com.zhengsy.zookeeper.simple;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 加入组程序
 * @author zhengsy
 * 2016-5-19
 */
public class JoinGroup extends ConnectionWatcher {

	public void join(String groupName, String memerName)
			throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memerName;
		//EPHEMERAL 短暂连接；OPEN_ACL_UNSAFE，对所有开放
		String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		System.out.println("create path " + createPath);
	}

	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		JoinGroup joinGroup = new JoinGroup();
		joinGroup.connect(args[0]);
		joinGroup.join(args[1], args[2]);

		//模拟客户端在工作
		Thread.sleep(Long.MAX_VALUE);
	}

}
