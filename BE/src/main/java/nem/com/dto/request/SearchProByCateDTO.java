package nem.com.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchProByCateDTO {
    private Short category ;
    private Double startPrice ;
    private Double endPrice ;
}
