package cse.dit012.lost.model.broadcast;

import androidx.lifecycle.LiveData;

import java.util.List;

import cse.dit012.lost.persistance.BroadcastRepositoryProvider;
import cse.dit012.lost.android.ui.screen.map.MapViewModel;
import cse.dit012.lost.persistance.firebase.FirebaseBroadcastRepository;
import java9.util.concurrent.CompletableFuture;

/**
 * Repository responsible for storing and retrieving information about broadcasts.
 * <p>
 * Author: Benjamin Sannholm
 * Uses: {@link Broadcast}, {@link BroadcastId}
 * Used by: {@link BroadcastRepositoryProvider}, BroadcastServiceImpl,
 * {@link FirebaseBroadcastRepository}, {@link MapViewModel}
 */
public interface BroadcastRepository {
    /**
     * Creates and returns a new broadcast ID.
     * The ID will be reasonably unique, but not guaranteed.
     *
     * @return the newly created {@link BroadcastId}
     */
    BroadcastId nextIdentity();

    /**
     * Permanently stores the given broadcast.
     * If a broadcast with the same ID as the given broadcast has already been stored previously,
     * the previously stored information is replaced.
     *
     * @param broadcast the {@link Broadcast} to be stored
     * @return a future completing with the latest version of the {@link Broadcast} after
     * storing has finished, or an exception if storing failed.
     */
    CompletableFuture<Broadcast> store(Broadcast broadcast);

    /**
     * Retrieves a broadcast by looking it up using its ID.
     *
     * @param id the {@link BroadcastId} of the broadcast to be retrieved
     * @return a future completing with the requested {@link Broadcast} or an exception if
     * a {@link Broadcast} with the given {@link BroadcastId} is not stored.
     */
    CompletableFuture<Broadcast> getById(BroadcastId id);

    /**
     * Retrieves an observable broadcast by looking it up using its ID.
     *
     * @param id the {@link BroadcastId} of the broadcast to be retrieved
     * @return a {@link LiveData} providing updates for the broadcast with the requested ID
     */
    LiveData<Broadcast> observeById(BroadcastId id);

    /**
     * Retrieves a live list of all currently active broadcasts.
     *
     * @return a {@link LiveData} containing the list of broadcasts
     */
    LiveData<List<Broadcast>> observeActiveBroadcasts();
}