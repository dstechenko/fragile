package fragile

import fragile.bool._
import fragile.nat._
import fragile.list._

package object product {
  type product [T, F <: T, S <: T]    = Product[T] {
                                                     type First  = F
                                                     type Second = S
                                                   }

  type first   [T, P <: Product[T]]   = P#First
  type second  [T, P <: Product[T]]   = P#Second

  type firstN  [P <: Product[Nat]]    = first[Nat, P]
  type firstB  [P <: Product[Bool]]   = first[Bool, P]
  type firstL  [P <: Product[List]]   = first[List, P]

  type secondN [P <: Product[Nat]]    = second[Nat, P]
  type secondB [P <: Product[Bool]]   = second[Bool, P]
  type secondL [P <: Product[List]]   = second[List, P]

  type productN[F <:  Nat, S <:  Nat] = product[Nat, F, S]
  type productB[F <: Bool, S <: Bool] = product[Bool, F, S]
  type productL[F <: List, S <: List] = product[List, F, S]

  type <~>     [F <: Bool, S <: Bool] = productB[F, S]
  type <->     [F <: Nat,  S <: Nat]  = productN[F, S]
  type <-->    [F <: List, S <: List] = productL[F, S]
}
