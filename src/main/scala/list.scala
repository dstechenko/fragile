import language.higherKinds

import Bool._
import Nat._
import List._

sealed trait List {
  type Map           [F[_ <: Nat] <: Nat]                     <: List
  type Filter        [F[_ <: Nat] <: Bool]                    <: List
  type Fold          [Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] <: Nat
  type Remove        [N <: Nat]                               <: List
  type Concat        [L <: List]                              <: List
  type Equal         [L <: List]                              <: Bool
  type Reversed                                               <: List
  type Sorted                                                 <: List
  type Size                                                   <: Nat

  protected type This                                         <: List
  protected type Min [N <: Nat]                               <: Nat
}

sealed trait ::[H <: Nat, T <: List] extends List {
  override type Map           [F[_ <: Nat] <: Nat]                     = F[H] :: T#Map[F]
  override type Filter        [F[_ <: Nat] <: Bool]                    = ifl[F[H], H :: T#Filter[F], T#Filter[F]]
  override type Fold          [Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = F[H, T#Fold[Z, F]]
  override type Remove        [N <: Nat]                               = ifl[H == N, T, H :: T#Remove[N]]
  override type Concat        [L <: List]                              = H :: T#Concat[L]
  override type Equal         [L <: List]                              = True
  override type Reversed                                               = T#Reversed#Concat[H :: Nil]
  override type Sorted                                                 = T#Min[H] :: (H :: T)#Remove[T#Min[H]]#Sorted
  override type Size                                                   = Succ[T#Size] // Fix with `_ + _1``

  override protected type This                                         = H :: T
  override protected type Min [N <: Nat]                               = T#Min[H min N]
}

sealed trait Nil extends List {
  override type Map           [F[_ <: Nat] <: Nat]                     = Nil
  override type Filter        [F[_ <: Nat] <: Bool]                    = Nil
  override type Fold          [Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = Z
  override type Remove        [N <: Nat]                               = Nil
  override type Concat        [L <: List]                              = L
  override type Equal         [L <: List]                              = isEmpty[L]
  override type Reversed                                               = Nil
  override type Sorted                                                 = Nil
  override type Size                                                   = _0

  override protected type This                                         = Nil
  override protected type Min [N <: Nat]                               = N
}

trait TListFunctions {
  type map     [L <: List, F[_ <: Nat] <: Nat]                     = L#Map[F]
  type filter  [L <: List, F[_ <: Nat] <: Bool]                    = L#Filter[F]
  type fold    [L <: List, Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = L#Fold[Z, F]
  type remove  [L <: List, N <: Nat]                               = L#Remove[N]
  type :::     [L <: List, R <: List]                              = L#Concat[R]
  type ===     [L <: List, R <: List]                              = L#Equal[R]
  type reversed[L <: List]                                         = L#Reversed
  type sorted  [L <: List]                                         = L#Sorted
  type size    [L <: List]                                         = L#Size

  type isEmpty [L <: List]                                         = size[L] == _0
  type nonEmpty[L <: List]                                         = ![isEmpty[L]]
  type reduce  [L <: List, F[_ <: Nat, _ <: Nat] <: Nat]           = fold[L, Zero, F]
  type sum     [L <: List]                                         = L reduce ({ type F[N <: Nat, A <: Nat] = A + N })#F
}

object List extends TListFunctions
