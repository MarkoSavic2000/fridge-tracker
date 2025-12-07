package com.fridge.tracker.application.shared.validation;

import lombok.Getter;
import lombok.Setter;

/**
 * Unit of communication between the server and the client.
 */
@Getter
@Setter
public class Message {
    /**
     * Indicates whether message is 'GLOBAL' or it is specific for certain reference.
     */
    private boolean global;

    /**
     * Reference to the value to which {@link Message} is related (e.g. name of the field).
     */
    private String reference;

    /**
     * Represents message text.
     */
    private String label;

    public Message(boolean global, String reference, String label) {
        this.global = global;
        this.reference = reference;
        this.label = label;
    }

    public Message(String reference, String label) {
        this.reference = reference;
        this.label = label;
    }
}
