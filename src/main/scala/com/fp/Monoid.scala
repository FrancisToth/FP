package com.fp

object Monoid {
  def apply[M: Monoid]: Monoid[M] = implicitly[Monoid[M]]

  implicit class MonoidOps[M: Monoid: SemiGroup](m: M) {
    def zero: M = Monoid[M].zero
    def |+|(m1: M): M = SemiGroup[M].|+|(m, m1)
  }
}

trait Monoid[M] {
  def zero: M
}

