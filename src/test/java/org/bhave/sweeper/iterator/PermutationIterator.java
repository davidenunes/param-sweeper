package org.bhave.sweeper.iterator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.iterators.IteratorChain;
import org.junit.Test;

public class PermutationIterator {

	@Test
	public void test() {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();

		for (int i = 0; i < 3; i++) {
			list1.add(i);
			list2.add(i);
		}

		IteratorChain itChain = new IteratorChain(new Iterator[] {
				list1.iterator(), list2.iterator() });

		while (itChain.hasNext()) {
			System.out.println(itChain.next());
		}
	}
}
