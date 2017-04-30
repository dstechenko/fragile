package fragile
package nat

object syntax {
  type +     [L <: Nat, R <: Nat] = plusN[L, R]
  type -     [L <: Nat, R <: Nat] = minusN[L, R]
  type *     [L <: Nat, R <: Nat] = multN[L, R]
  type ==     [L <: Nat, R <: Nat] = eqN[L, R]
  type =/=   [L <: Nat, R <: Nat] = neqN[L, R]
  type <     [L <: Nat, R <: Nat] = ltN[L, R]
  type >     [L <: Nat, R <: Nat] = gtN[L, R]
  type <=     [L <: Nat, R <: Nat] = lteN[L, R]
  type >=     [L <: Nat, R <: Nat] = gteN[L, R]
  type max   [L <: Nat, R <: Nat] = maxN[L, R]
  type min   [L <: Nat, R <: Nat] = minN[L, R]
  type unfold[L <: Nat]           = unfoldN[L]
  type safe  [N <: Nat]           = safeN[N]
}
