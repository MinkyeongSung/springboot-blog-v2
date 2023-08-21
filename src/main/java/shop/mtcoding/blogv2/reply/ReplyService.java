package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 삭제하기(Integer id) {
        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(id + "를 찾을 수 없어요");
        }
    }

    @Transactional
    public void 댓글쓰기(ReplyRequest.SaveDTO saveDTO, int sessionUserId) {

        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                // .board(saveDTO.getBoardId()) // DTO를 오브젝트로.
                // .board(Board.builder().id(saveDTO.getBoardId()).build()) // 바로 빌더. 생성시점따라 오류가능성있음
                .user(User.builder().id(sessionUserId).build())
                .build();

        replyRepository.save(reply);
    }

}
