package fragile

import fragile.nat._
import fragile.list._

package object bool {
  type || [L <: Bool, R <: Bool]            = L#Or[R]
  type !  [B <: Bool]                       = B#Not

  type ifB[B <: Bool, T <: Bool, E <: Bool] =      B#IfB[T, E]
  type ifN[B <: Bool, T <: Nat,  E <: Nat]  = safeN[B#IfN[T, E]]
  type ifL[B <: Bool, T <: List, E <: List] =      B#IfL[T, E]

  type && [L <: Bool, R <: Bool]            = ![![L] || ![R]]
}
