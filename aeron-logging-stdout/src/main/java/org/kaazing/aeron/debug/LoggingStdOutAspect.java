/*
    Copyright 2015 Kaazing Corporation

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package org.kaazing.aeron.debug;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;

/**
 * Functions as a logging aspect that can be weaved at runtime to add debug logging to other classes/jars. The advice
 * method will be called before any methods that match the patterns in the annotation (some methods have been intentionally
 * excluded) and it will then check if debug logging is enabled (sl4j) and if so it will log the method name and values of each
 * argument.
 *
 * The META-INF/aop.xml file identifies which packages and classes are eligible for the weaving with the following xml:
 * <pre>
       <aspectj>
           <aspects>
               <aspect name="org.kaazing.aeron.debug.LoggingStdOutAspect"/>
           </aspects>
           <weaver options="-verbose">
               <include within="uk.co.real_logic.aeron..*"/>
               <include within="org.kaazing.aeron.debug.LoggingStdOutAspect"/>
           </weaver>
       </aspectj>
</pre>

 * The following jars must be on the classpath: aeron-logging-stdout-VERSION.jar:aspectjweaver-VERSION.jar:aspectjrt-VERSION.jar
 * and the following java agent must be specified on the command line: -javaagent:path\to\aspectjweaver-VERSION.jar
 */
@Aspect
public class LoggingStdOutAspect
{
    
    @Before("execution (* uk.co.real_logic.aeron..*(..)) " +

            "&& !execution(* uk.co.real_logic.aeron.driver.DriverConductor.doWork(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.DriverConductor.processTimers(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.media.TransportPoller.pollTransports(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.Receiver.doWork(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.Sender.doWork(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.Receiver.timeoutPendingSetupMessages(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.Sender.doSend(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.media.UdpChannelTransport.receive(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.driver.media.UdpChannelTransport.pollForData(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.logbuffer.LogBufferPartition.status(..)) " +

            "&& !execution(* uk.co.real_logic.aeron.Subscription.poll(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.Subscription.ensureOpen(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.ClientConductor.processTimers(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.ClientConductor.doWork(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.Connection.poll(..)) " +
            "&& !execution(* uk.co.real_logic.aeron.DriverListenerAdapter.pollMessage(..)) "
    )
    public void advice(JoinPoint joinPoint)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(Thread.currentThread().getName()).append("]").append(" ");
        sb.append(joinPoint).append(" ");
            
        Object[] args = joinPoint.getArgs();
        for(int i = 0; i < args.length; i++)
        {
            if(i != 0) 
            {
                sb.append(", ");
            }
            sb.append("arg").append(i).append("=").append(args[i]);
        }
        System.out.println(sb.toString());   
    }
}