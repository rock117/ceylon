package org.eclipse.ceylon.common;

import java.util.HashSet;
import java.util.Set;

public class Backend {
    public static final Backend Header;
    public static final Backend Java;
    public static final Backend JavaScript;
    
    private static final Set<Backend> registeredBackends;
    private static final Set<Backend> allBackends;
    
    static {
        registeredBackends = new HashSet<Backend>();
        allBackends = new HashSet<Backend>();
        Header = createBackend("Header", "");
        Java = registerBackend("Java", "jvm");
        JavaScript = registerBackend("JavaScript", "js");
        registerBackend("Dart", "dart");
    }
    
    public final String name;
    public final String nativeAnnotation;
    
    private Backend(String name, String nativeAnnotation) {
        this.name = name;
        this.nativeAnnotation = nativeAnnotation;
    }
    
    public Backends asSet() {
        return Backends.fromAnnotation(nativeAnnotation);
    }
    
    public boolean isRegistered() {
        return registeredBackends.contains(this);
    }
    
    public static Backend registerBackend(String name, String backend) {
        Backend b = findAnnotation(backend);
        if (b == null) {
            b = createBackend(name, backend);
            registeredBackends.add(b);
        }
        return b;
    }
    
    public static Backends getRegisteredBackends() {
        return new Backends(registeredBackends);
    }

    public static boolean isRegisteredBackend(String backend) {
        if (backend.isEmpty()) {
            return true;
        }
        for (Backend b : registeredBackends) {
            if (b.nativeAnnotation.equals(backend)) {
                return true;
            }
        }
        return false;
    }
    
    public static Backend fromAnnotation(String backend) {
        if (backend == null) {
            return null;
        }
        Backend b = findAnnotation(backend);
        if (b == null) {
            b = createBackend("Unregistered", backend);
        }
        return b;
    }
    
    private static Backend findAnnotation(String backend) {
        if (backend != null) {
            if (backend.isEmpty()) {
                return Header;
            }
            for (Backend b : allBackends) {
                if (b.nativeAnnotation.equals(backend)) {
                    return b;
                }
            }
        }
        return null;
    }
    
    private static Backend createBackend(String name, String backend) {
        Backend b = new Backend(name, backend);
        allBackends.add(b);
        return b;
    }

    @Override
    public int hashCode() {
        return nativeAnnotation.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Backend) {
            return nativeAnnotation.equals(((Backend)that).nativeAnnotation);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return nativeAnnotation.isEmpty() ? "native" : "native(" + nativeAnnotation + ")";
    }
}
