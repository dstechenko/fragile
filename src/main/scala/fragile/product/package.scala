package fragile

import fragile.nat._
import fragile.list._

package object product {
  type Product [A0 <: Nat]              =             { type Arity = A0            }
  type product2[A1 <: List, A2 <: List] = Product[_2] { type P1 = A1; type P2 = A2 }
  type ->      [A1 <: List, A2 <: List] = product2[A1, A2]
}
