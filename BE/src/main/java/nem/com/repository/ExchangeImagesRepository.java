package nem.com.repository;

import nem.com.entity.ExchangeImages;
import nem.com.entity.Exchanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeImagesRepository extends JpaRepository<ExchangeImages, Integer> {
    List<ExchangeImages> findByExchange_Id(Integer exchange_id);
}
