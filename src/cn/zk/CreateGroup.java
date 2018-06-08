package cn.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import cn.zk.watcher.ConnectionWatcher;

/**
 * 创建组
 * @author Victor
 * @date 2018年6月8日 上午10:58:40
 */
public class CreateGroup extends ConnectionWatcher {
	
	private void create(String groupName, String data) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		if(zk.exists(path, false) == null) {
			zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		System.out.println("Created: " + path);
	}
	
	public static void main(String[] args) throws Exception {
		String groupName = "carl";
		String data = "carl is a good man";
		
		CreateGroup creatGrop = new CreateGroup();
		
		creatGrop.connect();
		
		creatGrop.create(groupName, data);
		
		creatGrop.close();
	}
	
}
