package es.upm.miw.devops.rest;

import es.upm.miw.devops.model.User;
import es.upm.miw.devops.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User read (@PathVariable long id){
        return userService.read(id);
    }

    @GetMapping
    public List<User> find(@RequestParam(required = false) String city, @RequestParam(required = false) String province,
                           @RequestParam(required = false) Boolean billable) {
        return userService.find(city, province, billable);
    }
}
