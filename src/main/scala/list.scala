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

  override protected type This                                            = Nil
  override protected type Min   [N <: Nat]                                = N
}

trait TListFunctions {
  type map             [L <: List, F[_ <: Nat] <: Nat]                     = L#Map[F]
  type flatMap         [L <: List, F[_ <: Nat] <: List]                    = L#FlatMap[F]
  type filter          [L <: List, F[_ <: Nat] <: Bool]                    = L#Filter[F]
  type fold            [L <: List, N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = L#Fold[N, F]
  type indexOf         [L <: List, N <: Nat]                               = L#IndexOf[N, _0]
  type remove          [L <: List, N <: Nat]                               = L#Remove[N]
  type :::             [L <: List, R <: List]                              = L#Concat[R]
  type reverse         [L <: List]                                         = L#Reverse
  type sort            [L <: List]                                         = L#Sort
  type size            [L <: List]                                         = L#Size

  type isEmpty         [L <: List]                                         = size[L] == _0
  type nonEmpty        [L <: List]                                         = ![isEmpty[L]]
  type reduceP         [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _0, F]
  type reduceM         [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _1, F]
  type sum             [L <: List]                                         = L reduceP ({ type F[LN <: Nat, RN <: Nat] = RN + LN })#F
  type product         [L <: List]                                         = L reduceM ({ type F[LN <: Nat, RN <: Nat] = RN * LN })#F
  type count           [L <: List, F[_ <: Nat] <: Bool]                    = size[L filter F]
  type contains        [L <: List, M <: Nat]                               = nonEmpty[L filter ({ type F[N <: Nat] = M == N })#F]
  type containsAll     [L <: List, R <: List]                              = R forall ({ type F[N <: Nat] = (L contains N)})#F
  type exists          [L <: List, F[_ <: Nat] <: Bool]                    = (L count F) > _0
  type filterNot       [L <: List, F[_ <: Nat] <: Bool]                    = L filter ({ type FN[N <: Nat] = ![F[N]] })#FN
  type forall          [L <: List, F[_ <: Nat] <: Bool]                    = ![L exists ({ type FN[N <: Nat] = ![F[N]] })#FN]
  type sortAsc         [L <: List]                                         = sort[L]
  type sortDesc        [L <: List]                                         = reverse[sortAsc[L]]
  type corresponds     [L <: List, R <: List]                              = L forall ({ type F[N <: Nat] = (L indexOf N) == (R indexOf N) })#F
  type ===             [L <: List, R <: List]                              = (size[L] == size[R]) && (L corresponds R) && (R corresponds L)
  type isDefinedAt     [L <: List, N <: Nat]                               = size[L] >= N

  type indexWhere      [L <: List, F[_ <: Nat] <: Bool]                    = Nothing
  type indexWhereFrom  [L <: List, F[_ <: Nat] <: Bool, B <: Nat]          = Nothing
  type indexOfSlice    [L <: List, R <: List]                              = Nothing
  type indexOfSliceFrom[L <: List, R <: List, B <: Nat]                    = Nothing
  type containsSlice   [L <: List, R <: List]                              = Nothing
  type distinct        [L <: List]                                         = reverse[reverse[L]#Distinct]
  type isDistinct      [L <: List]                                         = L === distinct[L]
  type dropLeft        [L <: List, N <: Nat]                               = Nothing
  type dropRight       [L <: List, N <: Nat]                               = Nothing
  type dropWhile       [L <: List, F[_ <: Nat] <: Bool]                    = Nothing
  type takeLeft        [L <: List, N <: Nat]                               = Nothing
  type takeRigh        [L <: List, N <: Nat]                               = Nothing
  type takeWhile       [L <: List, F[_ <: Nat] <: Bool]                    = Nothing
  type startsWith      [L <: List, R <: List]                              = Nothing
  type startsWithOffset[L <: List, R <: List, O <: Nat]                    = Nothing
  type endsWith        [L <: List, R <: List]                              = Nothing
  type diff            [L <: List, R <: List]                              = Nothing
  type union           [L <: List, R <: List]                              = Nothing
  type intersect       [L <: List, R <: List]                              = Nothing
  type permutations    [L <: List]                                         = Nothing
  type slice           [L <: List, B <: Nat, E <: Nat]                     = Nothing
}

object List extends TListFunctions
