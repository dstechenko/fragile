package fragile
package function

import language.higherKinds

sealed trait Function[A, B] {
  type Apply[_ <: A] <: B
}
