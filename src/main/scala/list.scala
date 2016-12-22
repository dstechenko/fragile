import language.higherKinds

import Bool._
import Nat._
import List._

sealed trait List {
  type Map             [F[_ <: Nat] <: Nat]                       <: List
  type Filter          [F[_ <: Nat] <: Bool]                      <: List
  type Fold            [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat]   <: Nat
  type Contains        [N <: Nat]                                 <: Bool
  type ContainsSlice   [L <: List]                                <: Bool
  type Remove          [N <: Nat]                                 <: List
  type Concat          [L <: List]                                <: List
  type Equal           [L <: List]                                <: Bool
  type Reverse                                                    <: List
  type Sort                                                       <: List
  type Size                                                       <: Nat

  protected type This                                             <: List
  protected type Min   [N <: Nat]                                 <: Nat
  protected type EqualH[L <: ::[_ <: Nat, _ <: List], F <: Bool]  <: Bool
}

sealed trait ::[H <: Nat, T <: List] extends List {
  override type Map           [F[_ <: Nat] <: Nat]                        = F[H] :: T#Map[F]
  override type Filter        [F[_ <: Nat] <: Bool]                       = ifl[F[H], H :: T#Filter[F], T#Filter[F]]
  override type Fold          [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat]    = F[H, T#Fold[N, F]]
  override type Remove        [N <: Nat]                                  = ifl[H == N, T, H :: T#Remove[N]]
  override type Concat        [L <: List]                                 = H :: T#Concat[L]
  override type Equal         [L <: List]                                 = L#EqualH[This, True]
  override type Reverse                                                   = T#Reverse#Concat[H :: Nil]
  override type Sort                                                      = T#Min[H] :: (H :: T)#Remove[T#Min[H]]#Sort
  override type Size                                                      = Succ[T#Size]

  override protected type This                                            = Head :: Tail
  override protected type Min [N <: Nat]                                  = T#Min[H min N]
  override protected type EqualH[L <: ::[_ <: Nat, _ <: List], F <: Bool] = ifb[F, ifb[Head == L#Head, L#Tail#EqualH[This, ![F]], False], Tail#EqualH[L, ![F]]]

  protected type Head                                                     = H
  protected type Tail                                                     = T
}

sealed trait Nil extends List {
  override type Map             [F[_ <: Nat] <: Nat]                      = This
  override type Filter          [F[_ <: Nat] <: Bool]                     = This
  override type Fold            [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat]  = N
  override type Remove          [N <: Nat]                                = This
  override type Concat          [L <: List]                               = L
  override type Equal           [L <: List]                               = isEmpty[L]
  override type Reverse                                                   = This
  override type Sort                                                      = This
  override type Size                                                      = _0

  override protected type This                                            = Nil
  override protected type Min   [N <: Nat]                                = N
  override protected type EqualH[L <: ::[_ <: Nat, _ <: List], F <: Bool] = False
}

trait TListFunctions {
  type map      [L <: List, F[_ <: Nat] <: Nat]                     = L#Map[F]
  type filter   [L <: List, F[_ <: Nat] <: Bool]                    = L#Filter[F]
  type fold     [L <: List, N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = L#Fold[N, F]
  type remove   [L <: List, N <: Nat]                               = L#Remove[N]
  type :::      [L <: List, R <: List]                              = L#Concat[R]
  type ===      [L <: List, R <: List]                              = L#Equal[R]
  type reverse  [L <: List]                                         = L#Reverse
  type sort     [L <: List]                                         = L#Sort
  type size     [L <: List]                                         = L#Size

  type isEmpty  [L <: List]                                         = size[L] == _0
  type nonEmpty [L <: List]                                         = ![isEmpty[L]]
  type reduceP  [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _0, F]
  type reduceM  [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, _1, F]
  type sum      [L <: List]                                         = L reduceP ({ type F[LN <: Nat, RN <: Nat] = RN + LN })#F
  type product  [L <: List]                                         = L reduceM ({ type F[LN <: Nat, RN <: Nat] = RN * LN })#F
  type count    [L <: List, F[_ <: Nat] <: Bool]                    = size[L filter F]
  type contains [L <: List, M <: Nat]                               = nonEmpty[L filter ({ type F[N <: Nat] = M == N })#F]
  type exists   [L <: List, F[_ <: Nat] <: Bool]                    = (L count F) > _0
  type filterNot[L <: List, F[_ <: Nat] <: Bool]                    = L filter ({ type FN[N <: Nat] = ![F[N]] })#FN
  type forall   [L <: List, F[_ <: Nat] <: Bool]                    = ![L exists ({ type FN[N <: Nat] = ![F[N]] })#FN]
}

object List extends TListFunctions
