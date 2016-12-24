package fragile.list

import fragile.bool._
import fragile.nat._

import language.higherKinds

sealed trait List {
  type Map                      [F[_ <: Nat] <: Nat]                     <: List
  type FlatMap                  [F[_ <: Nat] <: List]                    <: List
  type Filter                   [F[_ <: Nat] <: Bool]                    <: List
  type Fold                     [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] <: Nat
  type IndexOf                  [N <: Nat, I <: Nat]                     <: Nat
  type Remove                   [N <: Nat]                               <: List
  type Concat                   [L <: List]                              <: List
  type Reverse                                                           <: List
  type Distinct                                                          <: List
  type SelectSort                                                        <: List
  type QuickSort                                                         <: List
  type Size                                                              <: Nat
  type TakeLeft                 [N <: Nat]                               <: List
  type DropWhile                [F[_ <: Nat] <: Bool]                    <: List
  type ApplyOrElse              [N <: Nat, E <: Nat]                     <: Nat

  // type PadTo                    [N <: Nat, E <: Nat]                     = (N :: This)#PadTo[N, E - _1]

  protected type This                                                    <: List
  protected[list] type MinOrElse[N <: Nat]                               <: Nat
}

sealed trait ::[H <: Nat, T <: List] extends List {
  override type Map                      [F[_ <: Nat] <: Nat]                     = F[Head]  ::     Tail#Map[F]
  override type FlatMap                  [F[_ <: Nat] <: List]                    = F[Head] ::: Tail#FlatMap[F]
  override type Filter                   [F[_ <: Nat] <: Bool]                    = ifL[F[Head], list[Head], Nil] ::: Tail#Filter[F]
  override type Fold                     [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = F[Head, Tail#Fold[N, F]]
  override type IndexOf                  [N <: Nat, I <: Nat]                     = ifN[Head == N, I + _1, Tail#IndexOf[N, I + _1]]
  override type Remove                   [N <: Nat]                               = ifL[Head == N, Tail, Head :: Tail#Remove[N]]
  override type Concat                   [L <: List]                              = Head :: Tail#Concat[L]
  override type Reverse                                                           = Tail#Reverse ::: list[Head]

  override type Distinct                                                          = ({
                                                                                      type isInTail     = Tail contains Head
                                                                                      type tailDistinct = Tail#Distinct
                                                                                      type prefix       = ifL[isInTail, Nil, list[Head]]
                                                                                      type run          = prefix ::: tailDistinct
                                                                                    })#run

  override type SelectSort                                                        = ({
                                                                                      type selected = Tail minOrElse Head
                                                                                      type unsorted = This remove selected
                                                                                      type run      = selected :: unsorted#SelectSort
                                                                                    })#run

  override type QuickSort                                                         = ({
                                                                                      type pivot              = Head
                                                                                      type separate[N <: Nat] = N < pivot
                                                                                      type separated          = Tail partition separate
                                                                                      type left               = separated#FST#QuickSort
                                                                                      type right              = separated#SND#QuickSort
                                                                                      type run                = left ::: pivot :: right
                                                                                    })#run

  override type Size                                                              = Succ[Tail#Size]
  override type TakeLeft                 [N <: Nat]                               = ifL[N == _0,  Nil, Head :: Tail#TakeLeft[N - _1]]
  override type ApplyOrElse              [N <: Nat, E <: Nat]                     = ifN[N == _0, E, ifN[N == _1, Head, Tail#ApplyOrElse[N - _1, E]]]

  override protected type This                                                    = Head :: Tail
  override protected[list] type MinOrElse[N <: Nat]                               = Tail#MinOrElse[Head min N]

  protected type Head                                                             = H
  protected type Tail                                                             = T
}

sealed trait Nil extends List {
  override type Map                      [F[_ <: Nat] <: Nat]                     = This
  override type FlatMap                  [F[_ <: Nat] <: List]                    = This
  override type Filter                   [F[_ <: Nat] <: Bool]                    = This
  override type Fold                     [N <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = N
  override type IndexOf                  [N <: Nat, I <: Nat]                     = _0
  override type Remove                   [N <: Nat]                               = This
  override type Concat                   [L <: List]                              = L
  override type Reverse                                                           = This
  override type Distinct                                                          = This
  override type SelectSort                                                        = This
  override type QuickSort                                                         = This
  override type Size                                                              = _0
  override type TakeLeft                 [N <: Nat]                               = This
  override type ApplyOrElse              [N <: Nat, E <: Nat]                     = E

  override protected type This                                                    = Nil
  override protected[list] type MinOrElse[N <: Nat]                               = N
}
