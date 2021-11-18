package io.github.wcnnkh.qidian;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
class BaseRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	@Schema(description = "是否强制转换为文本", example = "false", defaultValue = "false")
	private boolean forceToText;
}
