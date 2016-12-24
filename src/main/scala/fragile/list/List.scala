package fragile.list

import fragile.bool._
import fragile.nat._

import language.higherKinds

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
  override type Map           [F[_ <: Nat] <: Nat]                     = F[Head] :: Tail#Map[F]
  override type FlatMap       [F[_ <: Nat] <: List]                    = F[Head] ::: Tail#FlatMap[F]
  override type Filter        [F[_ <: Nat] <: Bool]                    = ifL[F[Head], list[Head], Nil] ::: Tail#Filter[F]
  override type Fold          [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = F[Head, Tail#Fold[N, F]]
  override type IndexOf       [N <: Nat, I <: Nat]                     = ifN[Head == N, I + _1, Tail#IndexOf[N, I + _1]]
  override type Remove        [N <: Nat]                               = ifL[Head == N, Tail, Head :: Tail#Remove[N]]
  override type Concat        [L <: List]                              = Head :: Tail#Concat[L]
  override type Reverse                                                = Tail#Reverse#Concat[list[Head]]
  override type Distinct                                               = ifL[Tail contains Head, Tail#Distinct, Head :: Tail#Distinct]
  override type Sort                                                   = Tail#Min[Head] :: (Head :: Tail)#Remove[Tail#Min[Head]]#Sort
  override type Size                                                   = Succ[Tail#Size]
  override type TakeLeft      [N <: Nat]                               = ifL[isZero[N],  Nil, Head :: Tail#TakeLeft[N - _1]]
  override type ApplyOrElse   [N <: Nat, E <: Nat]                     = ifN[N == _0, E, ifN[N == _1, Head, Tail#ApplyOrElse[N - _1, E]]]

  override protected type This                                         = Head :: Tail
  override protected type Min [N <: Nat]                               = Tail#Min[Head min N]

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
