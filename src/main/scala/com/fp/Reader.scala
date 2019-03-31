package com.fp

object Reader {

  type ReaderF[E] = {type L[A] = Reader[E, A]}

  implicit def readerFunctors[E]: Functor[ReaderF[E]#L] = new Functor[ReaderF[E]#L] {
    override def fmap[A, B](fa: Reader[E, A])(f: A => B): Reader[E, B] =
      Reader(e => f(fa.run(e)))
  }

  implicit def readerMonads[E]: Monad[ReaderF[E]#L] =
    new Monad[ReaderF[E]#L] {
      override def unit[A](a: A): Reader[E, A] =
        Reader(_ => a)

      override def join[A](fa: Reader[E, Reader[E, A]]): Reader[E, A] =
        Reader { e => fa.run(e).run(e) }
  }

  def ask[E]: Reader[E,E] = Reader(e => e)
}

case class Reader[E, A](run: E => A)