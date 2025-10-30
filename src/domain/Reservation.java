package domain;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation {

    private final UUID reservationId;

    private final UUID memberId;

    private final UUID mediaId;

    private LocalDate createdDate;

    private ReservationStatus status;

    public Reservation(UUID memberId, UUID mediaId, LocalDate createdDate) {

    }
}
