package io.github.wcnnkh.qidian;

import io.basc.framework.util.Pair;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchOptions implements Serializable {
	private static final long serialVersionUID = 1L;
	@Schema(description = "站点")
	private List<Pair<String, String>> siteidOptions;
	@Schema(description = "分类")
	private List<Pair<String, String>> chanIdOptions;
	@Schema(description = "状态")
	private List<Pair<String, String>> actionOptions;
	@Schema(description = "属性")
	private List<Pair<String, String>> vipOptions;
	@Schema(description = "字数")
	private List<Pair<String, String>> sizeOptions;
	@Schema(description = "品质")
	private List<Pair<String, String>> signOptions;
	@Schema(description = "更新时间")
	private List<Pair<String, String>> updateOptions;
	@Schema(description = "排序")
	private List<Pair<String, String>> sortOptions;
}
