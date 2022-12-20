package nem.com.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellingDTO {
    private Long id;

    private Double totalPrice;

    private String note;

    private Integer customer;

    private List<ProductDetailsDTO> orderDetail;

    private Double discount;

    private Integer employee;

    private Double freight;

    private String shipName;

    private String shipAddress;

    private String shipPhone;

    private Integer checkSelling;
}
