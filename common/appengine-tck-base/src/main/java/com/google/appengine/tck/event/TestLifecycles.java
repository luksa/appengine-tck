package com.google.appengine.tck.event;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

import com.google.appengine.tck.base.TestBase;
import com.google.appengine.tck.base.TestContext;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Test lifecycle hooks.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class TestLifecycles {
    private static Map<ClassLoader, ServiceLoader<TestLifecycle>> cache = new WeakHashMap<ClassLoader, ServiceLoader<TestLifecycle>>();

    protected static Iterable<TestLifecycle> getServicesLifecycles() {
        return getServicesLifecycles(TestBase.class.getClassLoader());
    }

    public static Iterable<TestLifecycle> getServicesLifecycles(ClassLoader cl) {
        ServiceLoader<TestLifecycle> sl = cache.get(cl);
        if (sl == null) {
            sl = ServiceLoader.load(TestLifecycle.class, cl);
            cache.put(cl, sl);
        }
        return sl;
    }

    public static void reload(ClassLoader cl) {
        ServiceLoader<TestLifecycle> sl = cache.get(cl);
        if (sl != null) sl.reload();
    }

    public static void before(TestLifecycleEvent event) {
        for (TestLifecycle sl : getServicesLifecycles()) {
            sl.before(event);
        }
    }

    public static void after(TestLifecycleEvent event) {
        for (TestLifecycle sl : getServicesLifecycles()) {
            sl.after(event);
        }
    }

    public static TestLifecycleEvent createMergeLifecycleEvent(Class<?> caller, WebArchive deployment) {
        return new MergeLifecycleEventImpl(caller, deployment);
    }

    public static TestLifecycleEvent createTestContextLifecycleEvent(Class<?> caller, TestContext context) {
        return new TestContextLifecycleEventImpl(caller, context);
    }

    public static TestLifecycleEvent createServiceLifecycleEvent(Class<?> caller, Object service) {
        return new ServiceLifecycleEventImpl(caller, service);
    }
}