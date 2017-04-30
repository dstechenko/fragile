package fragile
package function

import nat._
import nat.syntax._
import list._

object FunctionLaws {
  type increase[N <: Nat] = N + _1
  type multiply[N <: Nat] = N * _2

  implicitly[list[_1] map identity                                      =:= list[_1]]
  implicitly[const[_1] apply _2                                         =:=       _1]
  implicitly[(eta[increase] andThen multiply andThen multiply) apply _1 =:=       _8]
  implicitly[(eta[increase] compose multiply compose multiply) apply _1 =:=       _5]
}
