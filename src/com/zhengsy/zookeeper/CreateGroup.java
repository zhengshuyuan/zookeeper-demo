package com.zhengsy.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * ��zookeeper�����ڵ�
 * 
 * @author zhengsy 2016��5��16��
 */
public class CreateGroup implements Watcher {

	private final static int SESSION_TIMEOUT = 5000;

	private ZooKeeper zk;
	private CountDownLatch latch = new CountDownLatch(1);// ������

	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			latch.countDown();
		}
	}

	public void connect(String hosts) throws IOException, InterruptedException {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		latch.await();// �ȴ���ֱ��zookeeper������ϣ�
	}

	public void create(String groupName) throws KeeperException,
			InterruptedException {
		String createPath = "/" + groupName;
		// �����½ڵ㣻
		zk.create(createPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("create " + createPath);
	}

	public void close() throws InterruptedException {
		zk.close();
	}

	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {

		CreateGroup group = new CreateGroup();
		group.connect(args[0]);
		group.create(args[1]);
		group.close();

	}

}
