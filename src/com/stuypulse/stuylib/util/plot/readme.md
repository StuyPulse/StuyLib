# StuyLib Plotting Library

A simple library for plotting streams and filters. Wraps [Knowm's XChart library](https://knowm.org/open-source/xchart/).

## What is a Series?

A series represents any kind of function or data to be graphed.

### Config

Series includes a Config class which stores the name and capacity of a series. The capacity is the max amount of points that can be plotted at the same time.

In order to free up space for new points to be added, the `.pop()` method is called to remove a point (usually the oldest) from a series.

### Polling

Because many series change with time, like following the user's mouse or adding points from a stream, a series can be is polled to update its data. Non-changing series like FuncSeries  disable polling by returning false in `.isPolling()`.

### Data

The actual data for a series is accessed through `.getSafeXValues()` and `.getSafeYValues()` in x, y pairs. These methods should return a 'safe' (copied) list, as XChart can modify the returned list at the same time as the series which could cause a concurrent modification exception. However, some series that don't modify their internal data don't need to return a copied list, like FuncSeries.

### [TimeSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/TimeSeries.java)

A [TimeSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/TimeSeries.java) plots the output of an [IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/IStream.java) on the y-axis and time on the x-axis. It can be used if you want to test [IFilters](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/IFilter.java) or any data that changes through time. A TimeSeries is additionally created with a `TimeSpan`, which determines the time range that the series will be graphed on. Using TimeSpan and `Config.capacity`, the x-axis is precomputed to put points evenly through the TimeSpan.

TimeSeries can be used with [MouseTracker](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/MouseTracker.java), like shown below:

```java
TimeSeries mouse = new TimeSeries(
    new Config("mouse", 500),
    new TimeSpan(0, 1),
    IStream.create(plot::getMouseY));

TimeSeries lpf = new TimeSeries(
    new Config("low pass filter", 500),
    new TimeSpan(0, 1),
    IStream.create(plot::getMouseY)
        .filtered(new LowPassFilter(0.2)));
```

### [FuncSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/FuncSeries.java)

[FuncSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/FuncSeries.java) graphs an [IFilter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/IFilter.java) and can be used to graph any static functions like x<sup>2</sup> with one input and one output. FuncSeries can also be used to test and visualize [Interpolators](https://github.com/StuyPulse/StuyLib/blob/bg/plot-docs/src/com/stuypulse/stuylib/math/interpolation/Interpolator.java). Similar to TimeSeries, FuncSeries is additionally created with a `Domain` which limits the x-values that function is graped over.

In the example below, x<sup>2</sup> is graphed by inputting `x -> x * x` as an IFilter.

```java
FuncSeries xsq = new FuncSeries(
    new Config("x^2", 500),
    new Domain(-2, 2),
    x -> x * x);
```

### [XYSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/XYSeries.java)
[XYSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/XYSeries.java) works with [VStreams](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/vectors/VStream.java) and [VFilters](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/vectors/filters/VFilter.java). Like TimeSeries, it can be used with `MouseTracker` to demonstrate filters, but this time in two dimensions! XYSeries is helpful to test VFilters and visualize 2D data.

```java
XYSeries mouse = new XYSeries(
    new Config("mouse pos", 500),
    VStream.create(plot::getMouse));

XYSeries jerk = new XYSeries(
    new Config("jerk limit", 500),
    VStream.create(plot::getMouse)
        .filtered(new VJerkLimit(10.0, 5.0)));
```

## What is a Plot?

A Plot contains all of the graphics elements and references to [Knowm's XChart library](https://knowm.org/open-source/xchart/). It also holds all of the Series to be graphed and a MouseTracker.

A Plot can be additionally created with a [Settings](https://github.com/StuyPulse/StuyLib/blob/bg/plot-docs/src/com/stuypulse/stuylib/util/plot/Settings.java), which can change the title, axes labels, size, and ranges of the plot.

### Working with a Plot

Like shown in the Series examples, the MouseTracker's methods are exposed through `.getMouse()`, `.getMouseY()`, and `.getMouseX()`. These methods can be used in creating a Series and can be casted to streams, ex. `IStream.create(plot::getMouseY)`.

Series can be added to the plot through `.addSeries()`, and Series are updated through `.updateSeries()`, which will poll each series and update its points. In `.isRunning()`, a plot will check if each of its Series are polling and will keep running if so. For example, a plot conatining only a FuncSeries will not be polling and will only run once.

A typical plot loop might look like this:

```java
Plot plot = new Plot();

while (plot.isRunning()) {
    plot.update();
    Thread.sleep(20);
}
```
