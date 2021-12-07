package io.github.wcnnkh.qidian;

import java.io.Serializable;

import io.basc.framework.util.page.SharedPagination;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	@Schema(description = "搜索结果")
	private SharedPagination<Book> results;
	@Schema(description = "选项")
	private SearchOptions options;
}
