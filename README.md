# delongator

Is your math using too much long all the time?

Is your GWT always plodding around, driving you crazy?!

Is long math tearing up your performances?

Think there's no answer?

You're so stupid!

There is!

**Delongator.**

Finally, there's a performant, portable number dirty hack!

# Why

This is an experiment to see if reducing the amount of math
involving `long` values in libGDX map and set types helps
with performance on GWT. At least some applications have seen
rather severe performance degradation on GWT 2.8.0 (old) when
using bitwise operations on `long` operands;
[see this issue](https://github.com/gwtproject/gwt/issues/9398).
These degradations appear to be acceptable to the GWT team, so
if they aren't acceptable to GWT users, it becomes the burden
of those users and the middleware developers they rely on.

Several classes in libGDX, as they currently stand, rely on
`long` math to place keys/items into slots in hash tables, for
maps and sets. It isn't a lot of `long` math; the `place()`
method performs one multiplication between the `hashCode()` of
an `Object` key (or just a normal `int` key) and a large `long`
constant, then does an unsigned right shift by a variable
amount to use only some amount of the uppermost bits. On all
other platforms, these operations are extremely fast.

...

GWT is not like all other platforms.

In browser JavaScript, `long` does not exist in any way that
GWT can use directly. Instead, GWT has to emulate at least some
`long` values with objects that hold three JS `Number`s, though
some (smaller) values can use just one `Number`. The performance
of math on `long` values varies (a lot) between browsers, but is
always significantly worse than math on `int`.

The libGDX maps and sets use `long` math because it behaves the
same (and correctly) on all platforms, where `int` math on GWT
has its own quirks. Namely, GWT doesn't have an actual `int`,
since it uses a JS `Number` as the internal representation of
an `int`. That means multiplying a large `int` by another large
`int` on desktop or Android will overflow (which is the
documented, valid behavior of the JVM), but on GWT it will just
become a much larger number, probably larger than
`Integer.MAX_VALUE`, and sometimes so large that the lower 8 or
more bits have their values lost to floating-point imprecision.
Yes, `Number` is a double-precision floating-point number, or
a `double` in Java. Lost precision is bad, but it turns out you
can adequately mix the bits of a `hashCode()` using some bitwise
math, and that can, at least on desktop, be faster than a
multiplication and a variable shift. The specific math we use
here is a XOR-rotate-XOR-rotate sequence, which doesn't really
matter in its specifics other than if all int hashCode() values
are given to it once, it will produce all mixed-up results of
that sequence equally often (exactly once each). Avoiding "holes"
in the possible results is a major goal of good mixing functions.

This project also has some methods in `Collections` so that user
code can have it. There's `Math.imul()` from the JS `Math` class,
which multiplies two ints and handles overflow the way Java does,
rather than by losing precision.
It also has `Math.clz32()` available from the JS `Math` class.
These can be called from GWT Java code as `Collections.imul()`,
`Collections.countLeadingZeros()` (given int or long), and
`Collections.countTrailingZeros()` (given int or long)

# Get

Depends on libGDX 1.12.1 ! Rips it apart with tooth and claw!
This should be available by at least JitPack! Because this
is for GWT, you will need a `:sources` dependency, but that is
all you need! You don't need to add anything to core.

You may want a more recent commit hash,
typically the most-recent non-SNAPSHOT version
[on JitPack.io](https://jitpack.io/#tommyettinger/delongator).
An example commit hash is `86360bffd4` .

```groovy
// this doesn't need a dependency in the core module.
// In the html module:
implementation 'com.github.tommyettinger:delongator:0.0.4:sources'
```

You will also need this GWT inherits line in your `GdxDefinition.gwt.xml` file:

```xml
<inherits name="com.github.tommyettinger.delongator" />
```

The order this is placed might matter, since this replaces libGDX sources. You should
add the above `inherits` line below any `inherits` line for libGDX.

If this doesn't make sense to you, you probably don't need this library!

# License

This project is licensed under [The Apache License 2.0](LICENSE).

Kitten Mittens are only available at Paddy's
Pub, the home of the original Kitten Mittens.
