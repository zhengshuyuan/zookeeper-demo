package com.zhengsy.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * �����ȴ�������Zookeeper���ӵĸ�����
 * @author zhengsy
 * 2016��5��16��
 */
public class ConnectionWatcher implements Watcher {
	
	private static final int SESSION_TIMEOUT = 5000;
	
	protected ZooKeeper zk;
	private CountDownLatch latch = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected){
			latch.countDown();
		}
	}
	
	public void connect(String hosts) throws IOException, InterruptedException{
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		latch.await();
	}
	
	public void close() throws InterruptedException{
		zk.close();
	}

}
