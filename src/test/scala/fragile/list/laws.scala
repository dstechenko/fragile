package fragile
package list

import bool._
import int._
import int.syntax._
import product._
import function._

object MapLaws {
  type given    = `3` :: `1` :: `2` :: `4` :: `0` :: Nil
  type expected = `4` :: `2` :: `3` :: `5` :: `1` :: Nil
  type result   = given map ({ type F[N <: Int] = N + `1` })#F
  type id       = given map identity

  implicitly[expected =:= result]
  implicitly[given    =:=     id]
}

object FlatMapLaws {
  type given    = `1` :: `2` :: `3` :: `4`                             :: Nil
  type expected = `2` :: `3` :: `3` :: `4` :: `4` :: `5` :: `5` :: `6` :: Nil
  type result   = given flatMap ({ type F[N <: Int] = (N + `1`) :: (N + `2`) :: Nil })#F
  type id       = given flatMap list

  implicitly[expected =:= result]
  implicitly[given    =:=     id]
}

object MonadLaws {
  type unit     [N <: Int] = list[N]
  type element             = `0`
  type monad               = `0` :: `1` :: `2` :: `3` :: `4` :: Nil
  type function1[N <: Int] = unit[N + `1`]
  type function2[N <: Int] = unit[N + `2`]
  type chained  [N <: Int] = function1[N] flatMap function2

  implicitly[list[element] flatMap function1                    =:=      function1[element]]
  implicitly[        monad flatMap unit                         =:=                   monad]
  implicitly[        monad flatMap function1 flatMap function2  =:= (monad flatMap chained)]
}

object FoldLaws {
  type given                       = `0` :: `1` :: `2` :: `3` :: Nil
  type combine[L <: Int, R <: Int] = L + R
  type expected                    = `6`

  implicitly[expected =:= foldRight[given, `0`, combine]]
  implicitly[expected =:= foldLeft [given, `0`, combine]]
}

object FilterLaws {
  type given    = `1` :: `2` :: `0` :: `2` :: `3` :: `5` :: `4` :: `1` :: Nil
  type expected = `1` :: `2` :: `0` :: `2` :: `3` :: `1`               :: Nil
  type result   = given filter ({ type F[N <: Int] = N <= `3` })#F

  implicitly[expected =:= result]
}

object FilterNotLaws {
  type given    = `1` :: `2` :: `0` :: `2` :: `3` :: `5` :: `4` :: `1` :: Nil
  type expected = `1` :: `2` :: `0` :: `2` :: `3` ::               `1` :: Nil
  type result   = given filterNot ({ type F[N <: Int] = N > `3` })#F

  implicitly[expected =:= result]
}

object RemoveLaws {
  type given    = `1` :: `2` :: `3` :: `2` :: `3` :: Nil
  type expected = `1` :: `2` ::        `2` :: `3` :: Nil
  type result   = given remove `3`

  implicitly[expected =:= result]
}

/* object SelectSortAscLaws {
 *   type given    = `0` :: `5` :: `3` :: `4` :: `2` :: `1` :: Nil
 *   type expected = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
 *   type result   = selectSortAsc[given]
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object SelecSortDescLaws {
 *   type given    = `0` :: `3` :: `4` :: `2` :: `5` :: `1` :: Nil
 *   type expected = `5` :: `4` :: `3` :: `2` :: `1` :: `0` :: Nil
 *   type result   = selectSortDesc[given]
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object QuickSortAscLaws {
 *   type given    = `0` :: `5` :: `3` :: `4` :: `2` :: `1` :: Nil
 *   type expected = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
 *   type result   = quickSortAsc[given]
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object QuickSortDescLaws {
 *   type given    = `0` :: `3` :: `4` :: `2` :: `5` :: `1` :: Nil
 *   type expected = `5` :: `4` :: `3` :: `2` :: `1` :: `0` :: Nil
 *   type result   = quickSortDesc[given]
 *
 *   implicitly[expected =:= result]
 * } */

object ConcatLaws {
  type givenLeft  = `0` :: `2` :: `1`                      :: Nil
  type givenRight =                      `4` :: `3` :: `5` :: Nil
  type expected   = `0` :: `2` :: `1` :: `4` :: `3` :: `5` :: Nil
  type result     = givenLeft ::: givenRight

  implicitly[expected =:= result]
}

object ReverseLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: Nil
  type expected = `3` :: `2` :: `1` :: `0` :: Nil
  type result   = reverse[given]

  implicitly[expected =:= result]
}

object SumLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: Nil
  type expected = `6`
  type result   = sum[given]

  implicitly[expected =:= result]
}

object ProductLaws {
  type given    = `1` :: `2` :: `3` :: Nil
  type expected = `6`
  type result   = product[given]

  implicitly[expected =:= result]
}

object SizeLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: Nil
  type expected = `4`
  type result   = size[given]

  implicitly[expected =:= result]
}

object ContainsLaws {
  type given = `0` :: `1` :: `2` :: `3` :: Nil

  implicitly[given contains `2` =:=  True]
  implicitly[given contains `4` =:= False]
}

 object ContainsAllLaws {
  type given = `0` :: `1` :: `2` :: `3` :: Nil
  type all   = `2` :: `0` :: Nil
  type other = `1` :: `4` :: Nil

  implicitly[given containsAll all   =:= True]
  implicitly[given containsAll other =:= False]
}

object CountLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: Nil
  type expected = `2`
  type result   = given count ({ type F[N <: Int] = N >= `2` })#F

  implicitly[expected =:= result]
}

object ExistsLaws {
  type given                 = `0` :: `1` :: `2` :: `3` :: Nil
  type existsEqual[M <: Int] = given exists ({ type F[N <: Int] = N == M })#F

  implicitly[existsEqual[`3`] =:=  True]
  implicitly[existsEqual[`5`] =:= False]
}

object ForallLaws {
  type given                   = `3` :: `4` :: `5` :: Nil
  type forallGreater[M <: Int] = given forall ({ type F[N <: Int] = N > M })#F

  implicitly[forallGreater[`2`] =:= True]
  implicitly[forallGreater[`3`] =:= False]
}

/* object IndexOfLaws {
 *   type given    = `3` :: `4` :: `5` :: Nil
 *   type expected = `2`
 *   type result   = given indexOf `4`
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object IndexOfUntilLaws {
 *   type given    = `3` :: `2` :: `5` :: `4` :: Nil
 *   type expected = `0`
 *   type result   = indexOfUntil[given, `4`, `3`]
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object LastIndexOfLaws {
 *   type given              = `3` :: `4` :: `5` :: `4` :: Nil
 *   type lastOf[N <: Int]   = given lastIndexOf N
 *
 *   implicitly[lastOf[`5`] =:= `3`]
 *   implicitly[lastOf[`4`] =:= `4`]
 *   implicitly[lastOf[`1`] =:= `0`]
 * }
 *
 * object LastIndexOfUntilLaws {
 *   type given               = `3` :: `4` :: `5` :: `2` :: `5` :: `1` :: Nil
 *   type lastUntil[N <: Int] = lastIndexOfUntil[given, `5`, N]
 *
 *   implicitly[lastUntil[`2`] =:= `0`]
 *   implicitly[lastUntil[`4`] =:= `3`]
 *   implicitly[lastUntil[`6`] =:= `5`]
 * } */

object ApplyOrElseLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: Nil
  type fallback = `5`
  type apply[N <: Int] = applyOrElse[given, N, fallback]

  implicitly[apply[`3`]  =:=       `2`]
  implicitly[apply[`6`]  =:= fallback]
}

object EqualLaws {
  type given = `0` :: `1` :: `2` :: `3` :: Nil
  type same  = `0` :: `1` :: `2` :: `3` :: Nil
  type other = `0` :: `2` :: `1` :: `3` :: Nil

  implicitly[(given ===  same)  =:=  True]
  implicitly[(given === other)  =:= False]
}

object DistinctLaws {
  type given    = `0` :: `0` :: `1` :: `2` :: `1` :: `0`  :: `0` :: Nil
  type expected = `0` ::        `1` :: `2`                       :: Nil
  type result   = distinct[given]

  implicitly[expected           =:= result]
  implicitly[isDistinct[given]  =:=  False]
  implicitly[isDistinct[result] =:=   True]
}

object TakeLeftLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type expected = `0` :: `1` :: `2`                      :: Nil
  type result   = given takeLeft  `3`

  implicitly[expected =:= result]
}

object DropRightLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type expected = `0` :: `1` :: `2`                      :: Nil
  type result   = given dropRight `3`

  implicitly[expected =:= result]
}

