package fragile

import nat._
import bool._
import product._
import int.syntax._

package object int {
  type safeI[N <: Int]                        = safeN[firstN[N]] <-> safeN[secondN[N]]

  type Int                                    = Product[Nat]

  type int               [N <: Nat]           = N <-> _0
  type nat               [N <: Int]           = firstN[N] minusN secondN[N]
  type inc               [N <: Int]           = Succ[firstN[N]] <-> secondN[N]

  type canonical         [N <: Int]           = ({
                                                  type first  = nat[N]
                                                  type second = nat[~[N]]
                                                  type run    = first <-> second
                                                })#run

  type plusI             [L <: Int, R <: Int] = ({
                                                  type first  = firstN[L] plusN firstN[R]
                                                  type second = secondN[L] plusN secondN[R]
                                                  type run    = canonical[first <-> second]
                                                })#run

  type multI             [L <: Int, R <: Int] = ({
                                                  type firstLeft   = firstN[L] multN firstN[R]
                                                  type firstRight  = secondN[L] multN secondN[R]
                                                  type first       = firstLeft plusN firstRight
                                                  type secondLeft  = firstN[L] multN secondN[R]
                                                  type secondRight = secondN[L] multN firstN[R]
                                                  type second      = secondLeft plusN secondRight
                                                  type run         = canonical[first <-> second]
                                                })#run

  type eqI               [L <: Int, R <: Int] = ({
                                                  type eqFirst  = firstN[L] eqN firstN[R]
                                                  type eqSecond = secondN[L] eqN secondN[R]
                                                  type run = eqFirst && eqSecond
                                                })#run

  type ltI               [L <: Int, R <: Int] = ({
                                                  type firstLeft     = firstN[L]
                                                  type firstRight    = firstN[R]
                                                  type secondLeft    = secondN[L]
                                                  type secondRight   = secondN[R]
                                                  type isFirstLower  = firstLeft ltN firstRight
                                                  type isFirstEqual  = firstLeft eqN firstRight
                                                  type isSecondLower = secondRight ltN secondLeft
                                                  type run           = isFirstLower || (isFirstEqual && isSecondLower)
                                                })#run

  type unfoldI           [N <: Int]           = nat[N]#Unfold

  type minusI            [L <: Int, R <: Int] = L + ~[R]
  type minI              [L <: Int, R <: Int] = ifI[L < R, L, R]
  type maxI              [L <: Int, R <: Int] = ifI[L < R, R, L]
  type neqI              [L <: Int, R <: Int] = ![L == R]
  type gtI               [L <: Int, R <: Int] = ![L <= R]
  type lteI              [L <: Int, R <: Int] = (L == R) || (L < R)
  type gteI              [L <: Int, R <: Int] = (L == R) || (L > R)

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
