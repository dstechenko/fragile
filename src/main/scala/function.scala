import language.higherKinds

trait FunctionFunctions {
  type Function = { type Apply[_ <: Nat] <: Nat }

  type identity[N <: Nat]                                = N
  type const   [C <: Nat]                                = Function { type Apply[N <: Nat] = C              }
  type eta     [L[_ <: Nat] <: Nat]                      = Function { type Apply[N <: Nat] = L[N]           }
  type andThen [L <: Function, R[_ <: Nat] <: Nat]       = Function { type Apply[N <: Nat] = R[apply[L, N]] }
  type apply   [F <: Function, N <: Nat]                 = F#Apply[N]
}

object Function extends FunctionFunctions
