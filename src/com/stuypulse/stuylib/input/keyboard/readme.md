# StuyLib Network Keyboard

Network keyboards can be used as input devices to trigger commands during testing, especially when running simulations or the [Romi](https://github.com/StuyPulse/StuyRomi). 

### [NetKeyboard](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/NetKeyboard.java)

[NetKeyboard](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/NetKeyboard.java) implements and simplifies interactions with keyboard information through a network table. 

### [NetKeyListener](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/NetKeyListener.java)

[NetKeyListener](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/NetKeyListener.java) listens for and uploads keyboard events to the keyboard network table. 

### [NetKeyWindow](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/NetKeyWindow.java)

[NetKeyWindow](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/NetKeyWindow.java) opens a Java AWT window and simulates a keyboard with a [NetKeyListener](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/input/keyboard/NetKeyListener.java) which can be used to control a robot.
