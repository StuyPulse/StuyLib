# StuyLib Control Algorithms Library

The Control Algorithms library contains various control algorithms which are commonly used in robotics such as [PID](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDController.java) or [Bang Bang](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/BangBangController.java). They are intuitively programmed and handles the complex mathematics behind each algorithm for the programmer, allowing for beginners to use PID in their control systems. However, it is HIGHLY recommended to have a concrete understanding of what you are using as they require tuning.

## What is control theory?



### [Controller](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/Controller.java)

### [BangBangController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/BangBangController.java)

### [PIDCalculator](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDCalculator.java)

### [PIDController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDController.java)

### [TBHController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/TBHController.java)

## Why is `Number` used over `double`

This is to allow for the use of [SmartNumber](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartNumber.java), which extends `Number`. This makes things like configuring PID values significantly easier, as they can be instantly hooked into SmartDashboard without any configuration.