# StuyLib

StuyLib is a collection of tools commonly and or uncommonly used in robot code.
The point of this library is to have a standard library for these tools that are
maintained separetly from the robot code. Because these tools are core tools, it
is important that any changes not be made as a requirement for a new robot arises.
This includes, importing robot code, robot dependant constants, and any other 
changes that could just be added to the robot instead.

All source code is in `src`.

> The rest of this file is mostly copied from [here](https://github.com/FRCTeam2910/Common-Public/blob/master/README.md)

## How to use in another project

The common library is imported into a project by using
[Git submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules). In
order to add this project as a submodule run `git submodule add
git@github.com:StuyPulse/StuyLib2020.git stuylib` in your project's root
directory. This will clone this repository into the directory `betterstuylib`.

### Using the Common library

A dependency on the common library can be created by adding the following to
your `build.gradle` file:
```gradle
dependencies {
    ...
    compile project(':stuylib')
    ...
}
```
The following also needs to be added to your `settings.gradle` file:
```gradle
include ':stuylib'
```
