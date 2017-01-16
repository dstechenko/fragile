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
* **bool**
* **function**
* **int**
* **list**
* **nat**
* **product**

Each package has a corresponding test laws file.
To run laws it is enough to simply compile tests.

Packages have corresponding functions outlined in their package objects.
And also they have **type pattern matching** cases in their regular scala files.

The idea is to use type matching as little as possible and re-use functions to build new and complex ones.

For specialized functions next syntax is used:
function**N** - **Nat**
function**B** - **Bool**
function**I** - **Int**
function**L** - **List**


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

All functions work only with **Nat**s.

### int

Functions implemented:
* Int
* Nat
* Canonical
* Flip
* Minus
* Plus
* Mult

Notice no need for **type pattern matching** here, as we build on top of **Nat**s.

### list

Functions implemented:


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

Notice handy shorcuts for **Nat**s in the package object.

### product

Functions implmeneted:
* First
* Second
* Product

All functions are specialized manually here.

## How?

## So?

What's next, you may ask?
* Implement **Map** as a **List** of **Product**s
* Upgrade **List** to handle **Int**s
* Upgrade **Int**s to support **Rational**s

But I am too lazy. 
:(

## Copyright and License
