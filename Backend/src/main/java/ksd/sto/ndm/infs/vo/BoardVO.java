package ksd.sto.ndm.infs.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BoardVO {
	private long boardId;
	private String boardTitle;
	private String boardContents;
	private String boardWriter;
	private int boardHits;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
