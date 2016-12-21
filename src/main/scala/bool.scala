import language.higherKinds

sealed trait Bool {
  type Not <: Bool
  type Or[B <: Bool] <: Bool
  type And[B <: Bool] <: Bool

  type IfB[C <: Bool, A <: Bool] <: Bool
  type IfN[C <: Nat, A <: Nat] <: Nat
  type IfL[C <: TList, A <: TList] <: TList
}

sealed trait True extends Bool {
  override type Not = False
  override type Or[B <: Bool] = True
  override type And[B <: Bool] = B

  override type IfB[C <: Bool, A <: Bool] = C
  override type IfN[C <: Nat, A <: Nat] = C
  override type IfL[C <: TList, A <: TList] = C
}

sealed trait False extends Bool {
  override type Not = True
  override type Or[B <: Bool] = B
  override type And[B <: Bool] = False

  override type IfB[C <: Bool, A <: Bool] = A
  override type IfN[C <: Nat, A <: Nat] = A
  override type IfL[C <: TList, A <: TList] = A
}

object Bool {
  type &&[L <: Bool, R <: Bool] = L#And[R]
  type ||[L <: Bool, R <: Bool] = L#Or[R]
  type ![B <: Bool] = B#Not

  type ifb[B <: Bool, C <: Bool, A <: Bool] = B#IfB[C, A]
  type ifn[B <: Bool, C <: Nat, A <: Nat] = B#IfN[C, A]
  type ifl[B <: Bool, C <: TList, A <: TList] = B#IfL[C, A]
}
