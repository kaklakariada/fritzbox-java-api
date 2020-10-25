# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2020-10-25

### Added

* [#15](https://github.com/kaklakariada/fritzbox-java-api/pull/15) Added new fields to be compatible with Fritz!OS 7.21. Thanks to [philippn](https://github.com/philippn)!

## [1.0.0] - 2019-12-27

### Breaking change

* Make Device properties `battery` and `batterylow` optional to distinguish between missing values and zero, see [issue #11](https://github.com/kaklakariada/fritzbox-java-api/issues/11).
