package com.weather.detaiils.web;

import com.weather.detaiils.model.FeatureFlag;
import com.weather.detaiils.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/flags")
public class FeatureFlagController {

    private final FeatureFlagService service;

    public FeatureFlagController(FeatureFlagService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<FeatureFlag> list() {
        return service.listAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<FeatureFlag> get(@PathVariable String name) {
        FeatureFlag f = service.listAll().stream()
                .filter(flag -> flag.name().equals(name))
                .findFirst().orElse(null);
        return f == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    // Admin toggle; in real apps protect this endpoint with auth
    @PostMapping("/{name}")
    public ResponseEntity<Void> set(@PathVariable String name, @RequestParam boolean enabled) {
        service.setFlag(name, enabled);
        return ResponseEntity.ok().build();
    }
}
