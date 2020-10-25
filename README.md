# fritzbox-java-api

[![Java CI](https://github.com/kaklakariada/fritzbox-java-api/workflows/Java%20CI/badge.svg)](https://github.com/kaklakariada/fritzbox-java-api/actions?query=workflow%3A%22Java+CI%22)
[![Download](https://api.bintray.com/packages/kaklakariada/maven/fritzbox-java-api/images/download.svg)](https://bintray.com/kaklakariada/maven/fritzbox-java-api/_latestVersion)

Java API for managing FritzBox HomeAutomation using [AVM Home Automation HTTP Interface](https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AHA-HTTP-Interface.pdf) inspired by grundid's [fritzbox-java-api](https://github.com/grundid/fritzbox-java-api). This also runs on Android devices (see [Andect](https://github.com/kaklakariada/Andect)).

## Changelog

See [CHANGELOG.md](CHANGELOG.md).

## Usage

* Add jcenter maven repository:
```groovy
repositories {
    jcenter()
}
```
* Add dependency
```groovy
dependencies {
    compile 'com.github.kaklakariada:fritzbox-java-api:1.0.0'
}
```

### Run sample program

1. Copy file `application.properties.template` to `application.properties` and enter settings for your device.
2. Run example class [`TestDriver`](https://github.com/kaklakariada/fritzbox-java-api/blob/master/src/main/java/com/github/kaklakariada/fritzbox/TestDriver.java).


## Generate / update license header

```bash
$ ./gradlew licenseFormat
```

## Building
Install to local maven repository:
```bash
./gradlew clean publishToMavenLocal
```

## Publish to jcenter

1. Add your bintray credentials to `~/.gradle/gradle.properties`:

    ```properties
    bintrayUser = <user>
    bintrayApiKey = <apiKey>
    ```

2. Increment version number in `build.gradle` and `README.md`, commit and push.
3. Run the following command:

    ```bash
    $ ./gradlew clean check bintrayUpload --info
    ```

4. Create a new [release](https://github.com/kaklakariada/fritzbox-java-api/releases) on GitHub.
5. Sign in at https://bintray.com/, go to https://bintray.com/kaklakariada/maven and publish the uploaded artifacts.
