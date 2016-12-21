import language.higherKinds

import Bool._

sealed trait TList {
  type Map[F[_ <: Nat] <: Nat] <: TList
  type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] <: Nat
  type Reduce[F[_ <: Nat, _ <: Nat] <: Nat] = Fold[Zero, F]
  type Filter[F[_ <: Nat] <: Bool] <: TList
}

sealed trait ::[H <: Nat, T <: TList] extends TList {
  override type Map[F[_ <: Nat] <: Nat] = F[H] :: T#Map[F]
  override type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = H F T#Fold[Z, F]
  override type Filter[F[_ <: Nat] <: Bool] = ifl[F[H], H :: T#Filter[F], T#Filter[F]]
}

sealed trait TNil extends TList {
  override type Map[F[_ <: Nat] <: Nat] = TNil
  override type Fold[Z <: Nat, F[_ <: Nat, _ <: Nat] <: Nat] = Z
  override type Filter[F[_ <: Nat] <: Bool] = TNil
}

trait TListFunctions {
  type map[L <: TList, F[_ <: Nat] <: Nat] = L#Map[F]
  type reduce[L <: TList, F[_ <: Nat, _ <: Nat] <: Nat] = L#Reduce[F]
  type filter[L <: TList, F[_ <: Nat] <: Bool] = L#Filter[F]
}

object TList extends TListFunctions
