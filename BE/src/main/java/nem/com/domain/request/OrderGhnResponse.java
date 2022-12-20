package nem.com.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nem.com.domain.dto.OrderGhnDTO;

@Getter
@Setter
@AllArgsConstructor
public class OrderGhnResponse {
    private String code;
    private String message;
    private OrderGhnDTO data;
}
