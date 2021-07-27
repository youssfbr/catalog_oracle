package com.github.youssf.catalog.controllers;

import com.github.youssf.catalog.dto.CategoryDTO;
import com.github.youssf.catalog.entities.Category;
import com.github.youssf.catalog.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(final CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> findAll(@PathVariable Long id) {
        return ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
        dto = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return created(uri).body(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable long id, @RequestBody CategoryDTO dto) {

        return ok(service.update(id, dto));
    }

}
