package sports.activities.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sports.activities.application.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
  List<Activity> findByUsernameContaining(String username);
}
