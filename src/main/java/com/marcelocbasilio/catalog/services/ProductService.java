package com.marcelocbasilio.catalog.services;

import com.marcelocbasilio.catalog.dto.CategoryDto;
import com.marcelocbasilio.catalog.dto.ProductDto;
import com.marcelocbasilio.catalog.entities.Category;
import com.marcelocbasilio.catalog.entities.Product;
import com.marcelocbasilio.catalog.repositories.CategoryRepository;
import com.marcelocbasilio.catalog.repositories.ProductRepository;
import com.marcelocbasilio.catalog.services.exceptions.DatabaseException;
import com.marcelocbasilio.catalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        // map(ProductDto::new) = map(x -> new ProductDto(x))
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductDto::new);
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDto(product, product.getCategories());
    }

    @Transactional
    public ProductDto insert(ProductDto productDto) {
        Product product = new Product();
        copyDtoToEntity(productDto, product);
        product = productRepository.save(product);
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto productDto) {
        try {
            Product product = productRepository.getReferenceById(id);
            copyDtoToEntity(productDto, product);
            product = productRepository.save(product);
            return new ProductDto(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure!");
        }
    }

    private void copyDtoToEntity(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setDate(productDto.getDate());
        product.setImgUrl(productDto.getImgUrl());
        product.setPrice(productDto.getPrice());

        product.getCategories().clear();
        for (CategoryDto categoryDto : productDto.getCategories()) {
            Category category = categoryRepository.getReferenceById(categoryDto.getId());
            product.getCategories().add(category);
        }
    }

}
