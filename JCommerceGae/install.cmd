echo off
rem change this
set JAVA_HOME=D:\Program Files\Java\jdk1.6.0_17
set GAE_HOME=D:\JCommerce\appengine-java-sdk-1.3.7

rem DO NOT change
Set CLASSPATH=%GAE_HOME%/lib/appengine-tools-api.jar
@"%JAVA_HOME%/bin/java" -cp %CLASSPATH% com.google.appengine.tools.admin.AppCfg %* --enable_jar_splitting update war