echo off

REM Use this batch file when you want to debug the Fishbowl Server.
REM When you attach, use the following settings:
REM
REM     Debugger:       Default Debugger (JPDA)
REM     Connector:      SocketAttach
REM     Transport:      dt_socket
REM     Host:           localhost
REM     Port:           7770
REM
REM
REM This assumes that environment variable JAVA_HOME has been set properly.
REM Otherwise it will assume its being run from an installation and will
REM use the jre that gets distributed with Fishbowl.
REM

REM Setup a path to Java
@if not "%JAVA_HOME%" == "" goto gotJavaHome
@set JAVA_HOME=..\..\jre
:gotJavaHome

REM Setup the Java classpath
@set CP=
@set CP=%CP%;WriTrackerBeta-1.0.jar

REM Setup the debugging arguments to Java.
set DEBUG_ARGS=-enableassertions -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,address=7770,suspend=n

REM Run the program with debugging available
@"%JAVA_HOME%\bin\java" %DEBUG_ARGS% -cp %CP% com.qbit.Assignment.Start -r -v

REM pause
