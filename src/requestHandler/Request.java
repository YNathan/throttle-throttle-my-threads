package requestHandler;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

enum ERequestStatus {
	// In Case of Lowest Rank Are running
	WAITING,
	// In Case of started
	STARTED,
	// When Finished
	FINISHED
}

public class Request {
	String userId;
	int rate;
	String request;
	ERequestStatus status;

	public Request() {
		
	}
	
	public int getRate() {
		return this.rate;
	}
	public Request(String m_userId, int m_rate, String m_request) {
		this.userId = m_userId;
		this.rate = m_rate;
		this.request = m_request;
		this.status = ERequestStatus.WAITING;
	}

	public String toString() {
		return "Task for userId :" + this.userId + ", Rate: " + this.rate + ", Request: " + this.request + ", STATUS: "
				+ this.status + ".";
	}

	public String toStringWithoutUser() {
		return "Rate: " + this.rate + ", Request: " + this.request + ", STATUS: " + this.status + ".";
	}

	public String toStringShort() {
		return "RT: " + this.rate + ", REQ: " + this.request + ", ST: " + this.status + ".";
	}

	public String toStringOnlyRate() {
		return "RT: " + this.rate;
	}

	public String toStringUserAndRate() {
		return "USR: " + this.userId + " RT: " + this.rate;
	}
}


