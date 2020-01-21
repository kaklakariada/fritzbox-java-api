package at.plate.michael.fritzbox;

import com.github.kaklakariada.fritzbox.FritzBoxSession;
import com.github.kaklakariada.fritzbox.LoginFailedException;
import de.ingo.fritzbox.data.Call;
import de.ingo.fritzbox.utils.CsvParser;
import de.ingo.fritzbox.utils.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

public class CallService {

    private static final Logger LOG = LoggerFactory.getLogger(CallService.class);
    private static final String QUERY_PATH = "/fon_num/foncalls_list.lua";
    private FritzBoxSession fritzBoxSession = null;
    private String httpFritzBox = null;

    public CallService(FritzBoxSession fritzBoxSession) {
        this.fritzBoxSession = fritzBoxSession;
    }

    public CallService(String httpFritzBox, String user, String password) {
        this.httpFritzBox = httpFritzBox;
        fritzBoxSession = new FritzBoxSession(httpFritzBox);
        try {
            //reset errormessage
            fritzBoxSession.setErrorMessage(null);
            fritzBoxSession.login("", password);
        } catch (LoginFailedException lfe) {
            LOG.error(lfe.getMessage());
            fritzBoxSession.setErrorMessage(lfe.getMessage());
        }
    }

    public String getHttpFritzBox() {
        return httpFritzBox;
    }

    public String getSid() throws Exception {
        validateSid();
        return fritzBoxSession.getSid();
    }

    private void validateSid() throws Exception {
        if (fritzBoxSession == null) {
            throw new Exception("No session exists");
        }
        if (fritzBoxSession.getSid() == null) {
            throw new Exception(fritzBoxSession.getErrorMessage());
        }
    }

    public List<Call> getCallList() throws Exception {
        final URL requestUrl = new URL(getHttpFritzBox() + QUERY_PATH + "?sid=" + fritzBoxSession.getSid() + "&csv=");
        final String response = HttpRequest.doGet(requestUrl);
        return CsvParser.parseCallList(response.toString());
    }

    public void logout() throws Exception {
        fritzBoxSession.logout();
    }
}
