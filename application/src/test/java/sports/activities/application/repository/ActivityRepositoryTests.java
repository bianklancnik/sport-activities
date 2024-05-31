package sports.activities.application.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import sports.activities.application.model.Activity;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ActivityRepositoryTests {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void ActivityRepository_CreateActivity_ReturnSavedActivity() {

        Activity activity = new Activity("Basketball", "Put the ball in basket", 2, "bian");

        Activity savedActivity = this.activityRepository.save(activity);

        Assertions.assertThat(savedActivity).isNotNull();
        Assertions.assertThat(savedActivity.getId()).isGreaterThan(0);
    }

    @Test
    public void ActivityRepository_GetActivityById_ReturnActivityNotNull() {

        Activity activityToSave = new Activity("Basketball", "Put the ball in basket", 5,
                "bian");

        activityRepository.save(activityToSave);

        Activity activity = activityRepository.findById(activityToSave.getId()).get();

        Assertions.assertThat(activity).isNotNull();
    }

    @Test
    public void ActivityRepository_UpdateActivity_ReturnActivityNotNull() {

        Activity activityToSave = new Activity("Basketball", "Put the ball in basket", 5,
                "bian");

        activityRepository.save(activityToSave);

        Activity activitySave = activityRepository.findById(activityToSave.getId()).get();

        activitySave.setAssessment(3);
        activitySave.setDescription("Put the ball in the basket");

        Activity updatedActivity = activityRepository.save(activitySave);

        Assertions.assertThat(updatedActivity.getAssessment()).isNotNull();
        Assertions.assertThat(updatedActivity.getDescription()).isNotNull();
    }

    @Test
    public void ActivityRepository_DeleteActivity_ReturnActivityIsEmpty() {

        Activity activityToSave = new Activity("Basketball", "Put the ball in basket", 5,
                "bian");

        activityRepository.save(activityToSave);

        activityRepository.deleteById(activityToSave.getId());

        Optional<Activity> activity = activityRepository.findById(activityToSave.getId());

        Assertions.assertThat(activity).isEmpty();
    }

    @Test
    public void ActivityRepository_FindByUsername_ReturnActivityNotNull() {

        Activity activity = new Activity("Basketball", "Put the ball in basket", 5,
                "bian");

        activityRepository.save(activity);

        List<Activity> usersActivities = activityRepository.findByUsernameContaining("bian");

        Assertions.assertThat(usersActivities).isNotNull();

    }

    @Test
    public void ActivityRepository_GetUsersActivities_ReturnAllUsersActivities() {

        Activity activity = new Activity("Basketball", "Put the ball in basket", 5,
                "bian");
        Activity activity2 = new Activity("Football", "Put the ball in net", 2,
                "bian");
        Activity activity3 = new Activity("Tenis", "Put the ball over net", 3,
                "miha");

        activityRepository.save(activity);
        activityRepository.save(activity2);
        activityRepository.save(activity3);

        List<Activity> usersActivities = activityRepository.findByUsernameContaining("bian");

        Assertions.assertThat(usersActivities).isNotNull();
        Assertions.assertThat(usersActivities.size()).isEqualTo(2);

    }
}
