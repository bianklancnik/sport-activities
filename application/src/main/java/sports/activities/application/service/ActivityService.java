package sports.activities.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import sports.activities.application.model.Activity;
import sports.activities.application.repository.ActivityRepository;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public List<Activity> getActivitiesByUser(String username) {
        List<Activity> activities = new ArrayList<>();

        activityRepository.findByUsernameContaining(username).forEach(activities::add);

        Collections.sort(activities, (act1, act2) -> act2.getAssessment() - act1.getAssessment());

        return activities;
    }

    public Activity createActivity(Activity activity) {
        Activity saveActivity = activityRepository
                .save(new Activity(activity.getName(), activity.getDescription(), activity.getAssessment(),
                        activity.getUsername()));
        return saveActivity;
    }

    public void deleteActivity(long id, String username) {
        Optional<Activity> activityData = activityRepository.findById(id);

        if (activityData.isPresent()) {
            Activity deleteActivity = activityData.get();
            if (!username.equals(deleteActivity.getUsername())) {
                throw new ErrorResponseException(HttpStatus.NOT_FOUND);
            }
        }
        activityRepository.deleteById(id);
    }

    public Activity updateActivity(long id, String username,
            Activity activity) throws ErrorResponseException {
        Optional<Activity> activityData = activityRepository.findById(id);

        if (activityData.isPresent()) {
            Activity updateActivity = activityData.get();
            if (!username.equals(updateActivity.getUsername())) {
                throw new ErrorResponseException(HttpStatus.UNAUTHORIZED);
            }
            updateActivity.setName(activity.getName());
            updateActivity.setDescription(activity.getDescription());
            updateActivity.setAssessment(activity.getAssessment());
            return activityRepository.save(updateActivity);
        } else {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
    }

}
