package task.ing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.ing.model.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByIsDeletedFalse();

    Optional<Account> findByIdAndIsDeletedFalse(Integer id);

    Optional<Account> findByIban(String iban);

}
