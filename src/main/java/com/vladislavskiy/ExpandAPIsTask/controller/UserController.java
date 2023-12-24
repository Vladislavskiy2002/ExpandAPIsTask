package com.vladislavskiy.ExpandAPIsTask.controller;
import com.vladislavskiy.ExpandAPIsTask.model.UserInfo;
import com.vladislavskiy.ExpandAPIsTask.service.UserInfoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserInfoService service;

    public UserController(@Qualifier("userInfoService") UserInfoService service) {
        this.service = service;
    }

    @GetMapping("/isUserExist/{username}")
    public String isUserWithNameExist(@PathVariable String username) {
        return service.checkUserOnExistByUsername(username).toString();
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

}
