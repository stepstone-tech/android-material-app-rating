# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [2.4.1]
### Added
- "setThreshold" method to set threshold. Threshold is number of stars which the user can send a comment for them.
-  "onPositiveButtonClickedWithComment", "onPositiveButtonClickedWithoutComment" methods instead of "onPositiveButtonClicked", To separate methods of below threshold and more than.
-  "setAfterInstallDay", "setNumberOfLaunches", "setRemindInterval" methods for auto show app rating dialog
-  "setDialogBackgroundColor" methods for change dialog background color

[2.4.1]: https://github.com/hosseiniSeyRo/android-app-rating/compare/v2.3.1...v2.4.1

## [2.3.1]
### Fixed
- crash #56 which occur when set default rating to 0

[2.3.1]: https://github.com/stepstone-tech/android-material-app-rating/compare/v2.3.0...v2.3.1

## [2.3.0]
### Added
- "setCancelable", "setCancelOnTouchOutside" methods, which allow to control dialog cancelling
- "setTargetFragment" method to handle listener in fragment
- "setCommentInputEnabled" method to enabled/disable comment box
- new api samples

### Fixed
- some minor issues

[2.3.0]: https://github.com/stepstone-tech/android-material-app-rating/compare/v2.2.0...v2.3.0

## [2.2.0]
### Added
- "setDefaultComment" method, which allow user to use dialog also for editing comments

[2.2.0]: https://github.com/stepstone-tech/android-material-app-rating/compare/v2.1.1...v2.2.0



## [2.1.1]
### Fixed
- crash on devices below Lollipop

[2.1.1]: https://github.com/stepstone-tech/android-material-app-rating/compare/v2.1.0...v2.1.1



## [2.1.0]
### Added
- support for custom styles for title, description, note texts, comments
- custom star color
- "Later" button

### Changed
- **Breaking change:** Added "onNeutralButtonClicked" method to listener

[2.1.0]: https://github.com/stepstone-tech/android-material-app-rating/compare/v2.0.0...v2.1.0



## [2.0.0]
### Added
- support for custom hint text and color in comment box

### Changed
- **Breaking change:** Replaced "positive/nagative button" clicked listener by a global listener which needs to be implemented by the host activity

[2.0.0]: https://github.com/stepstone-tech/android-material-app-rating/compare/v1.2.0...v2.0.0
