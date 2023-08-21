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
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(saveDTO.getBoardId())
                .user(User.builder().id(sessionUserId).build())
                .build();

        replyRepository.save(reply);
    }

}
