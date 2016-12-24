package fragile

import fragile.nat._

package object function {
  type Function = { type Apply[_ <: Nat] <: Nat }

  type identity[N <: Nat]                                = N
  type const   [C <: Nat]                                = Function { type Apply[N <: Nat] = C              }
  type eta     [L[_ <: Nat] <: Nat]                      = Function { type Apply[N <: Nat] = L[N]           }
  type andThen [L <: Function, R[_ <: Nat] <: Nat]       = Function { type Apply[N <: Nat] = R[apply[L, N]] }
  type compose [L <: Function, R[_ <: Nat] <: Nat]       = Function { type Apply[N <: Nat] = apply[L, R[N]] }
  type apply   [F <: Function, N <: Nat]                 = F#Apply[N]
}
