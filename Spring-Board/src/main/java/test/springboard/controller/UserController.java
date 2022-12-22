package test.springboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import test.springboard.domain.User;
import test.springboard.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String createForm() {
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(UserForm form){
        User user = new User();
        user.setId(form.getId());

        userService.join(user);

        return "redirect:/";
    }

}
