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
 * create sequences of Integer values.</p>
 *
 * <p>These Objects can also be created using the
 * {@link ParameterSweepUtil#createSweep(java.lang.String, int, int, int) }.</p>
 *
 * @see ParameterSweepUtil
 *
 * @author Davide Nunes
 */
public class IntegerSequenceSweep implements SequenceSweep<Integer> {

    private final int from;
    private final int to;
    private final int step;
    private final String param;

    /**
     * Constructor for this immutable Integer Sequence Sweep
     *
     * @param from the first item in the sequence
     * @param to the last item in the sequence
     * @param step the step to update the sequence
     */
    public IntegerSequenceSweep(String param, int from, int to, int step)
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
                new IntegerSequenceIterator(), new ConfigurationTranformer(
                param));

        return it;
    }

    @Override
    public Integer from() {
        return from;
    }

    @Override
    public Integer to() {
        return to;
    }

    @Override
    public Integer step() {
        return step;
    }

    /**
     * An Integer value iterator that generates this sequence on demand.
     */
    private class IntegerSequenceIterator implements Iterator<Integer> {

        private int currentValue;

        public IntegerSequenceIterator() {
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
        public Integer next() {
            int result = currentValue;
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
    public List<Integer> getValues() {
        Iterator<Integer> it = new IntegerSequenceIterator();
        List<Integer> values = new ArrayList<>();
        while (it.hasNext()) {
            values.add(it.next());
        }

        return values;
    }

    @Override
    public String toString() {
        return "[from:" + from + ", to:" + to + ", step:" + step + "]";

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + from;
        result = prime * result + ((param == null) ? 0 : param.hashCode());
        result = prime * result + step;
        result = prime * result + to;
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
        IntegerSequenceSweep other = (IntegerSequenceSweep) obj;
        if (from != other.from) {
            return false;
        }
        if (param == null) {
            if (other.param != null) {
                return false;
            }
        } else if (!param.equals(other.param)) {
            return false;
        }
        if (step != other.step) {
            return false;
        }
        if (to != other.to) {
            return false;
        }
        return true;
    }

    @Override
    public Iterator<Integer> valueIterator() {
        return new IntegerSequenceIterator();
    }

    @Override
    public int size() {
        int diff = Math.abs(to - from);

        if (!(to < 0 && from > 0 || to > 0 && from < 0)) {
            diff++;
        }
        return diff / Math.abs(step);
    }
}
