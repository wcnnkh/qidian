package io.github.wcnnkh.qidian;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BookDetails extends Book {
	private static final long serialVersionUID = 1L;
	private List<Chapter> chapters;
}
