package fragile.bool

import fragile.nat._
import fragile.list._

import language.higherKinds

sealed trait Bool {
  type Not                       <: Bool
  type Or [B <: Bool]            <: Bool

  type IfB[T <: Bool, E <: Bool] <: Bool
  type IfN[T <: Nat,  E <: Nat]  <: Nat
  type IfL[T <: List, E <: List] <: List
}

sealed trait True extends Bool {
  override type Not                       = False
  override type Or [B <: Bool]            = True

  override type IfB[T <: Bool, E <: Bool] = T
  override type IfN[T <: Nat,  E <: Nat]  = T
  override type IfL[T <: List, E <: List] = T
}

sealed trait False extends Bool {
  override type Not                       = True
  override type Or [B <: Bool]            = B

  override type IfB[T <: Bool, E <: Bool] = E
  override type IfN[T <: Nat,  E <: Nat]  = E
  override type IfL[T <: List, E <: List] = E
}
