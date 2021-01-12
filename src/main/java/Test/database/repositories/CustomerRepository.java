package Test.database.repositories;

import Test.database.tables.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByFirstName(String Firstname);
    List<Customer> findAll();
}
