package org.bhave.sweeper.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.SequenceSweep;

public class DoubleSequenceSweep implements SequenceSweep<Double> {

	private final double from;
	private final double to;
	private final double step;
	private final String param;

	/**
	 * Constructor for this immutable Integer Sequence Sweep
	 * 
	 * @param from
	 *            the first item in the sequence
	 * @param to
	 *            the last item in the sequence
	 * @param step
	 *            the step to update the sequence
	 */
	public DoubleSequenceSweep(String param, double from, double to, double step)
			throws IllegalArgumentException {
		this.from = from;
		this.to = to;
		this.step = step;
		this.param = param;

		if (step == 0)
			throw new IllegalArgumentException("step cannot be 0");
		if ((to > from && step < 0) || to < from && step > 0)
			throw new IllegalArgumentException(
					"step must be positive if to > from and negative if to < from");

	}

	public String getParameterName() {
		return param;
	}

	@SuppressWarnings("unchecked")
	public Iterator<Configuration> iterator() {
		TransformIterator it = new TransformIterator(
				new DoubleSequenceIterator(),
				new ConfigurationTranformer(param));

		return it;
	}

	public Double from() {
		return from;
	}

	public Double to() {
		return to;
	}

	public Double step() {
		return step;
	}

	private class DoubleSequenceIterator implements Iterator<Double> {
		private double currentValue;

		public DoubleSequenceIterator() {
			this.currentValue = from;
		}

		boolean canStep = true;

		public boolean hasNext() {
			if (!canStep)
				return false;
			return step >= 0 ? currentValue <= to : currentValue >= to;
		}

		public Double next() {
			double result = currentValue;
			if (step == 0) {
				canStep = false;
			}
			currentValue += step;

			return result;
		}

		public void remove() {
			throw new UnsupportedOperationException(
					"You cannot remove items from a sweep");
		}

	}

	/**
	 * Constructs the whole Sequence and returns it as a list
	 */
	public List<Double> getValues() {
		Iterator<Double> it = new DoubleSequenceIterator();
		List<Double> values = new ArrayList<Double>();
		while (it.hasNext()) {
			values.add(it.next());
		}

		return values;
	}

	public String toString() {
		return "[from:" + from + ", to:" + to + ", step:" + step + "]";

	}

	@Override
	public Iterator<Double> valueIterator() {
		return new DoubleSequenceIterator();
	}

	@Override
	public int size() {
		double diff = Math.abs(to - from);

		if (!(to < 0 && from > 0 || to > 0 && from < 0)) {
			diff += 1;
		}
		return new Double(diff / Math.abs(step)).intValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(from);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((param == null) ? 0 : param.hashCode());
		temp = Double.doubleToLongBits(step);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(to);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoubleSequenceSweep other = (DoubleSequenceSweep) obj;
		if (Double.doubleToLongBits(from) != Double
				.doubleToLongBits(other.from))
			return false;
		if (param == null) {
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		if (Double.doubleToLongBits(step) != Double
				.doubleToLongBits(other.step))
			return false;
		if (Double.doubleToLongBits(to) != Double.doubleToLongBits(other.to))
			return false;
		return true;
	}

}
