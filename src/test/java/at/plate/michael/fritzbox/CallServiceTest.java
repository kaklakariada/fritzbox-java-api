/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.plate.michael.fritzbox;

import de.ingo.fritzbox.data.Call;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CallServiceTest {

    private static CallService callService;

    @Before
    public void setUp() {
        callService = new CallService(Config.getHttpFritzBox(), "", Config.getPassword());
    }

    @After
    public void tearDown() {
        try {
            callService.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSessionIdTest() throws Exception {
        String sid = callService.getSid();
        Assert.assertNotNull(sid);
    }

    @Test
    public void readCallerlist() throws Exception {
        List<Call> callList = callService.getCallList();
        callList.stream().forEach(c -> {
            System.out.println(c.toString());
        });
        Assert.assertFalse(callList.isEmpty());
    }

}
