package fragile

import fragile.bool._
import fragile.list._

package object nat {
  private[nat] type pred  [N <: Nat]           = N#Pred
  private[nat] type isZero[N <: Nat]           = N#IsZero

  type +                  [L <: Nat, R <: Nat] = L#Plus[R]
  type -                  [L <: Nat, R <: Nat] = L#Minus[R]
  type *                  [L <: Nat, R <: Nat] = L#Mult[R, _0]
  type min                [L <: Nat, R <: Nat] = L#Min[R, L, R]
  type ==                 [L <: Nat, R <: Nat] = L#Equal[R]
  type <                  [L <: Nat, R <: Nat] = L#Lower[R]
  type unfold             [L <: Nat]           = reverse[L#Unfold]

  type max                [L <: Nat, R <: Nat] = ifN[L == (L min R), R, L]
  type =/=                [L <: Nat, R <: Nat] = ![L == R]
  type >                  [L <: Nat, R <: Nat] = ![L <= R]
  type <=                 [L <: Nat, R <: Nat] = (L == R) || (L < R)
  type >=                 [L <: Nat, R <: Nat] = (L == R) || (L > R)

  type safeN              [N <: Nat]           = N + _0

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
