package nem.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nem.com.entity.ExchangeImages;
import nem.com.entity.Exchanges;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDTO {
    private Exchanges exchanges;
    private List<ExchangeImages> exchangeImagesList;
}
