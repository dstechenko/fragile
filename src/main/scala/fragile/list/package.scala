package fragile

import bool._
import int._
import int.syntax._
import product._
import function._

import language.higherKinds

package object list {
  private[list] type minOrElse[L <: List, N <: Int]                               = L#MinOrElse[N]

  type safeL                  [L <: List]                                         = L map identity

  type list                   [N <: Int]                                          = N :: Nil

  type flatMap                [L <: List, F[_ <: Int] <: List]                    = L#FlatMap[F]
  type foldRight              [L <: List, N <: Int, F[_ <: Int, _ <: Int] <: Int] = L#FoldRight[N, F]
  type indexOf                [L <: List, N <: Int]                               = L#IndexOf[N, `0`]
  type :::                    [L <: List, R <: List]                              = L#Concat[R]
  type reverse                [L <: List]                                         = L#Reverse
  type selectSort             [L <: List]                                         = L#SelectSort
  type quickSort              [L <: List]                                         = L#QuickSort
  type size                   [L <: List]                                         = L#Size
  type takeLeft               [L <: List, N <: Int]                               = L#TakeLeft[N]
  type applyOrElse            [L <: List, N <: Int, E <: Int]                     = L#ApplyOrElse[N, E]

  type isEmpty                [L <: List]                                         = size[L] == `0`
  type nonEmpty               [L <: List]                                         = ![isEmpty[L]]
  type count                  [L <: List, F[_ <: Int] <: Bool]                    = size[L filter F]
  type contains               [L <: List, N <: Int]                               = (L indexOf N) > `-1`
  type containsAll            [L <: List, R <: List]                              = R forall ({ type F[N <: Int] = L contains N })#F
  type exists                 [L <: List, F[_ <: Int] <: Bool]                    = (L count F) > `0`
  type filterNot              [L <: List, F[_ <: Int] <: Bool]                    = L filter ({ type FN[N <: Int] = ![F[N]] })#FN
  type forall                 [L <: List, F[_ <: Int] <: Bool]                    = ![L exists ({ type FN[N <: Int] = ![F[N]] })#FN]
  type selectSortAsc          [L <: List]                                         = selectSort[L]
  type selectSortDesc         [L <: List]                                         = reverse[selectSortAsc[L]]
  type quickSortAsc           [L <: List]                                         = quickSort[L]
  type quickSortDesc          [L <: List]                                         = reverse[quickSortAsc[L]]
  type corresponds            [L <: List, R <: List]                              = L forall ({ type F[N <: Int] = (L indexOf N) == (R indexOf N) })#F
  type ===                    [L <: List, R <: List]                              = (size[L] == size[R]) && (L corresponds R) && (R corresponds L)
  type isDefinedAt            [L <: List, N <: Int]                               = size[L] >= N
  type distinct               [L <: List]                                         = reverse[reverse[L]#Distinct]
  type isDistinct             [L <: List]                                         = L === distinct[L]
  type removeEach             [L <: List, M <: Int]                               = L filterNot ({ type F[N <: Int] = M == N })#F
  type removeAll              [L <: List, R <: List]                              = L filterNot ({ type F[N <: Int] = R contains N })#F
  type dropLeft               [L <: List, N <: Int]                               = reverse[reverse[L] takeLeft (size[L] - N)]
  type dropRight              [L <: List, N <: Int]                               = L takeLeft (size[L] - N)
  type takeRight              [L <: List, N <: Int]                               = L dropLeft (size[L] - N)
  type startsWith             [L <: List, R <: List]                              = (L takeLeft size[R]) === R
  type startsWithOffset       [L <: List, R <: List, B <: Int]                    = (L dropLeft B) startsWith R
  type endsWith               [L <: List, R <: List]                              = (L takeRight size[R]) === R
  type slice                  [L <: List, B <: Int, E <: Int]                     = (L dropLeft (B - `1`)) dropRight (size[L] - E)
  type union                  [L <: List, R <: List]                              = L ::: R
  type diff                   [L <: List, R <: List]                              = L removeAll R
  type indexOfWhere           [L <: List, F[_ <: Int] <: Bool]                    = applyOrElse[(L filter F) map ({ type F[N <: Int] = (L indexOf N) })#F, `1`, `-1`]
  type indexOfWhereFrom       [L <: List, F[_ <: Int] <: Bool, B <: Int]          = ({
                                                                                      type offset = B - `1`
                                                                                      type suffix = L dropLeft offset
                                                                                      type index  = suffix indexOfWhere F
                                                                                      type run    = ifI[index == `-1`, `-1`, index + offset]
                                                                                    })#run
  type indexOfUntil           [L <: List, M <: Int, E <: Int]                     = (L takeLeft E) indexOfWhere ({ type F[N <: Int] = N == M })#F
  type lastIndexOfUntil       [L <: List, N <: Int, E <: Int]                     = (L takeLeft E) lastIndexOf N

  type lastIndexOf            [L <: List, N <: Int]                               = ({
                                                                                      type last  = reverse[L] indexOf N
                                                                                      type index = (size[L] + `1`) - last
                                                                                      type run   = ifI[L contains N, index, `-1`]
                                                                                    })#run

  type remove                 [L <: List, N <: Int]                               = ({
                                                                                      type index = L indexOf N
                                                                                      type left  = L takeLeft (index - `1`)
                                                                                      type right = L dropLeft index
                                                                                      type run   = left ::: right
                                                                                    })#run

  type filter                 [L <: List, F[_ <: Int] <: Bool]                    = ({
                                                                                      type unfold[N <: Int] = ifL[F[N], list[N], Nil]
                                                                                      type run              = safeL[L flatMap unfold]
                                                                                    })#run


  type map                    [L <: List, F[_ <: Int] <: Int]                     = ({
                                                                                       type lifted[N <: Int] = list[F[N]]
                                                                                       type run              = L flatMap lifted
                                                                                     })#run
  type countWhile             [L <: List, F[_ <: Int] <: Bool]                    = L indexOfWhere ({ type G[N <: Int] = ![F[N]] })#G - `1`
  type takeWhile              [L <: List, F[_ <: Int] <: Bool]                    = L takeLeft (L countWhile F)
  type dropWhile              [L <: List, F[_ <: Int] <: Bool]                    = L dropLeft (L countWhile F)
  type partition              [L <: List, F[_ <: Int] <: Bool]                    = (L filter F) <~~> (L filterNot F)
  type tabulate               [N <: Int, E <: Int]                                = unfold[E] map const[N]#Apply
  type padTo                  [L <: List, N <: Int, E <: Int]                     = tabulate[N, E] ::: L
  type intersect              [L <: List, R <: List]                              = L filter  ({ type F[N <: Int] = R contains N })#F
  type foldLeft               [L <: List, N <: Int, F[_ <: Int, _ <: Int] <: Int] = foldRight[reverse[L], N, F]


  type containsSlice          [L <: List, R <: List]                              = (L indexOfSlice R) > `-1`

  type indexOfSlice           [L <: List, R <: List]                              = ({
                                                                                      type index               = unfold[size[L]]
                                                                                      type locate   [N <: Int] = ifI[startsWithOffset[L, R, N - `1`], `1`, `0`]
                                                                                      type locations           = index map locate
                                                                                      type run                 = locations indexOf `1`
                                                                                    })#run

  type indexOfSliceFrom       [L <: List, R <: List, B <: Int]                    = ({
                                                                                      type offset   = B - `1`
                                                                                      type selected = L dropLeft offset
                                                                                      type hasSlice = selected containsSlice R
                                                                                      type index    = (selected indexOfSlice R) + offset
                                                                                      type run      = ifI[hasSlice, index, `-1`]
                                                                                    })#run

  type removeSlice            [L <: List, R <: List]                              = ({
                                                                                      type index    = L indexOfSlice R
                                                                                      type contains = index > `-1`
                                                                                      type left     = L takeLeft (index - `1`)
                                                                                      type right    = L dropLeft (index + size[R] - `1`)
                                                                                      type removed  = left ::: right
                                                                                      type run      = ifL[contains, removed, L]
                                                                                    })#run

  type lastIndexOfWhere       [L <: List, F[_ <: Int] <: Bool]                    = ({
                                                                                      type breaks   [N <: Int] = ![F[N]]
                                                                                      type index               = L indexOfWhere F
                                                                                      type remaining           = L dropLeft index
                                                                                      type breaksAt            = remaining indexOfWhere breaks
                                                                                      type lastIndex           = index + breaksAt - `1`
                                                                                      type contains            = index > `-1`
                                                                                      type run                 = ifI[contains, lastIndex, `-1`]
                                                                                    })#run

  type lastIndexOfWhereUntil  [L <: List, F[_ <: Int] <: Bool, E <: Int]          = (L takeLeft E) lastIndexOfWhere F


  type segmentLength          [L <: List, F[_ <: Int] <: Bool, B <: Int]          = ({
                                                                                      type isBroken[N <: Int] = ![F[N]]
                                                                                      type run                = indexOfWhereFrom[L, isBroken, B] - `1`
                                                                                    })#run

  type prefixLength           [L <: List, F[_ <: Int] <: Bool]                    = segmentLength[L, F, `1`]

  type updated                [L <: List, I <: Int, N <: Int]                     = ({
                                                                                      type length   = size[L]
                                                                                      type hasIndex = (I >= `0`) && (I <= length)
                                                                                      type before   = L takeLeft (I - `1`)
                                                                                      type after    = L dropLeft I
                                                                                      type indexed  = ifL[hasIndex, list[N], Nil]
                                                                                      type run      = before ::: indexed ::: after
                                                                                    })#run

  type sum                    [L <: List]                                         = ({
                                                                                      type zero                       = `0`
                                                                                      type append[A <: Int, B <: Int] = A + B
                                                                                      type run                        = foldLeft[L, zero, append]
                                                                                    })#run

  type product                [L <: List]                                         = ({
                                                                                      type zero                       = `1`
                                                                                      type append[A <: Int, B <: Int] = A * B
                                                                                      type run                        = foldLeft[L, zero, append]
                                                                                    })#run
}
