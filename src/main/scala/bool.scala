import language.higherKinds

sealed trait Bool {
  type Not <: Bool
  type Or[B <: Bool] <: Bool
  type And[B <: Bool] <: Bool

  type IfB[T <: Bool, E <: Bool] <: Bool
  type IfN[T <: Nat, E <: Nat] <: Nat
  type IfL[T <: TList, E <: TList] <: TList
}

sealed trait True extends Bool {
  override type Not            = False
  override type Or[B <: Bool]  = True
  override type And[B <: Bool] = B

  override type IfB[T <: Bool, E <: Bool]   = T
  override type IfN[T <: Nat, E <: Nat]     = T
  override type IfL[T <: TList, E <: TList] = T
}

sealed trait False extends Bool {
  override type Not            = True
  override type Or[B <: Bool]  = B
  override type And[B <: Bool] = False

  override type IfB[T <: Bool, E <: Bool]   = E
  override type IfN[T <: Nat, E <: Nat]     = E
  override type IfL[T <: TList, E <: TList] = E
}

trait BoolFunctions {
  type &&[L <: Bool, R <: Bool] = L#And[R]
  type ||[L <: Bool, R <: Bool] = L#Or[R]
  type ![B <: Bool]             = B#Not

  type ifb[B <: Bool, T <: Bool, E <: Bool]   = B#IfB[T, E]
  type ifn[B <: Bool, T <: Nat, E <: Nat]     = B#IfN[T, E]
  type ifl[B <: Bool, T <: TList, E <: TList] = B#IfL[T, E]
}

object Bool extends BoolFunctions
