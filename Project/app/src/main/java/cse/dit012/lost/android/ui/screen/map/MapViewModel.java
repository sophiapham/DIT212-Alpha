package cse.dit012.lost.android.ui.screen.map;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import cse.dit012.lost.Broadcast;
import cse.dit012.lost.BroadcastRepository;
import cse.dit012.lost.FirebaseBroadcastRepository;

/**
 * View model handling data to be displayed on the map screen.
 */
public class MapViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = new FirebaseBroadcastRepository();

    // Cached reference to active broadcasts
    private LiveData<List<Broadcast>> broadcasts;

    /**
     * Retrieves an immutable live list of all currently active broadcasts.
     * @return the list of broadcasts
     */
    public LiveData<List<Broadcast>> getActiveBroadcasts() {
        if (broadcasts == null) {
            broadcasts = broadcastRepository.getActiveBroadcasts();
        }
        return broadcasts;
    }
}
