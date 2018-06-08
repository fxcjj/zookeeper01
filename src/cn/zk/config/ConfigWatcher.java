package cn.zk.config;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ConfigWatcher implements Watcher {

	private ActiveKeyValueStore store;

	public ConfigWatcher(String hosts) throws IOException, InterruptedException {
		store = new ActiveKeyValueStore();
		store.connect();
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeDataChanged) {
			try {
				
				/**
				 * 一次setData只会触发一次，触发后 至 重新设置getData的watcher时间段内，这些信息不能读取到，即不能接收到每一个更新。
				 * 
				 * 我们还是不能保证接收到每一个更新，因为在收到观察事件通知与下一次读之间，znode可能已经被更新过，
				 * 而且可能是很多次，由于客户端在这段时间没有注册任何观察，因此不会收到通知。这个潜在的问题是不容忽视的！
				 */
				dispalyConfig();
			} catch (InterruptedException e) {
				System.err.println("Interrupted. exiting. ");
				Thread.currentThread().interrupt();
			} catch (KeeperException e) {
				System.out.printf("KeeperException锛?s. Exiting.\n", e);
			}

		}
	}

	private void dispalyConfig() throws KeeperException, InterruptedException {
		String value = store.read(ConfigUpdater.PATH, this);
		System.out.printf("Read %s as %s\n", ConfigUpdater.PATH, value);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String hosts = "";
        ConfigWatcher configWatcher = new ConfigWatcher(hosts);
        configWatcher.dispalyConfig();
        
        //stay alive until process is killed or Thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }

}
