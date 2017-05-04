package fragile
package bool

import nat._
import list._

import language.higherKinds

sealed trait Bool {
  type Not                    <: Bool
  type Or [B <: Bool]         <: Bool
  type If [T, C <: T, A <: T] <: T
}

sealed trait True extends Bool {
  override type Not                    = False
  override type Or [B <: Bool]         = True
  override type If [T, C <: T, A <: T] = C
}

sealed trait False extends Bool {
  override type Not                    = True
  override type Or [B <: Bool]         = B
  override type If [T, C <: T, A <: T] = A
}
