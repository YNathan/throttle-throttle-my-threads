package requestHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Handler {
	static Map<String, UserRequestMangaer> conMap;

	public Handler() {
		conMap = new ConcurrentHashMap<>();
		printerThread();
	}

	
	public static void handleRequest(Request req) throws InterruptedException {	
		new MapHelper(conMap, req);

	}

	public static Map<String,UserRequestMangaer> getMap() {
		return conMap;
	}
	private static void printerThread() {
		Thread printer = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (conMap.size() > 0) {
						System.out.println("\n-------------------------------------------------\n");
						String[] printer = { "", "" };
						int userCounter = 0;
						for (Map.Entry<String, UserRequestMangaer> e : conMap.entrySet()) {
							// System.out.println("User: " + e.getKey());
							printer[0] += "User: " + e.getKey() + "\t\t";
							userCounter++;
							int tasksCounter = 1;
							for (Request current : e.getValue().doneRequests) {
								tasksCounter++;
								if (printer.length < tasksCounter) {
									String[] tmp = new String[printer.length];
									System.arraycopy(printer, 0, tmp, 0, printer.length);
									printer = new String[tasksCounter];
									System.arraycopy(tmp, 0, printer, 0, tmp.length);
									printer[tasksCounter - 1] = "";
									for (int i = 0; i < userCounter - 1; i++) {
										printer[tasksCounter - 1] += "       \t\t";// " \t";
									}

								}
								printer[tasksCounter - 1] += current.toStringOnlyRate() + "\t\t";
							}

							while (tasksCounter < printer.length) {
								tasksCounter++;
								printer[tasksCounter - 1] += "       \t\t";// " \t";// "____________\t";
							}

						}

						for (String str : printer) {
							System.out.println(str);
						}

						System.out.println("-------------------------------------------------\n");
						try {
							Thread.sleep(1000);
							clearConsole();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				}

			}
		});
		printer.start();
	}

	public final static void clearConsole() {
		try {
			final String os = System.getProperty("os.name");

			if (os.contains("Windows")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (final Exception e) {
			// Handle any exceptions.
		}
	}

}

class MapHelper implements Runnable {
	Map<String, UserRequestMangaer> map;
	Request request;

	public MapHelper(Map<String, UserRequestMangaer> map, Request req) {
		this.map = map;
		this.request = req;

		new Thread(this, "HandlerForUser" + this.request.userId).start();
	}

	public void run() {
		if (map.get(this.request.userId) == null) {
			map.put(this.request.userId, new UserRequestMangaer());
		}

		// here we add the tasks to the waiting queue
		map.get(this.request.userId).newRequest(this.request);

		// first important part because every thing will happen synchronize only after
		// the put statement
		// and the task for x segment is blocked because we lock the segment of the key
		// its mean that
		// for each user the key will be add request by request
		// map.put(this.task.userId, utm);

		try {
			synchronized (this.request.userId) {
				map.get(this.request.userId).handleRequestInRateOrder();
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}
}
