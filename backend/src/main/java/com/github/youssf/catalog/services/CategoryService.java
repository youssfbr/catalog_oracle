package com.github.youssf.catalog.services;

import com.github.youssf.catalog.dto.CategoryDTO;
import com.github.youssf.catalog.entities.Category;
import com.github.youssf.catalog.repositories.CategoryRepository;
import com.github.youssf.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();

        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        return new CategoryDTO(entity);
    }

}
