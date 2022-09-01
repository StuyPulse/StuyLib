# StuyLib Gamepad Implementations

Implementations of various gamepads used in FRC robotics. 

### [AutoGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)

[AutoGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java) detects the gamepad you are using and uses this to determine which gamepad implementation to return for your connected gamepad. It is used to prevent wrong values from being read from your gamepad due to your code believing you are using a different type than the one you are using.

### [Logitech](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)

[Logitech](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java) is the implementation of the Logitech gamepad, which has an X and D mode. 

### [PS4](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)

[PS4](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java) is the implementation of the PS4 gamepad. 

### [Xbox](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)

[Xbox](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java) is the implementation of the Xbox gamepad.

### [KeyGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)

[KeyGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java) manages the keymapping for keyboard gamepads.

### [NetKeyGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)

[NetKeyGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)takes in keyboard inputs and maps them to a gamepad to simulate a gamepad, despite using a keyboard as an input medium.

### [SimKeyGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java)

[SimKeyGamepad](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/gamepads/AutoGamepad.java) opens a window which accepts keyboard / trackpad inputs within the window and maps them to a gamepad to simulate one. It is meant to be used in the robot simulation and will not work on an actual robot, except the [Romi](https://github.com/StuyPulse/StuyRomi) which utilizes the simulator for control.