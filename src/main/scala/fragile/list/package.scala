package fragile

import fragile.bool._
import fragile.nat._
import fragile.product._
import fragile.function._

import language.higherKinds

package object list {
  private[list] type minOrElse[L <: List, N <: Nat]                               = L#MinOrElse[N]
  private[list] type safeL    [L <: List]                                         = L map identity

  type list                   [N <: Nat]                                          = N :: Nil

  type flatMap                [L <: List, F[_ <: Nat] <: List]                    = L#FlatMap[F]
  type foldRight              [L <: List, N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = L#FoldRight[N, F]
  type indexOf                [L <: List, N <: Nat]                               = L#IndexOf[N, _0]
  type :::                    [L <: List, R <: List]                              = L#Concat[R]
  type reverse                [L <: List]                                         = L#Reverse
  type selectSort             [L <: List]                                         = L#SelectSort
  type quickSort              [L <: List]                                         = L#QuickSort
  type size                   [L <: List]                                         = L#Size
  type takeLeft               [L <: List, N <: Nat]                               = L#TakeLeft[N]
  type applyOrElse            [L <: List, N <: Nat, E <: Nat]                     = L#ApplyOrElse[N, E]

  type isEmpty                [L <: List]                                         = size[L] == _0
  type nonEmpty               [L <: List]                                         = ![isEmpty[L]]
  type count                  [L <: List, F[_ <: Nat] <: Bool]                    = size[L filter F]
  type contains               [L <: List, N <: Nat]                               = (L indexOf N) > _0
  type containsAll            [L <: List, R <: List]                              = R forall ({ type F[N <: Nat] = L contains N })#F
  type exists                 [L <: List, F[_ <: Nat] <: Bool]                    = (L count F) > _0
  type filterNot              [L <: List, F[_ <: Nat] <: Bool]                    = L filter ({ type FN[N <: Nat] = ![F[N]] })#FN
  type forall                 [L <: List, F[_ <: Nat] <: Bool]                    = ![L exists ({ type FN[N <: Nat] = ![F[N]] })#FN]
  type selectSortAsc          [L <: List]                                         = selectSort[L]
  type selectSortDesc         [L <: List]                                         = reverse[selectSortAsc[L]]
  type quickSortAsc           [L <: List]                                         = quickSort[L]
  type quickSortDesc          [L <: List]                                         = reverse[quickSortAsc[L]]
  type corresponds            [L <: List, R <: List]                              = L forall ({ type F[N <: Nat] = (L indexOf N) == (R indexOf N) })#F
  type ===                    [L <: List, R <: List]                              = (size[L] == size[R]) && (L corresponds R) && (R corresponds L)
  type isDefinedAt            [L <: List, N <: Nat]                               = size[L] >= N
  type distinct               [L <: List]                                         = reverse[reverse[L]#Distinct]
  type isDistinct             [L <: List]                                         = L === distinct[L]
  type removeEvery            [L <: List, M <: Nat]                               = L filterNot ({ type F[N <: Nat] = M == N })#F
  type removeAll              [L <: List, R <: List]                              = L filterNot ({ type F[N <: Nat] = R contains N })#F
  type dropLeft               [L <: List, N <: Nat]                               = reverse[reverse[L] takeLeft (size[L] - N)]
  type dropRight              [L <: List, N <: Nat]                               = L takeLeft (size[L] - N)
  type takeRight              [L <: List, N <: Nat]                               = L dropLeft (size[L] - N)
  type startsWith             [L <: List, R <: List]                              = (L takeLeft size[R]) === R
  type startsWithOffset       [L <: List, R <: List, B <: Nat]                    = (L dropLeft B) startsWith R
  type endsWith               [L <: List, R <: List]                              = (L takeRight size[R]) === R
  type slice                  [L <: List, B <: Nat, E <: Nat]                     = (L dropLeft (B - _1)) dropRight (size[L] - E)
  type union                  [L <: List, R <: List]                              = L ::: R
  type diff                   [L <: List, R <: List]                              = L removeAll R
  type indexOfWhere           [L <: List, F[_ <: Nat] <: Bool]                    = applyOrElse[(L filter F) map ({ type F[N <: Nat] = (L indexOf N) })#F, _1, _0]
  type indexOfWhereFrom       [L <: List, F[_ <: Nat] <: Bool, B <: Nat]          = ifN[(L dropLeft B) indexOfWhere F == _0, _0, ((L dropLeft B) indexOfWhere F) + B]
  type indexOfUntil           [L <: List, M <: Nat, E <: Nat]                     = (L takeLeft E) indexOfWhere ({ type F[N <: Nat] = N == M })#F
  type lastIndexOfUntil       [L <: List, N <: Nat, E <: Nat]                     = (L takeLeft E) lastIndexOf N

  type lastIndexOf            [L <: List, N <: Nat]                               = ({
                                                                                      type index = size[L] - (reverse[L] indexOf N) + _1
                                                                                      type run   = ifN[L contains N, index, _0]
                                                                                    })#run

  type remove                 [L <: List, N <: Nat]                               = ({
                                                                                      type index = L indexOf N
                                                                                      type left  = L takeLeft (index - _1)
                                                                                      type right = L dropLeft index
                                                                                      type run   = left ::: right
                                                                                    })#run

  type filter                 [L <: List, F[_ <: Nat] <: Bool]                    = ({
                                                                                      type unfold[N <: Nat] = ifL[F[N], list[N], Nil]
                                                                                      type run              = safeL[L flatMap unfold]
                                                                                    })#run

  type map                    [L <: List, F[_ <: Nat] <: Nat]                     = ({
                                                                                      type lifted[N <: Nat] = list[F[N]]
                                                                                      type run = L flatMap lifted
                                                                                    })#run

  type countWhile             [L <: List, F[_ <: Nat] <: Bool]                    = L indexOfWhere ({ type G[N <: Nat] = ![F[N]] })#G - _1
  type takeWhile              [L <: List, F[_ <: Nat] <: Bool]                    = L takeLeft (L countWhile F)
  type dropWhile              [L <: List, F[_ <: Nat] <: Bool]                    = L dropLeft (L countWhile F)
  type partition              [L <: List, F[_ <: Nat] <: Bool]                    = (L filter F) <--> (L filterNot F)
  type tabulate               [N <: Nat, E <: Nat]                                = unfold[E] map const[N]#Apply
  type padTo                  [L <: List, N <: Nat, E <: Nat]                     = tabulate[N, E] ::: L
  type intersect              [L <: List, R <: List]                              = L filter  ({ type F[N <: Nat] = R contains N })#F
  type foldLeft               [L <: List, N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = foldRight[reverse[L], N, F]


  type containsSlice          [L <: List, R <: List]                              = (L indexOfSlice R) > _0

  type indexOfSlice           [L <: List, R <: List]                              = ({
                                                                                      type index               = unfold[size[L]]
                                                                                      type locate   [N <: Nat] = ifN[startsWithOffset[L, R, N - _1], _1, _0]
                                                                                      type locations           = index map locate
                                                                                      type run                 = locations indexOf _1
                                                                                    })#run

  type indexOfSliceFrom       [L <: List, R <: List, B <: Nat]                    = ({
                                                                                      type offset   = B - _1
                                                                                      type selected = L dropLeft offset
                                                                                      type hasSlice = selected containsSlice R
                                                                                      type index    = (selected indexOfSlice R) + offset
                                                                                      type run      = ifN[hasSlice, index, _0]
                                                                                    })#run

  type removeSlice            [L <: List, R <: List]                              = ({
                                                                                      type index    = L indexOfSlice R
                                                                                      type contains = index > _0
                                                                                      type left     = L takeLeft (index - _1)
                                                                                      type right    = L dropLeft (index + size[R] - _1)
                                                                                      type removed  = left ::: right
                                                                                      type run      = ifL[contains, removed, L]
                                                                                    })#run

  type lastIndexOfWhere       [L <: List, F[_ <: Nat] <: Bool]                    = ({
                                                                                      type breaks   [N <: Nat] = ![F[N]]
                                                                                      type index               = L indexOfWhere F
                                                                                      type remaining           = L dropLeft index
                                                                                      type breaksAt            = remaining indexOfWhere breaks
                                                                                      type lastIndex           = index + (breaksAt - _1)
                                                                                      type contains            = index > _0
                                                                                      type run                 = ifN[contains, lastIndex, _0]
                                                                                    })#run

  type lastIndexOfWhereUntil  [L <: List, F[_ <: Nat] <: Bool, E <: Nat]          = (L takeLeft E) lastIndexOfWhere F


  type segmentLength          [L <: List, F[_ <: Nat] <: Bool, B <: Nat]          = ({
                                                                                      type isBroken[N <: Nat] = ![F[N]]
                                                                                      type run                = indexOfWhereFrom[L, isBroken, B] - _1
                                                                                    })#run

  type prefixLength           [L <: List, F[_ <: Nat] <: Bool]                    = segmentLength[L, F, _1]

  type updated                [L <: List, I <: Nat, N <: Nat]                     = ({
                                                                                      type length   = size[L]
                                                                                      type hasIndex = (I > _0) && (I <= length)
                                                                                      type before   = L takeLeft (I - _1)
                                                                                      type after    = L dropLeft I
                                                                                      type indexed  = ifL[hasIndex, list[N], Nil]
                                                                                      type run      = before ::: indexed ::: after
                                                                                    })#run

  type sum                    [L <: List]                                         = ({
                                                                                      type zero                       = _0
                                                                                      type append[A <: Nat, B <: Nat] = A + B
                                                                                      type run                        = foldLeft[L, zero, append]
                                                                                    })#run

  type product                [L <: List]                                         = ({
                                                                                      type zero                       = _1
                                                                                      type append[A <: Nat, B <: Nat] = A * B
                                                                                      type run                        = foldLeft[L, zero, append]
                                                                                    })#run
}
