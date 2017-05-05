package fragile
package function

import int._
import int.syntax._
import list._

object FunctionLaws {
  type increase[N <: Int] = N + `1`
  type multiply[N <: Int] = N * `2`

  implicitly[list[`1`] map identity                                      =:= list[`1`]]
  implicitly[const[`1`] apply `2`                                        =:=       `1`]
  implicitly[(eta[increase] andThen multiply andThen multiply) apply `1` =:=       `8`]
  implicitly[(eta[increase] compose multiply compose multiply) apply `1` =:=       `5`]
}
