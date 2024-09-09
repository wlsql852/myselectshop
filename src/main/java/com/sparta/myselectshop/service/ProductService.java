package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductReqository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductReqository productReqository;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productReqository.save(new Product(requestDto));
        return new ProductResponseDto(product);

    }
}
