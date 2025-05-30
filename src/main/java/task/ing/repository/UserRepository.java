package task.ing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.ing.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByIsDeletedFalse();

    Optional<User> findByIdAndIsDeletedFalse(Integer id);

    List<User> findAllByIsDeletedTrue();
}
