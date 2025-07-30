package com.demo.ssrspringthyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Home");
        model.addAttribute("metaDescription", "Welcome to SSR Spring Thyme Demo Application");
        // canonicalUrl remain as default (current URL)
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About");
        model.addAttribute("metaDescription", "Learn more about server-side rendered Spring Thymeleaf Application");
        return "about";
    }
}