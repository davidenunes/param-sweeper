package org.bhave.sweeper.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.ParameterSweepUtil;
import org.bhave.sweeper.SequenceSweep;

/**
 * <p>Implements the {@link SequenceSweep} Interface to provide a simple way to
 * create sequences of Double values.</p>
 *
 * <p>These Objects can also be created using the
 * {@link ParameterSweepUtil#createSweep(java.lang.String, double, double, double) }.</p>
 *
 * @see ParameterSweepUtil
 *
 * @author Davide Nunes
 */
public class DoubleSequenceSweep implements SequenceSweep<Double> {

    private final double from;
    private final double to;
    private final double step;
    private final String param;

    /**
     * Constructor for this immutable Integer Sequence Sweep
     *
     * @param from the first item in the sequence
     * @param to the last item in the sequence
     * @param step the step to update the sequence
     */
    public DoubleSequenceSweep(String param, double from, double to, double step)
            throws IllegalArgumentException {
        this.from = from;
        this.to = to;
        this.step = step;
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
        return from;
    }

    @Override
    public Double to() {
        return to;
    }

    @Override
    public Double step() {
        return step;
    }

    /**
     * An iterator that generates a sequence of Double objects on demand. This
     * means that the actual sequence is not generated until the next method is
     * called.
     */
    private class DoubleSequenceIterator implements Iterator<Double> {

        private double currentValue;

        public DoubleSequenceIterator() {
            this.currentValue = from;
        }
        boolean canStep = true;

        @Override
        public boolean hasNext() {
            if (!canStep) {
                return false;
            }
            return step >= 0 ? currentValue <= to : currentValue >= to;
        }

        @Override
        public Double next() {
            double result = currentValue;
            if (step == 0) {
                canStep = false;
            }
            currentValue += step;

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
        double diff = Math.abs(to - from);

        int size = (int) Math.floor((diff / Math.abs(step)));

        return (!(to < 0 && from > 0 || to > 0 && from < 0)) ? (size + 1)
                : size;
    }

    //EQUALS AND HASHCODE
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DoubleSequenceSweep other = (DoubleSequenceSweep) obj;
        if (Double.doubleToLongBits(from) != Double
                .doubleToLongBits(other.from)) {
            return false;
        }
        if (param == null) {
            if (other.param != null) {
                return false;
            }
        } else if (!param.equals(other.param)) {
            return false;
        }
        if (Double.doubleToLongBits(step) != Double
                .doubleToLongBits(other.step)) {
            return false;
        }
        if (Double.doubleToLongBits(to) != Double.doubleToLongBits(other.to)) {
            return false;
        }
        return true;
    }
}
