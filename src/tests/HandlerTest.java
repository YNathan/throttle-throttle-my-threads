package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requestHandler.Handler;
import requestHandler.Request;
import requestHandler.UserRequestMangaer;

class HandlerTest {
	Handler handler  = new Handler();
	static Map<String, UserRequestMangaer> conMap;
	
	void init() {
		System.out.println("start");
		 handler = new Handler();
	}
	
	@Test
	void test() throws InterruptedException {
		
		handler.handleRequest( new Request("1", 2, "Eating poulkes"));
		handler.handleRequest( new Request("1", 5, "Washing car"));
		handler.handleRequest( new Request("2", 1, "Eating poulkes"));
		handler.handleRequest( new Request("3", 2, "Going Shnorkel"));
		handler.handleRequest( new Request("4", 2, "Eating poulkes"));
		handler.handleRequest( new Request("5", 6, "Big Balagan"));
		handler.handleRequest( new Request("1", 1, "Eating Piza"));
		handler.handleRequest( new Request("6", 2, "Making Gefilte fish"));
		handler.handleRequest( new Request("6", 1, "Making Gefilte fish"));
		handler.handleRequest( new Request("6", 0, "Making Gefilte fish"));
		handler.handleRequest( new Request("7", 8, "Eating poulkes"));
		handler.handleRequest( new Request("7", 8, "Eating poulkes"));
		handler.handleRequest( new Request("8", 0, "Eating poulkes"));
		handler.handleRequest( new Request("8", 2, "Eating poulkes"));
		handler.handleRequest( new Request("9", 1, "Eating poulkes"));
		handler.handleRequest( new Request("9", 8, "Making Tshunt"));
		handler.handleRequest( new Request("9", 3, "Eating poulkes"));
		handler.handleRequest( new Request("9", 8, "Eating poulkes"));
		handler.handleRequest( new Request("9", 5, "Eating poulkes"));
		handler.handleRequest( new Request("9", 1, "Fixing the car"));
		handler.handleRequest( new Request("9", 9, "Eating Omlet"));
		handler.handleRequest( new Request("1", 0, "Eating Bafla"));
		handler.handleRequest( new Request("1", 3, "Eating eggs"));
		handler.handleRequest( new Request("4", 1, "Washing hands"));
		handler.handleRequest( new Request("4", 0, "Washing hands"));
		
		Thread.sleep(40000);
		
		conMap = handler.getMap();
		
		Request[] requests = new Request[conMap.get("1").getDoneRequests().size()];
		int requestsIndex = 0;
		for (Request current : conMap.get("1").getDoneRequests()) {
			requests[requestsIndex] = current;
			requestsIndex++;
		}
		
		assertEquals(2, requests[0].getRate());
		assertEquals(0, requests[1].getRate());
		assertEquals(1, requests[2].getRate());
		assertEquals(3, requests[3].getRate());
		assertEquals(5, requests[4].getRate());
		
	}

}
