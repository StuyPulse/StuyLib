# StuyLib Input Library

The Input library contains classes which simplifies gamepad / keyboard input and standardizes the way input is handled and retrieved. 

To learn about specific gamepads, [read here](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/readme.md). 

To learn about keyboard input, [read here](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/readme.md). 

### [Gamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/Gamepad.java)

[Gamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/Gamepad.java) is a class which standardizes the gamepad interface. It does not do anything by itself and must be extended by other gamepads. 

### [GamepadState](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/GamepadState.java)

[GamepadState](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/GamepadState.java) stores the state of a [`Gamepad`](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/Gamepad.java) separated by the different buttons and axis. Yet to be implemented, it can be used to "record" and "play back" gamepad input. 

### [SPIGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/WPIGamepad.java)

The [SPIGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/WPIGamepad.java) class adds functions which make interactions with WPILib's `Joystick` class simpler. 
