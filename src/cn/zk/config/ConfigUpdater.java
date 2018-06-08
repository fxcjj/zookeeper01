package cn.zk.config;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;

public class ConfigUpdater {
	
	public static final String PATH = "/config";
	
	private ActiveKeyValueStore store;
	private Random random = new Random();
	
	public ConfigUpdater(String hosts) throws IOException, InterruptedException {
		store = new ActiveKeyValueStore();
		store.connect();
	}
	
	public void run() throws KeeperException, InterruptedException {
		while(true) {
			String value = random.nextInt(1000) + "";
			store.write(PATH, value);
			System.out.printf("Set %s to %s\n", PATH, value);
			TimeUnit.SECONDS.sleep(random.nextInt(100));
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String hosts = "";
		ConfigUpdater configUpdater = new ConfigUpdater(hosts);
		configUpdater.run();
		
	}
	
}
