# fritzbox-java-api

[![Java CI](https://github.com/kaklakariada/fritzbox-java-api/workflows/Java%20CI/badge.svg)](https://github.com/kaklakariada/fritzbox-java-api/actions?query=workflow%3A%22Java+CI%22)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.kaklakariada%3Afritzbox-java-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.kaklakariada%3Afritzbox-java-api)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.kaklakariada/fritzbox-java-api)](https://search.maven.org/artifact/com.github.kaklakariada/fritzbox-java-api)

Java API for managing FritzBox HomeAutomation using [AVM Home Automation HTTP Interface](https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AHA-HTTP-Interface.pdf) inspired by grundid's [fritzbox-java-api](https://github.com/grundid/fritzbox-java-api). This also runs on Android devices (see [Andect](https://github.com/kaklakariada/Andect)).

## Changelog

See [CHANGELOG.md](CHANGELOG.md).

## Usage

* Add Maven Central maven repository:

    ```groovy
    repositories {
        mavenCentral()
    }
    ```

* Add dependency

    ```groovy
    dependencies {
        compile 'com.github.kaklakariada:fritzbox-java-api:1.2.2'
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

### Publish to Maven Central

1. Add the following to your `~/.gradle/gradle.properties`:

    ```properties
    ossrhUsername=<your maven central username>
    ossrhPassword=<your maven central passwort>

    signing.keyId=<gpg key id (last 8 chars)>
    signing.password=<gpg key password>
    signing.secretKeyRingFile=<path to secret keyring file>
    ```

2. Increment version number in `build.gradle` and `README.md`, update [CHANGELOG.md](CHANGELOG.md), commit and push.
3. Run the following command:

    ```bash
    $ ./gradlew clean check build publish --info
    ```

4. Create a new [release](https://github.com/kaklakariada/fritzbox-java-api/releases) on GitHub.
5. Release the artifacts at Maven Central:
    1. Login at [oss.sonatype.org](https://oss.sonatype.org).
    2. Go to the [staging repositories](https://oss.sonatype.org/#stagingRepositories).
    3. Select repository named `comgithubkaklakariada-*` and click the "Close" button.
    4. When closing was successfull, click the "Release" button.
    5. After some time the release will be available at [Maven Central](https://repo1.maven.org/maven2/com/github/kaklakariada/fritzbox-java-api/).
