package shop.mtcoding.blogv2.user;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserQueryRepository.class)
@DataJpaTest // JpaRepository만 메모리에 올려준다
public class UserQueryRepositioryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        userQueryRepository.save(user); // 영속화
        // em.flush();
    }

    // 1차 캐시
    @Test
    public void fintById_test() {
        System.out.println("1. pc는 비어있다");

        userQueryRepository.findById(1);
        System.out.println("2. pc의 user 1은 영속화 되어 있다");
        // pc는 user 1의 객체가 영속화 되어 있다.
        userQueryRepository.findById(1);
    }

    @Test
    public void update_test() {
        // JPA에서 update 알고리즘
        // 1. 영속화
        // 2. 객체 상태 변경
        // 3. em.flush() or @Transactional 정상종료(자동으로 플러시)
        User user = userQueryRepository.findById(1); // 영속화된 객체
        user.setEmail("ssarmango@nate.com");
        em.flush();
    }
}
