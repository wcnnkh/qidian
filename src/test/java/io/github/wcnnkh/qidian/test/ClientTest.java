package io.github.wcnnkh.qidian.test;

import java.io.IOException;

import org.junit.Test;

import io.github.wcnnkh.qidian.Book;
import io.github.wcnnkh.qidian.BookDetails;
import io.github.wcnnkh.qidian.Client;
import io.github.wcnnkh.qidian.BookDetailsRequest;
import io.github.wcnnkh.qidian.Chapter;
import io.github.wcnnkh.qidian.ChapterDetails;
import io.github.wcnnkh.qidian.ChapterDetailsRequest;
import io.github.wcnnkh.qidian.SearchRequest;
import io.github.wcnnkh.qidian.SearchResponse;


public class ClientTest {
	private static Client client = new Client();
	
	@Test
	public void test() throws IOException{
		SearchResponse response = client.search(SearchRequest.builder().kw("斗破").action(1).vip(1).build());
		System.out.println(response);
		Book book = response.getResults().getRows().get(0);
		BookDetails bookDetails = client.getBookDetails(BookDetailsRequest.builder().bookId(book.getId()).build());
		System.out.println(bookDetails);
		Chapter chapter = bookDetails.getChapters().get(bookDetails.getChapters().size() - 1);
		ChapterDetails chapterDetails = client.getChapterDetails(ChapterDetailsRequest.builder().chapterId(chapter.getId()).build());
		System.out.println(chapterDetails);
	}
}
