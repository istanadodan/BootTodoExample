

CREATE TABLE tb_board (
    board_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    board_writer VARCHAR(255) NOT NULL,
    board_title VARCHAR(255) NOT NULL,
    board_contents TEXT,
    board_hits INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO tb_board (
    board_writer, 
    board_title, 
    board_contents, 
    board_hits
) VALUES (
    '홍길동',
    '첫 번째 게시글입니다',
    '안녕하세요. 이것은 게시판의 첫 번째 글입니다. 잘 부탁드립니다!',
    0
);
