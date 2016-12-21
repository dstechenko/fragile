import language.higherKinds

import Bool._
import Nat._

sealed trait List {
  type Map[F[_ <: Nat] <: Nat] <: List
  type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] <: Nat
  type Reduce[F[_ <: Nat, _ <: Nat] <: Nat] = Fold[Zero, F]
  type Filter[F[_ <: Nat] <: Bool] <: List
  type Remove[N <: Nat] <: List
  type Sorted <: List
  type Concat[L <: List] <: List
  type Reversed <: List
  type This <: List
  type IsEmpty <: Bool
  type NonEmpty = ![IsEmpty]
  type Equal[L <: List] <: Bool
  type Sum = Reduce[({ type F[N <: Nat, A <: Nat] = A + N })#F]
  protected type Min[N <: Nat] <: Nat
}

sealed trait ::[H <: Nat, T <: List] extends List {
  override type Map[F[_ <: Nat] <: Nat]                      = F[H] :: T#Map[F]
  override type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = F[H, T#Fold[Z, F]]
  override type Filter[F[_ <: Nat] <: Bool]                  = ifl[F[H], H :: T#Filter[F], T#Filter[F]]
  override type Remove[N <: Nat]                             = ifl[H == N, T, H :: T#Remove[N]]
  override type Sorted                                       = T#Min[H] :: (H :: T)#Remove[T#Min[H]]#Sorted
  override type Concat[L <: List]                            = H :: T#Concat[L]
  override type Reversed                                     = T#Reversed#Concat[H :: Nil]
  override type This                                         = H :: T
  override type IsEmpty                                      = False
  override type Equal[L <: List]                             = True
  override protected type Min[N <: Nat]                      = T#Min[H min N]
}

sealed trait Nil extends List {
  override type Map[F[_ <: Nat] <: Nat]                      = Nil
  override type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = Z
  override type Filter[F[_ <: Nat] <: Bool]                  = Nil
  override type Remove[N <: Nat]                             = Nil
  override type Sorted                                       = Nil
  override type Concat[L <: List]                            = L
  override type Reversed                                     = Nil
  override type IsEmpty                                      = True
  override type This                                         = Nil
  override type Equal[L <: List]                             = L#IsEmpty
  override protected type Min[N <: Nat]                      = N
}

trait TListFunctions {
  type map[L <: List, F[_ <: Nat] <: Nat]              = L#Map[F]
  type reduce[L <: List, F[_ <: Nat, _ <: Nat] <: Nat] = L#Reduce[F]
  type filter[L <: List, F[_ <: Nat] <: Bool]          = L#Filter[F]
  type remove[L <: List, N <: Nat]                     = L#Remove[N]
  type sorted[L <: List]                               = L#Sorted
  type :::[L <: List, R <: List]                       = L#Concat[R]
  type reversed[L <: List]                             = L#Reversed
  type sum[L <: List]                                  = L#Sum
}

object List extends TListFunctions
