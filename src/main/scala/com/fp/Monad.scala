package com.fp

object Monad {
  def apply[M[_] : Monad]: Monad[M] = implicitly[Monad[M]]

  implicit class MonadOps[A, M[_] : Monad: Functor](ma: M[A]) {
    def flatMap[B](f: A => M[B]): M[B] = Monad[M].flatMap(ma)(f)
  }
}

trait Monad[M[_]] {

  def unit[A](a: A): M[A]

  def join[A](fa: M[M[A]]): M[A]

  def flatMap[A, B](ma: M[A])(f: A => M[B])(implicit F: Functor[M]): M[B] =
    join(F.fmap(ma)(f))
}
