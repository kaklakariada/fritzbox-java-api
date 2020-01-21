package de.ingo.fritzbox.utils;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.ingo.fritzbox.data.Call;


/**
 * A CsvParser to parse csv files provided by the FritzBox.<br>
 * Currently supported csv files: call list
 *
 * @author Ingo Schwarz
 */
@Deprecated
public class CsvParser {

    /**
     * Parses a call list CSV file.
     *
     * @param csvString The CSV file as string.
     * @return The parsed calls as list of Call objects.
     */
    public static List<Call> parseCallList(final String csvString) {
        final List<Call> callerList = new ArrayList<>();
        String csv = csvString.substring(csvString.indexOf("\n") + 1);
        csv = csv.substring(csv.indexOf("\n") + 1);
        csv = new String(csv.getBytes(), Charset.forName("UTF-8"));

        String line;
        while (csv.indexOf("\n") > 0 && (line = csv.substring(0, csv.indexOf("\n"))).length() > 6) {
            csv = csv.substring(csv.indexOf("\n") + 1);
            callerList.add(parseCallListEntry(line));
        }

        return callerList;
    }

    /**
     * Parses a certain line of the call list CSV file.
     *
     * @param csvLine The line of the CSV call list file.
     * @return The parsed call.
     */
    private static Call parseCallListEntry(final String csvLine) {

        final String[] lineData = csvLine.split(";");
        final Call.Builder builder = new Call.Builder().type(lineData[0]).callerName(lineData[2])
                .callerNumber(lineData[3]).substationName(lineData[4]).substationNumber(lineData[5]);

        try {
            builder.date(lineData[1]);
        } catch (final ParseException e) {
            // TODO log exception
        }
        try {
            builder.duration(lineData[6]);
        } catch (final ParseException e) {
            // TODO log exception
            e.printStackTrace();
        }

        return builder.build();
    }

}
