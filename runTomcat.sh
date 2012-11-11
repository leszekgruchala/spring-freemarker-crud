#!/bin/bash
echo Running application...
${CATALINA_HOME:?"Need to set CATALINA_HOME"}
echo Tomcat found at $CATALINA_HOME
echo Coping war to Tomcat
cp -fr target/crud.war $CATALINA_HOME/webapps
echo Starting Tomcat
echo Open this link in your browser http://localhost:8080/crud
$CATALINA_HOME/bin/startup.sh