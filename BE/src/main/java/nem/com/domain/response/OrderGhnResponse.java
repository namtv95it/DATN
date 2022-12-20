package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGhnResponse {
    private int code;
    private String codeMassageValue;
    private String message;
    private String messageDisplay;
    private OrderGhnData data;
}
