package io.github.wcnnkh.qidian.test;

import java.io.IOException;

import org.junit.Test;

import io.github.wcnnkh.qidian.Client;
import io.github.wcnnkh.qidian.SearchRequest;
import io.github.wcnnkh.qidian.SearchResponse;


public class ClientTest {
	private static Client client = new Client();
	
	@Test
	public void test() throws IOException{
		SearchResponse response = client.search(SearchRequest.builder().kw("我").build());
		System.out.println(response);
	}
}
