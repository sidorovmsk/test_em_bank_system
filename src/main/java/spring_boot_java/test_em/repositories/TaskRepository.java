package spring_boot_java.test_em.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_java.test_em.models.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByAuthorId(int id, Pageable pageable);

    List<Task> findAllByAssigneeId(int id, Pageable pageable);
}
