package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nem.com.entity.Colors;
import nem.com.entity.Products;
import nem.com.entity.Sizes;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponseDTO {
    Products product;
    Colors color;
    int quantity;
    Sizes size;
}
