public class ControlStructures() {
    Object something = "hello";
    String? name = null;
    String[] names = {};
    Entry<String,String>[] entries = { Entry("hello", "world") };
    
    void print(String name) {}
    
    if (exists name) {
        print(name);
    }

    if (exists n = name) {
        print(n);
    }
    
    variable String? var := "gavin"; 
    @error if (exists var) {}
    if (exists v = var) {}
    
    if (nonempty names) {
        print(names.first);
        for (n in names) {
            print(n);
        }
    }

    if (nonempty ns = names) {
        print(ns.first);
        for (n in ns) {
            print(n);
        }
    }
    
    variable String[] varseq := {};
    @error if (nonempty varseq) {}
    if (nonempty vs = varseq) {}
    
    if (is String something) {
        print(something);
    }

    if (is String string = something) {
        print(string);
    }
    
    variable Object o := "hello";
    @error if (is String o) {}

    for (n in names) {
        print(n);
    }
    
    /*for (value n in names) {
        print(n);
    }*/
    
    /*for (@error function n in names) {
        print(n);
    }*/
    
    for (key->item in entries) {
        print(key + "->" + item);
    }
    
    /*for (value key->value item in entries) {
        print(key + "->" + item);
    }*/
    
    class Transaction() satisfies Closeable {
        shared void rollbackOnly() {}
        shared actual void close(Exception? e) {}
    }

    try (Transaction()) {}

    try (tx = Transaction()) {
        tx.rollbackOnly();
    }
    
    Transaction tx = Transaction();
    function trans() { return tx; }
    try (tx) {}
    try (t = tx) {}
    try (Transaction t = tx) {}
    try (Transaction()) {}
    try (t = Transaction()) {}
    try (Transaction t = Transaction()) {}
    @error try (trans()) {}
    try (t = trans()) {}
    try (Transaction t = trans()) {}
    
    try {
        writeLine("hello");
    }
    catch (e) {
        
    }

    class Exception1() extends Exception() {}
    class Exception2() extends Exception() {}
    
    try {
        writeLine("hello");
    }
    catch (Exception1|Exception2 e) {
        
    }
    catch (@error String s) {
        
    }
    
    try {}
    catch (Exception1 e1) {}
    catch (Exception2 e2) {}
    catch (Exception e) {}
    
    try {}
    catch (Exception1 e1) {}
    catch (@error Exception1 e2) {}
    
    try {}
    catch (Exception1 e1) {}
    catch (Exception e) {}
    catch (@error Exception2 e2) {}
    
    try {}
    catch (Exception1|Exception2 e) {}
    catch (@error Exception2 e2) {}
    
    try {}
    catch (Exception1 e1) {}
    catch (@error Exception1|Exception2 e) {}
    
    @error try ("hello") {}
    @error try (Exception()) {}
    try (@error s = "hello") {}
    try (@error e = Exception()) {}
    try (@error Object t = Transaction()) {}
    try (@error Transaction tx) {}
    
    @error while ("hello") {}
    
    /*do {
        Boolean test = false;
    }
    while (test); //TODO: is this allowed?*/
    
    /*variable Boolean test;
    do {
        test := false;
    }
    while (test);*/

    /*Boolean test2;
    @error do {
        @error test2 = false;
    }
    while (test2);*/

}