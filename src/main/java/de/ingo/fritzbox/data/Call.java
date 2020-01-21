package de.ingo.fritzbox.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This objects represents a call. The object itself is immutable and can only
 * be created by the Call.Builder.
 *
 * @author Ingo Schwarz
 */
public class Call implements Serializable {

    private static final long serialVersionUID = 3726603860912178672L;
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yy HH:mm");
    private static final DateFormat DATE_FORMATTER_DD_MM_YYYY_HH_MM = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static final DateFormat DURATION_FORMATTER = new SimpleDateFormat("HH:mm");

    private final CallType type;
    private final Date date;
    private final String callerName;
    private final String callerNumber;
    private final String substation;
    private final String substationNumber;
    private final int duration;


    private Call(final Builder builder) {
        this.type = builder.type;
        this.date = builder.date;
        this.callerName = builder.callerName;
        this.callerNumber = builder.callerNumber;
        this.substation = builder.substation;
        this.substationNumber = builder.substationNumber;
        this.duration = builder.duration;
    }

    /**
     * Gives the call type.
     *
     * @return The call type as CallType object.
     */
    public CallType getType() {
        return this.type;
    }

    /**
     * Gives the call type.
     *
     * @return The call date as Date object.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Gives the caller name.
     *
     * @return The name of the caller as string object.
     */
    public String getCallerName() {
        return this.callerName;
    }

    /**
     * Gives the caller number.
     *
     * @return The number of the caller as string object.
     */
    public String getCallerNumber() {
        return this.callerNumber;
    }

    /**
     * Gives the substation name.
     *
     * @return The name of the substation as string object.
     */
    public String getSubstationName() {
        return this.substation;
    }

    /**
     * Gives the substation number.
     *
     * @return The number of the substation as string object.
     */
    public String getSubstationNumber() {
        return this.substationNumber;
    }

    /**
     * The duration of the call in minutes.
     *
     * @return A integer indication the duration of the call in minutes.
     */
    public int getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        return "[Call: [type: " + this.type + "; " + "date: " + DATE_FORMATTER_DD_MM_YYYY_HH_MM.format(this.date) + "; "
                + "callerName: " + this.callerName + "; " + "callerNumber: " + this.callerNumber + "; "
                + "substation: " + this.substation + "; " + "substationNumber: " + this.substationNumber + "; "
                + "duration: " + this.duration + "s]]";
    }


    /**
     * A Builder to create a Call object.
     *
     * @author Ingo Schwarz
     */
    public static class Builder implements Serializable {

        private static final long serialVersionUID = -8476721398229927890L;
        private CallType type = CallType.UNKNOWEN;
        private Date date;
        private String callerName;
        private String callerNumber;
        private String substationNumber;
        private int duration;
        private String substation;

        /**
         * Sets the type of the call.
         *
         * @param type The call type to set.
         * @return The Builder instance. (Fluent-API)
         */
        public Builder type(final CallType type) {
            this.type = type;
            return this;
        }

        /**
         * Transforms the integer to a call type and sets it.
         *
         * @param type The call type to set.
         * @return The Builder instance. (Fluent-API)
         */
        public Builder type(final int type) {
            this.type = CallType.fromInteger(type);
            return this;
        }

        /**
         * Casts the string to a Integer to be transformed into a CallType and
         * sets it.
         *
         * @param type The call type to set. (Must be able to be cast to integer)
         * @return The Builder instance. (Fluent-API)
         */
        public Builder type(final String type) {
            this.type = CallType.fromInteger(Integer.parseInt(type));
            return this;
        }

        /**
         * Casts the string to a Date object and sets it as call date.
         *
         * @param date The date as string object of format "dd.MM.yyyy HH:mm"
         * @return The Builder instance. (Fluent-API)
         * @throws ParseException occurs if string can't be parsed.
         */
        public Builder date(final String date) throws ParseException {
            return this.date(DATE_FORMATTER.parse(date));
        }

        /**
         * Sets the given date as call date.
         *
         * @param date The call date as date object.
         * @return The Builder instance. (Fluent-API)
         */
        public Builder date(final Date date) {
            this.date = date;
            return this;
        }

        /**
         * Sets the caller name.
         *
         * @param callerName The caller name as string object.
         * @return The Builder instance. (Fluent-API)
         */
        public Builder callerName(final String callerName) {
            this.callerName = callerName;
            return this;
        }

        /**
         * Sets the caller number.
         *
         * @param callerNumber The caller number as string object.
         * @return The Builder instance. (Fluent-API)
         */
        public Builder callerNumber(final String callerNumber) {
            this.callerNumber = callerNumber;
            return this;
        }

        /**
         * Sets the substationName
         *
         * @param substationName The substation name as string object.
         * @return The Builder instance. (Fluent-API)
         */
        public Builder substationName(final String substationName) {
            this.substation = substationName;
            return this;
        }

        /**
         * Sets the substation number.
         *
         * @param substationNumber The substation number as string object.
         * @return The Builder instance. (Fluent-API)
         */
        public Builder substationNumber(final String substationNumber) {
            this.substationNumber = substationNumber;
            return this;
        }

        /**
         * Parses a string of the type "HH:mm" to seconds.
         *
         * @param durationString The duration in the described format.
         * @return The Builder instance. (Fluent-API)
         * @throws ParseException If the String can't be parsed.
         */
        public Builder duration(final String durationString) throws ParseException {
            this.duration((int) (DURATION_FORMATTER.parse(durationString).getTime() / 1000 / 60) + 60);
            return this;
        }

        /**
         * Sets the duration of the call in minutes.
         *
         * @param duration The duration of the call in minutes
         * @return The Builder instance. (Fluent-API)
         */
        public Builder duration(final int duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Creates a Call-object from the values set in this builder.
         *
         * @return The created call instance.
         */
        public Call build() {
            return new Call(this);
        }

    }

}
