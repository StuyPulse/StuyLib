# StuyLib Math Library

## What is the point of these classes

### [SLMath](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/SLMath.java)

SLMath is just a class with some static math functions that are useful in robotics.

For example, when squaring a number, `SLMath.square(x)` will keep the sign of the number. *this is used when filtering controller inputs* `SLMath.pow(x, p)` will also keep the sign of x no matter what the value of x or p. This might not seem useful normally, but is nice to have in FRC.

Other functions like `SLMath.limit` or `SLMath.deadband` are super helpful in FRC.

### [Vector2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java)

This is just a standard Vector2D class that has every feature you would need, and also works with our other classes in Stuylib. It also works nicely with our [Angle](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Angle.java) class, which is a nice bonus.

### [Angle](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java)

This is an angle class that removes the confusion about degrees / radians, and works nicely with our [Vector2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java). When doing math with the angle class, you do not need to worry about the value of the angle being outside the range of `-pi < x < pi`.

## Summary

This Math folder in stuylib contains some helpful utilities that make programming FRC code a little bit easier.