package thanhnt.ec.ecsb.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import thanhnt.ec.ecsb.dto.ProductDTO;
import thanhnt.ec.ecsb.dto.ProductImageDTO;
import thanhnt.ec.ecsb.exceptions.DataNotFoundException;
import thanhnt.ec.ecsb.exceptions.InvalidParamException;
import thanhnt.ec.ecsb.model.Category;
import thanhnt.ec.ecsb.model.Product;
import thanhnt.ec.ecsb.model.ProductImage;
import thanhnt.ec.ecsb.repositories.CategoryRepository;
import thanhnt.ec.ecsb.repositories.ProductImageRepository;
import thanhnt.ec.ecsb.repositories.ProductRepository;
import thanhnt.ec.ecsb.response.ProductResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.
                findById(productDTO.getCategoryId()).
                orElseThrow(() -> new DataNotFoundException(
                        "Cannot find category with id: " + productDTO.getCategoryId()
                ));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.getDetailProduct(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id =" + id);
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        Page<Product> productsPage;
        productsPage = productRepository.searchProducts(keyword, categoryId, pageRequest);
        return productsPage.map(ProductResponse::fromProduct);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            Category existingCategory = categoryRepository.
                    findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " +
                                            productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            productRepository.save(existingProduct);
        }
        return existingProduct;
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception {

        Product existingProduct = productRepository.
                findById(productId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id " + productId
                ));

        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        // No insert over 5 images for 1 product
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMG_UPLOAD) {
            throw new InvalidParamException(
                    "Number of images must be <= " +
                    ProductImage.MAXIMUM_IMG_UPLOAD
            );
        }
        return productImageRepository.save(newProductImage);
    }
}
