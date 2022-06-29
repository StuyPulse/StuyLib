# StuyLib Plotting Library

A simple library for plotting streams and filters. Wraps [Knowm's XChart library](https://knowm.org/open-source/xchart/).

## What is a Series?

A series can represent any kind of function or data to be graphed.

### [TimeSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/TimeSeries.java)

A [TimeSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/TimeSeries.java) plots the output of an [IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/IStream.java) on the y-axis and time on the x-axis. A `TimeSeries` is created with a `TimeSpan`, which determines the time range that the series will be graphed on.

`TimeSeries` can be used with [MouseTracker](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/MouseTracker.java) along with other filters, like shown below:

```java
plot.addSeries(new TimeSeries(
        new Config("mouse", 500),
        new TimeSpan(0, 1),
        IStream.create(plot::getMouseY)))

    .addSeries(new TimeSeries(
        new Config("low pass filter", 500),
        new TimeSpan(0, 1),
        IStream.create(plot::getMouseY)
            .filtered(new LowPassFilter(0.2))))
```

### [FuncSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/FuncSeries.java)

[FuncSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/FuncSeries.java) can be used to graph any static functions like x<sup>2</sup>. Similar to `TimeSeries`, `FuncSeries` is created with a `Domain` which limits the x-values that function is graped over.

```java
plot.addSeries(new FuncSeries(
        new Config("x^2", 500),
        new Domain(-2, 2),
        x -> x * x))
```

### [XYSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/XYSeries.java)
[XYSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/XYSeries.java) works with [VStreams](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/vectors/VStream.java) and [VFilters](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/vectors/filters/VFilter.java). Like TimeSeries, it can be used with `MouseTracker` to demonstrate filters, but this time in two dimensions!

```java
plot.addSeries(new XYSeries(
        new Config("mouse pos", 500),
        VStream.create(plot::getMouse)))

    .addSeries(new XYSeries(
        new Config("jerk limit", 500),
        VStream.create(plot::getMouse)
            .filtered(new VJerkLimit(10.0, 5.0))))
```

## Parts of a Series

### Config

Series includes a Config class which stores the name and capacity of a series. The capacity is the max amount of points that can be plotted at the same time.

In order to free up space for new points to be added, the `.pop()` method is called to remove a point (usually the oldest) from a series.

### Polling

Because many series change with time, like following the user's mouse or adding points from a stream, a series can be is polled to update its points. Non-changing series like FuncSeries  disable polling by returning false in `.isPolling()`.

### Data

The actual data for a series is accessed through `.getSafeXValues()` and `.getSafeYValues()` in x, y pairs. These methods should return a 'safe' (copied) list, as XChart can modify the returned list at the same time as the series which could cause a concurrent modification exception. However, some series that don't modify their internal data don't need to return a copied list, like FuncSeries.

## 
