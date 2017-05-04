package fragile
package int

import product._

object syntax {
  type ~   [I <: Int]           = flipN[I]
  type -   [L <: Int, R <: Int] = minusI[L, R]
  type +   [L <: Int, R <: Int] = plusI[L, R]
  type *   [L <: Int, R <: Int] = multI[L, R]
  type ==  [L <: Int, R <: Int] = eqI[L, R]
  type =/= [L <: Int, R <: Int] = neqI[L, R]
  type <   [L <: Int, R <: Int] = ltI[L, R]
  type >   [L <: Int, R <: Int] = gtI[L, R]
  type <=  [L <: Int, R <: Int] = lteI[L, R]
  type >=  [L <: Int, R <: Int] = gteI[L, R]
  type min [L <: Int, R <: Int] = minI[L, R]
  type max [L <: Int, R <: Int] = maxI[L, R]
  type safe[I <: Int]           = safeI[I]
}
