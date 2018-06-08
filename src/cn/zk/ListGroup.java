package cn.zk;

import java.util.List;

import org.apache.zookeeper.KeeperException;

import cn.zk.watcher.ConnectionWatcher;

/**
 * 组列表
 * @author Victor
 * @date 2018年4月24日 下午7:30:33
 */
public class ListGroup extends ConnectionWatcher {
	
	public void list(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		List<String> children = zk.getChildren(path, false);
		
		if(children.isEmpty()) {
			System.out.printf("No members in goup %s\n", groupName);
			System.exit(1);
		}
		
		for(String c : children) {
			System.out.println(c);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String groupName = "app1";
		ListGroup listGroup = new ListGroup();
		
		listGroup.connect();
		listGroup.list(groupName);
		listGroup.close();
	}
	
}