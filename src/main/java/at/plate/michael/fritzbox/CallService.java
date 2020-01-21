package at.plate.michael.fritzbox;

import com.github.kaklakariada.fritzbox.EnergyStatisticsService;
import com.github.kaklakariada.fritzbox.FritzBoxSession;
import com.github.kaklakariada.fritzbox.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallService {

    private static final Logger LOG = LoggerFactory.getLogger(CallService.class);
    private static final String QUERY_PATH = "/fon_num/foncalls_list.lua";
    private FritzBoxSession fritzBoxSession = null;

    public CallService(FritzBoxSession fritzBoxSession) {
        this.fritzBoxSession = fritzBoxSession;
    }

    public CallService(String httpFritzBox, String user, String password) {
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

    public String getSid() throws Exception {
        validateSid();
        return fritzBoxSession.getSid();
    }

    private void validateSid() throws Exception {
        if(fritzBoxSession==null){
            throw new Exception("No session exists");
        }
        if (fritzBoxSession.getSid() == null) {
            throw new Exception(fritzBoxSession.getErrorMessage());
        }
    }
}
