import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.junit.*;

public class CollatzProblemPublicTest {
	// ========== SYSTEM ==========
	protected static final String EX_NAME = "CollatzProblem";
	protected static final String CLASS_NAME_CollatzProblem = "CollatzProblem";
	private static final String METHOD_NAME_f = "f";
	private static final String HELPER_CLASS_NAME_CollatzSeq = "CollatzSeq";
	private static final String HELPER_METHOD_NAME_add = "add";
	private static final String TEST_HELPER_METHOD_NAME_commonTestCode = "commonTestCode";
	// --------------------

	// ========== The "_intestines_" cases... ==========
	@Test(timeout = 666)
	public void pubTest__Intestines__THIS_TEST_IS_VERY_IMPORTANT_IF_IT_FAILS_THEN_YOU_WILL_GET_NO_POINTS_AT_ALL() {
		Class<CollatzProblem> clazz = CollatzProblem.class;
		assertTrue(clazz + " must be public!", Modifier.isPublic(clazz.getModifiers()));
		assertFalse(clazz + " must not be abstract!", Modifier.isAbstract(clazz.getModifiers()));
		assertFalse(clazz + " must not be an annotation!", clazz.isAnnotation());
		assertFalse(clazz + " must not be an enumeration!", clazz.isEnum());
		assertFalse(clazz + " must not be an interface!", clazz.isInterface());
		assertSame(clazz + " must extend a certain super-class!", Object.class, clazz.getSuperclass());
		assertEquals(clazz + " must implement a certain number of interfaces!", 0, clazz.getInterfaces().length);
		assertSame(clazz + " must implement a certain number of interfaces!", 0, clazz.getInterfaces().length);
		assertEquals(clazz + " must declare a certain number of inner annotations!", 0, clazz.getDeclaredAnnotations().length);
		assertEquals(clazz + " must declare a certain number of inner classes!", 0, getDeclaredClasses(clazz).length);
		Field[] fields = getDeclaredFields(clazz);
		assertEquals(clazz + " must declare a certain number of fields!", 0, fields.length);
		Constructor<?>[] constructors = getDeclaredConstructors(clazz);
		assertEquals(clazz + " must declare a certain number of constructors (possibly including default constructor)!", 1, constructors.length);
		for (Constructor<?> constructor : constructors) {
			assertTrue(constructor + " - constructor must be public!", Modifier.isPublic(constructor.getModifiers()));
			assertEquals(constructor + " - constructor must have a certain number of parameters!", 0, constructors[0].getParameterTypes().length);
		}
		Method[] methods = getDeclaredMethods(clazz);
		assertEquals(clazz + " must declare a certain number of methods!", 1, methods.length);
		for (Method method : methods) {
			assertTrue(method + " - method must be public!", Modifier.isPublic(method.getModifiers()));
			assertTrue(method + " - method must be static!", Modifier.isStatic(method.getModifiers()));
			assertEquals(method + " - method must have a certain name!", METHOD_NAME_f, method.getName());
		}
	}

	// ========== PUBLIC TEST ==========
	@Test(timeout = 666)
	public void pubTest__n42__x3__exerciseSheet_withRecursionCheck__IF_THIS_VERY_IMPORTANT_TEST_FAILS_THEN_YOU_WILL_PROBABLY_GET_NO_POINTS_AT_ALL() {
		CollatzProblemPublicTest.commonTestCode(42L, 3, new long[]{42, 21, 90, 45, 162, 81, 270, 135, 432, 216}, 10, 55, "55");
	}

	// ========== HELPER ==========
	private static long[] listToArr(List<Long> in) {
		Long[] inArr = in.toArray(new Long[0]);
		long[] out = new long[inArr.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = inArr[i];
		}
		return out;
	}

