package nem.com.service;
import nem.com.domain.request.ProductDetailsDTO;
import nem.com.domain.request.SellingDTO;
import nem.com.domain.request.ServiceResult;

import java.util.List;

public interface SellingService {
    ServiceResult<?> selling(SellingDTO sellingDTO);

    List<ProductDetailsDTO> resetQuantityInventory(List<Long> lstId);

}
