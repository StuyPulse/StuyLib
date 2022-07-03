/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import java.util.ArrayList;
import java.util.List;

import com.stuypulse.stuylib.math.Vector2D;

public class DataSeries extends Series {

	private List<Double> xValues;
	private List<Double> yValues;

	public DataSeries(String label, Vector2D... points) {
		super(new Config(label, points.length), false);

		xValues = new ArrayList<Double>();
		yValues = new ArrayList<Double>();

		for (Vector2D point : points) {
			xValues.add(point.x);
			yValues.add(point.y);
		}
	}

	@Override
	public int size() {
		return yValues.size();
	}

	@Override
	protected List<Double> getSafeXValues() {
		return xValues;
	}

	@Override
	protected List<Double> getSafeYValues() {
		return yValues;
	}

	@Override
	protected void pop() {}

	@Override
	protected void poll() {}
	
}
