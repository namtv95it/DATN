package nem.com.service;

import nem.com.domain.request.ProductDetailsDTO;
import nem.com.domain.request.ServiceResult;
import nem.com.domain.response.ProductDetailResponseDTO;
import nem.com.entity.ProductsDetails;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductDetailService {

    List<ProductsDetails> getAll();

    ByteArrayInputStream dowloadExcel()throws IOException;

    ProductsDetails getOne(Integer id);

    ProductsDetails update(ProductsDetails productsDetails);

    void delete(Integer id);

    ProductsDetails save(ProductsDetails productsDetails);

    List<ProductDetailsDTO> getByIdProduct(Integer id);
    List<ProductsDetails> findProductsDetailsByProductId(Integer productId);

    ProductsDetails findProductDetailBySizeAndColor(Integer productId, Integer sizeId, Integer colorId);
    List<ProductDetailResponseDTO> createProductDetails(List<ProductDetailResponseDTO> list);

    List<ProductsDetails> findProductDetailByProductSizeColor(ProductDetailResponseDTO productViewDto);

    ProductsDetails getByBarcode(String barcode);

    ServiceResult<?> generateBarcode(Integer id);
}
