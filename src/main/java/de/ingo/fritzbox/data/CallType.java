package de.ingo.fritzbox.data;

import java.io.Serializable;


/**
 * This enumeration contains all known call types. Every call type contains the
 * value with which they are identified at the FritzBox.
 *
 * @author Ingo Schwarz
 */
public enum CallType implements Serializable {

    INCOMMING_CALL(1), MISSED_CALL(2), UNKNOWEN(3), OUTGOING_CALL(4);


    private final int value;

    CallType(final int value) {
        this.value = value;
    }

    /**
     * Returns the value of the CallType the FritzBox is working with.
     *
     * @return The call type value as integer.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Creates a call type object from the given value.
     *
     * @param x The value to create a CallType for.
     * @return The created CallType.
     */
    public static CallType fromInteger(final int x) {
        switch (x) {
            case 1:
                return INCOMMING_CALL;
            case 2:
                return MISSED_CALL;
            case 4:
                return OUTGOING_CALL;
            default:
                return UNKNOWEN;
        }
    }

}
