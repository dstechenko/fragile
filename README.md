# fragile

> The mind is the only weapon that doesn't need a holster. © Paul Blart

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
* [**bool**](src/main/scala/bool)
* [**function**](src/main/scala/function)
* [**int**](src/main/scala/int)
* [**list**](src/main/scala/list)
* [**nat**](src/main/scala/nat)
* [**product**](src/main/scala/product)

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

### bool

Functions implemented:
* Not
* Or
* And
* If

Notice **De Morgan's laws** usage to reduce **type pattern matching** cases.

Also, **if** function is specialized manually.

### function

Functions implemented:
* Identity
* Const
* Manual Eta-expansion
* AndThen
* Compose
* Apply

All functions work only with **Nats**.

### int

Functions implemented:
* Int
* Nat
* Canonical
* Flip
* Minus
* Plus
* Mult

Notice no need for **type pattern matching** here, as we build on top of **Nats**.

### list

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

### nat

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

### product

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



### Safe types



### Manual eta-expansion



### Laws



## So?

What's next, you may ask?
* Implement **Map** as a **List** of **Products**
* Upgrade **List** to handle **Ints**
* Upgrade **Ints** to support **Rationals**

But I am too lazy. 

:(

## Copyright and License

Copyright © 2017 Dmytro Stechenko

[GPL License](http://www.gnu.org/licenses/gpl.html)
