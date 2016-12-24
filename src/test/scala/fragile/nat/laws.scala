package fragile.nat

import fragile.bool._

object PlusLaws {
  implicitly[_2 + _3          =:=               _5]
  implicitly[(_2 + _3)        =:=        (_3 + _2)]
  implicitly[_3 + _0          =:=               _3]
  implicitly[_0 + _3          =:=               _3]
  implicitly[((_1 + _2) + _3) =:= (_1 + (_2 + _3))]
}

object MultLaws {
  implicitly[_2 * _3          =:=               _6]
  implicitly[(_2 * _3)        =:=        (_3 * _2)]
  implicitly[_3 * _0          =:=               _0]
  implicitly[_0 * _3          =:=               _0]
  implicitly[((_2 * _3) * _4) =:= (_2 * (_3 * _4))]
}

object MinusLaws {
  implicitly[_0 - _0 =:= _0]
  implicitly[_3 - _3 =:= _0]
  implicitly[_2 - _3 =:= _0]
  implicitly[_3 - _2 =:= _1]
}

object EqualLaws {
  implicitly[_2 == _2 =:=  True]
  implicitly[_2 == _3 =:= False]
  implicitly[_3 == _2 =:= False]
}

object NotEqualLaws {
  implicitly[_2 =/= _2 =:= False]
  implicitly[_2 =/= _3 =:=  True]
  implicitly[_3 =/= _2 =:=  True]
}

object LowerEqualLaws {
  implicitly[_2 <= _3 =:=  True]
  implicitly[_3 <= _3 =:=  True]
  implicitly[_4 <= _3 =:= False]
}

object GreaterEqualLaws {
  implicitly[_2 >= _3 =:= False]
  implicitly[_3 >= _3 =:=  True]
  implicitly[_3 >= _2 =:=  True]
}

object LowerLaws {
  implicitly[_2 < _3 =:=  True]
  implicitly[_3 < _3 =:= False]
  implicitly[_3 < _2 =:= False]
}

object GreateLaws {
  implicitly[_2 > _3   =:= False]
  implicitly[_3 > _3   =:= False]
  implicitly[_3 > _2   =:=  True]
}

object MinLaws {
  implicitly[_3 min _2 =:= _2]
  implicitly[_2 min _3 =:= _2]
  implicitly[_3 min _3 =:= _3]
}

object MaxLaws {
  implicitly[_3 max _2 =:= _3]
  implicitly[_2 max _3 =:= _3]
  implicitly[_3 max _3 =:= _3]
}
