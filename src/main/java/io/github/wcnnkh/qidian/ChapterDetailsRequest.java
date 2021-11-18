package io.github.wcnnkh.qidian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper= true)
@ToString(callSuper = true)
public class ChapterDetailsRequest extends BaseRequest{
	private static final long serialVersionUID = 1L;
	private String chapterId;
}
