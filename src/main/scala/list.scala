import language.higherKinds

import Bool._
import Nat._
import List._

sealed trait List {
  type Map           [F[_ <: Nat] <: Nat]                     <: List
  type FlatMap       [F[_ <: Nat] <: List]                    <: List
  type Filter        [F[_ <: Nat] <: Bool]                    <: List
  type Fold          [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] <: Nat
  type IndexOf       [N <: Nat, I <: Nat]                     <: Nat
  type Remove        [N <: Nat]                               <: List
  type Concat        [L <: List]                              <: List
  type Reverse                                                <: List
  type Distinct                                               <: List
  type Sort                                                   <: List
  type Size                                                   <: Nat
  type TakeLeft      [N <: Nat]                               <: List
  type DropWhile     [F[_ <: Nat] <: Bool]                    <: List
  type ApplyOrElse   [N <: Nat, E <: Nat]                     <: Nat

  protected type This                                         <: List
  protected type Min [N <: Nat]                               <: Nat
}

sealed trait ::[H <: Nat, T <: List] extends List {
  override type Map           [F[_ <: Nat] <: Nat]                     = F[H] :: T#Map[F]
  override type FlatMap       [F[_ <: Nat] <: List]                    = F[H] ::: T#FlatMap[F]
  override type Filter        [F[_ <: Nat] <: Bool]                    = ifL[F[H], H :: T#Filter[F], T#Filter[F]]
  override type Fold          [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = F[H, T#Fold[N, F]]
  override type IndexOf       [N <: Nat, I <: Nat]                     = ifN[Head == N, I + _1, Tail#IndexOf[N, I + _1]]
  override type Remove        [N <: Nat]                               = ifL[H == N, T, H :: T#Remove[N]]
  override type Concat        [L <: List]                              = H :: T#Concat[L]
  override type Reverse                                                = T#Reverse#Concat[H :: Nil]
  override type Distinct                                               = ifL[Tail contains Head, Tail#Distinct, H :: Tail#Distinct]
  override type Sort                                                   = T#Min[H] :: (H :: T)#Remove[T#Min[H]]#Sort
  override type Size                                                   = Succ[T#Size]
  override type TakeLeft      [N <: Nat]                               = ifL[isZero[N],  Nil, Head :: Tail#TakeLeft[N - _1]]
  override type ApplyOrElse   [N <: Nat, E <: Nat]                     = ifN[N == _0, E, ifN[N == _1, Head, Tail#ApplyOrElse[N - _1, E]]]

  override protected type This                                         = Head :: Tail
  override protected type Min [N <: Nat]                               = T#Min[H min N]

  protected type Head                                                  = H
  protected type Tail                                                  = T
}

sealed trait Nil extends List {
  override type Map             [F[_ <: Nat] <: Nat]                      = This
  override type FlatMap         [F[_ <: Nat] <: List]                     = This
  override type Filter          [F[_ <: Nat] <: Bool]                     = This
  override type Fold            [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat]  = N
  override type IndexOf         [N <: Nat, I <: Nat]                      = _0
  override type Remove          [N <: Nat]                                = This
  override type Concat          [L <: List]                               = L
  override type Reverse                                                   = This
  override type Distinct                                                  = This
  override type Sort                                                      = This
  override type Size                                                      = _0
  override type TakeLeft        [N <: Nat]                                = This
  override type ApplyOrElse     [N <: Nat, E <: Nat]                      = E

  override protected type This                                            = Nil
  override protected type Min   [N <: Nat]                                = N
}

sealed trait TListFunctions {
  type list                 [N <: Nat]                                          = N :: Nil
  type map                  [L <: List, F[_ <: Nat] <: Nat]                     = L#Map[F]
  type flatMap              [L <: List, F[_ <: Nat] <: List]                    = L#FlatMap[F]
  type filter               [L <: List, F[_ <: Nat] <: Bool]                    = L#Filter[F]
  type fold                 [L <: List, N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = L#Fold[N, F]
  type indexOf              [L <: List, N <: Nat]                               = L#IndexOf[N, _0]
  type remove               [L <: List, N <: Nat]                               = L#Remove[N]
  type :::                  [L <: List, R <: List]                              = L#Concat[R]
  type reverse              [L <: List]                                         = L#Reverse
  type sort                 [L <: List]                                         = L#Sort
  type size                 [L <: List]                                         = L#Size
  type takeLeft             [L <: List, N <: Nat]                               = L#TakeLeft[N]
  type applyOrElse          [L <: List, N <: Nat, E <: Nat]                     = L#ApplyOrElse[N, E]

  type isEmpty              [L <: List]                                         = size[L] == _0
  type nonEmpty             [L <: List]                                         = ![isEmpty[L]]
  type reduceP              [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _0, F]
  type reduceM              [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _1, F]
  type sum                  [L <: List]                                         = L reduceP ({ type F[LN <: Nat, RN <: Nat] = RN + LN })#F
  type count                [L <: List, F[_ <: Nat] <: Bool]                    = size[L filter F]
  type contains             [L <: List, M <: Nat]                               = nonEmpty[L filter ({ type F[N <: Nat] = M == N })#F]
  type containsAll          [L <: List, R <: List]                              = R forall ({ type F[N <: Nat] = L contains N })#F
  type exists               [L <: List, F[_ <: Nat] <: Bool]                    = (L count F) > _0
  type filterNot            [L <: List, F[_ <: Nat] <: Bool]                    = L filter ({ type FN[N <: Nat] = ![F[N]] })#FN
  type forall               [L <: List, F[_ <: Nat] <: Bool]                    = ![L exists ({ type FN[N <: Nat] = ![F[N]] })#FN]
  type sortAsc              [L <: List]                                         = sort[L]
  type sortDesc             [L <: List]                                         = reverse[sortAsc[L]]
  type corresponds          [L <: List, R <: List]                              = L forall ({ type F[N <: Nat] = (L indexOf N) == (R indexOf N) })#F
  type ===                  [L <: List, R <: List]                              = (size[L] == size[R]) && (L corresponds R) && (R corresponds L)
  type isDefinedAt          [L <: List, N <: Nat]                               = size[L] >= N
  type distinct             [L <: List]                                         = reverse[reverse[L]#Distinct]
  type isDistinct           [L <: List]                                         = L === distinct[L]
  type removeEvery          [L <: List, M <: Nat]                               = L filterNot ({ type F[N <: Nat] = M == N })#F
  type removeAll            [L <: List, R <: List]                              = L filterNot ({ type F[N <: Nat] = R contains N })#F
  type dropLeft             [L <: List, N <: Nat]                               = reverse[reverse[L] takeLeft (size[L] - N)]
  type dropRight            [L <: List, N <: Nat]                               = L takeLeft (size[L] - N)
  type takeRight            [L <: List, N <: Nat]                               = L dropLeft (size[L] - N)
  type startsWith           [L <: List, R <: List]                              = (L takeLeft size[R]) === R
  type startsWithOffset     [L <: List, R <: List, B <: Nat]                    = (L dropLeft B) startsWith R
  type endsWith             [L <: List, R <: List]                              = (L takeRight size[R]) === R
  type slice                [L <: List, B <: Nat, E <: Nat]                     = (L dropLeft (B - _1)) dropRight (size[L] - E)

  type indexOfUntil         [L <: List, N <: Nat, E <: Nat]                     = (L takeLeft E) indexOf N
  type lastIndexOf          [L <: List, N <: Nat]                               = size[L] - (reverse[L] indexOf N)
  type lastIndexOfUntil     [L <: List, N <: Nat, E <: Nat]                     = (L takeLeft E) lastIndexOf N
  type indexWhere           [L <: List, F[_ <: Nat] <: Bool]                    = (L map ({ type FN[N <: Nat] = ifN[F[N], _1, _0] })#FN) indexOf _1
  type indexWhereFrom       [L <: List, F[_ <: Nat] <: Bool, B <: Nat]          = (L dropLeft B) indexWhere F
  type product              [L <: List]                                         = L reduceM ({ type F[LN <: Nat, RN <: Nat] = RN * LN })#F

  type indexOfSlice         [L <: List, R <: List]                              <: Nat
  type indexOfSliceFrom     [L <: List, R <: List, B <: Nat]                    <: Nat
  type lastIndexOfSlice     [L <: List, R <: List]                              <: Nat
  type lastIndexOfSliceUntil[L <: List, R <: Nat, E <: Nat]                     <: Nat
  type lastIndexOfWhere     [L <: List, F[_ <: Nat] <: Bool]                    <: Nat
  type lastIndexOfWhereUntil[L <: List, F[_ <: Nat] <: Bool, E <: Nat]          <: Nat
  type containsSlice        [L <: List, R <: List]                              <: List
  type removeSlice          [L <: List, R <: List]                              <: List
  type dropWhile            [L <: List, F[_ <: Nat] <: Bool]                    <: List
  type takeWhile            [L <: List, F[_ <: Nat] <: Bool]                    <: List
  type partition            [L <: List, F[_ <: Nat] <: Bool]                    <: List
  type padTo                [L <: List, N <: Nat, E <: Nat]                     <: List
  type diff                 [L <: List, R <: List]                              <: List
  type union                [L <: List, R <: List]                              <: List
  type intersect            [L <: List, R <: List]                              <: List
  type permutations         [L <: List]                                         <: List
}

object List extends TListFunctions
