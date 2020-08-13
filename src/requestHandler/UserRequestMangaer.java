package requestHandler;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class UserRequestMangaer {

	Hashtable<Integer, ArrayList<Request>> waitingRequests;
	// only for dev purpose delete
	ArrayList<Request> doneRequests;

	public ArrayList<Request> getDoneRequests(){
		return doneRequests;
	}
	public void newRequest(Request req) {
		if (this.waitingRequests.get(req.rate) == null) {
			this.waitingRequests.put(req.rate, new ArrayList<>());
		}
		this.waitingRequests.get(req.rate).add(req);
		System.out.println("new task added for user : " + req.userId + ", rate: " + req.rate);
	}

	public int getLowestRateTheMostImportent() {
		// get all the rates
		Enumeration<Integer> keyses = this.waitingRequests.keys();
		// defined a max rate val
		int lowestRate = Integer.MAX_VALUE;
		// running while hashtable has reuquestes
		while (keyses.hasMoreElements()) {
			// taking the lowest rate lowest the rate are hight importent is
			int currentRate = keyses.nextElement();
			if (lowestRate > currentRate) {
				lowestRate = currentRate;
			}
		}
		return lowestRate;
	}

	public void handleRequestInRateOrder() throws InterruptedException, Exception {

		int lowestRate = getLowestRateTheMostImportent();
		// taking all the task of the current lowest rate the is the most important
		// tasks
		if (this.waitingRequests.get(lowestRate) != null) {

			for (int i = 0; i < this.waitingRequests.get(lowestRate).size(); i++) {
				Request req = this.waitingRequests.get(lowestRate).get(i);
				// here is the task that need to be do one
				Thread.sleep(1000);
				System.out.println("Handled USR: " + req.userId + " RT: " + req.rate);
				this.waitingRequests.get(lowestRate).remove(i);
				i--;
				this.doneRequests.add(req);
			}
		}

		try {
			// and then remove it from the hash-table at this key
			if (this.waitingRequests.get(lowestRate) != null /*&& this.waitingRequests.get(lowestRate).size() == 0*/) {				
				this.waitingRequests.remove(lowestRate);
			}

		} catch (Exception e) {
			System.out.println("exception when remove rate: " + lowestRate);
		}

		// if is not empty its men that we still have more request waiting
		if (!this.waitingRequests.isEmpty()) {
			this.handleRequestInRateOrder();
		}

	}

	public UserRequestMangaer() {
		this.waitingRequests = new Hashtable();
		this.doneRequests = new ArrayList<>();
	}
}

