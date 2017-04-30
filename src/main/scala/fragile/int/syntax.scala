package fragile
package int

import product._

object syntax {
  type -[L <: Int, R <: Int] = minusI[L, R]
  type +[L <: Int, R <: Int] = plusI[L, R]
  type *[L <: Int, R <: Int] = multI[L, R]
  type ~[I <: Int]           = flipN[I]
}
