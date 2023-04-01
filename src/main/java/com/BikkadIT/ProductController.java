package com.BikkadIT;

import com.BikkadIT.entity.Product;
import com.BikkadIT.helper.MyExcelHelper;
import com.BikkadIT.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/product/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

        if (MyExcelHelper.checkExcelFormat(file)) {
            this.productService.save(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved in db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please upload excel file only");
    }

    @GetMapping("/product")
    public List<Product> getAllProducts() {

        return this.productService.getAllProducts();
    }
}
