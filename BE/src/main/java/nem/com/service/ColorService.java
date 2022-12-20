package nem.com.service;

import nem.com.domain.response.ColorDTO;
import nem.com.entity.Colors;

import java.util.List;

public interface ColorService {
    Colors save(Colors colors);

    Colors getOne(Integer id);

    List<ColorDTO> getAll();

    List<Colors> findAllColorInProductDetails(Integer productId);
}
