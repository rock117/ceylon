doc "A key, together with a value associated with the key,
     called the item. Used primarily to represent the 
     elements of a Map."
see (Map)
by "Gavin"
shared class Entry<out Key, out Item>(Key key, Item item)
        extends Object()
        satisfies Equality
        given Key satisfies Equality
        given Item satisfies Equality {
    
    doc "The key used to access the entry."
    shared Key key = key;
    
    doc "The value associated with the key."
    shared Item item = item;
    
    doc "Determines if the this entry is equal to the given
         entry. Two entries are equal if they have the same
         key and the same value."
    shared actual Boolean equals(Equality that) {
        if (is Entry<Equality,Equality> that) {
            return this.key==that.key && 
                    this.item==that.item;
        }
        else {
            return false;
        }
    }
    
    shared actual Integer hash = key.hash/2 + item.hash/2; //TODO: really should be xor
    
    shared actual String string {
        return key.string + "->" + item.string;
    }
    
}