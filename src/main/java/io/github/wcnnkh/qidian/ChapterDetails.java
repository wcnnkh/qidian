package io.github.wcnnkh.qidian;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	private String content;
}
