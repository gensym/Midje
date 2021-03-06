= 0.6.0
* Can "unfold" nested function calls in provided facts, so
  that (f (g 1)) produces two mock calls, the first of which
  returns a metaconstant that the second expects.

= 0.5.0 (stable)
* Line numbers almost always point to the line that provoked
  the error (one of the lines with => on them).
* The in-any-order checker should work correctly.
* Expectations (x => y) can appear at any level, so they can
  be nested within lets.

= 0.4.0
* midje.sweet in workable shape.
* (not-called f) expectation added (Wilkes Joiner)
* key&value arguments can be passed to expect(), fake(), and
  not-called() to override defaults.
* (fact) and (expect) return boolean variables because
  that's more informative in REPL.
* The checker for (f) => (throws Throwable) is supported.

= 0.3.0
* Works with 1.2-RC1 (Wilkes Joiner)

= 0.2.0

* Can fake functions from other namespaces. (Wilkes Joiner)
