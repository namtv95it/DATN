package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeGhn {
    private int mainService;
    private int insurance;
    private int stationDo;
    private int stationPu;
    private int returns;
    private int r2s;
    private int coupon;
}
