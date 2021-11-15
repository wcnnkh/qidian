package io.github.wcnnkh.qidian;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	@Schema(description = "关键字")
	@NotEmpty
	private String kw;
	@Schema(description = "页码")
	private Integer page;
	@Schema(description = "默认：搜索相关性, 1:更新时间, 11:总收藏, 4:总字数, 9:周推荐票, 10:月推荐票, 3:总推荐票")
	private Integer sort;
	@Schema(description = "站点")
	private Integer siteid;
	@Schema(description = "分类")
	private Integer chanId;
	@Schema(description = "状态")
	private Integer action;
	@Schema(description = "0免费， 1 vip")
	private Integer vip;
	@Schema(description = "字数")
	private Integer size;
	@Schema(description = "品质")
	private Integer sign;
	@Schema(description = "更新时间")
	private Integer update;
}
