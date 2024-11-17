package thanhnt.ec.ecsb.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import thanhnt.ec.ecsb.dto.ProductDTO;
import thanhnt.ec.ecsb.dto.ProductImageDTO;
import thanhnt.ec.ecsb.model.Product;
import thanhnt.ec.ecsb.model.ProductImage;
import thanhnt.ec.ecsb.response.ProductResponse;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(Long id) throws Exception;
    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);
    Product updateProduct(Long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(Long id) throws Exception;
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;
    List<Product> findProductsByIds(List<Long> productIds);
}
