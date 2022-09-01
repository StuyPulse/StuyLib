# StuyLib Robot Networking Library

The Robot Networking Library simplifies user interaction with the APIs of various network elements of FRC Robotics such as NetworkTables / SmartDashboard or the Limelight. 

## Usage

Say we wanted to insert values into SmartDashboard which we could use to control a motor, either in code or on SmartDashboard, we could write:
```
SmartNumber MOTOR_SPEED = new SmartNumber("Conveyor/Motor Speed", 1.0);
SmartBoolean MOTOR_DISABLED = new SmartBoolean("Conveyor/Disabled", false)
```

By creating a new SmartNumber, we can now adjust the speed of the motor using SmartDashboard during testing. In addition, we can toggle the reject function of the motor through SmartDashboard or by manipulating the SmartBoolean value through code. Using SmartDashboard to store values allow for them to be "centralized" and more easily accessible throughout the program.

For example, if we wanted to disable a motor: 
```
if (shouldDisableMotor()) {
    Settings.Subsystem.MOTOR_DISABLED.set(true);
}
```
If we want to run a motor: 
```
if (hasBall()) {
    motor.set(Settings.Subsystem.MOTOR_SPEED.get())
}
else {
    motor.stopMotor()
}
```

### [Limelight](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/limelight/readme.md)

Read the limelight [`readme.md`](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/limelight/readme.md) to learn more. 

### [SLNetworkTable](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SLNetworkTable.java)

[SLNetworkTable](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SLNetworkTable.java) is used to easily interface with a network table. It can be used to get or set values on the network table. 

### [SmartBoolean](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartBoolean.java)

[SmartBoolean](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartBoolean.java) is a wrapper of the SmartDashboard API for manipulating `boolean` values on SmartDashboard. 

### [SmartNumber](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartNumber.java)

[SmartNumber](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartNumber.java) is a wrapper of the SmartDashboard API for manipulating `double` values on SmartDashboard. Their values can be the returned, casted as a `double`, `float`, `int`, or `long`.

### [SmartString](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartString.java)

[SmartString](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartString.java) is a wrapper of the SmartDashboard API for manipulating `String` values on SmartDashboard. 