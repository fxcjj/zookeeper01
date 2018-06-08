package cn.zk.watcher;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author Victor
 * @date 2018年4月24日 下午7:18:41
 */
public class ConnectionWatcher implements Watcher {
	
	private static final int SESSION_TIMEOUT = 5000;
	protected ZooKeeper zk;
	CountDownLatch connSignal = new CountDownLatch(1);
	
	public static final String host = "192.168.42.128:2181";
	
	public void connect() throws IOException, InterruptedException {
		zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
		connSignal.await();
	}
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected) {
			connSignal.countDown();
		}
	}
	
	public void close() throws InterruptedException {
		zk.close();
	}
	
}
