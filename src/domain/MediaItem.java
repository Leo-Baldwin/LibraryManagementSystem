package domain;

import java.util.UUID;

/**
 * Abstract superclass that represents any item that can exist within the Library's
 * collection (e.g., Books, DVD's Magazines).
 * <p>
 *     Encapsulates identity, availability status, and basic behaviours shared by all
 *     media types. Concrete subclasses such as {@link Book}, {@link Dvd}, and {@link Magazine}
 *     provide additional attributes specific to each media item.
 * </p>
 */
public abstract class MediaItem {

    /** Unique and immutable identifier for this media item. */
    private final UUID mediaId;

    /** Current availability status of the item. */
    private AvailabilityStatus status;

    /**
     * Constructs a new MediaItem with a generated unique identifier.
     */
    protected MediaItem() {
        this.mediaId = UUID.randomUUID();
        this.status = AvailabilityStatus.AVAILABLE;
    }

    /**
     * Returns the unique identifier of this item.
     *
     * @return UUID representing this media item
     */
    public UUID getMediaId() {
        return mediaId;
    }

    /**
     * Returns the current availability status of this item.
     *
     * @return the current {@link AvailabilityStatus}
     */
    public AvailabilityStatus getStatus() {
        return status;
    }

    /**
     * Sets a new availability status for the media item.
     * @param status the new {@link AvailabilityStatus} to assign
     * @throws IllegalArgumentException if {@code status} is null
     */
    public void setStatus(AvailabilityStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    /**
     * Method to show whether the media item is currently available to be borrowed.
     *
     * @return true if the status is AVAILABLE; false if not.
     */
    public boolean isAvailable() {
        return status == AvailabilityStatus.AVAILABLE;
    }

    /**
     * Returns a human-readable string representing the item.
     * @return a string containing the class name and current status
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [mediaId=" + mediaId + ", status=" + status + "]";
    }
}
