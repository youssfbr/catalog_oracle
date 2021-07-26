package com.github.youssf.catalog.services;

import com.github.youssf.catalog.entities.Category;
import com.github.youssf.catalog.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(final CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return  repository.findAll();
    }

}
