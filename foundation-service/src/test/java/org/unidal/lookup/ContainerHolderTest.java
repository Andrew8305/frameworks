package org.unidal.lookup;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import junit.framework.Assert;

import org.junit.Test;
import org.unidal.lookup.annotation.Inject;

public class ContainerHolderTest extends ComponentTestCase {
	@Test
	public void testBadObject() {
		try {
			lookup(BadObject.class);

			Assert.fail("Component lookup for BadObject must be failed!");
		} catch (Exception e) {
			String message = toString(e);

			Assert.assertTrue(message, message.contains("java.lang.IllegalStateException: Unkown reason!"));
		}
	}

	@Test
	public void testLookupForCollection() throws Exception {
		Assert.assertEquals(LinkedList.class, lookup(Queue.class, "non-blocking").getClass());
		Assert.assertEquals(LinkedBlockingQueue.class, lookup(Queue.class, "blocking").getClass());
		Assert.assertEquals(ArrayList.class, lookup(List.class, "array").getClass());
		Assert.assertEquals(HashMap.class, lookup(Map.class, "hash").getClass());
	}

	@Test
	public void testLookupForCollectionAsDependency() throws Exception {
		// Plexus issue: array, Map and Collection is not supported as dependency
		try {
			lookup(BadCollectionHolder.class);

			Assert.fail("Component lookup for List must be failed since it's NOT supported!");
		} catch (Exception e) {
			String message = toString(e);
e.printStackTrace();
			Assert.assertTrue(message, message.contains("Unable to find a valid field for "));
		}
	}

	@Test
	public void testBadObjectHolder() {
		try {
			lookup(BadObjectHolder.class);

			Assert.fail("Component lookup for BadObjectHolder must be failed!");
		} catch (Exception e) {
			String message = toString(e);

			Assert.assertTrue(message, message.contains("java.lang.IllegalStateException: Unkown reason!"));
		}
	}

	@Test
	public void testHasComponent() throws Exception {
		MockContainer container = lookup(MockContainer.class);

		Assert.assertEquals(true, container.hasComponent(MockInterface.class));
		Assert.assertEquals(true, container.hasComponent(MockInterface.class, "secondary"));
		Assert.assertEquals(true, container.hasComponent(MockInterface.class, "third"));

		Assert.assertEquals(false, container.hasComponent(Object.class));
		Assert.assertEquals(false, container.hasComponent(MockInterface.class, "unknown"));
	}

	@Test
	public void testLookup() throws Exception {
		MockContainer container = lookup(MockContainer.class);
		MockInterface o1 = container.lookup(MockInterface.class);
		MockInterface o2 = container.lookup(MockInterface.class, "secondary");
		MockInterface o3 = container.lookup(MockInterface.class, "third");

		Assert.assertEquals(MockObject.class, o1.getClass());
		Assert.assertEquals(MockObject2.class, o2.getClass());
		Assert.assertEquals(MockObject3.class, o3.getClass());
	}

	@Test
	public void testLookupList() throws Exception {
		MockContainer container = lookup(MockContainer.class);
		MockInterface o1 = container.lookup(MockInterface.class);
		MockInterface o2 = container.lookup(MockInterface.class, "secondary");
		MockInterface o3 = container.lookup(MockInterface.class, "third");
		List<MockInterface> list = container.lookupList(MockInterface.class);
		int index = 0;

		Assert.assertEquals("[MockObject, MockObject2, MockObject3]", list.toString());
		Assert.assertSame(o1, list.get(index++));
		Assert.assertSame(o2, list.get(index++));
		Assert.assertSame(o3, list.get(index++));

		List<MockInterface> list2 = container.lookupList(MockInterface.class, Arrays.asList("third", "secondary"));

		index = 0;
		Assert.assertEquals("[MockObject3, MockObject2]", list2.toString());
		Assert.assertSame(o3, list2.get(index++));
		Assert.assertSame(o2, list2.get(index++));
	}

	@Test
	public void testLookupMap() throws Exception {
		MockContainer container = lookup(MockContainer.class);
		MockInterface o1 = container.lookup(MockInterface.class);
		MockInterface o2 = container.lookup(MockInterface.class, "secondary");
		MockInterface o3 = container.lookup(MockInterface.class, "third");
		Map<String, MockInterface> map = container.lookupMap(MockInterface.class);

		Assert.assertEquals("{default=MockObject, secondary=MockObject2, third=MockObject3}", map.toString());
		Assert.assertSame(o1, map.get("default"));
		Assert.assertSame(o2, map.get("secondary"));
		Assert.assertSame(o3, map.get("third"));

		Map<String, MockInterface> map2 = container.lookupMap(MockInterface.class, Arrays.asList("third", "secondary"));

		Assert.assertEquals("{third=MockObject3, secondary=MockObject2}", map2.toString());
		Assert.assertSame(o2, map2.get("secondary"));
		Assert.assertSame(o3, map2.get("third"));
	}

	private String toString(Exception e) {
		Writer sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();
	}

	public static class BadObject {
		public BadObject() {
			throw new IllegalStateException("Unkown reason!");
		}
	}

	public static class BadObjectHolder {
		@Inject
		private BadObject m_badObject;

		public BadObject getBadObject() {
			return m_badObject;
		}
	}

	public static class BadCollectionHolder {
		@Inject("array")
		private List<String> m_list;

		public List<String> getList() {
			return m_list;
		}
	}

	public static class MockContainer extends ContainerHolder {
	}

	public static interface MockInterface {
	}

	public static class MockObject implements MockInterface {
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	public static class MockObject2 implements MockInterface {
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	public static class MockObject3 implements MockInterface {
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}
}
