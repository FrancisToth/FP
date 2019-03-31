package com.fp

trait Functor[F[_]] {
  def fmap[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  def apply[F[_] : Functor]: Functor[F] = implicitly[Functor[F]]

  implicit class Ops[A, F[_]](fa: F[A])(implicit F: Functor[F]) {
    def fmap[B](f: A => B): F[B] = F.fmap(fa)(f)
  }

  object std {
    implicit def listF: Functor[List] = new Functor[List] {
      override def fmap[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
    }
  }
}
