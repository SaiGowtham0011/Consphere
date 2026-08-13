package com.project.consphere.controller;

import com.project.consphere.dto.CreateFilterRequest;
import com.project.consphere.dto.FilterResponse;
import com.project.consphere.dto.PostResponse;
import com.project.consphere.dto.UpdateFilterRequest;
import com.project.consphere.service.FilterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filters")
public class FilterController {

    @Autowired
    private FilterService filterService;

    @PostMapping
    public ResponseEntity<FilterResponse> createFilter(@Valid @RequestBody CreateFilterRequest request) {
        FilterResponse response = filterService.createFilter(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FilterResponse>> getAllFilters() {
        List<FilterResponse> response = filterService.getAllFilters();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-filters")
    public ResponseEntity<List<FilterResponse>> getMyFilters() {
        List<FilterResponse> response = filterService.getMyFilters();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/built-in")
    public ResponseEntity<List<FilterResponse>> getBuiltInFilters() {
        List<FilterResponse> response = filterService.getBuiltInFilters();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilterResponse> getFilterById(@PathVariable Long id) {
        FilterResponse response = filterService.getFilterById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilterResponse> updateFilter(@PathVariable Long id, @Valid @RequestBody UpdateFilterRequest request) {
        FilterResponse response = filterService.updateFilter(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        filterService.deleteFilter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/feed")
    public ResponseEntity<List<PostResponse>> getFilterFeed(@PathVariable Long id) {
        List<PostResponse> response = filterService.getFilterFeed(id);
        return ResponseEntity.ok(response);
    }
}
