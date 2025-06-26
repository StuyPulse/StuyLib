# StuyLib Filters Library

## What is a filter?

A filter is basically a class / function that takes in a value, and returns the "filtered" value. 

For example say we made a Moving Average Filter.

```java
// Returns the average of the last 3 values
IFilter avg = new MovingAverage(3); 

System.out.println(avg.get(1)); // Prints: 1.0
System.out.println(avg.get(2)); // Prints: 1.5
System.out.println(avg.get(3)); // Prints: 2.0
System.out.println(avg.get(4)); // Prints: 3.0
System.out.println(avg.get(5)); // Prints: 4.0
```

Everytime we called `.get()` on the filter, it returned the averagee of the last 3 values, because thats what the filter was made to do.

## What should we use them for?

### Ramping Motors

When sending speed values to motors, you might not always want them to update immediately, as that could cause jerk and current spikes.

In WPILib, there is the option to ramp motors when configuring them. This ramping algorithm is analygous to the filtering done by the filter called [Rate Limit](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/RateLimit.java). RateLimit works good for smoothing out the motion of the robot, but through testing we have found that the filter called [LowPass Filter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/LowPassFilter.java), does a much nicer job of providing smooth motion without introducing a large delay.

We did experiment with [Speed Profile](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/SpeedProfile.java), which algebraicly removes jerk from the inputs of a conroller, however the delay it introduced killed its usefulness compared to [LowPass Filter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/LowPassFilter.java).

The reduced jerk when controlling the motors is a huge advantage as it uses less power, and prevents the robot from skipping / tilting, which can mess with sensor data. This allows the driver to drive faster without worrying about damaging the robot.

Delay is the biggest issue with filters. Because they can not predict the future, it's impossible for it to smooth out values without introducing some amount of delay. We've found that filters that prioritize recent values more than past ones, are able to do the best.

### Filtering Data

When reading data from something like the Limelight or an encoder, sometimes noise can be an issue. Encoders sometimes include a form of [Moving Average](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/MovingAverage.java) to smooth out noise in data. However, filters can often add a delay, which if fed into a PID loop, can often cause decreased performance. We've found that using a small [LowPass Filter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/LowPassFilter.java) on data that has a slight bit of noise in it can help a lot, however it will decrease performance.

A recently added filter named [Median Filter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/MedianFilter.java) is able to reduce noise while not having a huge impact on delay. It is good to combine this filter with other filters to get an optimal result.

## Which Filter should I use?

Some filters have internal timers in them which keep track of real world time. This is helpful if the data your collecting is real time, and in a time series. When it comes to FRC Robotics, almost all data that we have used filters on is real time. Be sure to read the documentation of certain filters to see which ones use timers in them. 

Depending on which type of data you are working with, some filters might be better than others. However, we've found the [LowPass Filter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/LowPassFilter.java) to be an amazing all around filter, and should be used as a Rule of Thumb when testing out filters. *(meaning that LowPass Filters will usually be the best)*

Also, combine filters using [IFilterGroup](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/IFilterGroup.java), as filtering filtered values can often have interesting and desirable results. 

## Why do filters take in `Numbers` instead of `double`?

In order to help make filters more configurable, the configuation is done using the Number class. Why? Because [SmartNumber](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/network/SmartNumber.java) extends `Number`, which makes it extremely easy to change values like the RC of a [LowPass Filter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/LowPassFilter.java) on the fly.
