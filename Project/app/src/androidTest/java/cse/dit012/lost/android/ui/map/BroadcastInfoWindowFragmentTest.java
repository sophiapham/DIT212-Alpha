package cse.dit012.lost.android.ui.map;

import android.os.Bundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import cse.dit012.lost.R;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.User;
import cse.dit012.lost.service.BroadcastService;

import static androidx.fragment.app.testing.FragmentScenario.launchInContainer;
import static androidx.test.espresso.Espresso.onView;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_COURSE;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_DESCRIPTION;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_ID;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

public class BroadcastInfoWindowFragmentTest {

    User user = new User("Berta");
    MapCoordinates coordinates = new MapCoordinates(0, 0);
    String description = "test";
    CourseCode code = new CourseCode("DIT000");

    @Before //Adds a test-broadcast
    public void setup() throws ExecutionException, InterruptedException {
        BroadcastService broadcastService = BroadcastService.get();
        Broadcast broadcast = broadcastService.createBroadcast(coordinates, user, code, description).get();
        Bundle args = new Bundle();
        args.putString(PARAM_COURSE, broadcast.getCourse().toString());
        args.putString(PARAM_DESCRIPTION, broadcast.getDescription());
        args.putString(PARAM_ID, broadcast.getId().toString());
        launchInContainer(BroadcastInfoWindowFragment.class, args);
    }

    @After
    public void refresh() {
        //TODO remove the test-broadcast
    }

    //The course displayed should be the same as the one in the database
    @Test
    public void onViewCreatedCourse() {
        //onView(withId(R.id.course))
        // .check(code);
    }

    //The description displayed should be the same as the one in the database
    @Test
    public void onViewCreatedDescription() {

    }

    //The course should not change in the View after clicking the cancel button
    @Test
    public void cancelCourseInfoWindowView() {
        /*BroadcastInfoWindowFragment broadcastInfoWindowFragment = new BroadcastInfoWindowFragment();
        //TODO click cancel
        //broadcastInfoWindowFragment.getView().cancelInfoWindowButton;
        String courseAfterClick = broadcastInfoWindowFragment.getCourse();
        String courseExpected = "DIT000";
        assertEquals(courseExpected, courseAfterClick);*/
    }

    //The description should not change in the View after clicking the cancel button
    @Test
    public void cancelDescriptionInfoWindowView() {

    }

    //The course should change to the right course in the View after clicking the save button
    @Test
    public void saveCourseInfoWindowView() {

    }

    //The description should change to the right description in the View after clicking the save button
    @Test
    public void saveDescriptionInfoWindowView() {

    }


    //The course edited and then canceled should be not be changed in the database
    @Test
    public void cancelCourseInfoWindowDatabase() {

    }

    //The description edited and then canceled should be not be changed in the database
    @Test
    public void cancelDescriptionInfoWindowDatabase() {

    }


    //The course edited should be updated in the database
    @Test
    public void editCourseInfoWindowDatabase() {
        /*BroadcastInfoWindowFragment broadcastInfoWindowFragment = new BroadcastInfoWindowFragment();
        BroadcastService broadcastService = BroadcastService.get();
        String courseInput = "DIT123";
        //TODO get the updated value
        String courseUpdated = broadcastInfoWindowFragment.broadcastService.updateBroadcastEdit(new BroadcastId(id), new CourseCode(courseInput), "");
        String courseExpected = "DIT123";
        assertEquals(courseExpected, courseUpdated);*/
    }

    //The description edited should be updated in the database
    @Test
    public void editDescriptionInfoWindowDatabase() {
        /*BroadcastInfoWindowFragment broadcastInfoWindowFragment = new BroadcastInfoWindowFragment();
        BroadcastService broadcastService = BroadcastService.get();
        String descriptionInput = "I am studying here!";
        //TODO get the updated value
        String descriptionUpdated = broadcastInfoWindowFragment.broadcastService.updateBroadcastEdit(new BroadcastId(id), new CourseCode(""), descriptionInput);
        String descriptionExpected = "DIT123";
        assertEquals(descriptionExpected, descriptionUpdated);*/
    }


}