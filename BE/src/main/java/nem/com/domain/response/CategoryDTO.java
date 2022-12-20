package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryDTO {
    @Id
    private Short id ;
    private String name ;
    private Long quantity ;
}
