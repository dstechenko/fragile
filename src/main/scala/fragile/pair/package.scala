package fragile

import fragile.bool._
import fragile.nat._
import fragile.list._

package object pair {
  type pair [T, F <: T, S <: T]    = Pair[T] { type FST = F; type SND = S }

  type pairN[F <:  Nat, S <:  Nat] = pair[Nat, F, S]
  type pairB[F <: Bool, S <: Bool] = pair[Bool, F, S]
  type pairL[F <: List, S <: List] = pair[List, F, S]

  type <~>  [F <: Bool, S <: Bool] = pairB[F, S]
  type <->  [F <: Nat, S <: Nat]   = pairN[F, S]
  type <--> [F <: List, S <: List] = pairL[F, S]
}
