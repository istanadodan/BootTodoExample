package kr.or.ksd.sto.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 custom query method들을 여기에 정의할 수 있습니다
    
    // 이메일로 사용자 찾기
    User findByEmail(String email);
    
    // 이름으로 사용자들 검색
    List<User> findByNameContaining(String name);
    
    // 활성 상태인 사용자들 조회
    List<User> findByActiveTrue();
    
    // 특정 역할을 가진 사용자들 조회
    List<User> findByRole(String role);

	Optional<User> findByUsername(String username);
}
