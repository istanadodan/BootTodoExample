package ksd.sto.ndm.domain.dto;

import java.util.Date;

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
public class BoardDTO {
	private long boardId;
	private String boardTitle;
	private String boardContents;
	private String boardWriter;
	private int boardHits;
	private Date createdAt;
	private Date modifiedAt;
}
