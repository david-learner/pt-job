package com.david.ptjob.item.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping("/getting-item-price-form")
    public String gettingItemPriceForm() {
        return "item/getting-item-price";
    }
}