	protected static void commonTestCode(long n, int x, long[] res, long cfCallsExpected, long recCallsExpected, String recCallsStr) {
		final LinkedList<Long> csList = new LinkedList<>();
		final AtomicInteger cfCallCounter = new AtomicInteger(), recCallCounter = new AtomicInteger();
		final CollatzSeq cs = new CollatzSeq() {
			public void add(long element) {
				csList.add(element);
				cfCallCounter.addAndGet(1);
				StackTraceElement[] st = Thread.currentThread().getStackTrace();
				assertTrue("The number of (recursive) calls to " + CLASS_NAME_CollatzProblem + "." + METHOD_NAME_f + "(n) seems wrong (the stack trace is shorter than expected)!", st.length >= 2 + csList.size());
				assertEquals("The most recent method call must go to " + HELPER_CLASS_NAME_CollatzSeq + ".*" + HELPER_METHOD_NAME_add + "* (i.e. here)!", HELPER_METHOD_NAME_add, st[1].getMethodName());
				for (int i = 0; i < csList.size(); i++) {
					assertEquals("There must be exactly " + csList.size() + " (recursive) calls to *" + CLASS_NAME_CollatzProblem + "*." + METHOD_NAME_f + "(n) prior to ending with " + HELPER_CLASS_NAME_CollatzSeq + "." + HELPER_METHOD_NAME_add + "!", CLASS_NAME_CollatzProblem, st[2 + i].getClassName());
					assertEquals("There must be exactly " + csList.size() + " (recursive) calls to " + CLASS_NAME_CollatzProblem + ".*" + METHOD_NAME_f + "*(n) prior to ending with " + HELPER_CLASS_NAME_CollatzSeq + "." + HELPER_METHOD_NAME_add + "!", METHOD_NAME_f, st[2 + i].getMethodName());
				}
				assertEquals("The initial call to " + CLASS_NAME_CollatzProblem + "." + METHOD_NAME_f + "(n) must have come from the method surrounding this place!", TEST_HELPER_METHOD_NAME_commonTestCode, st[2 + csList.size()].getMethodName());
				for (StackTraceElement ste : st) {
					if (ste.getClassName().equals(CLASS_NAME_CollatzProblem) && ste.getMethodName().equals(METHOD_NAME_f)) {
						recCallCounter.addAndGet(1);
					}
				}
			}
		};
		CollatzProblem.f(cs, n, x);
		assertArrayEquals("The computed Collatz sequence is wrong.", res, listToArr(csList));
		assertEquals(CLASS_NAME_CollatzProblem + "." + METHOD_NAME_f + "(n) resp. " + HELPER_CLASS_NAME_CollatzSeq + "." + HELPER_METHOD_NAME_add + " must have been called a certain number of times!", cfCallsExpected, cfCallCounter.longValue());
		assertEquals("The number of calls to " + CLASS_NAME_CollatzProblem + "." + METHOD_NAME_f + "(n) seen in all stack traces must sum up to a certain total amount (" + recCallsStr + ")!", recCallsExpected, recCallCounter.longValue());
	}

	// ========== HELPER: Intestines ==========
	// @AuD-STUDENT: DO NOT USE REFLECTION IN YOUR OWN SUBMISSION!
	private static Class<?>[] getDeclaredClasses(Class<?> clazz) {
		java.util.List<Class<?>> declaredClasses = new java.util.ArrayList<>();
		for (Class<?> c : clazz.getDeclaredClasses()) {
			if (!c.isSynthetic()) {
				declaredClasses.add(c);
			}
		}
		return declaredClasses.toArray(new Class[0]);
	}

	private static Field[] getDeclaredFields(Class<?> clazz) {
		java.util.List<Field> declaredFields = new java.util.ArrayList<>();
		for (Field f : clazz.getDeclaredFields()) {
			if (!f.isSynthetic()) {
				declaredFields.add(f);
			}
		}
		return declaredFields.toArray(new Field[0]);
	}

	private static Constructor<?>[] getDeclaredConstructors(Class<?> clazz) {
		java.util.List<Constructor<?>> declaredConstructors = new java.util.ArrayList<>();
		for (Constructor<?> c : clazz.getDeclaredConstructors()) {
			if (!c.isSynthetic()) {
				declaredConstructors.add(c);
			}
		}
		return declaredConstructors.toArray(new Constructor[0]);
	}

	private static Method[] getDeclaredMethods(Class<?> clazz) {
		java.util.List<Method> declaredMethods = new java.util.ArrayList<>();
		for (Method m : clazz.getDeclaredMethods()) {
			if (!m.isBridge() && !m.isSynthetic()) {
				declaredMethods.add(m);
			}
		}
		return declaredMethods.toArray(new Method[0]);
	}
}