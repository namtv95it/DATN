package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGhnData {
    private String orderCode;
    private String sortCode;
    private String transType;
    private String wardEncode;
    private String districtEncode;
    private int totalFee;
    private LocalDate expectedDeliveryTime;
    private FeeGhn fee;

}
