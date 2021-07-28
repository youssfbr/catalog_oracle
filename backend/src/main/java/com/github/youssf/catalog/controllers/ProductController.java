package com.github.youssf.catalog.controllers;

import com.github.youssf.catalog.dto.ProductDTO;
import com.github.youssf.catalog.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(final ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ok(service.findAll());
    }

    @GetMapping
    @RequestMapping("/paged")
    public ResponseEntity<Page<ProductDTO>> findAllPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return ok(service.findAllPaged(pageRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> findAll(@PathVariable Long id) {
        return ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        dto = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return created(uri).body(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable long id, @RequestBody ProductDTO dto) {

        return ok(service.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {

        service.delete(id);

        return noContent().build();
    }

}
