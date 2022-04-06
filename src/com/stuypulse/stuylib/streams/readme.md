# StuyLib Streams Library 

The Streams library in StuyLib contains streams which return a stream of data. It also contains [filters](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/filters) which can be used to filter through raw input to reduce noise, jitter, input spikes, and jerkiness. 

## What is a stream?

A stream is any class / lambda that when you call `.get()` it returns a double.

For example, say we wanted a stream of controller inputs, we would write.

```java
Gamepad driver = new PS4(0);

// Basic Right - Left calculation to get speed
IStream speed = () -> driver.getRightTrigger() - driver.getLeftTrigger();

System.out.println(speed.get()); // Prints the current desired speed
```

By using a stream here, we now have an object we can call / pass around to get the desired speed of the robot. All we would need to do is call `.get()` to recieve the data.

## Why is this useful?

An [IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/IStream.java) is a useful object to pass around as it represents a constantly updating stream of information. There are things we can do with this stream to get some extra functionality.

### [Filtered IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/FilteredIStream.java)

A [Filtered ISteam](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/FilteredIStream.java) applies a [Filter](https://github.com/StuyPulse/StuyLib/tree/main/src/com/stuypulse/stuylib/streams/filters) to a stream, and returns that value instead. 

Continuing from the example above, we could do:

```java
IStream filtered_speed = new FilteredIStream(speed, new LowPassFilter(0.5));

System.out.println(filtered_speed.get()); // Prints the filtered value of the stream
```

### [PollingIStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/PollingIStream.java)

[PollingIStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/PollingIStream.java) is a very powerful wrapper around an [IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/IStream.java) that polls the underlying `.get()` function a fixed number of times per second. When you call `.get()` on a [PollingIStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/PollingIStream.java), it will return you the last polled value from the underlying stream.

```java
// Creates an IStream that is updated 50 times a second.
IStream polled_speed = new PollingIStream(speed, 50.0);
```

This is useful for streams where the state changes when you call `.get()`. Say you were using reading from [CSVIStream.Reader](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/CSVIStream.java), but you wanted a new value every 50th of a second. By using a PollingIStream, no matter how fast you call `.get()`, you will only ever recieve a new value every 50th of a second. When using a [Filtered IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/FilteredIStream.java) or a [CSVIStream.Reader](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/CSVIStream.java), the value can change when you call `.get()`, so its important to be aware when you poll a stream.

### [BufferedIStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/BufferedIStream.java)

A [BufferedIStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/BufferedIStream.java) is just a wrapper around a normal [IStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/IStream.java) that lets you access past values with `.get(delta)`. When stored inside an IStream it acts identically to the original stream, making its usage a bit difficult.

### [CSVIStream](https://github.com/StuyPulse/StuyLib/blob/main/src/com/stuypulse/stuylib/streams/CSVIStream.java)

CSVIStream has two child classes in it, `CSVIStream.Writer` and `CSVIStream.Reader`.

`CSVIStream.Writer` is used to save the values of a stream as you read it, and can be used as so. 

```java
IStream recorded_speed = new CSVIStream.Writer("./speeds.csv", speed);

System.out.println(recorded_speed.get()); // Prints the value of the speed stream and writes the value to the CSV.
```

`CSVIStream.Reader` is used to read back the values of a CSV file in the form of a stream. Imagine that this is playing back the file we just created from before:

```java
IStream file_speed = new CSVIStream.Reader("./speeds.csv");

System.out.println(file_speed.get()); // The first value of the CSV file.
System.out.println(file_speed.get()); // The second value of the CSV file.
System.out.println(file_speed.get()); // The third value of the CSV file.
```

This is useful in general for things like logging or reading CSV files in a streamlined way.
