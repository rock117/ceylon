import check { ... }

native shared String f();

native("jvm", "js") shared String f() => "not really native...";

native shared class C() {
  shared native String f();
  shared void g() => f();
}

native("jvm","js") shared class C() {
  shared native("jvm","js") String f() => "multinative";
}

shared void test() {
  f();
  C().g();
  if (exists cont=`C.f`.container) {
    check(cont==`C`);
  } else {
    fail("Native metamodel container");
  }
  results();
}
