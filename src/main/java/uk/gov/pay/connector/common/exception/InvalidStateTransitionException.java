package uk.gov.pay.connector.common.exception;

import uk.gov.pay.connector.events.model.Event;

import static java.lang.String.format;

public class InvalidStateTransitionException extends IllegalStateException {
    
    private final String currentState;
    
    private final String targetState;

    public String getCurrentState() {
        return currentState;
    }

    public String getTargetState() {
        return targetState;
    }

    public InvalidStateTransitionException(String currentState, String targetState, Event event) {
        super(format("Charge state transition [%s] -> [%s] not allowed [event={%s}]", currentState, targetState, event));
        this.currentState = currentState;
        this.targetState = targetState;
    }
}
