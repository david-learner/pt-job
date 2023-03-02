package com.david.ptjob.item.ui;

import com.david.ptjob.item.service.ItemService;
import com.david.ptjob.item.service.dto.GettingItemRequest;
import com.david.ptjob.item.service.dto.GettingItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;
    @GetMapping("/api/items")
    public ResponseEntity<GettingItemResponse> findItem(GettingItemRequest request) {
        return ResponseEntity.ok(itemService.findItem(request));
    }
}
