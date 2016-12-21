import language.higherKinds

import Nat._
import Bool._

sealed trait Nat {
  type Pred                                  <: Nat
  type Plus  [N <: Nat]                      <: Nat
  type Mult  [N <: Nat]                      <: Nat
  type Min   [N <: Nat, O <: Nat, NO <: Nat] <: Nat
  type IsZero                                <: Bool
  type Equal [N <: Nat]                      <: Bool
  type Lower [N <: Nat]                      <: Bool
}

trait NatFunctions {
  type pred  [N <: Nat]           = N#Pred
  type +     [L <: Nat, R <: Nat] = L#Plus[R]
  type *     [L <: Nat, R <: Nat] = L#Mult[R]
  type min   [L <: Nat, R <: Nat] = L#Min[R, L, R]
  type isZero[N <: Nat]           = N#IsZero
  type ==    [L <: Nat, R <: Nat] = L#Equal[R]
  type <     [L <: Nat, R <: Nat] = L#Lower[R]

  type max   [L <: Nat, R <: Nat] = ifn[L == min[L, R], R, L]
  type =/=   [L <: Nat, R <: Nat] = ![L == R]
  type >     [L <: Nat, R <: Nat] = ![L <= R]
  type <=    [L <: Nat, R <: Nat] = ==[L, R] || <[L, R]
  type >=    [L <: Nat, R <: Nat] = ==[L, R] || >[L, R]
}

trait NatInstances {
  type _0 = Zero
  type _1 = Succ[_0]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]
  type _5 = Succ[_4]
  type _6 = Succ[_5]
  type _7 = Succ[_6]
  type _8 = Succ[_7]
  type _9 = Succ[_8]
}

object Nat extends NatFunctions with NatInstances

sealed trait Succ[P <: Nat] extends Nat {
  override type Pred                                  = P
  override type Plus  [N <: Nat]                      = Succ[N + Pred]
  override type Min   [N <: Nat, O <: Nat, NO <: Nat] = ifn[isZero[N], NO, Pred#Min[pred[N], O, NO]]
  override type IsZero                                = False

  private type loop   [N <: Nat, F[_ <: Nat] <: Bool] = ifb[isZero[N], False, F[pred[N]]]

  override type Equal [N <: Nat]                      = loop[N, Pred#Equal]
  override type Lower [N <: Nat]                      = loop[N, Pred#Lower]
}

sealed trait Zero extends Nat {
  override type Pred                                  = Zero
  override type Plus  [N <: Nat]                      = N
  override type Min   [N <: Nat, O <: Nat, NO <: Nat] = O
  override type IsZero                                = True
  override type Equal [N <: Nat]                      = isZero[N]
  override type Lower [N <: Nat]                      = ![Equal[N]]
}
