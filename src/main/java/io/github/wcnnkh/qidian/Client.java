package io.github.wcnnkh.qidian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.basc.framework.logger.Logger;
import io.basc.framework.logger.LoggerFactory;
import io.basc.framework.mapper.Fields;
import io.basc.framework.util.Pair;
import io.basc.framework.util.page.SharedPagination;

public class Client {
	private static Logger logger = LoggerFactory.getLogger(Client.class);

	private static final List<Pair<String, String>> DEFAULT_SORT_OPTIONS = new ArrayList<>(8);

	static {
		// 此处见index.*.js写死的
		DEFAULT_SORT_OPTIONS.add(new Pair<String, String>("0", "推荐票"));
		DEFAULT_SORT_OPTIONS.add(new Pair<String, String>("9", "周推荐票"));
		DEFAULT_SORT_OPTIONS.add(new Pair<String, String>("10", "月推荐票"));
		DEFAULT_SORT_OPTIONS.add(new Pair<String, String>("3", "总推荐票"));
	}

	public SearchResponse search(SearchRequest request) throws IOException {
		Map<String, String> params = Fields.getFields(SearchRequest.class).entity().getValueMap(request).entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) -> String.valueOf(e.getValue())));
		Connection connection = HttpConnection.connect("https://www.qidian.com/search").data(params);
		Document document = connection.get();
		if (logger.isDebugEnabled()) {
			logger.debug("Search[{}] response:", request, document.html());
		}
		Elements items = document.select("#result-list li.res-book-item");
		List<Book> books = items.stream().map((element) -> {
			Book book = new Book();
			book.setId(element.attr("data-bid"));
			book.setImg(element.select("div.book-img-box a>img").first().absUrl("src"));
			Element info = element.select("div.book-mid-info").first();
			book.setName(getContent(request, info.select("h4>a").first()));
			book.setIntro(getContent(request, info.select("p.intro").first()));
			return book;
		}).collect(Collectors.toList());
		SharedPagination<Book> page = new SharedPagination<Book>();
		page.setList(books);
		page.setCount(books.size());
		if (request.getPage() != null) {
			page.setPageNumber(request.getPage());
		}

		SearchOptions searchOptions = new SearchOptions();
		Elements elements = document.select("div.search-filter ul li");
		searchOptions.setSiteidOptions(elements.select("a[data-type='site']").stream()
				.map((e) -> new Pair<>(e.attr("data-id"), getContent(request, e))).collect(Collectors.toList()));
		searchOptions.setChanIdOptions(elements.select("a[data-type='category']").stream()
				.map((e) -> new Pair<>(e.attr("data-id"), getContent(request, e))).collect(Collectors.toList()));
		searchOptions.setActionOptions(elements.select("a[data-type='action']").stream()
				.map((e) -> new Pair<>(e.attr("data-id"), getContent(request, e))).collect(Collectors.toList()));
		searchOptions.setVipOptions(elements.select("a[data-type='vip']").stream()
				.map((e) -> new Pair<>(e.attr("data-id"), getContent(request, e))).collect(Collectors.toList()));
		searchOptions.setSizeOptions(elements.select("a[data-type='size']").stream()
				.map((e) -> new Pair<>(e.attr("data-id"), getContent(request, e))).collect(Collectors.toList()));
		searchOptions.setSignOptions(elements.select("a[data-type='sign']").stream()
				.map((e) -> new Pair<>(e.attr("data-id"), getContent(request, e))).collect(Collectors.toList()));
		searchOptions.setUpdateOptions(elements.select("a[data-type='update']").stream()
				.map((e) -> new Pair<>(e.attr("data-id"), getContent(request, e))).collect(Collectors.toList()));
		List<Pair<String, String>> sortOptions = new ArrayList<>();
		Elements sortSwitcher = document.select("div.sort-switcher");
		if (sortSwitcher != null) {
			Element time = sortSwitcher.select("a[data-type='time']").first();
			if (time != null) {
				sortOptions.add(new Pair<String, String>(time.attr("data-id"), getContent(request, time)));
			}

			Element totalCollect = sortSwitcher.select("a[data-type='totalCollect']").first();
			if (totalCollect != null) {
				sortOptions
						.add(new Pair<String, String>(totalCollect.attr("data-id"), getContent(request, totalCollect)));
			}

			Element wordscnt = sortSwitcher.select("a[data-type='wordscnt']").first();
			if (totalCollect != null) {
				sortOptions.add(new Pair<String, String>(wordscnt.attr("data-id"), getContent(request, wordscnt)));
			}
		}
		sortOptions.addAll(DEFAULT_SORT_OPTIONS);
		searchOptions.setSortOptions(sortOptions);
		return SearchResponse.builder().results(page).options(searchOptions).build();
	}

	private String getContent(BaseRequest request, Element element) {
		if (element == null) {
			return null;
		}
		return request.isForceToText() ? element.text() : element.html();
	}

	public BookDetails getBookDetails(BookDetailsRequest request) throws IOException {
		Connection connection = HttpConnection.connect("https://book.qidian.com/info/" + request.getBookId());
		Document document = connection.get();
		if (logger.isDebugEnabled()) {
			logger.debug("Get details [{}] response:", request, document.html());
		}
		List<Chapter> chapters = document.select("#j-catalogWrap div.volume ul.cf>li").stream().map((element) -> {
			return Chapter.builder().id(element.select("a").first().absUrl("href"))
					.name(getContent(request, element.select("a").first())).build();
		}).collect(Collectors.toList());
		
		BookDetails details = new BookDetails();
		details.setChapters(chapters);
		return details;
	}
	
	public ChapterDetails getChapterDetails(ChapterDetailsRequest request) throws IOException {
		Connection connection = HttpConnection.connect(request.getChapterId());
		Document document = connection.get();
		return ChapterDetails.builder().content(getContent(request, document.select("div.read-content").first())).build();
	}
}
