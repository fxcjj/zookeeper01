package cn.zk;

import java.util.List;

import org.apache.zookeeper.KeeperException;

import cn.zk.watcher.ConnectionWatcher;

/**
 * 删除组及组下所有成员
 * @author Victor
 * @date 2018年6月8日 上午10:57:47
 */
public class DeleteGroup extends ConnectionWatcher {
	
	//删除指定节点（当节点下有子节点时，无法删除）
	/*private void delete(String path) throws KeeperException, InterruptedException {
		zk.delete(path, -1);
		System.out.println("Deleted: " + path);
	}*/
	
	//删除组及组下所有成员
	private void delete2(String groupName) throws KeeperException, InterruptedException {
		
		String path = "/" + groupName;
		List<String> children = zk.getChildren(path, false);
		
		for(String child : children) {
			zk.delete(path + "/" + child, -1);
		}
		
		zk.delete(path, -1);
		System.out.println("Deleted: " + path);
	}
	
	public static void main(String[] args) throws Exception {
		String path = "carl";
		
		DeleteGroup deleteGroup = new DeleteGroup();
		deleteGroup.connect();
		
		deleteGroup.delete2(path);
		
		deleteGroup.close();
	}
	
}
