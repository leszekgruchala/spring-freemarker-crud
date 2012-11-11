#!/bin/bash
echo Making application...
${M2_HOME:?"Need to set M2_HOME"}
echo Maven found at $M2_HOME
echo Cleaning and compiling and testing…
$M2_HOME/bin/mvn clean package