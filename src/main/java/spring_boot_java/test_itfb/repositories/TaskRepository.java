package spring_boot_java.test_itfb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_java.test_itfb.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
