package fragile
package list

import bool._
import int._
import int.syntax._
import product._

import language.higherKinds

sealed trait List {
  type FlatMap                  [F[_ <: Int] <: List]                    <: List
  type FoldRight                [N <: Int, F[_ <: Int, _ <: Int] <: Int] <: Int
  type IndexOf                  [N <: Int, I <: Int]                     <: Int
  type Concat                   [L <: List]                              <: List
  type Reverse                                                           <: List
  type Distinct                                                          <: List
  type SelectSort                                                        <: List
  type QuickSort                                                         <: List
  type Size                                                              <: Int
  type TakeLeft                 [N <: Int]                               <: List
  type DropWhile                [F[_ <: Int] <: Bool]                    <: List
  type ApplyOrElse              [N <: Int, E <: Int]                     <: Int

  protected type This                                                    <: List
  protected[list] type MinOrElse[N <: Int]                               <: Int
}

sealed trait ::[H <: Int, T <: List] extends List {
  override type FlatMap                  [F[_ <: Int] <: List]                    = F[Head] ::: Tail#FlatMap[F]
  override type FoldRight                [N <: Int, F[_ <: Int, _ <: Int] <: Int] = F[Head, Tail#FoldRight[N, F]]
  override type IndexOf                  [N <: Int, I <: Int]                     = ifI[Head == N, I + `1`, Tail#IndexOf[N, I + `1`]]
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
                                                                                      type separate[N <: Int] = N < pivot
                                                                                      type separated          = Tail partition separate
                                                                                      type left               = firstL[separated]#QuickSort
                                                                                      type right              = secondL[separated]#QuickSort
                                                                                      type run                = left ::: pivot :: right
                                                                                    })#run

  override type Size                                                              = inc[Tail#Size]
  override type TakeLeft                 [N <: Int]                               = ifL[N == `0`,  Nil, Head :: Tail#TakeLeft[N - `1`]]
  override type ApplyOrElse              [N <: Int, E <: Int]                     = ifI[N == `0`, E, ifI[N == `1`, Head, Tail#ApplyOrElse[N - `1`, E]]]

  override protected type This                                                    = Head :: Tail
  override protected[list] type MinOrElse[N <: Int]                               = Tail#MinOrElse[Head min N]

  private type Head                                                               = H
  private type Tail                                                               = T
}

sealed trait Nil extends List {
  override type FlatMap                  [F[_ <: Int] <: List]                    = This
  override type FoldRight                [N <: Int, F[_ <: Int, _ <: Int] <: Int] = N
  override type IndexOf                  [N <: Int, I <: Int]                     = `-1`
  override type Concat                   [L <: List]                              = L
  override type Reverse                                                           = This
  override type Distinct                                                          = This
  override type SelectSort                                                        = This
  override type QuickSort                                                         = This
  override type Size                                                              = `0`
  override type TakeLeft                 [N <: Int]                               = This
  override type ApplyOrElse              [N <: Int, E <: Int]                     = E

  override protected type This                                                    = Nil
  override protected[list] type MinOrElse[N <: Int]                               = N
}
