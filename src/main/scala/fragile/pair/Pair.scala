package fragile.pair

sealed trait Pair[T] {
  type FST <: T
  type SND <: T
}