object StartsWithLaws {
  type given  = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type prefix = `0` :: `1` :: `2` :: Nil
  type other  = `0` :: `2` :: `1` :: Nil

  implicitly[(given startsWith prefix) =:=  True]
  implicitly[(given startsWith other)  =:= False]
}

object StartsWithOffsetLaws {
  type given             = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type prefix            =               `2` :: `3` :: `4`        :: Nil
  type other             = `0` :: `2` :: `1`                      :: Nil
  type starts[L <: List] = startsWithOffset[given, L, `2`]

  implicitly[starts[prefix] =:=   True]
  implicitly[starts[other]  =:=  False]
}

object EndsWithtLaws {
  type given  = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type suffix = `3` :: `4` :: `5` :: Nil
  type other  = `2` :: `3` :: `4` :: Nil

  implicitly[(given endsWith suffix) =:=  True]
  implicitly[(given endsWith other)  =:= False]
}

object RemoveAllLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: `6` :: Nil
  type delta    =        `1` ::        `3` ::        `5`        :: Nil
  type expected = `0` ::        `2` ::        `4` ::        `6` :: Nil
  type result   = given removeAll delta

  implicitly[expected =:= result]
}

object DropLeftLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type expected =                      `3` :: `4` :: `5` :: Nil
  type result   = given dropLeft  `3`

  implicitly[expected =:= result]
}

object TakeRightLaws {
  type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type expected =                      `3` :: `4` :: `5` :: Nil
  type result   = given takeRight `3`

  implicitly[expected =:= result]
}

object TakeWhile {
  type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type expected = `0` :: `1` :: `2`                      :: Nil
  type result   = given takeWhile ({ type F[N <: Int] = N < `3` })#F

  implicitly[expected =:= result]
}

object DropWhile {
  type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
  type expected =                      `3` :: `4` :: `5` :: Nil
  type result   = given dropWhile ({ type F[N <: Int] = N < `3` })#F

  implicitly[expected =:= result]
}

/* object SliceLaws {
 *   type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: Nil
 *   type expected =               `2` :: `3` :: `4`        :: Nil
 *   type result   = slice[given, `3`, `5`]
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object IndexOfWhereLaws {
 *   type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: `6` :: Nil
 *   type expected = `5`
 *   type result   = given indexOfWhere ({ type F[N <: Int] = N == `4` })#F
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object IndexOfSliceLaws {
 *   type given    = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: `6` :: Nil
 *   type sliced   =                      `3` :: `4` :: `5`        :: Nil
 *   type expected = `4`
 *   type result   = given indexOfSlice sliced
 *
 *   implicitly[expected =:= result]
 * }
 *
 * object IndexOfSliceFromLaws {
 *   type given               = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: `6` :: Nil
 *   type slice               =               `2` :: `3` :: `4`               :: Nil
 *   type indexFrom[N <: Int] = indexOfSliceFrom[given, slice, N]
 *
 *   implicitly[indexFrom[`1`] =:= `3`]
 *   implicitly[indexFrom[`2`] =:= `3`]
 *   implicitly[indexFrom[`3`] =:= `3`]
 *   implicitly[indexFrom[`4`] =:= `0`]
 * }
 *
 * object ContainsSliceLaws {
 *   type given                                  = `0` :: `1` :: `2` :: `3` :: `4` :: `5` :: `6` :: Nil
 *   type contains[A <: Int, B <: Int, C <: Int] = given containsSlice (A :: B :: C :: Nil)
 *
 *   implicitly[contains[`1`, `2`, `3`] =:= True]
 *   implicitly[contains[`3`, `4`, `5`] =:= True]
 *   implicitly[contains[`1`, `2`, `4`] =:= False]
 *   implicitly[contains[`7`, `8`, `9`] =:= False]
 * }
 *
 * object IndexOfWhereFromLaws {
 *   type given                = `0` :: `4` :: `2` :: `3` :: `4` :: `5` :: `6` :: Nil
 *   type findFrom[B <: Int]   = indexOfWhereFrom[given, ({ type F[N <: Int] = N == `4` })#F, B]
 *
 *   implicitly[findFrom[`3`] =:= `5`]
 *   implicitly[findFrom[`6`] =:= `0`]
 * } */

object DiffLaws {
  type givenLeft    = `1` :: `2` :: `3` :: `1` :: Nil
  type givenRight   = `3` :: `4` :: `2` :: `5` :: Nil
  type expected     = `1` :: `1` :: Nil
  type result       = givenLeft diff givenRight

