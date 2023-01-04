package test.springboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @GetMapping("/content/list")
    public String BoardList() { return "content/list"; }

    @GetMapping("/content/view")
    public String BoardView() { return "content/view"; }

    @GetMapping("/content/write")
    public String BoardWrite() { return "content/write"; }

    @GetMapping("/content/edit")
    public String BoardEdit() { return "content/edit"; }

}
