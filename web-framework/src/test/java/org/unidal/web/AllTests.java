package org.unidal.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import org.unidal.web.jsp.function.FormatTest;
import org.unidal.web.lifecycle.ActionResolverTest;
import org.unidal.web.mvc.model.AnnotationMatrixTest;
import org.unidal.web.mvc.model.ModuleManagerTest;
import org.unidal.web.mvc.payload.PayloadProviderTest;

@RunWith(Suite.class)
@SuiteClasses({

MvcTest.class,

FormatTest.class,

ActionResolverTest.class,

AnnotationMatrixTest.class,

ModuleManagerTest.class,

PayloadProviderTest.class

})
public class AllTests {

}
