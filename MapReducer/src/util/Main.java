package util;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Main implements Watcher {

	public static void main(String[] args) throws Exception {
		String connectString = "localhost";
		int sessionTimeout = 10000;

		new Main().run(connectString, sessionTimeout);
	}

	ZooKeeper zk;

	private void run(String connectString, int sessionTimeout) throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, this);
		deleteAll("/");
	}

	private void deleteAll(String prefix) throws KeeperException, InterruptedException {
		// System.out.println("take a look of " + prefix);
		for (String path : zk.getChildren(prefix, false)) {
			// System.out.println("now it is " + path);
			if (path.contains("zookeeper"))
				continue;

			String path2 = prefix + (prefix.equals("/") ? "" : "/") + path;
			deleteAll(path2);
		}
		if (prefix.equals("/"))
			return;
		System.out.println(prefix + " should be deleted");
		zk.delete(prefix, -1);
		System.out.println(prefix + " has been deleted");
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub

	}

}
