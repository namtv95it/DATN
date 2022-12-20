package nem.com.service;

import nem.com.domain.response.SizeDTO;
import nem.com.entity.Sizes;

import java.util.List;

public interface SizeService {
    List<SizeDTO> getAll();
    Sizes getOne(Integer id);
    List<Sizes> findAllSizeInProductDetails(Integer productId);
}
