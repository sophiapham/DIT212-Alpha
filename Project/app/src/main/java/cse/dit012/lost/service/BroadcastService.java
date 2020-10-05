package cse.dit012.lost.service;

import android.content.Context;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.User;
import java9.util.concurrent.CompletableFuture;

/**
 * Service responsible for preforming tasks related to broadcasts.
 * Author: Benjamin Sannholm, Sophia Pham
 */
public interface BroadcastService {
    static BroadcastService get() {
        return new BroadcastServiceImpl(BroadcastRepository.get());
    }

    /**
     * Creates a new broadcast placed at the given coordinates for a specific course and with a given description.
     *
     * @param coordinates coordinates of broadcast
     * @param courseCode  the course code of the course the broadcast is for
     * @param description the description of the broadcast
     * @return the newly created broadcast
     */
    CompletableFuture<Broadcast> createBroadcast(MapCoordinates coordinates, User user, CourseCode courseCode, String description);

    void startActiveBroadcastService(Context context, BroadcastId id);

    CompletableFuture<Broadcast> updateBroadcastLastActive(BroadcastId id);

    CompletableFuture<Void> updateBroadcastEdit(BroadcastId id, CourseCode course, String description);
}