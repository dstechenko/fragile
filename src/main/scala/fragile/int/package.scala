package fragile

import nat._
import product._
import int.syntax._

package object int {
  private[int] type safeI[I <: Int]           = I + `0`

  type Int                                    = Product[Nat]

  type int               [N <: Nat]           = N <-> _0
  type nat               [I <: Int]           = firstN[I] minusN secondN[I]

  type canonical         [I <: Int]           = ({
                                                  type first  = nat[I]
                                                  type second = nat[~[I]]
                                                  type run    = first <-> second
                                                })#run

  type plusI             [L <: Int, R <: Int] = ({
                                                  type first  = firstN[L] plusN firstN[R]
                                                  type second = secondN[L] plusN secondN[R]
                                                  type run    = canonical[first <-> second]
                                                })#run

  type minusI            [L <: Int, R <: Int] = L + ~[R]

  type multI             [L <: Int, R <: Int] = ({
                                                  type firstLeft   = firstN[L] multN firstN[R]
                                                  type firstRight  = secondN[L] multN secondN[R]
                                                  type first       = firstLeft plusN firstRight
                                                  type secondLeft  = firstN[L] multN secondN[R]
                                                  type secondRight = secondN[L] multN firstN[R]
                                                  type second      = secondLeft plusN secondRight
                                                  type run         = canonical[first <-> second]
                                                })#run


  type `0`                                    = int[_0]
  type `1`                                    = int[_1]
  type `2`                                    = int[_2]
  type `3`                                    = int[_3]
  type `4`                                    = int[_4]
  type `5`                                    = int[_5]
  type `6`                                    = int[_6]
  type `7`                                    = int[_7]
  type `8`                                    = int[_8]
  type `9`                                    = int[_9]

  type `-1`                                   = `0` - `1`
}
