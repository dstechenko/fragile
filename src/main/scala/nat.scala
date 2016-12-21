import language.higherKinds

import Nat._
import Bool._

sealed trait Nat {
  type Pred <: Nat
  type Plus[N <: Nat] <: Nat
  type IsZero <: Bool
  type Equal[N <: Nat] <: Bool
  type LowerEqual[N <: Nat] <: Bool
  type Min[N <: Nat, LO <: Nat, RO <: Nat] <: Nat
}

trait NatSyntax {
  type +[L <: Nat, R <: Nat] = L#Plus[R]
  type ==[L <: Nat, R <: Nat] = L#Equal[R]
  type <=[L <: Nat, R <: Nat] = L#LowerEqual[R]
  type isZero[N <: Nat] = N#IsZero
  type pred[N <: Nat] = N#Pred
  type min[L <: Nat, R <: Nat] = L#Min[R, L, R]
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

object Nat extends NatSyntax with NatInstances

sealed trait Succ[P <: Nat] extends Nat {
  override type Pred = P
  override type Plus[N <: Nat] = Succ[Pred + N]
  override type IsZero = False
  override type Equal[N <: Nat] = ifb[isZero[N], False, Pred#Equal[pred[N]]]
  override type LowerEqual[N <: Nat] = ifb[isZero[N], False, Pred#LowerEqual[pred[N]]]
  override type Min[N <: Nat, LO <: Nat, RO <: Nat] = ifn[isZero[N], RO, Pred#Min[pred[N], LO, RO]]
}

sealed trait Zero extends Nat {
  override type Pred = Zero
  override type Plus[N <: Nat] = N
  override type IsZero = True
  override type Equal[N <: Nat] = isZero[N]
  override type LowerEqual[N <: Nat] = True
  override type Min[N <: Nat, LO <: Nat, RO <: Nat] = LO
}
