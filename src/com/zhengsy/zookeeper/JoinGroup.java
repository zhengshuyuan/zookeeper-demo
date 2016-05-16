package com.zhengsy.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * ������
 * 
 * @author zhengsy 2016��5��16��
 */
public class JoinGroup extends ConnectionWatcher {

	public void join(String groupName, String memerName)
			throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memerName;
		// ��������znode��Ϊ��znode���ӽڵ㣬ͨ��������ģ����������ĳ�ֹ�����ֱ���ý��̱�ǿ����ֹ
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
