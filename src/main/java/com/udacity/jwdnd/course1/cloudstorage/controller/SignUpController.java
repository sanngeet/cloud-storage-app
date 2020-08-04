package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUpView() {
        return "signup";
    }

    @PostMapping
    public String signUpUser(@NonNull @ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {

        String signupError = null;

        if (!this.userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            if (userService.createUser(user) < 1) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:login";
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }
    }

    @PostMapping("/")
    public String redirectLogin() {
        return "redirect:login";
    }
}
