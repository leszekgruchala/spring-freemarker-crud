##### Spring CRUD is sample Create, Read, Update, Delete application build with Spring 3.2 with completely no XML configuration.

It uses Freemarker, Twitter Bootstrap, JQuery, Hibernate, embedded HSQLDB, Logback, TestNG, FEST matchers, Mockito and Maven.
Shows how to wire Spring through Java Config with Hibernate, Validators, Freemarker and other stuff.

There are three scripts:

* build.sh - cleans, compiles, builds WAR file and runs tests
* runTomcat.sh - searchers for CATALINA_COME, copies WAR file there and runs Tomcat
* runJetty.sh - runs app directly from sources with Jetty 8 from jetty-maven-plugin. Easies and fastest solution :-)

Make sure you have execution rights and click this link [http://localhost:8080/crud](http://localhost:8080/crud)

License: Copy it and use as you like :-)