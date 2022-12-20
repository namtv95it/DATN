package nem.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchOrderDTO {
    private String fullName;
    private Long id;
    private String orderCode;
    private int status;
}
