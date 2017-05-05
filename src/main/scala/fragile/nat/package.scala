package fragile

import bool._
import list._
import nat.syntax._

package object nat {
  private[nat] type pred  [N <: Nat]           = N#Pred
  private[nat] type isZero[N <: Nat]           = N#IsZero

  type safeN              [N <: Nat]           = N + _0

  type plusN              [L <: Nat, R <: Nat] = L#Plus[R]
  type minusN             [L <: Nat, R <: Nat] = L#Minus[R]
  type multN              [L <: Nat, R <: Nat] = L#Mult[R, _0]
  type minN               [L <: Nat, R <: Nat] = L#Min[R, L, R]
  type eqN                [L <: Nat, R <: Nat] = L#Equal[R]
  type ltN                [L <: Nat, R <: Nat] = L#Lower[R]
  type unfoldN            [N <: Nat]           = reverse[N#Unfold]

  type maxN               [L <: Nat, R <: Nat] = ifN[L == (L min R), R, L]
  type neqN               [L <: Nat, R <: Nat] = ![L == R]
  type gtN                [L <: Nat, R <: Nat] = ![L <= R]
  type lteN               [L <: Nat, R <: Nat] = (L == R) || (L < R)
  type gteN               [L <: Nat, R <: Nat] = (L == R) || (L > R)

  type _0                                      = Zero
  type _1                                      = Succ[_0]
  type _2                                      = Succ[_1]
  type _3                                      = Succ[_2]
  type _4                                      = Succ[_3]
  type _5                                      = Succ[_4]
  type _6                                      = Succ[_5]
  type _7                                      = Succ[_6]
  type _8                                      = Succ[_7]
  type _9                                      = Succ[_8]
}
