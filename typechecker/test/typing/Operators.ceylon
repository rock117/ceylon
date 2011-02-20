class Operators() {
    
    class X() {}
    class Y() {}
    
    @type["String"] "Hello" + " " + "World";
    
    @error "Hello" + 1;
    
    @type["Float"] 1.3 + 2.3;
    
    @type["Float"] -2.5;
    
    @type["Float"] 2.4 ** 0.5;
    
    @type["Natural"]  1 + 2 - -3;
    
    @type["Float"]  1.0 * 2.5 ** (-0.5);
    
    @error 1 + 1.0;
    
    @error 1.0 * 2.5 ** -0.5;
    
    @type["Boolean"]  !( true || false ) && true;
    
    @type["Boolean"]  1 < 100;
    
    @error "foo" < 100;
    
    @type["Boolean"]  "foo" == "bar";
    
    @error "foo" == 12;
    
    @type["Boolean"] Y() === X();
    
    @error "foo" === 12;
    
    @type["String"] $12.34;
    
    X? nothing = null.nothing<X>();
    
    @type["Optional<X>"] nothing ? nothing;
    
    @type["X"] nothing ? X();
    
    @type["X"] nothing ? nothing ? X();
    
    @error X() ? X();
    
    @error X() ? nothing;
    
    X[] sequence = {X(), X()};
    
    @type["Optional<X>"] sequence[0];
    @type["Sequence<X>"] sequence[0..1];
    
    @error sequence["hello"];
    @error sequence[1.."hello"];
    
    @type["Sequence<Sequence<Character>>"] {"hello", "world"}[].chars;
    @type["Optional<Sequence<Character>>"] null.nothing<String>()?.chars;

}