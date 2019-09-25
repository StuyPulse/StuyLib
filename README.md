# BetterStuyLib
Clean, Standardized, Robust utilitys for use int robot code. Try to include javadocs in all files.
> The rest of this file is mostly copied from [here](https://github.com/FRCTeam2910/Common-Public/blob/master/README.md)

## How to use in another project

The common library is imported into a project by using
[Git submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules). In
order to add this project as a submodule run `git submodule add
git@github.com:StuyPulse/BetterStuyLib.git betterstuylib` in your project's root
directory. This will clone this repository into the directory `betterstuylib`.

### Using the Common library

A dependency on the common library can be created by adding the following to
your `build.gradle` file:
```gradle
dependencies {
    ...
    compile project(':betterstuylib')
    ...
}
```
The following also needs to be added to your `settings.gradle` file:
```gradle
include ':betterstuylib'
```
