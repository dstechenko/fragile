import Bool._
import Nat._
import List._
import Function._

object NatLaws {
  implicitly[_2 + _3   =:= _5]

  implicitly[_2 - _3   =:= _0]
  implicitly[_5 - _3   =:= _2]

  implicitly[_2 == _2  =:= True]
  implicitly[_2 == _3  =:= False]

  implicitly[_2 =/= _2 =:= False]
  implicitly[_2 =/= _3 =:= True]

  implicitly[_2 <= _3  =:= True]
  implicitly[_3 <= _3  =:= True]
  implicitly[_4 <= _3  =:= False]

  implicitly[_3 >= _3  =:= True]
  implicitly[_3 >= _2  =:= True]
  implicitly[_2 >= _3  =:= False]

  implicitly[_2 < _3   =:= True]
  implicitly[_3 < _3   =:= False]

  implicitly[_4 > _3   =:= True]
  implicitly[_3 > _3   =:= False]

  implicitly[_4 min _3 =:= _3]
  implicitly[_1 min _4 =:= _1]

  implicitly[_4 max _3 =:= _4]
  implicitly[_1 max _4 =:= _4]

  implicitly[_9 * _0   =:= _0]
  implicitly[_0 * _9   =:= _0]
  implicitly[_5 * _1   =:= _5]
  implicitly[_2 * _3   =:= _6]
  implicitly[_4 * _2   =:= _8]
  implicitly[_3 * _3   =:= _9]
}

object BoolLaws {
  implicitly[![False]       =:= True]
  implicitly[![True]        =:= False]

  implicitly[True  || False =:= True]
  implicitly[False || False =:= False]

  implicitly[True  && False =:= False]
  implicitly[False && True  =:= False]
  implicitly[True  && True  =:= True]
}

object MapLaws {
  type given    = _3 :: _1 :: _2 :: _4 :: _0 :: Nil
  type expected = _4 :: _2 :: _3 :: _5 :: _1 :: Nil
  type result   = given map ({ type F[N <: Nat] = N + _1 })#F
  type id       = given map identity

  implicitly[expected =:= result]
  implicitly[given    =:=     id]
}

object FlatMapLaws {
  type given    = _1 :: _2 :: _3 :: _4 :: Nil
  type expected = _2 :: _3 :: _3 :: _4 :: _4 :: _5 :: _5 :: _6 :: Nil
  type result   = given flatMap ({ type F[N <: Nat] = (N + _1) :: (N + _2) :: Nil })#F
  type id       = given flatMap list

  implicitly[expected =:= result]
  implicitly[given    =:=     id]
}

object MonadLaws {
  type unit     [N <: Nat] = list[N]
  type element             = _0
  type monad               = _0 :: _1 :: _2 :: _3 :: _4 :: Nil
  type function1[N <: Nat] = unit[N + _1]
  type function2[N <: Nat] = unit[N + _2]
  type chained  [N <: Nat] = function1[N] flatMap function2

  implicitly[list[element] flatMap function1                    =:=      function1[element]]
  implicitly[        monad flatMap unit                         =:=                   monad]
  implicitly[        monad flatMap function1 flatMap function2  =:= (monad flatMap chained)]
}

object FilterLaws {
  type given    = _1 :: _2 :: _0 :: _2 :: _3 :: _5 :: _4 :: _1 :: Nil
  type expected = _1 :: _2 :: _0 :: _2 :: _3 :: _1 :: Nil
  type result   = given filter ({ type F[N <: Nat] = N <= _3 })#F

  implicitly[expected =:= result]
}

object FilterNotLaws {
  type given    = _1 :: _2 :: _0 :: _2 :: _3 :: _5 :: _4 :: _1 :: Nil
  type expected = _1 :: _2 :: _0 :: _2 :: _3 :: _1 :: Nil
  type result   = given filterNot ({ type F[N <: Nat] = N > _3 })#F

  implicitly[expected =:= result]
}

object RemoveLaws {
  type given    = _1 :: _2 :: _3 :: _2 :: _3 :: Nil
  type expected = _1 :: _2 ::       _2 :: _3 :: Nil
  type result   = given remove _3

  implicitly[expected =:= result]
}

object SortAscLaws {
  type given    = _0 :: _5 :: _3 :: _4 :: _2 :: _1 :: Nil
  type expected = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type result   = sortAsc[given]

  implicitly[expected =:= result]
}

object SortDescLaws {
  type given    = _0 :: _3 :: _4 :: _2 :: _5 :: _1 :: Nil
  type expected = _5 :: _4 :: _3 :: _2 :: _1 :: _0 :: Nil
  type result   = sortDesc[given]

  implicitly[expected =:= result]
}

object ConcatLaws {
  type givenLeft  = _0 :: _2 :: _1                   :: Nil
  type givenRight =                   _4 :: _3 :: _5 :: Nil
  type expected   = _0 :: _2 :: _1 :: _4 :: _3 :: _5 :: Nil
  type result     = givenLeft ::: givenRight

  implicitly[expected =:= result]
}

object ReverseLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _3 :: _2 :: _1 :: _0 :: Nil
  type result   = reverse[given]

  implicitly[expected =:= result]
}

object SumLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _6
  type result   = sum[given]

  implicitly[expected =:= result]
}

object SizeLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _4
  type result   = size[given]

  implicitly[expected =:= result]
}

object ContainsLaws {
  type given = _0 :: _1 :: _2 :: _3 :: Nil

  implicitly[given contains _2 =:= True]
  implicitly[given contains _4 =:= False]
}

