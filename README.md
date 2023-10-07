# fritzbox-java-api

[![Java CI](https://github.com/kaklakariada/fritzbox-java-api/workflows/Java%20CI/badge.svg)](https://github.com/kaklakariada/fritzbox-java-api/actions?query=workflow%3A%22Java+CI%22)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.kaklakariada%3Afritzbox-java-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.kaklakariada%3Afritzbox-java-api)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.kaklakariada/fritzbox-java-api)](https://search.maven.org/artifact/com.github.kaklakariada/fritzbox-java-api)

Java API for managing FritzBox HomeAutomation using [AVM Home Automation HTTP Interface](https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AHA-HTTP-Interface.pdf) inspired by grundid's [fritzbox-java-api](https://github.com/grundid/fritzbox-java-api). This also runs on Android devices (see [Andect](https://github.com/kaklakariada/Andect)).

## Important: Migration to Maven Central

Due to the [deprecation of JCenter](https://jfrog.com/blog/into-the-sunset-bintray-jcenter-gocenter-and-chartcenter/) new versions will be published to [Maven Central](https://search.maven.org/artifact/com.github.kaklakariada/fritzbox-java-api). In your build script please use

```groovy
repositories {
    mavenCentral()
}
```

## Changelog

See [CHANGELOG.md](CHANGELOG.md).

## Usage

### Gradle

* Add Maven Central maven repository:

    ```groovy
    repositories {
        mavenCentral()
    }
    ```

* Add dependency

    ```groovy
    dependencies {
        compile 'com.github.kaklakariada:fritzbox-java-api:1.6.1'
    }
    ```

### Maven

```xml
<dependency>
  <groupId>com.github.kaklakariada</groupId>
  <artifactId>fritzbox-java-api</artifactId>
  <version>1.6.1</version>
</dependency>
```

### Run sample program

1. Create file `application.properties` with the following content and enter settings for your device:

    ``` properties
    fritzbox.url = https://fritz.box
    fritzbox.username = user
    fritzbox.password = secret
    ```
2. Run example class [`TestDriver`](https://github.com/kaklakariada/fritzbox-java-api/blob/master/src/main/java/com/github/kaklakariada/fritzbox/TestDriver.java).

## Development

### Generate / update license header

```sh
./gradlew licenseFormat
```

### Check if dependencies are up-to-date

```sh
./gradlew dependencyUpdates
```

### Check dependencies for vulnerabilities

```sh
./gradlew ossIndexAudit
```

### Building

Install to local maven repository:
```sh
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

3. Optional: run the following command to do a dry-run:

    ```sh
    ./gradlew clean check build publishToSonatype closeSonatypeStagingRepository --info
    ```

4. Run the following command to publish to Maven Central:

    ```sh
    ./gradlew clean check build publishToSonatype closeAndReleaseSonatypeStagingRepository --info
    ```

5. Create a new [release](https://github.com/kaklakariada/fritzbox-java-api/releases) on GitHub.
6. After some time the release will be available at [Maven Central](https://repo1.maven.org/maven2/com/github/kaklakariada/fritzbox-java-api/).
