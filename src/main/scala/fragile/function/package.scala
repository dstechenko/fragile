package fragile

import int._

import language.higherKinds

package object function {
  type ->      [A, B]                                  = Function[A, B]

  type identity[N <: Int]                              = N
  type const   [C <: Int]                              = (Int -> Int) { type Apply[N <: Int] = C              }
  type eta     [L[_ <: Int] <: Int]                    = (Int -> Int) { type Apply[N <: Int] = L[N]           }
  type andThen [L <: (Int -> Int), R[_ <: Int] <: Int] = (Int -> Int) { type Apply[N <: Int] = R[apply[L, N]] }
  type compose [L <: (Int -> Int), R[_ <: Int] <: Int] = (Int -> Int) { type Apply[N <: Int] = apply[L, R[N]] }
  type apply   [F <: (Int -> Int), N <: Int]           = F#Apply[N]
}
