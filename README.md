# fragile

> The mind is the only weapon that doesn't need a holster.
> Paul Blart

## Why?

This module is designed to investigate and play with Scala type system and it's **Turing completeness**.
Also to play with **lambda calculus** encodings in types.

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

Packages have corresponding functions outlined in their package objects.
And also they have **type pattern matching** cases in their regular scala files.

## How much?



## How?

## So?

## Copyright and License
