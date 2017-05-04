package fragile
package int

import product._

object syntax {
  type ~ [I <: Int]           = flipN[I]
  type - [L <: Int, R <: Int] = minusI[L, R]
  type + [L <: Int, R <: Int] = plusI[L, R]
  type * [L <: Int, R <: Int] = multI[L, R]
  type < [L <: Int, R <: Int] = ltI[L, R]
  type ==[L <: Int, R <: Int] = eqI[L, R]
}
