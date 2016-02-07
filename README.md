# fritzbox-java-api

[![Build Status](https://travis-ci.org/kaklakariada/fritzbox-java-api.svg?branch=master)](https://travis-ci.org/kaklakariada/fritzbox-java-api)
[![Download](https://api.bintray.com/packages/kaklakariada/maven/fritzbox-java-api/images/download.svg)](https://bintray.com/kaklakariada/maven/fritzbox-java-api/_latestVersion)

Java API for managing FritzBox HomeAutomation using [AVM Home Automation HTTP Interface](https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AHA-HTTP-Interface.pdf) inspired by grundid's [fritzbox-java-api](https://github.com/grundid/fritzbox-java-api). This also runs on Android devices (see [Andect](https://github.com/kaklakariada/Andect)).

## Usage
* Copy file `application.properties.template` to `application.properties` and enter settings for your device.
* Run example class [`TestDriver`](https://github.com/kaklakariada/fritzbox-java-api/blob/master/src/main/java/com/github/kaklakariada/fritzbox/TestDriver.java).
* Use API in your program.

## Developing
1. Run `./gradlew eclipse`
2. Import into eclipse


## Building
Install to local maven repository:
```bash
./gradlew clean install
```
