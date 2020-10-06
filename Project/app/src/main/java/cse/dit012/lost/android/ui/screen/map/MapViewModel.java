package cse.dit012.lost.android.ui.screen.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.user.User;
import cse.dit012.lost.service.UserInfoService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * View model handling data to be displayed on the map screen.
 * Author: Benjamin Sannholm, Bashar Oumari
 */
public final class MapViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = BroadcastRepository.get();
    private final MutableLiveData<String> courseCode = new MutableLiveData<>("");
    public void setCourseCode(String course) {
        courseCode.setValue(checkNotNull(course));
    }
    User user = new User("1","2");
    /**
     * Retrieves an immutable live list of all currently active broadcasts.
     *
     * @return the list of broadcasts
     */
    public LiveData<List<Broadcast>> getActiveBroadcastsFilteredByCourse() {
        MediatorLiveData<List<Broadcast>> mediatorLiveDataMerger = new MediatorLiveData<>();

        LiveData<List<Broadcast>> activeBroadcasts = broadcastRepository.getActiveBroadcasts();

        mediatorLiveDataMerger.addSource(activeBroadcasts, broadcasts -> {
            mediatorLiveDataMerger.setValue(filterBroadcastsOnCourse(activeBroadcasts, courseCode));
        });
        mediatorLiveDataMerger.addSource(courseCode, broadcasts -> {
            mediatorLiveDataMerger.setValue(filterBroadcastsOnCourse(activeBroadcasts, courseCode));
        });

        return mediatorLiveDataMerger;
    }

    private static List<Broadcast> filterBroadcastsOnCourse(LiveData<List<Broadcast>> broadcasts, LiveData<String> courseCode) {
        if (broadcasts.getValue() == null || courseCode.getValue() == null) {
            return Collections.emptyList();
        }

        boolean noCourseSelected = courseCode.getValue().isEmpty();
        if (noCourseSelected) {
            return broadcasts.getValue();
        }

        List<Broadcast> filteredBroadcasts = new ArrayList<>();
        for (Broadcast broadcast : broadcasts.getValue()) {
            if (broadcast.getCourse().toString().equals(courseCode.getValue())) {
                filteredBroadcasts.add(broadcast);
            }
        }
        return filteredBroadcasts;
    }
    public User getUser(){
        return user;
    }
}