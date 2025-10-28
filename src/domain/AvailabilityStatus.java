package domain;

/**
 * Represents the availability of a MediaItem within the library system.
 * States are used by Library to gate actions like making a reservation and loaning an item.
 */
public enum AvailabilityStatus {
    AVAILABLE,  // Item can be loaned or reserved
    ON_LOAN,    // Item is currently loaned to a member
    RESERVED    // Item is being held for a reservation (not available to others)
}
