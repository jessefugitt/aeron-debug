# aeron-debug
This repo contains tools that are used to assist with debugging Aeron (https://github.com/real-logic/Aeron), which is a high performance messaging transport.  The log tracing tools are used to instrument the Aeron jars at runtime (without modifying them) to produce standard java logging output such as the following snippets from a standard Aeron media driver:
```
2015-06-18 11:27:54.268 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(void uk.co.real_logic.aeron.driver.DriverConductor.onAddSubscription(String, int, long, long)), arg0=udp://localhost:62777, arg1=9999, arg2=1, arg3=0] 
2015-06-18 11:27:54.280 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(UdpChannel uk.co.real_logic.aeron.driver.media.UdpChannel.parse(String)), arg0=udp://localhost:62777] 
2015-06-18 11:27:54.281 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(AeronUri uk.co.real_logic.aeron.driver.media.UdpChannel.parseIntoAeronUri(String)), arg0=udp://localhost:62777] 
2015-06-18 11:27:54.281 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(AeronUri uk.co.real_logic.aeron.driver.media.UdpChannel.parseUdpUriToAeronUri(String)), arg0=udp://localhost:62777] 
...
2015-06-18 11:27:54.396 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(void uk.co.real_logic.aeron.driver.DriverConductor.onAddPublication(String, int, int, long, long)), arg0=udp://localhost:62777, arg1=1784088632, arg2=9999, arg3=2, arg4=0] 
2015-06-18 11:27:54.396 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(UdpChannel uk.co.real_logic.aeron.driver.media.UdpChannel.parse(String)), arg0=udp://localhost:62777] 
2015-06-18 11:27:54.396 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(AeronUri uk.co.real_logic.aeron.driver.media.UdpChannel.parseIntoAeronUri(String)), arg0=udp://localhost:62777] 
2015-06-18 11:27:54.397 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(AeronUri uk.co.real_logic.aeron.driver.media.UdpChannel.parseUdpUriToAeronUri(String)), arg0=udp://localhost:62777] 
2015-06-18 11:27:54.397 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(Map uk.co.real_logic.aeron.driver.UriUtil.parseQueryString(URI, Map)), arg0=udp://localhost:62777, arg1={}] 
...
2015-06-18 11:27:54.640 [receiver] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(int uk.co.real_logic.aeron.driver.media.ReceiverUdpChannelTransport.dispatch(UnsafeBuffer, int, InetSocketAddress)), arg0=uk.co.real_logic.agrona.concurrent.UnsafeBuffer@363e357e, arg1=36, arg2=/127.0.0.1:61827] 
2015-06-18 11:27:54.640 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(boolean uk.co.real_logic.aeron.driver.uri.AeronUri.containsAnyKey(String[])), arg0=[Ljava.lang.String;@5338e6d2] 
2015-06-18 11:27:54.640 [sender] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(int uk.co.real_logic.aeron.driver.NetworkPublication.send()), ] 
2015-06-18 11:27:54.640 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(boolean uk.co.real_logic.aeron.driver.uri.AeronUri.containsAnyKey(String[])), arg0=[Ljava.lang.String;@682cbf0b] 
2015-06-18 11:27:54.640 [receiver] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(int uk.co.real_logic.aeron.logbuffer.FrameDescriptor.frameType(UnsafeBuffer, int)), arg0=uk.co.real_logic.agrona.concurrent.UnsafeBuffer@363e357e, arg1=0] 
2015-06-18 11:27:54.641 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(UdpChannel.Context uk.co.real_logic.aeron.driver.media.UdpChannel.Context.uriStr(String)), arg0=udp://localhost:31111] 
2015-06-18 11:27:54.641 [sender] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(int uk.co.real_logic.aeron.logbuffer.LogBufferDescriptor.computeTermIdFromPosition(long, int, int)), arg0=0, arg1=24, arg2=233907791] 
2015-06-18 11:27:54.642 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(boolean uk.co.real_logic.aeron.driver.media.UdpChannel.isMulticast(AeronUri)), arg0=uk.co.real_logic.aeron.driver.uri.AeronUri@4a193a4c] 
2015-06-18 11:27:54.642 [receiver] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(int uk.co.real_logic.aeron.logbuffer.FrameDescriptor.typeOffset(int)), arg0=0] 
2015-06-18 11:27:54.642 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(boolean uk.co.real_logic.aeron.driver.uri.AeronUri.containsKey(String)), arg0=group] 
2015-06-18 11:27:54.642 [sender] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(void uk.co.real_logic.aeron.driver.NetworkPublication.setupMessageCheck(long, int, int, long)), arg0=174218380537723, arg1=233907791, arg2=0, arg3=0] 
2015-06-18 11:27:54.643 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(InetSocketAddress uk.co.real_logic.aeron.driver.uri.AeronUri.getSocketAddress(String)), arg0=remote] 
2015-06-18 11:27:54.643 [receiver] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(void uk.co.real_logic.aeron.driver.DataPacketDispatcher.onSetupMessage(SetupFlyweight, UnsafeBuffer, int, InetSocketAddress)), arg0=uk.co.real_logic.aeron.protocol.SetupFlyweight@77e3b9ca, arg1=uk.co.real_logic.agrona.concurrent.UnsafeBuffer@363e357e, arg2=36, arg3=/127.0.0.1:61827] 
2015-06-18 11:27:54.643 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(String uk.co.real_logic.aeron.driver.uri.AeronUri.get(String)), arg0=remote] 
2015-06-18 11:27:54.643 [sender] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(int uk.co.real_logic.aeron.driver.NetworkPublication.sendData(long, long, int)), arg0=174218380537723, arg1=0, arg2=0] 
2015-06-18 11:27:54.644 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(InetSocketAddress uk.co.real_logic.aeron.driver.uri.SocketAddressUtil.parse(CharSequence)), arg0=localhost:31111] 
2015-06-18 11:27:54.644 [receiver] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(int uk.co.real_logic.aeron.protocol.SetupFlyweight.streamId()), ] 
2015-06-18 11:27:54.645 [driver-conductor] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(InetSocketAddress uk.co.real_logic.aeron.driver.uri.SocketAddressUtil.parse(CharSequence, BiFunction)), arg0=localhost:31111, arg1=uk.co.real_logic.aeron.driver.uri.SocketAddressUtil$$Lambda$41/1071746190@6301ecb8] 
2015-06-18 11:27:54.645 [sender] TRACE org.kaazing.aeron.debug.LoggingSlf4jAspect - [execution(void uk.co.real_logic.aeron.driver.NetworkPublication.heartbeatMessageCheck(long, long, int)), arg0=174218380537723, arg1=0, arg2=233907791] 


```

## Trace Logging
Since Aeron does not implement any standard debug logging, a supplementary tool is needed to provide the typical trace or debug logging that can output timestamp, thread name, log level, log level, method name, argument values, etc.  Among the many ways this can be done, runtime instrumentation via a javaagent was chosen as an initial solution because it does not require any modification to the Aeron jars and there are libraries like AspectJ that allow the instrumentation to be written in standard Java.

The high level usage is straightforward and involves adding a few additional jars to the classpath and specifying a -javaagent on the command line when starting a Java process.  For example, if trace logging to stdout is needed on an Aeron MediaDriver process the command line would change from:

```
java -cp /path/to/aeronjars/* uk.co.real_logic.aeron.driver.MediaDriver
```

to

```
java -javaagent:/path/to/aspectjweaver-1.8.6.jar -cp /path/to/aeron-jars/*:/path/to/aspectj-jars/*:/path/to/aeron-logging-jar uk.co.real_logic.aeron.driver.MediaDriver
```

In version 0.0.1, there are two aeron-debug logging artifacts that can be used and both intercept calls to Aeron methods and print the method name and argument values when used with the aspectjweaver java agent.  One is a simple "print to stdout" implementation and the other is for intergrating with existing java logging frameworks at a TRACE log level.

### Trace Logging - 3rdParty jars
Download the aspectj and other third party jars from the aeron-debug Releases page [3rdParty.zip] (https://github.com/jessefugitt/aeron-debug/releases/download/v0.0.1/3rdParty.zip).

### Trace Logging - stdout
The first artifact, [aeron-logging-stdout-0.0.1.jar] (https://github.com/jessefugitt/aeron-debug/releases/download/v0.0.1/aeron-logging-stdout-0.0.1.jar), simply prints the information directly to stdout and doesn't depend on any additional jars besides aspectj.

First, build Aeron and cd to the folder with the samples.jar (super jar containing Aeron/Agrona jars).  Download the aeron-logging-stdout jar. Extract the aeron-debug 3rdParty.zip.  Then, run the following command to make sure everything works without logging:
```
java -cp samples.jar uk.co.real_logic.aeron.driver.MediaDriver
```

Finally, run the following command to try stdout logging:
```
java -javaagent:3rdParty/libs/aspectjweaver-1.8.6.jar -cp samples.jar;3rdParty/libs/aspectjrt-1.8.6.jar;3rdParty/libs/aspectjweaver-1.8.6.jar;aeron-logging-stdout-0.0.1.jar uk.co.real_logic.aeron.driver.MediaDriver
```


### Trace Logging - slf4j
The second artifact, [aeron-logging-slf4j-0.0.1.jar] (https://github.com/jessefugitt/aeron-debug/releases/download/v0.0.1/aeron-logging-slf4j-0.0.1.jar), uses the [slf4j](http://www.slf4j.org/) api at the TRACE logging level to log the information.  This allows many different logging implementations (log4j, java util logging, etc) to do the actual logging and choose whether to output the results to the console and/or a file and allows an existing Java application that is already using slf4j to pick up the Aeron logging results as if the Aeron library contained actual log statements.

First, build Aeron and cd to the folder with the samples.jar (super jar containing Aeron/Agrona jars).  Download the aeron-logging-slf4j jar. Extract the aeron-debug 3rdParty.zip.  Then, run the following command to make sure everything works without logging:
```
java -cp samples.jar uk.co.real_logic.aeron.driver.MediaDriver
```

Finally, run the following command to try slf4j logging with log4j2:
```
java -javaagent:3rdParty/libs/aspectjweaver-1.8.6.jar -cp samples.jar;3rdParty/libs/aspectjrt-1.8.6.jar;3rdParty/libs/aspectjweaver-1.8.6.jar;3rdParty/libs/slf4j-log4j2/*;3rdParty/libs/slf4j-log4j2;aeron-logging-slf4j-0.0.1.jar uk.co.real_logic.aeron.driver.MediaDriver
```

This will pick up slf4j-api and the log4j2 impl jars on the classpath via 3rdParty/libs/slf4j-log4j2/*.  It will also pick up a sample log4j2.xml file that does console and file logging from the classpath via 3rdParty/libs/slf4j-log4j2.

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
