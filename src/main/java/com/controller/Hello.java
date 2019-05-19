package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Generated;

@Controller
public class Hello {
    @Value("${netAddress}")
    private String host;

    @RequestMapping("getHello.action")
    @ResponseBody
    public String getString() {
        return "hello";
    }

    @RequestMapping("start.action")
    @ResponseBody
    public String start() {
        return "hello";
    }

    @RequestMapping("getHost.action")
    @ResponseBody
    public String getHost() {
        return host;
    }
}
