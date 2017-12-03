Param Sweeper
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

//2 in this case is the number of runs for each parameter combination
//the CombinedParameterSweep Iterator will present each configuration twice
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
## Loading Parameter Spaces from Files
To make this library more flexible when used with other packages. We added a `ParameterSweep` loader. This can be used either by 
creating a `SweepLoader` instance and using the appropriate method to load the parameters or by using the `ParameterSweepUtil` to 
load sweeps from a file or create `CombinedParameterSweep` objects directly using that file.

```java
//loading a list of Parameter Seeps from a file

SweepLoader sweepLoader = new SweepLoader();
List<ParameterSweep> sweeps = sweepLoader.fromFile(file);

//creating a combined sweep using the parameter sweep utility
int runs = 30;

CombinedParameterSweep combinedSweep = ParameterSweepUtil.loadCombinedSweep(file, runs);


```

A typical parameter sweep file looks like this:

```
# Parameter Sweep File

# Parameters 
params.0.name = p1-single
params.0.sweep = single
params.0.type = int
params.0.value = 15

params.1.name = p2-list
params.1.sweep = list
params.1.type = int
params.1.value = 1,2,3,4,5,6,7,8,9,10

params.2.name = p3-sequence
params.2.sweep = sequence
params.2.type = double
params.2.value.from = 0.0
params.2.value.to = 10.0
params.2.value.step = 0.5
```

For each parameter, you must provide:

* string that identifies uniquely the parameter as its `name`;
* a `type of sweep` which can be `single`, `list` or `sequence`;
* a `data type` which determines the type of the values: this can be `int`, `double`, `string`, or `boolean`;
* a `value` which can be a single vale a list of values or a sequence defined using the `from`, `to` to define the range of the sequence and the `step` which defines the intermediate values of the sequence;


## Dependencies
* commons-configuration-1.9
  * commons-logging-1.1.1
  * commons-lang-2.6
* commons-collections-3.2.1


## Licence
 Param Sweeper Library
 
 * Copyright (C) 2013 Davide Nunes 
 * Authors : Davide Nunes - davidelnunes@gmail.com
 
 The param-sweeper library is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 The param-sweeper library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with the b-have sweeper library.  
 If not, see [GPL 3.0](http://www.gnu.org/licenses/gpl.html).
