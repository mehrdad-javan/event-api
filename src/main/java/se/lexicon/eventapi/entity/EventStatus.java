package se.lexicon.eventapi.entity;

public enum EventStatus {
    PLANNED,
    ONGOING,
    COMPLETED,
    CANCELLED;

    public static EventStatus fromString(String status) {
        if (status == null || status.isBlank()) {
            return PLANNED;
        }
        try {
            return EventStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status + ". Valid statuses are: PLANNED, ONGOING, COMPLETED, CANCELLED");
        }
    }
}
