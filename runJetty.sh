#!/bin/bash
echo Running application with maven Jetty plugin...
${M2_HOME:?"Need to set M2_HOME"}
echo Maven found at $M2_HOME
echo Open this link in your browser http://localhost:8080/crud
$M2_HOME/bin/mvn jetty:run