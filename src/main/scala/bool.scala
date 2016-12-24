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

trait BoolSyntax {
  type || [L <: Bool, R <: Bool]            = L#Or[R]
  type !  [B <: Bool]                       = B#Not

  type ifB[B <: Bool, T <: Bool, E <: Bool] = B#IfB[T, E]
  type ifN[B <: Bool, T <: Nat,  E <: Nat]  = B#IfN[T, E]
  type ifL[B <: Bool, T <: List, E <: List] = B#IfL[T, E]

  type && [L <: Bool, R <: Bool]            = ![![L] || ![R]]
}

object Bool extends BoolSyntax
