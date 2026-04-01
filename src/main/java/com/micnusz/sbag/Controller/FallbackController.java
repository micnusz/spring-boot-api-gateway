package com.micnusz.sbag.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("fallback/test")
public class FallbackController {

    @GetMapping("/fallback/test")
    public ResponseEntity<Map<String, Object>> fallback() {
        return ResponseEntity.status(503).body(
                Map.of(
                        "status", "fallback",
                        "message", "Service degraded",
                        "source", "gateway"
                )
        );
    }
}
