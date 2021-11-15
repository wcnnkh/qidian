package io.github.wcnnkh.qidian.test;

import java.io.IOException;

import io.basc.framework.json.JSONUtils;
import io.github.wcnnkh.qidian.Client;
import io.github.wcnnkh.qidian.SearchRequest;
import io.github.wcnnkh.qidian.SearchResponse;

import org.junit.Test;


public class ClientTest {
	private static Client client = new Client();
	
	@Test
	public void test() throws IOException{
		SearchResponse response = client.search(SearchRequest.builder().kw("我").build());
		System.out.println(JSONUtils.getJsonSupport().toJSONString(response));
	}
}
