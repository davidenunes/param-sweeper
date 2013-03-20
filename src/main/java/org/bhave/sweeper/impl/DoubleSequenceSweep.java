/**
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * This file is part of the b-have sweeper library.
 * 
 * The b-have sweeper library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The b-have sweeper library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the b-have network library.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.sweeper.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.ParameterSweepUtil;
import org.bhave.sweeper.SequenceSweep;

/**
 * <p>
 * Implements the {@link SequenceSweep} Interface to provide a simple way to
 * create sequences of Double values.
 * </p>
 * 
 * <p>
 * These Objects can also be created using the
 * {@link ParameterSweepUtil#createSweep(java.lang.String, double, double, double) }
 * .
 * </p>
 * 
 * @see ParameterSweepUtil
 * 
 * @author Davide Nunes
 */
public class DoubleSequenceSweep implements SequenceSweep<Double> {

	private final BigDecimal from;
	private final BigDecimal to;
	private final BigDecimal step;
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
		this.from = new BigDecimal(from);
		this.to = new BigDecimal(to);
		this.step = new BigDecimal(step);
		this.param = param;

		if (step == 0) {
			throw new IllegalArgumentException("step cannot be 0");
		}
		if ((to > from && step < 0) || to < from && step > 0) {
			throw new IllegalArgumentException(
					"step must be positive if to > from and negative if to < from");
		}

	}

	@Override
	public String getParameterName() {
		return param;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Configuration> iterator() {
		TransformIterator it = new TransformIterator(
				new DoubleSequenceIterator(),
				new ConfigurationTranformer(param));

		return it;
	}

	@Override
	public Double from() {
		return from.doubleValue();
	}

	@Override
	public Double to() {
		return to.doubleValue();
	}

	@Override
	public Double step() {
		return step.doubleValue();
	}

	/**
	 * An iterator that generates a sequence of Double objects on demand. This
	 * means that the actual sequence is not generated until the next method is
	 * called.
	 */
	private class DoubleSequenceIterator implements Iterator<Double> {

		private BigDecimal currentValue;

		public DoubleSequenceIterator() {
			this.currentValue = new BigDecimal(from.doubleValue());
		}

		boolean canStep = true;

		@Override
		public boolean hasNext() {
			if (!canStep) {
				return false;
			}

			return step.signum() > 0 ? currentValue.compareTo(to) <= 0
					: currentValue.compareTo(to) >= 0;
		}

		@Override
		public Double next() {
			double result = currentValue.round(MathContext.DECIMAL32)
					.doubleValue();

			if (step.signum() == 0) {
				canStep = false;
			}
			currentValue = currentValue.add(step);
			currentValue = currentValue.round(MathContext.DECIMAL32);

			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(
					"You cannot remove items from a sweep");
		}
	}

	/**
	 * Constructs the whole Sequence and returns it as a list
	 */
	@Override
	public List<Double> getValues() {
		Iterator<Double> it = new DoubleSequenceIterator();
		List<Double> values = new ArrayList<>();
		while (it.hasNext()) {
			values.add(it.next());
		}

		return values;
	}

	@Override
	public String toString() {
		return "[from:" + from + ", to:" + to + ", step:" + step + "]";

	}

	/**
	 * Returns an iterator over the possible Double values generator by this
	 * Parameter Sweep
	 * 
	 * @return an Iterator over Double objects
	 */
	@Override
	public Iterator<Double> valueIterator() {
		return new DoubleSequenceIterator();
	}

	@Override
	public int size() {
		BigDecimal diff = to.subtract(from).abs();

		int size = diff.divide(step.abs(), RoundingMode.FLOOR).intValue();

		return (!(to.compareTo(BigDecimal.ZERO) < 0
				&& from.compareTo(BigDecimal.ZERO) > 0 || to
				.compareTo(BigDecimal.ZERO) > 0
				&& from.compareTo(BigDecimal.ZERO) < 0)) ? (size + 1) : size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((param == null) ? 0 : param.hashCode());
		result = prime * result + ((step == null) ? 0 : step.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (param == null) {
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		if (step == null) {
			if (other.step != null)
				return false;
		} else if (!step.equals(other.step))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

}
