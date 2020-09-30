package cse.dit012.lost.model.broadcast;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;

import static com.google.common.base.Preconditions.*;

public final class Broadcast {
    private final BroadcastId id;
    private final Date createdAt;
    private Date lastActive;
    private final MapCoordinates coordinates;
    private CourseCode course;
    private String description;

    public Broadcast(BroadcastId id, Date createdAt, Date lastActive, MapCoordinates coordinates, CourseCode course, String description) {
        this.id = checkNotNull(id);
        this.createdAt = checkNotNull(createdAt);
        this.lastActive = checkNotNull(lastActive);
        this.coordinates = coordinates;
        this.course = checkNotNull(course);
        this.description = checkNotNull(description);
    }

    public BroadcastId getId() {
        return id;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public Date getLastActive() {
        return new Date(lastActive.getTime());
    }

    public void updateLastActive() {
        this.lastActive = new Date(System.currentTimeMillis());
    }

    public MapCoordinates getCoordinates() {
        return coordinates;
    }

    public CourseCode getCourse() {
        return course;
    }

    public void updateCourse(CourseCode courseCode) {
        course = checkNotNull(courseCode);
    }

    public String getDescription() {
        return description;
    }

    public void updateDescription(String description) {
        this.description = checkNotNull(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Broadcast broadcast = (Broadcast) o;
        return id.equals(broadcast.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("createdAt", getCreatedAt())
                .add("lastActive", getLastActive())
                .add("coordinates", getCoordinates())
                .add("course", getCourse())
                .add("description", getDescription())
                .toString();
    }
}