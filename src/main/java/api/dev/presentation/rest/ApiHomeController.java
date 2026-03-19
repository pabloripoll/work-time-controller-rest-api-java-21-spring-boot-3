package api.dev.presentation.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiHomeController {

    @GetMapping("/api")
    public ResponseEntity<Map<String, String>> apiHome() {
        return ResponseEntity
                .status(200)
                .body(Map.of("message", "API up and running."));
    }

    @GetMapping("/api/v1")
    public ResponseEntity<Map<String, String>> apiV1() {
        return ResponseEntity
                .status(200)
                .body(Map.of("message", "API version 1 is released."));
    }

    @GetMapping("/api/v2")
    public ResponseEntity<Map<String, String>> apiV2() {
        return ResponseEntity
                .status(200)
                .body(Map.of("message", "No API version 2 is available."));
    }
}
