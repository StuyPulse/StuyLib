# StuyLib Control Algorithms

WPI...

## Why is `Number` used over `double`

This is to allow for the use of [SmartNumber](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartNumber.java), which extends `Number`. This makes things like configuring PID values significantly easier, as they can be instantly hooked into SmartDashboard without any configuration.