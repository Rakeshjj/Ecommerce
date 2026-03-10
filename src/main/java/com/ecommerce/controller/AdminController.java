package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("api/admin/products")
public class AdminController {

	private ProductService productService;

	@Autowired
	public AdminController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@RequestParam String name, @RequestParam String description,
			@RequestParam Double price, @RequestParam String sku, @RequestParam Boolean availability,
			@RequestParam MultipartFile image) throws IOException {
		ProductRequest request = new ProductRequest();
		request.setName(name);
		request.setDescription(description);
		request.setPrice(price);
		request.setSku(sku);
		request.setAvailability(availability);

		productService.createproduct(request, image);
		return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED.value(), "Product created.."));
	}

	@PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") Long productId,
			@RequestParam(required = false) String name, @RequestParam(required = false) String description,
			@RequestParam(required = false) Double price, @RequestParam(required = false) String sku,
			@RequestParam(required = false) Boolean availability, @RequestParam(required = false) MultipartFile image)
			throws IOException {

		productService.updateProduct(productId, name, description, price, sku, availability, image);
		return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Product updated successfully.."));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Product deleted successfully.."));

	}

	@GetMapping("/all")
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

}
