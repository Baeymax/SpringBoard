package test.springboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import test.springboard.domain.User;
import test.springboard.domain.UserCheck;
import test.springboard.service.UserService;

@Controller // 사용자의 요청이 진입하는 지점이며 요청에 따라 어떤 처리를 할지 결정
public class UserController {
    private final UserService userService;

    @Autowired // 객체 생성할 때 해당 애노테이션이 붙은 곳으로 의존 주입을 시도
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/content/join") // HTTP GET 요청을 특정 핸들러 메소드에 맵핑하기 위한 annotation, 주소에 파라미터가 노출
    public String createUser() {
        return "content/join";
    }

    @PostMapping("/content/join") // HTTP POST 요청을 특정 핸들러 메소드에 맵핑하기 위한 annotation, 주소에 파라미터가 노출되지 않음
    public String create(UserForm form){
        User user = new User();
        user.setId(form.getId());
        user.setPassword(form.getPassword());
        user.setNickname(form.getNickname());
        user.setEmail(form.getEmail());

        userService.join(user);

        return "redirect:/";
    }

    @GetMapping("/content/login")
    public String loginUser() { return "content/login"; }

    @PostMapping("/content/login")
    public String login(UserLogin login){
        UserCheck usercheck = new UserCheck();
        usercheck.setId(login.getId());
        usercheck.setPassword(login.getPassword());

        userService.login(usercheck);

        return "cotent/loginpage";
    }
}
