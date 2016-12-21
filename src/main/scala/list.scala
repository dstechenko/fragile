import language.higherKinds

import Bool._
import Nat._

sealed trait TList {
  type Map[F[_ <: Nat] <: Nat] <: TList
  type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] <: Nat
  type Reduce[F[_ <: Nat, _ <: Nat] <: Nat] = Fold[Zero, F]
  type Filter[F[_ <: Nat] <: Bool] <: TList
  type Remove[N <: Nat] <: TList
  type Sorted <: TList
  type Min[N <: Nat] <: Nat
  type Concat[L <: TList] <: TList
  type Reversed <: TList
  type This <: TList
  type IsEmpty <: Bool
  type NonEmpty = ![IsEmpty]
  type Equal[L <: TList] <: Bool
  type Sum = Reduce[({ type F[N <: Nat, A <: Nat] = A + N })#F]
}

sealed trait ::[H <: Nat, T <: TList] extends TList {
  override type Map[F[_ <: Nat] <: Nat]                      = F[H] :: T#Map[F]
  override type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = F[H, T#Fold[Z, F]]
  override type Filter[F[_ <: Nat] <: Bool]                  = ifl[F[H], H :: T#Filter[F], T#Filter[F]]
  override type Remove[N <: Nat]                             = ifl[H == N, T, H :: T#Remove[N]]
  override type Sorted                                       = T#Min[H] :: (H :: T)#Remove[T#Min[H]]#Sorted
  override type Min[N <: Nat]                                = T#Min[H min N]
  override type Concat[L <: TList]                           = H :: T#Concat[L]
  override type Reversed                                     = T#Reversed#Concat[H :: TNil]
  override type This                                         = H :: T
  override type IsEmpty                                      = False
  override type Equal[L <: TList]                            = True
}

sealed trait TNil extends TList {
  override type Map[F[_ <: Nat] <: Nat]                      = TNil
  override type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = Z
  override type Filter[F[_ <: Nat] <: Bool]                  = TNil
  override type Remove[N <: Nat]                             = TNil
  override type Sorted                                       = TNil
  override type Min[N <: Nat]                                = N
  override type Concat[L <: TList]                           = L
  override type Reversed                                     = TNil
  override type IsEmpty                                      = True
  override type This                                         = TNil
  override type Equal[L <: TList]                            = L#IsEmpty
}

trait TListFunctions {
  type map[L <: TList, F[_ <: Nat] <: Nat]              = L#Map[F]
  type reduce[L <: TList, F[_ <: Nat, _ <: Nat] <: Nat] = L#Reduce[F]
  type filter[L <: TList, F[_ <: Nat] <: Bool]          = L#Filter[F]
  type remove[L <: TList, N <: Nat]                     = L#Remove[N]
  type sorted[L <: TList]                               = L#Sorted
  type :::[L <: TList, R <: TList]                      = L#Concat[R]
  type reversed[L <: TList]                             = L#Reversed
  type sum[L <: TList]                                  = L#Sum
}

object TList extends TListFunctions
