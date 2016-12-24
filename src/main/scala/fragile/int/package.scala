package fragile

import fragile.bool._
import fragile.nat._
import fragile.pair._

package object int {
  type Int                     = Pair[Nat]
  type int[N <: Nat]           = N <-> _0

  type ~  [I <: Int]           = secondN[I] <-> firstN[I]
  type ++ [L <: Int, R <: Int] = (firstN[L] + firstN[R]) <-> (secondN[L] + secondN[R])
  type -- [L <: Int, R <: Int] = L ++ ~[R]
  type ** [L <: Int, R <: Int] = ((firstN[L] * firstN[R]) + (secondN[L] * secondN[R])) <-> ((firstN[L] * secondN[R]) + (secondN[L] * firstN[R]))
  type eq [L <: Int, R <: Int] = False
}
