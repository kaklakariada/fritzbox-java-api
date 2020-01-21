package at.plate.michael.fritzbox;

public class Config {

    private static final String PASSWORD = "";
    private static final String HTTP_FRITZ_BOX = "http://fritz.box";

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getHttpFritzBox() {
        return HTTP_FRITZ_BOX;
    }
}
