package org.unidal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.unidal.helper.DatesTest;
import org.unidal.helper.FormatsTest;
import org.unidal.helper.PropertiesTest;
import org.unidal.helper.SplittersTest;
import org.unidal.helper.StringizersTest;
import org.unidal.helper.ThreadsTest;
import org.unidal.lookup.ComponentTestCaseTest;
import org.unidal.lookup.ContainerHolderTest;
import org.unidal.lookup.ContainerLoaderTest;
import org.unidal.lookup.logger.TimedConsoleLoggerTest;
import org.unidal.net.SocketsTest;
import org.unidal.tuple.TupleTest;

@RunWith(Suite.class)
@SuiteClasses({

DatesTest.class,

FormatsTest.class,

StringizersTest.class,

SplittersTest.class,

ThreadsTest.class,

PropertiesTest.class,

ComponentTestCaseTest.class,

ContainerHolderTest.class,

ContainerLoaderTest.class,

TimedConsoleLoggerTest.class,

SocketsTest.class,

TupleTest.class

})
public class AllTests {

}
