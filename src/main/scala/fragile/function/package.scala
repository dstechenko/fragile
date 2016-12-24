package fragile

import fragile.nat._

import language.higherKinds

package object function {
  type ->      [A, B]                                  = Function[A, B]

  type identity[N <: Nat]                              = N
  type const   [C <: Nat]                              = (Nat -> Nat) { type Apply[N <: Nat] = C              }
  type eta     [L[_ <: Nat] <: Nat]                    = (Nat -> Nat) { type Apply[N <: Nat] = L[N]           }
  type andThen [L <: (Nat -> Nat), R[_ <: Nat] <: Nat] = (Nat -> Nat) { type Apply[N <: Nat] = R[apply[L, N]] }
  type compose [L <: (Nat -> Nat), R[_ <: Nat] <: Nat] = (Nat -> Nat) { type Apply[N <: Nat] = apply[L, R[N]] }
  type apply   [F <: (Nat -> Nat), N <: Nat]           = F#Apply[N]
}
