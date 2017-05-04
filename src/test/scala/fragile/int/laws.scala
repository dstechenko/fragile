package fragile
package int

import bool._
import syntax._

object PlusLaws {
  implicitly[`2` + `3`           =:=                 `5`]
  implicitly[`3` + `0`           =:=                 `3`]
  implicitly[`0` + `3`           =:=                 `3`]
  implicitly[(`2` + `3`)         =:=         (`3` + `2`)]
  implicitly[((`1` + `2`) + `3`) =:= (`1` + (`2` + `3`))]
}

object MinusLaws {
  implicitly[`0` - `0` =:=  `0`]
  implicitly[`3` - `3` =:=  `0`]
  implicitly[`3` - `2` =:=  `1`]
  implicitly[`2` - `3` =:= `-1`]
}

object MultLaws {
  implicitly[`2` * `3`           =:=                 `6`]
  implicitly[`3` * `0`           =:=                 `0`]
  implicitly[`0` * `3`           =:=                 `0`]
  implicitly[(`2` * `3`)         =:=         (`3` * `2`)]
  implicitly[((`2` * `3`) * `4`) =:= (`2` * (`3` * `4`))]
}

object EqualLaws {
  implicitly[`-1` == `-1` =:=  True]
  implicitly[`2`  == `2`  =:=  True]
  implicitly[`2`  == `3`  =:= False]
  implicitly[`3`  == `2`  =:= False]
  implicitly[`-1` == `0`  =:= False]
}

object LowerLaws {
  implicitly[`2` < `3`  =:=  True]
  implicitly[`3` < `3`  =:= False]
  implicitly[`3` < `2`  =:= False]
  implicitly[`-1` < `0` =:=  True]
  implicitly[`0` < `-1` =:= False]
}
