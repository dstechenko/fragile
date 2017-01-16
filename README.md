# fragile

> The mind is the only weapon that doesn't need a holster. © Paul Blart

**WARNING!** No values were harmed in this module.

## Why?

This module is designed to investigate and play with Scala type system and it's **Turing completeness**.

Also to play with **lambda calculus** encodings in types.

The whole module has no single value used, only types.

Scala type system allows encoding of simple building blocks and re-use (in a way) of those to create even more.
You could encode natural numbers, build integers on top of those, etc.
The same goes for structures like List, Map and even functions.

Some things cannot be done in Scala type system, at least I cannot work out an elegant way to do those.
Things like abstract over types for type functions, so I have to specialize them manually.
We could go and use macros for that, but I decided to keep it simple.

This module may be helpful for folks who would want to see some weird type level hacking.
Or who would want to understand what could be done in Scala.

The name of the module is dedicated to the Scala Compiler.

## Where?

Module is broken down into packages for specific types:
* [**bool**](src/main/scala/fragile/bool)
* [**function**](src/main/scala/fragile/function)
* [**int**](src/main/scala/fragile/int)
* [**list**](src/main/scala/fragile/list)
* [**nat**](src/main/scala/fragile/nat)
* [**product**](src/main/scala/fragile/product)

Each package has a corresponding test laws file.
To run laws it is enough to simply compile tests.

Packages have corresponding functions outlined in their package objects.
And also they have **type pattern matching** cases in their regular scala files.

The idea is to use type matching as little as possible and re-use functions to build new and complex ones.

For specialized functions next syntax is used:
* function**N** - **Nat**
* function**B** - **Bool**
* function**I** - **Int**
* function**L** - **List**


## How much?

### [Bool](src/main/scala/fragile/bool/package.scala)

Functions implemented:
* Not
* Or
* And
* If

Notice **De Morgan's laws** usage to reduce **type pattern matching** cases.

Also, **if** function is specialized manually.

### [Function](src/main/scala/fragile/function/package.scala)

Functions implemented:
* Identity
* Const
* Manual Eta-expansion
* AndThen
* Compose
* Apply

All functions work only with **Nats**.

### [Int](src/main/scala/fragile/int/package.scala)

Functions implemented:
* Int
* Nat
* Canonical
* Flip
* Minus
* Plus
* Mult

Notice no need for **type pattern matching** here, as we build on top of **Nats**.

### [List](src/main/scala/fragile/list/package.scala)

Functions implemented:
* List
* Map
* FlatMap
* FoldRight
* IndexOf
* :::
* Reverse
* SelectSort
* QuickSort
* Size
* TakeLeft
* ApplyOrElse
* IsEmpty
* NonEmpty
* Count
* Contains
* ContainsAll
* Exists
* FilterNot
* Forall
* SelectSortAsc
* SelectSortDesc
* QuickSortAsc
* QuickSortDesc
* Corresponds
* ===
* IsDefinedAt
* Distinct
* IsDistinct
* RemoveEvery
* RemoveAll
* DropLeft
* DropRight
* TakeRight
* StartsWith
* StartsWithOffset
* EndsWith
* Slice
* Union
* Diff
* IndexOfWhere
* IndexOfWhereFrom
* IndexOfUntil
* LastIndexOfUntil
* LastIndexOf
* Remove
* Filter
* CountWhile
* TakeWhile
* DropWhile
* Partition
* Tabulate
* PadTo
* Intersect
* FoldLeft
* ContainsSlice
* IndexOfSlice
* IndexOfSliceFrom
* RemoveSlice
* LastIndexOfWhere
* LastIndexOfWhereUntil
* SegmentLength
* PrefixLength
* Updated
* Sum
* Product

Notice that all of the **STD Scala List** functions implemented.

### [Nat](src/main/scala/fragile/nat/package.scala)

Functions implmeneted:
* Minus
* Plus
* Mult
* Min
* Max
* Equal
* NotEqual
* Lower
* Unfold
* Greater
* GreaterEqual
* Lower
* LowerEqual

Notice handy shorcuts for **Nats** in the package object.

### [Product](src/main/scala/fragile/product/package.scala)

Functions implmeneted:
* First
* Second
* Product

All functions are specialized manually here.

## How?

### Code blocks

You may find code organized in blocks. And these blocks follow the same pattern.

These blocks are used as regular value level code blocks.

Each block has to be manually executed by extracting the last type in the sequence.

The type is called **run** in these blocks.

``` Scala
type computed = ({
                  type a   = _1
                  type b   = _1
                  type run = a + b
                })#run
```

### Type pattern matching

Each package has a dedicated scala file with **type pattern matching** cases.

For instance [**Nat.scala**](src/main/scala/fragile/nat/Nat.scala).

In these files you may find different type instances with corresponding match cases.
The idea is to avoid these cases as much as possible and use functions instead.
Functions are defined in corresponding package objects.

``` Scala
// in Bool
type Not <: Bool

// in True
type Not = False

// in False
type Not = True
```

### Safe types

Scala Compiler is very fragile. Hence the name.

It cannot resolve cyclic type references, so extra tricks has to be in place for it not to freak out.

But when you re-use functions in a functional way, you tend to hit those cyclic resolve situations.

To protect the resolving process you need to re-wrap the calculated type on cyclic type operations.
Basically tricking the compiler into thinking it is not a cyclic type reference.

These safe operations are defined in: 
* [Nats](src/main/scala/fragile/nat/package.scala) as **safeN**
* [Lists](src/main/scala/fragile/list/package.scala) as **safeL**
* [Ints](src/main/scala/fragile/int/package.scala) as **safeI**

### Manual eta-expansion

As in the value world, on type level there has to be eta-expansion as well.
There has to be a way to convert from a type level function definition, to a function value-like type level entity.

Basically a way to convert a type definition to a [Function](src/main/scala/fragile/function/Function.scala) type.

This is done manually, compiler has no idea what's going on, it may be improved by a Scala Compiler Plugin, but meh.

``` Scala
type increase[N <: Nat] = N + _1

type expanded = eta[increase]
```

Then all of the functions for composition are available.

### Laws

Tests are organized as law sets.

To run tests simply compile them.

Laws contain all of the function usages and scenarios:
* [Bool](src/test/scala/fragile/bool/laws.scala)
* [Function](src/test/scala/fragile/function/laws.scala)
* [Int](src/test/scala/fragile/int/laws.scala)
* [List](src/test/scala/fragile/list/laws.scala)
* [Nat](src/test/scala/fragile/nat/laws.scala)

## So?

What's next, you may ask?
* Implement **Map** as a [**List**](src/main/scala/fragile/list/List.scala) of [**Products**](src/main/scala/fragile/product/Product.scala)
* Upgrade [**List**](src/main/scala/fragile/list/List.scala) to handle [**Ints**](src/main/scala/fragile/int/package.scala)
* Upgrade [**Ints**](src/main/scala/fragile/int/package.scala) to support **Rationals**

But I am too lazy. 

:(

## Copyright and License

Copyright © 2017 Dmytro Stechenko

[GPL License](http://www.gnu.org/licenses/gpl.html)