  implicitly[expected =:= result]
}

object IntersectLaws {
  type givenLeft    = `1` :: `2` :: `3` :: `1` :: Nil
  type givenRight   = `3` :: `4` :: `2` :: `5` :: Nil
  type expected     = `2` :: `3` :: Nil
  type result       = givenLeft intersect givenRight

  implicitly[expected =:= result]
}

/* object PartitionLaws {
 *   type given        = `1` :: `5` :: `2` :: `0` :: `6` :: `9` ::  `3` :: `1` :: Nil
 *   type left         = `1` ::        `2` :: `0` ::                `3` :: `1` :: Nil
 *   type right        = `5` ::                      `6` :: `9`                :: Nil
 *   type expected     = left <~~> right
 *   type result       = given partition ({ type F[N <: Int] = N < `5` })#F
 *
 *   implicitly[expected =:= result]
 * } */

object PadToLaws {
  type given    =                                           `1` :: `2` :: `3` :: `4` :: Nil
  type expected = `0` :: `0` :: `0` :: `0` :: `0` :: `0` :: `1` :: `2` :: `3` :: `4` :: Nil
  type result   = padTo[given, `0`, `6`]

  implicitly[expected =:= result]
}

/* object UpdatedLaws {
 *   type given            = `1` :: `2` :: `3` :: Nil
 *   type expected         = `1` :: `0` :: `3` :: Nil
 *   type update[I <: Int] = updated[given, I, `0`]
 *
 *   implicitly[update[`2`] =:= expected]
 *   implicitly[update[`0`] =:=    given]
 *   implicitly[update[`4`] =:=    given]
 * } */

/*  object SegmentLengthLaws {
 *   type given            = `1` :: `2` :: `3` :: `4` :: `5` :: `6` :: `7` :: `8` :: Nil
 *   type select[N <: Int] = N < `5`
 *
 *   implicitly[segmentLength[given, select, `1`] =:= `4`]
 *   implicitly[segmentLength[given, select, `2`] =:= `4`]
 * }
 *
 * object PrefixLengthLaws {
 *   type given                   = `1` :: `2` :: `3` :: `4` :: `5` :: `6` :: `7` :: `8` :: Nil
 *   type selectValid  [N <: Int] = N < `5`
 *   type selectInvalid[N <: Int] = N > `2`
 *
 *   implicitly[prefixLength[given, selectValid]   =:= `4`]
 *   implicitly[prefixLength[given, selectInvalid] =:= `0`]
 * }
 *
 * object RemoveSliceLaws {
 *   type given         = `1` :: `2` :: `3` :: `4` :: Nil
 *   type validSlice    =        `2` :: `3`        :: Nil
 *   type validResult   = `1`               :: `4` :: Nil
 *   type invalidSlice  =        `2` ::        `4` :: Nil
 *   type invalidResult = `1` :: `2` :: `3` :: `4` :: Nil
 *
 *   implicitly[(given removeSlice validSlice)   =:=   validResult]
 *   implicitly[(given removeSlice invalidSlice) =:= invalidResult]
 * }
 *
 * object LastIndexOfWhereLaws {
 *   type given                  = `1` :: `2` :: `3` :: `4` :: `1` :: Nil
 *   type holdsValid  [N <: Int] = N > `3`
 *   type holdsInvalid[N <: Int] = N < `0`
 *
 *   implicitly[(given lastIndexOfWhere holdsValid)   =:= `4`]
 *   implicitly[(given lastIndexOfWhere holdsInvalid) =:= `0`]
 * }
 *
 * object LastIndexOfWhereUntilLaws {
 *   type given                  = `1` :: `2` :: `3` :: `4` :: `1` :: Nil
 *   type holdsValid  [N <: Int] = N > `3`
 *   type holdsInvalid[N <: Int] = N < `0`
 *
 *   implicitly[lastIndexOfWhereUntil[given, holdsValid, `3`]   =:= `0`]
 *   implicitly[lastIndexOfWhereUntil[given, holdsValid, `4`]   =:= `4`]
 *   implicitly[lastIndexOfWhereUntil[given, holdsValid, `5`]   =:= `4`]
 *   implicitly[lastIndexOfWhereUntil[given, holdsInvalid, `5`] =:= `0`]
 * } */
