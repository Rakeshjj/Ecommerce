package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;
	private ProductRepository productRepository;

	@Autowired
	public ProductController(ProductService productService, ProductRepository productRepository) {
		super();
		this.productService = productService;
		this.productRepository = productRepository;
	}

	@GetMapping
	public ResponseEntity<PageResponse<ProductResponse>> searchProducts(@RequestParam String q,
			@RequestParam(defaultValue = "0") int page) {

		PageResponse<ProductResponse> response = productService.searchProducts(q, page);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long productId) {
		ProductResponse productResponse = productService.getproduct(productId);
		return ResponseEntity.ok(productResponse);
	}

	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {

		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		return ResponseEntity.ok().header("Content-Type", "image/jpeg").body(product.getImageData());
	}
}
