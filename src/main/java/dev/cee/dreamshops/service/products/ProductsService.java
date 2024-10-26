package dev.cee.dreamshops.service.products;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.cee.dreamshops.dtos.AddProductDto;
import dev.cee.dreamshops.dtos.ImageDto;
import dev.cee.dreamshops.dtos.ProductResponseDto;
import dev.cee.dreamshops.dtos.ProductUpdateRequestDto;
import dev.cee.dreamshops.exceptions.AlreadyExistException;
import dev.cee.dreamshops.exceptions.ResourceNotFoundException;
import dev.cee.dreamshops.model.Category;
import dev.cee.dreamshops.model.Image;
import dev.cee.dreamshops.model.Product;
import dev.cee.dreamshops.repository.image.ImageRepository;
import dev.cee.dreamshops.repository.product.ProductRepository;
import dev.cee.dreamshops.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductsService implements IproductService {


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    private final ImageRepository imageRepository;


    public Product addProduct(AddProductDto request) {

        if (productExist(request.getName(), request.getBrand())) {
            throw new AlreadyExistException("Product " + request.getName() + " " + request.getBrand() + " already exist");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        // Ensure the correct category is set in the request DTO
        request.setCategory(category);

        // Save and return the product
        return productRepository.save(createProduct(request, category));
    }


    private Product createProduct(AddProductDto request, Category category) {

        Product newProduct = new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );


        return newProduct;
    }

    private boolean productExist(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequestDto productUpdateRequestDto, Long productId) {
        return productRepository.findById(productId)
                .map(existingProd -> updateExistingProduct(existingProd, productUpdateRequestDto))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequestDto productUpdateRequestDto) {

        existingProduct.setName(productUpdateRequestDto.getName());
        existingProduct.setBrand(productUpdateRequestDto.getBrand());
        existingProduct.setPrice(productUpdateRequestDto.getPrice());
        existingProduct.setInventory(productUpdateRequestDto.getInventory());
        existingProduct.setDescription(productUpdateRequestDto.getDescription());

        Category newCategory = new Category(productUpdateRequestDto.getCategory().getName());
        existingProduct.setCategory(newCategory);

        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository
                .findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brandName) {
        return productRepository.findByBrand(brandName);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brandName) {
        return productRepository.findByCategoryNameAndBrand(category, brandName);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brandName, String productName) {
        return productRepository.findByBrandAndName(brandName, productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brandName, String productName) {
        return productRepository.countByBrandAndName(brandName, productName);
    }

    @Override
    public List<ProductResponseDto> getConvertedProducts(List<Product> products) {
        return products
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ProductResponseDto convertToDto(Product product) {
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);

        List<Image> images = imageRepository.findByProductId(product.getId());

        List<ImageDto> imageDtos = images
                .stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productResponseDto.setImages(imageDtos);

        return productResponseDto;

    }
}
