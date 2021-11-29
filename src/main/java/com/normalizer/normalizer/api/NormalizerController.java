package com.normalizer.normalizer.api;

import com.normalizer.normalizer.features.normalizer.exception.InvalidJobTitleException;
import com.normalizer.normalizer.features.normalizer.service.NormalizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/normalizer")
public class NormalizerController {

    private NormalizerService normalizerService;

    @Autowired
    void setNormalizerService(NormalizerService normalizerService) {
        this.normalizerService = normalizerService;
    }

    @GetMapping("/singleJobTitle")
    public ResponseEntity<?> normalizeSingleJobTitle(@RequestParam String jobTitle) {
        try {
            return new ResponseEntity<>(normalizerService.normalizeJobTitle(jobTitle), HttpStatus.OK);
        } catch (InvalidJobTitleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
