package fragile

import nat._
import int._
import list._

package object bool {
  type || [L <: Bool, R <: Bool]            = L#Or[R]
  type !  [B <: Bool]                       = B#Not

  type ifB[B <: Bool, T <: Bool, E <: Bool] = B#If[Bool, T, E]
  type ifN[B <: Bool, T <: Nat,  E <: Nat]  = safeN[B#If[Nat, T, E]]
  type ifI[B <: Bool, T <: Int,  E <: Int]  = safeI[B#If[Int, T, E]]
  type ifL[B <: Bool, T <: List, E <: List] = safeL[B#If[List, T, E]]

  type && [L <: Bool, R <: Bool]            = ![![L] || ![R]]
}
