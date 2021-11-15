package io.github.wcnnkh.qidian;

import io.basc.framework.logger.Logger;
import io.basc.framework.logger.LoggerFactory;
import io.basc.framework.util.page.SharedPage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Client {
	private static Logger logger = LoggerFactory.getLogger(Client.class);

	public SearchResponse search(SearchRequest request) throws IOException {
		Connection connection = HttpConnection
				.connect("https://www.qidian.com/search");
		connection = connection.data("kw", request.getKw());
		Document document = connection.get();
		if (logger.isDebugEnabled()) {
			logger.debug("Search[{}] response:", request, document.html());
		}
		Elements items = document.select("#result-list li.res-book-item");
		List<Book> books = items
				.stream()
				.map((element) -> {
					Book book = new Book();
					book.setId(element.attr("data-bid"));
					book.setImg(element.select("div.book-img-box a>img")
							.first().absUrl("src"));
					Element info = element.select("div.book-mid-info").first();
					book.setName(info.select("h4>a")
							.first().html());
					book.setIntro(info.select("p.intro").first().html());
					return book;
				}).collect(Collectors.toList());
		SharedPage<Book> page = new SharedPage<Book>(books.size());
		page.setRows(books);
		page.setCount(books.size());
		if (request.getPage() != null) {
			page.setPageNumber(request.getPage());
		}
		return SearchResponse.builder().results(page).build();
	}
}
