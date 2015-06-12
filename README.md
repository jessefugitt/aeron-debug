# aeron-debug
This repo contains tools that are used to assist with debugging Aeron (https://github.com/real-logic/Aeron), which is a high performance messaging transport.

## Trace Logging
Since Aeron does not implement any standard debug logging, a supplementary tool is needed to provide the typical trace or debug logging that can output timestamp, thread name, log level, log level, method name, argument values, etc.  Among the many ways this can be done, runtime instrumentation via a javaagent was chosen as an initial solution because it does not require any modification to the Aeron jars and there are libraries like AspectJ that allow the instrumentation to be written in standard Java.

The high level usage is straightforward and involves adding a few additional jars to the classpath and specifying a -javaagent on the command line when starting a Java process.  For example, if trace logging to stdout is needed on an Aeron MediaDriver process the command line would change from:

```
java -cp /path/to/agrona/*:/path/to/aeronjars/* uk.co.real_logic.aeron.driver.MediaDriver
```

to

```
java -javaagent:/path/to/aspectjweaver-1.8.6.jar -cp /path/to/agrona/*:/path/to/aeron-jars/*:/path/to/aspectj-jars/*:/path/to/aeron-logging-jar uk.co.real_logic.aeron.driver.MediaDriver
```

In version 0.0.1, there are two aeron-debug logging artifacts that can be used and both intercept calls to Aeron methods and print the method name and argument values when used with the aspectjweaver java agent.  One is a simple "print to stdout" implementation and the other is for intergrating with existing java logging frameworks at a TRACE log level.

### Trace Logging - 3rdParty jars
Download the aspectj and other third party jars from the aeron-debug Releases page [3rdParty.zip] (https://github.com/jessefugitt/aeron-debug/releases/download/v0.0.1/3rdParty.zip).

### Trace Logging - stdout
The first artifact, [aeron-logging-stdout-0.0.1.jar] (https://github.com/jessefugitt/aeron-debug/releases/download/v0.0.1/aeron-logging-stdout-0.0.1.jar), simply prints the information directly to stdout and doesn't depend on any additional jars besides aspectj.



### Trace Logging - slf4j
The second artifact, aeron-logging-slf4j-0.0.1.jar ((https://github.com/jessefugitt/aeron-debug/releases/download/v0.0.1/aeron-logging-slf4j-0.0.1.jar), uses the [slf4j](http://www.slf4j.org/) api at the TRACE logging level to log the information.  This allows many different logging implementations (log4j, java util logging, etc) to do the actual logging and choose whether to output the results to the console and/or a file and allows an existing Java application that is already using slf4j to pick up the Aeron logging results as if the Aeron library contained actual log statements.

### Gradle/Maven Repo
The v0.0.1 release currently only exists in a github hosted maven repository here:
```
http://jessefugitt.github.io/maven-repo

group=org.kaazing
artifact=aeron-logging-stdout or aeron-logging-slf4j
version=0.0.1
```


This can be added to a gradle file with the following blocks:
```
    repositories {
        maven {
            url "http://jessefugitt.github.io/maven-repo"
        }
    }
    
    dependencies {
        ... //Existing dependencies like Aeron, etc
        runtime 'org.kaazing:aeron-logging-stdout:0.0.1'
        runtimeAgent 'org.aspectj:aspectjweaver:1.8.6@jar'
    }
    
    //Optional - create a new task that can launch a Java process and specify the javaagent
    task traceMediaDriver(type:JavaExec) {
        main = 'uk.co.real_logic.aeron.driver.MediaDriver'
        classpath = sourceSets.main.runtimeClasspath
        systemProperties(System.properties);
        
        //Include a javaagent to implementation runtime instrumentation and trace logging
        jvmArgs "-javaagent:${configurations.runtimeAgent.singleFile}"
    }
```
