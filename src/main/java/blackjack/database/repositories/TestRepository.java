package blackjack.database.repositories;

import blackjack.database.tables.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

    Test findById(Integer id);
}
