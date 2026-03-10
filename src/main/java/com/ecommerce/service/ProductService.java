package com.ecommerce.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;

public interface ProductService {

	public PageResponse<ProductResponse> searchProducts(String keyword, int page);

	public ProductResponse createproduct(ProductRequest request, MultipartFile file) throws IOException;

	public ProductResponse getproduct(Long productId);

	public void deleteProduct(Long productId);

	public ProductResponse updateProduct(Long productId, String name, String description, Double price, String sku,
			Boolean availability, MultipartFile image) throws IOException;

	public List<ProductResponse> getAllProducts();

}
