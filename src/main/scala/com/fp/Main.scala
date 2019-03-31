package com.fp

import Functor._
import Monad._

object Main extends App{

  val foo: Reader[String, Int] = Reader[String, Int](s => s.length)

  val reader = foo.fmap(_ + 1)

  foo.flatMap[Int](i => Reader(_ => i + 1))

//  Reader.ask[String].fmap()


  println(reader.run("Test"))
  println(reader.run("Hello World"))
}