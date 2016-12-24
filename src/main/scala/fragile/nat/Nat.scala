package fragile.nat

import fragile.bool._

import language.higherKinds

sealed trait Nat {
  type Pred                                  <: Nat
  type Plus  [N <: Nat]                      <: Nat
  type Minus [N <: Nat]                      <: Nat
  type Mult  [N <: Nat, A <: Nat]            <: Nat
  type Min   [N <: Nat, O <: Nat, NO <: Nat] <: Nat
  type IsZero                                <: Bool
  type Equal [N <: Nat]                      <: Bool
  type Lower [N <: Nat]                      <: Bool

  protected type This                        <: Nat
}

sealed trait Succ[P <: Nat] extends Nat {
  override type Pred                                  = P
  override type Plus  [N <: Nat]                      = Succ[Pred + N]
  override type Minus [N <: Nat]                      = ifN[isZero[N], This, Pred - pred[N]]
  override type Mult  [N <: Nat, A <: Nat]            = Pred#Mult[N, A + N]
  override type Min   [N <: Nat, O <: Nat, NO <: Nat] = ifN[isZero[N], NO, Pred#Min[pred[N], O, NO]]
  override type IsZero                                = False

  private type loop   [N <: Nat, F[_ <: Nat] <: Bool] = ifB[isZero[N], False, F[pred[N]]]

  override type Equal [N <: Nat]                      = loop[N, Pred#Equal]
  override type Lower [N <: Nat]                      = loop[N, Pred#Lower]

  override protected type This                        = Succ[P]
}

sealed trait Zero extends Nat {
  override type Pred                                  = This
  override type Plus  [N <: Nat]                      = N
  override type Minus [N <: Nat]                      = This
  override type Mult  [N <: Nat, A <: Nat]            = A
  override type Min   [N <: Nat, O <: Nat, NO <: Nat] = O
  override type IsZero                                = True
  override type Equal [N <: Nat]                      = isZero[N]
  override type Lower [N <: Nat]                      = ![Equal[N]]

  override protected type This                        = Zero
}
