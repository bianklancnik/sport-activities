package sports.activities.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sports.activities.application.model.Activity;
import sports.activities.application.service.ActivityService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getActivitiesByUser(@RequestParam String username) {
        try {
            return new ResponseEntity<>(activityService.getActivitiesByUser(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/activities")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        try {
            return new ResponseEntity<>(activityService.createActivity(activity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/activities/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable("id") long id, @RequestParam String username,
            @RequestBody Activity activity) throws ErrorResponseException {
        try {
            Activity updatedActivity = activityService.updateActivity(id, username, activity);
            return new ResponseEntity<>(updatedActivity, HttpStatus.OK);
        } catch (ErrorResponseException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    @DeleteMapping("/activities/{id}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable("id") long id, @RequestParam String username) {
        try {
            activityService.deleteActivity(id, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ErrorResponseException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }
}
