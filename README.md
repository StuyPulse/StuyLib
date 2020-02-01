![](https://github.com/StuyPulse/StuyLib/raw/master/pictures/StuyLib%20Banner.png)

[![](https://jitpack.io/v/StuyPulse/StuyLib.svg)](https://jitpack.io/#StuyPulse/StuyLib)

# What is StuyLib?

Every year, our team among many others is tasked with creating a useful, yet daunting util folder. This folder must contain important files like `Gamepad.java`, but because it must be reinvented every year. It is often filled with unpolished, messy code. Stuylib is here to change that!

It did get a little bit bigger more advanced than just a util folder, but not everything is necessary.

# How do I use StuyLib?

### Setting up StuyLib

[Instructions from JitPack.io](https://jitpack.io/#StuyPulse/StuyLib)

Step 1. Add the JitPack repository to your build file

 - Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

 - Step 2. Add the dependency
```
	dependencies {
	    compile 'com.github.StuyPulse:StuyLib:VERSION'
	}
```

The version tag can be seen at the top of the readme. An example would be:
```
	dependencies {
	    compile 'com.github.StuyPulse:StuyLib:2020.1.8.1'
	}
```


### Other Dependencies

If you want the NetworkTable classes to work on something other than the robot, then you need to include other dependencies.

If you are using network tables anywhere else than the robot, ie. on a laptop, you will need to include the jar files in the `./lib` folder. For example, when using NetKeyboard, you need to run a program to send the keyboard input, [here is the program](https://github.com/Sam-Belliveau/NetworkKeyboardServer). This program uses these jar files to send network table data to the robot. It is otherwise pretty rare that you would need to use these

# Criteria for Code Contribution

When contributing code, as always, make a branch and a pull request.

All code MUST be commented, and no single class should be to complicated.

At the top of each class, give a brief description of the class, along with an author tag which contains an email just in case anybody has any questions on how to use it.

# Current Positions

Current Lead Repository Maintainer

 - [Sam Belliveau](https://github.com/Sam-Belliveau)


Current Programmers

 - [Ivan Wei](https://github.com/iwei20)
 - [Winston Peng](https://github.com/CreativePenguin)
 - [Kevin Cai](https://github.com/Kevin16777126)
