package com.fp

object Monad {
  def apply[F[_] : Monad]: Monad[F] = implicitly[Monad[F]]

  implicit class MonadOps[A, F[_] : Monad](fa: F[A]) {
    def flatMap[B](f: A => F[B]): F[B] = Monad[F].flatMap(fa)(f)
  }

}

trait Monad[F[_]] {

  implicit val F: Functor[F]

  def unit[A](a: A): F[A]

  def join[A](fa: F[F[A]]): F[A]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] =
    join(F.fmap(fa)(f))
}
