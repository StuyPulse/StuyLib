# StuyLib Math Library

The Math library in StuyLib contains helpful utilities that make FRC programming easier and cleaner. 

### [SLMath](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/SLMath.java)

SLMath is a class with static math functions that are useful in robotics.

For example, when squaring a number, `SLMath.square(x)` will keep the sign of the number. *This is used when filtering controller inputs.* `SLMath.pow(x, p)` will also keep the sign of `x` no matter what the value of `x` or `p`. This might not seem useful normally, but is nice to have in FRC.

Other functions like `SLMath.limit` or `SLMath.deadband` are super helpful in FRC.

### [Angle](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java)

This is an [Angle](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java) class that removes the confusion about degrees and radians, and works nicely with our [Vector2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java). When doing math with the [Angle](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java) class, you do not need to worry about the value of the angle being outside the range of `-pi < x < pi`.

### [Vector2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java)

The [Vector2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java) class contains features that cover common coordinate vector manipulation needs, and also works with other classes in StuyLib. It also works nicely with the [Angle](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Angle.java) class.

### [Polar2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Polar2D.java)

The [Polar2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Polar2D.java) class contains features that cover common polar vector manipulation needs. It is heavily dependant on the [Angle](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Angle.java) and [Vector2D](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/math/Vector2D.java) classes. 