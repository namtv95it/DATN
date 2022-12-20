package nem.com.repository;

import nem.com.entity.Slides;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlidesRepository extends JpaRepository<Slides, Integer> {
}