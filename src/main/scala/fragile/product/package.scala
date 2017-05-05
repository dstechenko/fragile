package fragile

import bool._
import nat._
import list._
import int._

package object product {
  type product [T, F <: T, S <: T]    = Product[T] {
                                                     type First  = F
                                                     type Second = S
                                                   }

  type first   [T, P <: Product[T]]   = P#First
  type second  [T, P <: Product[T]]   = P#Second

  type flip    [T, P <: Product[T]]   = product[T, second[T, P], first[T, P]]

  type firstN  [P <: Product[Nat]]    = first[Nat, P]
  type firstI  [P <: Product[Int]]    = first[Int, P]
  type firstB  [P <: Product[Bool]]   = first[Bool, P]
  type firstL  [P <: Product[List]]   = first[List, P]

  type secondN [P <: Product[Nat]]    = second[Nat, P]
  type secondI [P <: Product[Int]]    = second[Int, P]
  type secondB [P <: Product[Bool]]   = second[Bool, P]
  type secondL [P <: Product[List]]   = second[List, P]

  type productN[F <:  Nat, S <:  Nat] = product[Nat, F, S]
  type productI[F <:  Int, S <:  Int] = product[Int, F, S]
  type productB[F <: Bool, S <: Bool] = product[Bool, F, S]
  type productL[F <: List, S <: List] = product[List, F, S]

  type <~>     [F <: Bool, S <: Bool] = productB[F, S]
  type <->      [F <: Nat,  S <: Nat]  = productN[F, S]
  type <-->    [F <: Int,  S <: Int]  = productI[F, S]
  type <~~>    [F <: List, S <: List] = productL[F, S]

  type flipN   [P <: Product[Nat]]    = flip[Nat, P]
  type flipI   [P <: Product[Int]]    = flip[Int, P]
  type flipB   [P <: Product[Bool]]   = flip[Bool, P]
  type flipL   [P <: Product[List]]   = flip[List, P]
}
