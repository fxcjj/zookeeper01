package cn.zk;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import cn.zk.watcher.ConnectionWatcher;

/**
 * 修改数据
 * @author Victor
 * @date 2018年6月8日 上午10:58:28
 */
public class SetGroup extends ConnectionWatcher {
	
	private void setData(String path, String data) throws KeeperException, InterruptedException {
		Stat stat = zk.setData(path, data.getBytes(), -1);
		System.out.println("Set data success, version: " + stat.getVersion());
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String path = "/carl";
		String data = "bad man";
		
		SetGroup setGrop = new SetGroup();
		
		setGrop.connect();
		setGrop.setData(path, data);
		setGrop.close();
	}
	
}
