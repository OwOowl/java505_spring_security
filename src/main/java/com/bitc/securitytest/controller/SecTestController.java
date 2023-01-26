package com.bitc.securitytest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sec")
public class SecTestController {
    @RequestMapping("")
    public String index() {
        return "sec/index";
    }

    @RequestMapping("/all")
    public String all() {
        return "sec/all";
    }

    @RequestMapping("/member")
    public String member() {
        return "sec/member";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "sec/admin";
    }
}
