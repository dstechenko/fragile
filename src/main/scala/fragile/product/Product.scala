package fragile.product

sealed trait Product[T] {
  type First   <: T
  type Second  <: T
}
