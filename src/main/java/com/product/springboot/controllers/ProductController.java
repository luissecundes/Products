package com.product.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.springboot.dtos.ProductRecordDto;
import com.product.springboot.models.ProductModel;
import com.product.springboot.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@PostMapping("/products")
	public ResponseEntity<ProductModel> saveProduct (@RequestBody @Valid ProductRecordDto productRecordDto){
	var productModel = new ProductModel();
	BeanUtils.copyProperties(productRecordDto, productModel);
	return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}
	
	@GetMapping("/products/all")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id){
		Optional<ProductModel> product0 = productRepository.findById(id);
		if(product0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o produto.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(product0.get());
	}
	
	@GetMapping("/products")
	public ResponseEntity<Object> getProductByName(@RequestParam(value = "name") String name) {
	    List<ProductModel> products = productRepository.findByNameContainingIgnoreCase(name);
	    if (products.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar produtos com o nome fornecido.");
	    }
	    return ResponseEntity.status(HttpStatus.OK).body(products);
	}

	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
		Optional<ProductModel> product0 = productRepository.findById(id);
		if(product0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o produto.");
		}
		var productModel = product0.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
	}
	
	@DeleteMapping("products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id){
		Optional<ProductModel> product0 = productRepository.findById(id);
		if(product0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o produto.");
		}
		productRepository.delete(product0.get());
		return ResponseEntity.status(HttpStatus.OK).body("Produto excluído com sucesso!");
	}
}










