package vfafpacda24060.demerde.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vfafpacda24060.demerde.model.User;
import vfafpacda24060.demerde.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value={"", "/home"})
    public String home(Model model) {
        Iterable<User> listUsers = userService.findAll();
        model.addAttribute("listUsers", listUsers);
        return "home";
    }
}
