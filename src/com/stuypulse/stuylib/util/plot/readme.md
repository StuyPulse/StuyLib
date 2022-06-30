# StuyLib Plotting Library

A simple library for plotting streams and filters. Wraps [Knowm's XChart library](https://knowm.org/open-source/xchart/).

The library is based around a `Plot` that can have various types of `Series`. A plot handles displaying data, while a Series handles organizing data.

## What is a Series?

A series represents any kind of function or data to be graphed. There are three things that series must say about itself: its "configuration", whether or not it's "polling", and its (x,y) data. The main assumption made about a series is that the data should be represented by a line.

There are actual no methods in a `Series` that the programmer has to call, but the way a `Series` works and the methods that make it work are covered below:

### Config

Series includes a Config class which stores the name and capacity of a series. The capacity is the max amount of points that can be plotted at the same time.

The `.pop()` method is called to remove the oldest point from a series when its reached its capacity. If a series is non-changing it can leave this blank.

The name of the config is what will appear on the legend of the plot.

### Polling

Because many series change with time, like following the user's mouse or adding points from a stream, a series can be is polled to update its data. Non-changing series (e.g. all data points can be pre-calculated) disable polling by setting `Series.polling` to false through Series' constructor.

One method in Series enable polling: `.poll()`. Here a series should add a value from wherever its reading data to itself. If a series is non-changing it can leave this blank.

### Data

The actual data for a series is accessed through `.getSafeXValues()` and `.getSafeYValues()` in x, y pairs. These methods should return a 'safe' (e.g. copied) list. XChart can modify the returned list at the same time as the series, which will cause a concurrent modification exception.

Some series don't modify their internal data, so these series don't need to copy and can simply return a reference to their data.

## Types of Series

### [TimeSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/TimeSeries.java)

A [TimeSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/TimeSeries.java) is used to plot a stream of values that changes over time.

A `TimeSeries` uses an [IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/IStream.java) to get new values. Note, the `IStream` does not have access to time, it is simply a stream of data. If a function of time is needed, see `FuncSeries`. A `TimeSeries` can be used to test [IFilters](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/IFilter.java) because an `IStream` can be filtered.

A `TimeSeries` is created with a `TimeSpan`, which determines the time range that the series will be graphed on. Using `TimeSpan` and `Config.capacity`, the x-axis is precomputed to put points evenly through the TimeSpan.

`TimeSeries` can be used with [MouseTracker](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/MouseTracker.java), like shown below:

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

A [FuncSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/FuncSeries.java) is used to plot a function on a given domain.

[FuncSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/FuncSeries.java) uses an [IFilter](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters/IFilter.java) to represent a function.

A `FuncSeries` should graph static functions like x<sup>2</sup> (e.g. functions whose outputs for a given x-value don't change with time). This is because a `FuncSeries` precomputes the graph on the entire domain, so if the outputs *did* change over time, they wouldn't be reflect in the graph. However, this does mean that a `FuncSeries` can also be used to visualize [Interpolators](https://github.com/StuyPulse/StuyLib/blob/bg/plot-docs/src/com/stuypulse/stuylib/math/interpolation/Interpolator.java).

Similar to `TimeSeries`, `FuncSeries` is created with a `Domain` which limits the x-values that function is graped over.

In the example below, x<sup>2</sup> is graphed by inputting `x -> x * x` as an IFilter.

```java
FuncSeries xsq = new FuncSeries(
    new Config("x^2", 500),
    new Domain(-2, 2),
    x -> x * x);
```

### [XYSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/XYSeries.java)

A [XYSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/XYSeries.java) is used to plot a stream of points (x,y values) that changes over time.

[XYSeries](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/util/plot/XYSeries.java) uses [VStreams](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/vectors/VStream.java) to get data. These streams can be paired with [VFilters](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/vectors/filters/VFilter.java) using `.filtered(...)`.

Like `TimeSeries`, it can be used with `MouseTracker` to demonstrate filters, but this time in two dimensions! XYSeries is helpful to test VFilters and visualize 2D data.

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
