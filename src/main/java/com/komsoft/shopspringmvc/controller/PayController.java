package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pay")
public class PayController {


    @RequestMapping(method = RequestMethod.POST)
    public String doPay(ModelMap modelMap) {
        String view = Header.PAY_PAGE;
        modelMap.addAttribute(Header.PAY_MESSAGE, "You can pay your items on this page");
        return view;
    }

}
