package fragile

import fragile.bool._
import fragile.nat._
import fragile.list._

package object pair {
  type pair   [T, F <: T, S <: T]    = Pair[T] {
                                                 type First  = F
                                                 type Second = S
                                               }

  type first  [T, P <: Pair[T]]      = P#First
  type second [T, P <: Pair[T]]      = P#Second

  type firstN [P <: Pair[Nat]]       = first[Nat, P]
  type firstB [P <: Pair[Bool]]      = first[Bool, P]
  type firstL [P <: Pair[List]]      = first[List, P]

  type secondN[P <: Pair[Nat]]       = second[Nat, P]
  type secondB[P <: Pair[Bool]]      = second[Bool, P]
  type secondL[P <: Pair[List]]      = second[List, P]

  type pairN  [F <:  Nat, S <:  Nat] = pair[Nat, F, S]
  type pairB  [F <: Bool, S <: Bool] = pair[Bool, F, S]
  type pairL  [F <: List, S <: List] = pair[List, F, S]

  type <~>    [F <: Bool, S <: Bool] = pairB[F, S]
  type <->    [F <: Nat, S <: Nat]   = pairN[F, S]
  type <-->   [F <: List, S <: List] = pairL[F, S]
}
