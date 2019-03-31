package com.fp

object CoMonad {

  def apply[F[_]: CoMonad]: CoMonad[F] = implicitly[CoMonad[F]]

  implicit class CoMonadOps[A, F[_]: CoMonad](fa: F[A]) {
    def duplicate: F[F[A]]  = CoMonad[F].duplicate(fa)
    def coflatMap[B](f: F[A] => B): F[B] = CoMonad[F].coflatMap(fa)(f)
  }
}

trait CoMonad[F[_]] {
  // pure's dual
  def extract[A](fa: F[A]): A

  // also called coflatten
  def duplicate[A](fa: F[A]): F[F[A]]

  // flatMap's dual
  def coflatMap[A, B](fa: F[A])(f: F[A] => B): F[B]
}

