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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.model.homeautomation.Device;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;

/**
 * command line interface for home automation
 * 
 * @author wf
 *
 */
public class CmdLine {
  private final static Logger LOG = LoggerFactory.getLogger(CmdLine.class);

  public static final String VERSION = "0.4.2";

  @Option(name = "-d", aliases = {
      "--debug" }, usage = "debug\ncreate additional debug output if this switch is used")
  boolean debug = false;

  @Option(name = "-h", aliases = { "--help" }, usage = "help\nshow this usage")
  boolean showHelp = false;

  @Option(name = "-l", aliases = { "--list" }, usage = "list\nlist devices")
  boolean listDevices = false;

  @Option(name = "-r", handler = StringArrayOptionHandler.class, aliases = {
      "--read" }, usage = "read\nread the given devices")
  List<String> readDevices = new ArrayList<>();

  @Option(name = "-s", handler = StringArrayOptionHandler.class, aliases = {
      "--set" }, usage = "set\nset the given devices to the givnen states e.g. main off")
  List<String> setDevices = new ArrayList<>();

  @Option(name = "-v", aliases = {
      "--version" }, usage = "showVersion\nshow current version if this switch is used")
  boolean showVersion = false;

  private CmdLineParser parser;
  static int exitCode;
  public static boolean testMode = false;

  /**
   * handle the given Throwable
   * 
   * @param t
   *          the Throwable to handle
   */
  public void handle(Throwable t) {
    System.out.flush();
    t.printStackTrace();
    usage(t.getMessage());
  }

  /**
   * show the Version
   */
  public void showVersion() {
    System.err.println("FritzBox Java API Command Line Version: " + VERSION);
    System.err.println();
    System.err
        .println("github: https://github.com/kaklakariada/fritzbox-java-api");
    System.err.println("");
  }

  /**
   * display usage
   * 
   * @param msg
   *          - a message to be displayed (if any)
   */
  public void usage(String msg) {
    System.err.println(msg);
    showVersion();
    System.err.println("  usage: java -jar fritzbox.jar");
    parser.printUsage(System.err);
    exitCode = 1;
  }

  /**
   * show Help
   */
  public void showHelp() {
    usage("Help");
  }

  /**
   * handle the command line command
   */
  public void doCommand() {
    final Config config = ConfigService.readConfig();

    if (debug) {
      LOG.info("Logging in to '{}' with username '{}'", config.baseUrl,
          config.username);
    }
    final HomeAutomation homeAutomation = HomeAutomation.connect(config);

    final DeviceList devices = homeAutomation.getDeviceListInfos();
    if (debug) {
      LOG.info("Found {} devices", devices.getDevices().size());
      for (final Device device : devices.getDevices()) {
        LOG.info("\t{}", device);
      }
    }

    final List<String> ids = homeAutomation.getSwitchList();
    if (debug) {
      LOG.info("Found {} device ids: {}", ids.size(), ids);
    }

    if (devices.getDevices().isEmpty()) {
      homeAutomation.logout();
      return;
    }

    if (listDevices) {
      show("%20s | %10s | %15s | %s", "Name", "By", "Product", "Identifier");
      show("%21s+%12s+%17s+%s", dash(21), dash(12), dash(17), dash(25));
      for (final Device device : devices.getDevices()) {
        show("%20s | %10s | %15s | %s", device.getName(),
            device.getManufacturer(), device.getProductName(),
            device.getIdentifier());
      }
    }
    final Map<String, String> ainByName = new HashMap<>();
    for (final Device device : devices.getDevices()) {
      ainByName.put(device.getName(), device.getIdentifier().replace(" ", ""));
    }
    /**
     * loop over all devices to be read
     */
    for (final String readDevice : readDevices) {
      // devices can be specified by name or id
      String ain = readDevice;
      // check if a name was given
      if (ainByName.containsKey(ain)) {
        // get the ain for the name
        ain = ainByName.get(ain);
      }
      readSwitch(homeAutomation, ain);
    }

    /**
     * loop over all devices to be set
     */
    if (setDevices.size() % 2 != 0) {
      usage("set needs pairs of name=on/off");
    }
    for (int i = 0; i < setDevices.size(); i += 2) {
      final String name = setDevices.get(i);
      final String powerState = setDevices.get(i + 1);
      // check if a name was given
      String ain = name;
      if (ainByName.containsKey(ain)) {
        // get the ain for the name
        ain = ainByName.get(ain);
      }
      boolean newState = false;
      switch (powerState) {
      case "on":
        newState = true;
        break;
      case "off":
        newState = false;
        break;
      default:
        usage(String.format(
            "%s is not a valid powerState it needs to be on or off",
            powerState));
      }
      show("switching %s %s", name, powerState);
      homeAutomation.switchPowerState(ain, newState);
    }
  }

  /**
   * read the given switch
   * 
   * @param homeAutomation
   * @param ain
   *          - the identification of the switch
   */
  private void readSwitch(HomeAutomation homeAutomation, String ain) {
    show(" name: %s", homeAutomation.getSwitchName(ain));
    show("   id: %s ", ain);
    show("alive: %s", homeAutomation.getSwitchPresent(ain));
    show("   on: %s", homeAutomation.getSwitchState(ain));
    show(" uses: %5.0f     W", homeAutomation.getSwitchPowerWatt(ain));
    show(" used: %9.3f kWh",
        homeAutomation.getSwitchEnergyWattHour(ain) / 1000.0);
    show(" temp: %7.1f   °C", homeAutomation.getTemperature(ain));
    show("");
  }

  /**
   * show the given values in the given format
   * 
   * @param format
   * @param values
   */
  public void show(String format, Object... values) {
    System.out.println(String.format(format, values));
  }

  /**
   * get the given number of dashes
   * 
   * @param number
   * @return
   */
  public String dash(int number) {
    return s('-', number);
  }

  /**
   * get a string with number repetitions of the given char
   * 
   * @param c
   * @param number
   * @return - the repeated char
   */
  public String s(char c, int number) {
    final StringBuffer sb = new StringBuffer();
    for (int i = 0; i < number; i++) {
      sb.append(c);
    }
    return sb.toString();
  }

  /**
   * work on the given commands
   * 
   * @throws Exception
   *           if a problem occurs
   */
  protected void work() throws Exception {
    if (this.showVersion || this.debug) {
      showVersion();
    }
    if (this.showHelp) {
      showHelp();
    } else {
      doCommand();
    }
  }

  /**
   * main routine
   * 
   * @param args
   *          - the command line arguments
   * @return - the exit statusCode
   * 
   */
  public int maininstance(String[] args) {
    parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);
      work();
      exitCode = 0;
    } catch (final CmdLineException e) {
      // handling of wrong arguments
      usage(e.getMessage());
    } catch (final Exception e) {
      handle(e);
      // System.exit(1);
      exitCode = 1;
    }
    return exitCode;
  }

  /**
   * main routine
   * 
   * @param args
   *          - command line arguments
   */
  public static void main(String[] args) {
    final CmdLine cmdLine = new CmdLine();
    final int result = cmdLine.maininstance(args);
    if (!testMode) {
      System.exit(result);
    }
  }
}
