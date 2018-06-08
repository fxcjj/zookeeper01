package cn.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import cn.zk.watcher.ConnectionWatcher;

/**
 * 向组中添加成员
 * @author Victor
 * @date 2018年6月8日 上午10:58:03
 */
public class JoinGroup extends ConnectionWatcher {
	
	private void join(String groupName, String memberName, String data) throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memberName;
		//临时节点
		String rst = zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("Created: " + rst);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String groupName = "carl";
		String memberName = "birthday";
		String data = "2018/12/12";
		
		JoinGroup setGrop = new JoinGroup();
		
		setGrop.connect();
		setGrop.join(groupName, memberName, data);
		setGrop.close();
		
		//stay alive until process is killed or thread is interrupted
		Thread.sleep(Integer.MAX_VALUE);
	}
	
}
