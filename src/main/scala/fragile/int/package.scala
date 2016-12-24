package fragile

import fragile.bool._
import fragile.nat._
import fragile.pair._

package object int {
  type Int                       = Pair[Nat]
  type int[F <: Nat, S <: Nat]   = F <-> S

  type ~  [I <: Int]             = int[I#SND, I#FST]
  type ++ [L <: Int, R <: Int]   = int[L#FST + R#FST, L#SND + R#SND]
  type -- [L <: Int, R <: Int]   = L ++ ~[R]
  type ** [L <: Int, R <: Int]   = _0
}
