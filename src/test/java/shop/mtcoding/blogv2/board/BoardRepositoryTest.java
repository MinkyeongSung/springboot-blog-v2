package shop.mtcoding.blogv2.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Repository, EntityManger가 뜸
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void save_test() {
        Board board = Board.builder()
                .title("제목6")
                .content("내용6")
                .user(null)
                .build();

        // 영속 객체
        System.out.println("전 : " + board.getId());
        boardRepository.save(board); // insert 자동으로 실행됨
        // DB데이터와 동기화 됨
        System.out.println("후 : " + board.getId());
    }

}
