package io.github.application.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
class LangController {
    private final Logger logger = LoggerFactory.getLogger(LangController.class);

    private LangService service;

    LangController(LangService service) {
        this.service = service;
    }

    @GetMapping("/langs")
    ResponseEntity<List<LangDTO>> findAllLangs() {
        logger.info("Got request");
        return ResponseEntity.ok(service.findAll());
    }
}
