![](https://github.com/StuyPulse/StuyLib/raw/master/pictures/StuyLib%20Banner.png)

# What is StuyLib?

Every year, our team among many others is tasked with creating a usefull, yet daunting util folder. This folder must contain important files like `Gamepad.java`, but because it must be reinvented every year, it is often filled with unpolished code. Stuylib is here to change that!

This repository will be filled with the cutting edge technology developed at StuyPulse itself. Uncommented / messy code will NOT be tolerated here, and neither will any code that is robot specific. Why? Because what is robot specific code doing in a library? That should be in robot code, along with any other messy code that we think of at competition and build season.

# How do I use StuyLib?

### Setting up StuyLib

Go to our [release page](https://github.com/StuyPulse/StuyLib/releases) and download the newest version.

Put this jar file in the root of your project and put

```
dependencies { 
    // ... other dependencies
    compile files("./StuyLib.jar")
    // ... other dependencies
}
```

### Other Dependencies

You will also need to import `WPI` and `REVRobotics` for certain classes to work. This is for things in the `network` folder and the `encoder` folder.

If you are using network tables anywhere else than the robot, ie. on a laptop, you will need to include the jar files in the `./lib` folder. For example, when using NetKeyboard, you need to run a program to send the keyboard input, [here is the program](https://github.com/Sam-Belliveau/NetworkKeyboardServer). This program uses these jar files to send network table data to the robot. It is otherwise pretty rare that you would need to use these

# Criteria for Code Contribution

When contributing code, as always, make a branch and a pull request.

All code MUST be commented, and no single class should be to complicated.

At the top of each class, give a breif description of the class, along with an author tag which contains an email just in case anybody has any questions on how to use it.

# Current Positions

Current Lead Repository Maintainer

 - [Sam Belliveau](https://github.com/Sam-Belliveau)


Current Programmers

 - [Kevin Cai](https://github.com/Kevin16777126)
 - [Winston Peng](https://github.com/CreativePenguin)
 - [Ivan Wei](https://github.com/iwei20)
