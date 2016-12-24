import Nat._

trait ProductSyntax {
  type Product [A <: Nat]               = { type Arity = A }
  type product2[A1 <: List, A2 <: List] = Product[_2] { type P1 = A1; type P2 = A2 }
  type ->      [A1 <: List, A2 <: List] = product2[A1, A2]
}

object Product extends ProductSyntax
