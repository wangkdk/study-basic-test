package study.springboot.basictest.cafe.beverage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    boolean existsByName(String name);
}
