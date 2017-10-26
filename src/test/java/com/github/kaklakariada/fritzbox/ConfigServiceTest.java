/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox;

import org.junit.Test;

/**
 * Unit test for {@link ConfigService} warn user if application.properties have
 * not been set
 */
public class ConfigServiceTest {

  @Test
  public void testConfig() {
    try {
      final Config config = ConfigService.readConfig();
    } catch (final FritzBoxException fbe) {
      System.err.println("You might want to set your application properties in "
          + ConfigService.getConfigFile());
    }
  }
}
