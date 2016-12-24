package fragile.pair

sealed trait Pair[T] {
  type First  <: T
  type Second <: T
}
