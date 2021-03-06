package org.eclipse.ceylon.model.typechecker.context;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.ceylon.model.typechecker.model.Type;
import org.eclipse.ceylon.model.typechecker.model.TypeDeclaration;
import org.eclipse.ceylon.model.typechecker.model.UnknownType;

public class TypeCache {
    
    private static boolean cachingEnabledByDefault = true;
    
    public static void setEnabledByDefault(boolean enabled) {
        cachingEnabledByDefault = enabled;
    }
    
    private static final ThreadLocal<Boolean> cachingEnabled = 
            new ThreadLocal<Boolean>();
    
    public static Boolean setEnabled(Boolean enabled) {
        Boolean was = cachingEnabled.get();
        cachingEnabled.set(enabled);
        return was;
    }
    
    private static <T> T doWithExplicitCaching(boolean cacheEnabled, final Callable<T> action) {
        Boolean was = TypeCache.setEnabled(cacheEnabled);
        try {
            return action.call();
        } catch(Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            TypeCache.setEnabled(was);
        }
    }
    
    private static void doWithExplicitCaching(boolean cacheEnabled, final Runnable action) {
        Boolean was = TypeCache.setEnabled(cacheEnabled);
        try {
            action.run();
        } catch(Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            TypeCache.setEnabled(was);
        }
    }
    
    public static <T> T doWithCaching(final Callable<T> action) {
        return doWithExplicitCaching(true, action);
    }

    public static <T> T doWithoutCaching(final Callable<T> action) {
        return doWithExplicitCaching(false, action);
    }

    public static void doWithCaching(final Runnable action) {
        doWithExplicitCaching(true, action);
    }

    public static void doWithoutCaching(final Runnable action) {
        doWithExplicitCaching(false, action);
    }

    public static boolean isEnabled() {
        Boolean cie = cachingEnabled.get();
        return cie == null ? cachingEnabledByDefault : cie;
    }
    
    // need a special value for null because ConcurrentHashMap does not support null
    public final static Type NULL_VALUE = new UnknownType(null).getType();
    // need ConcurrentHashMap even for the cache, otherwise get/put/containsKey can get info infinite loops
    // on concurrent operations
    private final Map<Type, Map<TypeDeclaration, Type>> superTypes = 
            new ConcurrentHashMap<Type, Map<TypeDeclaration, Type>>();
    
    public boolean containsKey(Type producedType, TypeDeclaration dec) {
        Map<TypeDeclaration, Type> cache = superTypes.get(producedType);
        if (cache == null) {
            return false;
        }
        return cache.containsKey(dec);
    }

    /**
     * Returns a cached type, possibly NULL_VALUE to indicate a non-supertype
     * @param producedType
     * @param dec
     * @return
     */
    public Type get(Type producedType, TypeDeclaration dec) {
        Map<TypeDeclaration, Type> cache = superTypes.get(producedType);
        if (cache == null) {
            return null;
        }
        return cache.get(dec);
    }

    /**
     * Caches a new super type. Use NULL_VALUE to cache a non-supertype
     * @param producedType
     * @param dec
     * @param superType
     */
    public void put(Type producedType, TypeDeclaration dec, Type superType) {
        Map<TypeDeclaration, Type> cache = superTypes.get(producedType);
        if (cache == null) {
            // need ConcurrentHashMap even for the cache, otherwise get/put/containsKey can get info infinite loops
            // on concurrent operations
            cache = new ConcurrentHashMap<TypeDeclaration, Type>();
            superTypes.put(producedType, cache);
        }
        cache.put(dec, superType);
    }

    public void clear(){
        superTypes.clear();
    }

    /**
     * Clears this type from the cache as a cached root and as a cached type value
     * @param producedType
     */
    public void remove(Type producedType) {
        Map<TypeDeclaration, Type> cache = superTypes.get(producedType);
        if (cache != null) {
            // help GC a bit
            cache.clear();
            superTypes.remove(producedType);
        }
        int hashCode = producedType.hashCode();
        // also clear cached values
        for(Map<TypeDeclaration, Type> cacheValues : superTypes.values()){
            Iterator<Entry<TypeDeclaration, Type>> iterator = cacheValues.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<TypeDeclaration, Type> entry = iterator.next();
                if(entry.getValue() != NULL_VALUE
                        && !entry.getValue().isUnknown()
                        && entry.getValue().hashCode() == hashCode
                        && entry.getValue().equals(producedType))
                    iterator.remove();
            }
        }
    }

    public void clearForDeclaration(TypeDeclaration decl) {
        // clear the whole cache for now, unless I can figure out exactly what to
        // clear
        clear();
    }
    
    public void clearNullValues() {
        List<Type> cachesToremove = new LinkedList<Type>();
        for (Map.Entry<Type, Map<TypeDeclaration, Type>> entry: 
                superTypes.entrySet()) {
            Map<TypeDeclaration, Type> cache = entry.getValue();
            if (cache == null) {
                cachesToremove.add(entry.getKey());
            }
            else {
                List<TypeDeclaration> valuesToremove = 
                        new LinkedList<TypeDeclaration>();
                for (Map.Entry<TypeDeclaration, Type> cacheEntry: 
                        cache.entrySet()) {
                    if (cacheEntry.getValue() == NULL_VALUE) {
                        valuesToremove.add(cacheEntry.getKey());
                    }
                }
                for (TypeDeclaration toRemove: valuesToremove) {
                    cache.remove(toRemove);
                }
            }
        }
        for (Type toRemove: cachesToremove) {
            superTypes.remove(toRemove);
        }
    }
}
