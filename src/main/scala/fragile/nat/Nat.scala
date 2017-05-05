package fragile
package nat

import int._
import bool._
import list._
import syntax._

import language.higherKinds

sealed trait Nat {
  type Plus                 [N <: Nat]                      <: Nat
  type Minus                [N <: Nat]                      <: Nat
  type Mult                 [N <: Nat, A <: Nat]            <: Nat
  type Min                  [N <: Nat, O <: Nat, NO <: Nat] <: Nat
  type Equal                [N <: Nat]                      <: Bool
  type Lower                [N <: Nat]                      <: Bool
  type Unfold                                               <: List

  protected type This                                       <: Nat
  protected[nat] type Pred                                  <: Nat
  protected[nat] type IsZero                                <: Bool
}

sealed trait Succ[P <: Nat] extends Nat {
  override type Plus                 [N <: Nat]                      = Succ[Pred + N]
  override type Minus                [N <: Nat]                      = ifN[N == _0, This, Pred - pred[N]]
  override type Mult                 [N <: Nat, A <: Nat]            = safeN[Pred]#Mult[N, A + N]
  override type Min                  [N <: Nat, O <: Nat, NO <: Nat] = ifN[N == _0, NO, Pred#Min[pred[N], O, NO]]

  private type loop                  [N <: Nat, F[_ <: Nat] <: Bool] = ifB[N == _0, False, F[pred[N]]]

  override type Equal                [N <: Nat]                      = loop[N, Pred#Equal]
  override type Lower                [N <: Nat]                      = loop[N, Pred#Lower]
  override type Unfold                                               = int[This] :: Pred#Unfold

  override protected type This                                       = Pred + _1
  override protected[nat] type Pred                                  = P
  override protected[nat] type IsZero                                = False
}

sealed trait Zero extends Nat {
  override type Plus                 [N <: Nat]                      = N
  override type Minus                [N <: Nat]                      = This
  override type Mult                 [N <: Nat, A <: Nat]            = A
  override type Min                  [N <: Nat, O <: Nat, NO <: Nat] = O
  override type Equal                [N <: Nat]                      = isZero[N]
  override type Lower                [N <: Nat]                      = ![This == N]
  override type Unfold                                               = Nil

  override protected type This                                       = Zero
  override protected[nat] type Pred                                  = This
  override protected[nat] type IsZero                                = True
}
