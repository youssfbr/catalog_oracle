package com.github.youssf.catalog.services;

import com.github.youssf.catalog.dto.ProductDTO;
import com.github.youssf.catalog.entities.Product;
import com.github.youssf.catalog.repositories.ProductRepository;
import com.github.youssf.catalog.services.exceptions.DatabaseException;
import com.github.youssf.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(final ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> list = repository.findAll();

        return list.stream().map(cat -> {
            return new ProductDTO(cat, cat.getCategories());
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);

        return list.map(cat -> {
            return new ProductDTO(cat, cat.getCategories());
        });
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(long id, ProductDTO dto) {
         return repository.findById(id)
                .map(entity -> {
                    entity.setName(dto.getName());
                    entity = repository.save(entity);

                    return new ProductDTO(entity);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Id Resource " + id + " not found"));
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id Resource " + id + " not found");
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }
    }

}
