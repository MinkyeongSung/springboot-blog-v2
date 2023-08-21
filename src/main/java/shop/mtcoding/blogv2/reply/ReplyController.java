package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping("/reply/{id}/delete")
    public @ResponseBody String delete(@PathVariable Integer id) {
        replyService.삭제하기(id);
        return Script.href("/board/"+id);
        // return Script.href("redirect:/board/" + id);
        // return "redirect:/board/" + id;
    }

    @PostMapping("/reply/{id}/save")
    public String save(ReplyRequest.SaveDTO saveDTO, @PathVariable Integer id) {
        replyService.댓글쓰기(saveDTO, id);
        // return Script.href("/board/"+saveDTO.getBoardId());
        return "redirect:/board/"+id;
    }
}
