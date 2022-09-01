# StuyLib Limelight Library

The Limelight Library allows the programmer to interface with the Limelight's network table. It allows for cleaner code which would be used for robot functions such as alignment for shooting or target acquisition. 

### [Limelight](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/limelight/Limelight.java)

[Limelight](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/limelight/Limelight.java) allows you to check the status of the limelight and get raw data from the limelight, along with data regarding the target, and utilize the returned data in manipulating the robot to align with a target. It also features low level control over the various elements of the Limelight's pipeline such as `LEDMode`.

Example: To determine the distance of the limelight from the target...

```
double targetHeight = 96.0;
double limelightHeight = 38.7;
double limelightPitch = 27.0;
double heightDifference = targetHeight - limelightHeight;

public static boolean hasAnyTarget() {
  return kLimelight.getValidTarget();
}

public static Angle getYAngle() {
  if (hasAnyTarget()) {
    return Angle.fromDegrees(kLimelight.getTargetYAngle() + limelightPitch);
  }
  return Angle.kZero;
}

public static double getDistance() {
  if (hasAnyTarget()) {
    return heightDifference / getYAngle().tan();
  }
  return Angle.kZero;
}
```
