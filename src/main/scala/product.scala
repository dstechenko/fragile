import Nat._

trait ProductSyntax {
  type Product[A <: Nat] = { type Arity = A }
  type product2[A1 <: Nat, A2 <: Nat] = Product[_2] { type P1 = A1; type P2 = A2 }
}

object Product extends ProductSyntax
