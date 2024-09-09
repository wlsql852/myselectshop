package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.ProductReqository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductReqository productReqository;

    public static final int MIN_MY_PRICE = 100;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productReqository.save(new Product(requestDto));
        return new ProductResponseDto(product);

    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        if(myprice < MIN_MY_PRICE) throw new IllegalArgumentException("유효하지 않은 관심가격입니다. 최소 "+MIN_MY_PRICE+"원 이상으로 설정해 주세요.");
        Product product = productReqository.findById(id).orElseThrow(()-> new NullPointerException("해당 상품을 찾을 수 없습니다."));
        product.update(requestDto);
        return new ProductResponseDto(product);


    }

    public List<ProductResponseDto> getProducts() {
        List<Product> productList = productReqository.findAll();
        List<ProductResponseDto> responseDtoList = new ArrayList<>();
        for (Product product : productList) {
            responseDtoList.add(new ProductResponseDto(product));
        }
        return responseDtoList;
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productReqository.findById(id).orElseThrow(()-> new NullPointerException("해당 상품은 존재하지 않습니다."));
        product.updateByItemDto(itemDto);
    }
}
