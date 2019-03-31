package com.fp

trait SemiGroup[S] {
  def |+|(s1: S, s2: S): S
}

object SemiGroup {
  def apply[S: SemiGroup]: SemiGroup[S] = implicitly[SemiGroup[S]]

  implicit class SemiGroupOps[S: SemiGroup](s: S) {
    def |+|(s1: S): S = SemiGroup[S].|+|(s, s1)
  }
}
