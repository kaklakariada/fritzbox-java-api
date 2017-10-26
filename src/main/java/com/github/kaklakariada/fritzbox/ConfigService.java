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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * access to the configuration
 *
 */
public class ConfigService {
  private final static Logger LOG = LoggerFactory
      .getLogger(ConfigService.class);

  /**
   * read the properties from the given configuration path
   * 
   * @param path
   * @return - the properties
   */
  private static Properties readConfig(Path path) {
    final Properties config = new Properties();
    final Path absolutePath = path.toAbsolutePath();
    LOG.debug("Reading config from file {}", absolutePath);
    try (InputStream in = Files.newInputStream(absolutePath)) {
      config.load(in);
    } catch (final IOException e) {
      throw new FritzBoxException(
          "Error loading configuration from " + absolutePath, e);
    }
    return config;
  }

  /**
   * get the Config directory
   * 
   * @return the config directory
   */
  public static File getConfigDirectory() {
    final String home = System.getProperty("user.home");
    final File configDirectory = new File(home + "/.fritzbox/");
    return configDirectory;
  }

  /**
   * read the configuration from the .fritzbox path
   * 
   * @return the Configuration
   */
  public static Config readConfig() {
    final Properties configProps = readConfig(getConfigFile().toPath());
    final Config config = new Config(configProps);
    return config;
  }

  /**
   * get the property file
   * 
   * @return - the File
   */
  public static File getConfigFile() {
    final File propertyFile = new File(getConfigDirectory(),
        "application.properties");
    return propertyFile;
  }
}