object ContainsAllLaws {
  type given = _0 :: _1 :: _2 :: _3 :: Nil
  type all   = _2 :: _0 :: Nil
  type other = _1 :: _4 :: Nil

  implicitly[given containsAll all   =:= True]
  implicitly[given containsAll other =:= False]
}

object CountLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type expected = _2
  type result   = given count ({ type F[N <: Nat] = N >= _2 })#F

  implicitly[expected =:= result]
}

object ExistsLaws {
  type given                 = _0 :: _1 :: _2 :: _3 :: Nil
  type existsEqual[M <: Nat] = given exists ({ type F[N <: Nat] = N == M })#F

  implicitly[existsEqual[_3] =:= True]
  implicitly[existsEqual[_5] =:= False]
}

object ForallLaws {
  type given                   = _3 :: _4 :: _5 :: Nil
  type forallGreater[M <: Nat] = given forall ({ type F[N <: Nat] = N > M })#F

  implicitly[forallGreater[_2] =:= True]
  implicitly[forallGreater[_3] =:= False]
}

object IndexOfLaws {
  type given    = _3 :: _4 :: _5 :: Nil
  type expected = _2
  type result   = given indexOf _4

  implicitly[expected =:= result]
}

object IndexOfUntilLaws {
  type given    = _3 :: _2 :: _5 :: _4 :: Nil
  type expected = _0
  // type result   = indexOfUntil[given, _4, _3]

  // implicitly[expected =:= result]
}

object LastIndexOfLaws {
  type given    = _3 :: _4 :: _5 :: _4 :: Nil
  type expected = _4
  // type result   = given lastIndexOf _4

  // implicitly[expected =:= result]
}

object LastIndexOfUntilLaws {
  type given    = _3 :: _4 :: _5 :: _2 :: _5:: Nil
  type expected = _3
  // type result   = indexOfUntil[given, _5, _4]

  // implicitly[expected =:= result]
}

object ApplyOrElseLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: Nil
  type fallback = _5
  type apply[N <: Nat] = applyOrElse[given, N, fallback]

  implicitly[apply[_3]  =:=       _2]
  implicitly[apply[_6]  =:= fallback]
}

object EqualLaws {
  type given = _0 :: _1 :: _2 :: _3 :: Nil
  type same  = _0 :: _1 :: _2 :: _3 :: Nil
  type other = _0 :: _2 :: _1 :: _3 :: Nil

  implicitly[===[given, same]  =:= True]
  implicitly[===[given, other] =:= False]
}

object DistinctLaws {
  type given    = _0 :: _0 :: _1 :: _2 :: _1 :: _0  :: _0 :: Nil
  type expected = _0 ::       _1 :: _2                    :: Nil
  type result   = distinct[given]

  implicitly[expected           =:= result]
  implicitly[isDistinct[given]  =:=  False]
  implicitly[isDistinct[result] =:=   True]
}

object TakeLeftLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type expected = _0 :: _1 :: _2                   :: Nil
  type result   = given takeLeft  _3

  implicitly[expected =:= result]
}

object DropRightLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type expected = _0 :: _1 :: _2                   :: Nil
  type result   = given dropRight _3

  implicitly[expected =:= result]
}

object StartsWithLaws {
  type given  = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type prefix = _0 :: _1 :: _2 :: Nil
  type other  = _0 :: _2 :: _1 :: Nil

  implicitly[(given startsWith prefix) =:=  True]
  implicitly[(given startsWith other)  =:= False]
}

object StartsWithOffsetLaws {
  type given             = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type prefix            =             _2 :: _3 :: _4       :: Nil
  type other             = _0 :: _2 :: _1                   :: Nil
  type starts[L <: List] = startsWithOffset[given, L, _2]

  implicitly[starts[prefix] =:=   True]
  implicitly[starts[other]  =:=  False]
}

object EndsWithtLaws {
  type given  = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type suffix = _3 :: _4 :: _5 :: Nil
  type other  = _2 :: _3 :: _4 :: Nil

  implicitly[(given endsWith suffix) =:=  True]
  implicitly[(given endsWith other)  =:= False]
}

object RemoveAllLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: _6 :: Nil
  type delta    =       _1 ::       _3 ::       _5       :: Nil
  type expected = _0 ::       _2 ::       _4 ::       _6 :: Nil
  type result   = given removeAll delta

  implicitly[expected =:= result]
}

object DropLeftLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type expected =                   _3 :: _4 :: _5 :: Nil
  type result   = given dropLeft  _3

  implicitly[expected =:= result]
}

object TakeRightLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type expected =                   _3 :: _4 :: _5 :: Nil
  type result   = given takeRight _3

  implicitly[expected =:= result]
}

object SliceLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: Nil
  type expected =             _2 :: _3 :: _4       :: Nil
  type result   = slice[given, _3, _5]

  implicitly[expected =:= result]
}

object IndexWhereLaws {
  type given    = _0 :: _1 :: _2 :: _3 :: _4 :: _5 :: _6 :: Nil
  type expected = _5
  // type result   = given indexWhere ({ type F[N <: Nat] = N == _4 })#F

  // implicitly[expected =:= result]
}

object ProductLaws {
  type given    = _1 :: _2 :: _3 :: Nil
  type expected = _6
  // type result   = product[given]

  // implicitly[expected =:= result]
}

object DiffLaws {
  type givenLeft    = _1 :: _2 :: _3 :: _1 :: Nil
  type givenRight   = _3 :: _4 :: _2 :: _5 :: Nil
  type expected     = _1 :: _1 :: Nil
  type result       = givenLeft diff givenRight

  implicitly[expected =:= result]
}
