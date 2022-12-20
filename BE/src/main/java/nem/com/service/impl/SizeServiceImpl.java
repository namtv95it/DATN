package nem.com.service.impl;

import nem.com.domain.response.SizeDTO;
import nem.com.entity.Sizes;
import nem.com.repository.SizesRepository;
import nem.com.service.SizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {
    private final SizesRepository sizesRepository;

    public SizeServiceImpl(SizesRepository sizesRepository) {
        this.sizesRepository = sizesRepository;
    }


    @Override
    public List<SizeDTO> getAll() {
        return this.sizesRepository.getSizes();
    }

    @Override
    public Sizes getOne(Integer id) {
        return this.sizesRepository.findById(id).get();
    }

    @Override
    public List<Sizes> findAllSizeInProductDetails(Integer productId) {
        return this.sizesRepository.findAllSizeInProductDetails(productId);
    }
}
