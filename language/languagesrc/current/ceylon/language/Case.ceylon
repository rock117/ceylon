shared abstract class Case(String caseName) {
    
    shared actual default String string = caseName;
    
    shared actual Boolean equals(Equality that) {
        return super.equals(that);
    }
    
}