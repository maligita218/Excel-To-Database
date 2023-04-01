package com.BikkadIT.service;

import com.BikkadIT.entity.Product;
import com.BikkadIT.helper.MyExcelHelper;
import com.BikkadIT.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void save(MultipartFile file) {
        try {
            List<Product> products = MyExcelHelper.convertExcelToListOfProduct(file.getInputStream());

            List<Product> productList = products.stream().filter(prod -> !prod.getProductDesc().isEmpty()
                    && !prod.getProductName().isEmpty()
                    && prod.getProductId() != 0
                    && prod.getProductPrice() >= 1).collect(Collectors.toList());

            this.productRepository.saveAll(productList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {

        return this.productRepository.findAll();

    }
}
