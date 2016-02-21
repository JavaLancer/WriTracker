echo off

REM Setup a path to Java
@if not "%JAVA_HOME%" == "" goto gotJavaHome
@set JAVA_HOME=..\..\jre
:gotJavaHome

REM Setup the Java classpath
@set CP=
@set CP=%CP%;WriTrackerBeta-1.5.jar

REM Setup the debugging arguments to Java.
set DEBUG_ARGS=-enableassertions -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,address=7770,suspend=n

REM Run the program with debugging available
@"%JAVA_HOME%\bin\java" %DEBUG_ARGS% -cp %CP% com.qbit.Assignment.Start -r -v

REM pause
