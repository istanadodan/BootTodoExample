-- 데이터베이스 선택
USE mydb;

-- 사용자 테이블 생성
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 게시글 테이블 생성
CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 샘플 사용자 데이터 삽입
INSERT INTO users (username, email, full_name) VALUES
('johndoe', 'john@example.com', 'John Doe'),
('janedoe', 'jane@example.com', 'Jane Doe'),
('bobsmith', 'bob@example.com', 'Bob Smith');

-- 샘플 게시글 데이터 삽입
INSERT INTO posts (user_id, title, content) VALUES
(1, '첫 번째 게시글', '안녕하세요, 이것은 제 첫 번째 게시글입니다.'),
(1, '두 번째 게시글', '오늘은 날씨가 좋네요.'),
(2, 'MySQL 사용법', 'MySQL을 사용하는 방법에 대해 알아봅시다.'),
(3, '자바 프로그래밍', '자바 프로그래밍의 기초에 대해 설명합니다.');

-- 인덱스 생성 (선택사항)
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_posts_user_id ON posts(user_id);