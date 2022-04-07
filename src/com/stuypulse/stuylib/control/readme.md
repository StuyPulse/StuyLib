# StuyLib Control Algorithms Library

The Control Algorithms library contains various control algorithms which are commonly used in robotics such as [PID](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDController.java) or [Bang Bang](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/BangBangController.java). They are intuitively programmed and handles the complex mathematics behind each algorithm for the programmer, allowing for beginners to use PID in their control systems. However, it is HIGHLY recommended to have a concrete understanding of what you are using as they require tuning.

## What is control theory?

Control theory is the application of mathematics to predict and analyze the behavior of systems and make them responsive and robust to external forces. We apply control theory to the systems we design to make them meet our expected output and behavioral specifications. 

## Why is `Number` used over `double`

This is to allow for the use of [SmartNumber](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartNumber.java), which extends `Number`. This makes things like configuring PID values significantly easier, as they can be instantly hooked into SmartDashboard without any configuration.

### [Controller](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/Controller.java)

The [Controller](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/Controller.java) abstract class is used to create controllers and includes functions which makes controller implementations easier. 

### [BangBangController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/BangBangController.java)

<!-- A [BangBangController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/BangBangController.java), also known as an on-off controller, essentially only has two states.  -->
<!-- A [BangBangController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/BangBangController.java) which returns positive values for positive error and negative values for negative error.  -->

### [PIDCalculator](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDCalculator.java)

[PIDCalculator](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDCalculator.java) is a Bang-Bang controller which can be used to calculate the values for the [PIDController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDController.java). It creates a [PIDController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDController.java) with correct values as oscillations occur.

### [PIDController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDController.java)

[PIDController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/PIDController.java) is an implementation of a PID controller. Our implementation features an intergral reset every time the error reaches 0 to prevevnt integral lag. Setups involving rate may not be suitable for this controller. 

### [TBHController](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/control/TBHController.java)

A "take back half" controller which increases the speed until it goes over the reference, then decreases the speed by half and repeats the process. 
