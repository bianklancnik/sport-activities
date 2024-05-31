package sports.activities.application.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import sports.activities.application.model.Activity;
import sports.activities.application.service.ActivityService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ActivityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ActivityService activityService;

    private Activity activity;
    private Activity updateActivity;

    @BeforeEach
    public void init() {
        activity = new Activity("Basketball", "Put the ball in net", 5, "bian");
        updateActivity = new Activity("Basketball", "Put the ball in net!", 5, "bian");
    }

    @Test
    public void ActivityController_CreateActivity_ReturnCreated() throws Exception {
        given(activityService.createActivity(ArgumentMatchers.any()))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions result = mockMvc.perform(post("/api/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(activity)));

        result.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(activity.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(activity.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assessment", CoreMatchers.is(activity.getAssessment())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(activity.getUsername())));
    }

    @Test
    public void ActivityController_GetUserActivities_ReturnActivityList() throws Exception {
        List<Activity> activityList = new ArrayList<>(Arrays.asList(activity));
        when(activityService.getActivitiesByUser("bian")).thenReturn(activityList);

        ResultActions result = mockMvc.perform(get("/api/activities")
                .param("username", "bian")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(activityList.size())));
    }

    @Test
    public void ActivityController_UpdateActivity_ReturnActivity() throws Exception {
        int id = 1;
        when(activityService.updateActivity(id, "bian", activity)).thenReturn(activity);

        ResultActions result = mockMvc.perform(put("/api/activities/{id}", id)
                .param("username", "bian")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateActivity)));

        result.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        // .andExpect(MockMvcResultMatchers.jsonPath("$.name",
        // CoreMatchers.is(activity.getName())))
        // .andExpect(MockMvcResultMatchers.jsonPath("$.description",
        // CoreMatchers.is(activity.getDescription())))
        // .andExpect(MockMvcResultMatchers.jsonPath("$.assessment",
        // CoreMatchers.is(activity.getAssessment())))
        // .andExpect(MockMvcResultMatchers.jsonPath("$.username",
        // CoreMatchers.is(activity.getUsername())));
    }

    @Test
    public void ActivityController_DeleteActivity_ReturnStatus() throws Exception {
        int id = 1;
        doNothing().when(activityService).deleteActivity(id, "bian");

        ResultActions result = mockMvc.perform(delete("/api/activities/1")
                .param("username", "bian")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

}
