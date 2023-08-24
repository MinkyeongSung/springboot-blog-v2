package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    

    // 브라우저 GET /logout 요청을 함 (request 1)
    // 서버는 / 주소를 응답의 헤더에 담음 (Location), 상태코드 302
    // 브라우저는 GET / 로 재요청을 함 (request 2)
    // index 페이지 응답받고 렌더링함
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // C - V
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        // System.out.println(joinDTO.getPic().getOriginalFilename());
        // System.out.println(joinDTO.getPic().getSize());
        // System.out.println(joinDTO.getPic().getContentType());

        // 핵심로직(바이트화)
        userService.회원가입(joinDTO);
        
        return "user/loginForm"; // persist 초기화
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    @GetMapping("/api/check")
    public @ResponseBody ApiUtil<String> check(String username) {
        if (username == "") {
            throw new MyApiException("사용할 수 없는 이름입니다");
        }

        userService.중복체크(username); // ?????????
        System.out.println("체크 : " + username);
        // if (username == false){
        // throw new MyApiException("중복된 이름입니다");
        // }

        return new ApiUtil<String>(true, "사용할 수 있는 이름입니다");
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        // 1. 회원수정 (서비스)
        // 2. 세션동기화
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }

    // @GetMapping("/check")
    // public ResponseEntity<String> check(String username) {
    // User user = userRepository.findByUsername(username);
    // if (user != null) {
    // throw new MyException("유저네임이 중복되었습니다.");
    // }
    // throw new MyException("유저네임을 사용할 수 있습니다");
    // }
}