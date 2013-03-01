B-have Sweeper
=============

A Parameter Sweeping Utility Library. This library is intended to easily create parameter sweeps for simulation models in Java. 
The task of sweeping parameter spaces is rather trivial but can lead to code cluttered with the creation of multiple parameter value ranges and
ugly nested cycles to create possible parameter combinations. This library provides the utilities to easily create parameter sweeps of various types
and combine them into combined parameter sweeps. Both parameter sweeps and the combined parameter sweeps are easily iterable and supply configuration instances on demand.

This library uses the [Java Configuration API](http://commons.apache.org/proper/commons-configuration/) from apache commons to 
create simple key-value structures ([`Configuration` objects](http://commons.apache.org/proper/commons-configuration/apidocs/index.html))
that can be used to configure simulation model (or whathever you want to configure).

## Simple Library Usage
You can use the `ParameterSweepUtil` to create multiple parameter sweeps in the following manner:

```java
//create an integer sequence from 0 to 10 with steps of 1
ParameterSweep sweepSequence1 = ParameterSweepUtil.createSweep("p1", 0, 10, 1);

//create a sequence of double values from 0 to 10 with steps of 0.5
ParameterSweep sweepSequence2 = ParameterSweepUtil.createSweep("p2", 0.0, 10.0, 0.5);

//create a parameter sweep with only one value
ParameterSweep sweepSingleValue1 = ParameterSweepUtil.createSweep("p3", 1);

ParameterSweep sweepSingleValue2 = ParameterSweepUtil.createSweep("p4", 1.0);

ParameterSweep sweepSingleValue3 = ParameterSweepUtil.createSweep("p5", "jabbas");

//create a parameter sweep with a pre-existing value list
List<Integer> values = new ArrayList<>();
values.add(1);
values.add(5);
values.add(10);

ParameterSweep sweepList = ParameterSweepUtil.<Integer>createSweep("p6", values);

//create a CombinedParameterSweep
List<ParameterSweep> sweeps = new LinkedList<>();

sweeps.add(sweepSequence1);
sweeps.add(sweepSequence2);
sweeps.add(sweepSingleValue3);
sweeps.add(sweepList);


CombinedParameterSweep paramSpace = ParameterSweepUtil.createCombinedSweep(sweeps,2);

//iterate over possible configurations
for (Configuration config : paramSpace) {
  //do stuff with the values in the configuration for example
  int v1 = config.getInt("p1");
  int v2 = config.getInt("p2");
  String v3 = config.getString("p5");
  //... etc
}


```


## Latest releases

Current version is 1.0
* [bhave.sweeper-1.0-with-dependencies.jar](http://dl.dropbox.com/u/336879/Projects/Releases/bhave.sweeper/sweeper-1.0-jar-with-dependencies.jar)
* [bhave.sweeper-1.0.jar](http://dl.dropbox.com/u/336879/Projects/Releases/bhave.sweeper/sweeper-1.0.jar)
* [bhave.sweeper-1.0-sources.jar](http://dl.dropbox.com/u/336879/Projects/Releases/bhave.sweeper/sweeper-1.0-sources.jar)
* [bhave.sweeper-1.0-javadoc.jar](http://dl.dropbox.com/u/336879/Projects/Releases/bhave.sweeper/sweeper-1.0-javadoc.jar)

## Dependencies
**Note:** the above jar with dependencies includes all of the following dependencies:
* commons-configuration-1.9
  * commons-logging-1.1.1
  * commons-lang-2.6
* commons-collections-3.2.1


## Licence
 B-Have Sweeper Library
 
 * Copyright (C) 2013 Davide Nunes 
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://bhaveproject.org
 
 The b-have sweeper library is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 The b-have sweeper library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with the b-have sweeper library.  
 If not, see [GPL 3.0](http://www.gnu.org/licenses/gpl.html).
