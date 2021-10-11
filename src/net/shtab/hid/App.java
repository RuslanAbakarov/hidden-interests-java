package net.shtab.hid;

import java.util.Timer;

public class App {
	private static final int TIMEOUT = 3600;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Loader list is empty. Please provide it as an execution params");
			LoaderFactory.showLoaderList();
			return;
		}
		
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		LoadTask task = new LoadTask(args, TIMEOUT);
		if (!task.isReady()) {
			System.out.println("Timer task was not initialized. Program terminated");
			System.exit(0);
		}
		
		new Timer().scheduleAtFixedRate(task, 0, TIMEOUT * 1000);
	}
}
