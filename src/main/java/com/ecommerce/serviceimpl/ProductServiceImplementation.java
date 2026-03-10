package com.ecommerce.serviceimpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductResponse createproduct(ProductRequest request, MultipartFile image) throws IOException {

		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setSku(request.getSku());
		product.setImageData(image.getBytes());
		product.setAvailability(request.getAvailability());

		productRepository.save(product);
		return mapToResponse(product);
	}

	@Override
	public ProductResponse getproduct(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		return mapToResponse(product);
	}


	@Override
	public void deleteProduct(Long productId) {
		if (!productRepository.existsById(productId)) {
			throw new ResourceNotFoundException("Prodcut not found");
		}
		productRepository.deleteById(productId);
	}

	@Override
	@Transactional
	public PageResponse<ProductResponse> searchProducts(String keyword, int page) {

		Pageable pageable = PageRequest.of(page, 10);

		Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
		PageResponse<ProductResponse> response = new PageResponse<>();

		response.setContent(productPage.getContent().stream().map(this::mapToResponse).toList());

		response.setPage(productPage.getNumber());
		response.setSize(productPage.getSize());
		response.setTotalPages(productPage.getTotalPages());
		response.setTotalElements(productPage.getTotalElements());

		return response;
	}

	private ProductResponse mapToResponse(Product product) {

		ProductResponse response = new ProductResponse();
		response.setProductId(product.getProductId());
		response.setName(product.getName());
		response.setDescription(product.getDescription());
		response.setPrice(product.getPrice());
		response.setSku(product.getSku());
		response.setAvailability(product.getAvailability());

		if (product.getImageData() != null) {
			response.setImageUrl("/api/products/image/" + product.getProductId());
		}

		return response;

	}

	@Override
	public ProductResponse updateProduct(Long productId, String name, String description, Double price, String sku,
			Boolean availability, MultipartFile image) throws IOException {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		if (name != null) {
			product.setName(name);
		}

		if (description != null) {
			product.setDescription(description);
		}

		if (price != null) {
			product.setPrice(price);
		}

		if (sku != null) {
			product.setSku(sku);
		}

		if (image != null && !image.isEmpty()) {
			product.setImageData(image.getBytes());
		}

		if (availability != null) {
			product.setAvailability(availability);
		}

		productRepository.save(product);

		return mapToResponse(product);
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(this::mapToResponse).toList();
	}

}
