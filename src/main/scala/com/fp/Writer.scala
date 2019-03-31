package com.fp

import Monoid._

object Writer {

  type WriterF[W] = { type L[A] = Writer[A, W] }

  implicit def writerFunctor[W]: Functor[WriterF[W]#L] = new Functor[WriterF[W]#L] {
    override def fmap[A, B](w: Writer[A, W])(f: A => B): Writer[B, W] =
      Writer(f(w.value), w.log)
  }

  implicit def writerMonad[W: Monoid: SemiGroup]: Monad[WriterF[W]#L] = new Monad[WriterF[W]#L] {
    override def unit[A](a: A): Writer[A, W] =
      Writer(a, Monoid[W].zero)


    override def join[A](wwa: Writer[Writer[A, W], W]): Writer[A, W] =
      Writer(wwa.value.value, wwa.log |+| wwa.value.log)
  }

  def tell[W: Monoid](w: W): Writer[Unit, W] = Writer((), w)
}

case class Writer[A, W](value: A, log: W)

