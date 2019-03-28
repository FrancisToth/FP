package com.fp

import Functor._
import Monad._

object TwoParametersFunctor extends App{

  val foo: Reader[String, Int] = Reader[String, Int](s => s.length)

  val i = foo.fmap(_ + 1)

  println(i.run("Test"))
  println(i.run("Hello World"))



//  def foo[F[_], A](fa: F[A]): String = fa.toString()
//
//  foo[Int ] { x: Int => x * 2 }

}

/*

scala> def foo[F[_], A](fa: F[A]): String = fa.toString
foo: [F[_], A](fa: F[A])String

scala> foo { x: Int => x * 2 }
res9: String = $$Lambda$4464/837760512@31d58184

 */