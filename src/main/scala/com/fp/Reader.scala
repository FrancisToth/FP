package com.fp

object Reader {

  type ReaderF[X] = ({type L[A] = Reader[X, A]})

  implicit def readerFunctors[E]: Functor[ReaderF[E]#L] =
    new Functor[ReaderF[E]#L] {
      override def fmap[A, B](fa: Reader[E, A])(f: A => B): Reader[E, B] =
        Reader(e => f(fa.run(e)))
    }

//  implicit def readerMonads[E](implicit RF: Functor[ReaderF[E]]): Monad[ReaderF[E]#L] =
//    new Monad[ReaderF[E]#L] {
//
//      override implicit val F: Functor[ReaderF[E]#L] = RF
//
//    override def unit[A](a: A): Reader[E, A] = ???
//
//    override def join[A](fa: Reader[E, Reader[E, A]]): Reader[E, A] = ???
//  }


}

case class Reader[A, B](run: A => B)