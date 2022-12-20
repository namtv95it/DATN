package nem.com.repository;

import nem.com.domain.response.CategoryDTO;
import nem.com.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Short> {

    @Query("select new CategoryDTO(c.id , c.name , count(p.id) ) " +
            "from Categories c join Products p on c.id = p.category.id " +
            " where p.id in ( select pd.product.id from  ProductsDetails pd " +
            " where  pd.color.id is not null and pd.size.id is not null)"  +
            " group by c.id , c.name ")
    List<CategoryDTO> getCategories() ;

    @Query("select c from Categories c where c.status = 1")
    List<Categories> findAllByStatus();
}
