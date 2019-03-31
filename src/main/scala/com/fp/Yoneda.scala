package com.fp

/*
  Yoneda provides a way to abstract and optimize a functor's
  abilities. In other words, Yoneda is a lazy functor: Instead
  of applying a sequence of functions to the functor through
  fmap, we compose those in one single function, and run the
  result once it is required. This is especially useful when
  several mapping functions need to be run on a List for
  example.

  https://bartoszmilewski.com/2013/05/15/understanding-yoneda/
  https://medium.com/@olxc/yoneda-and-coyoneda-trick-f5a0321aeba4
 */
abstract class Yoneda[F[_], A] { self =>
  // function to be applied when we call "run"
  // original fmap
  def transformation[B](f: A => B): F[B]

  def run: F[A] = transformation(identity)

  // interace 'map' to be used as functor
  def map[B](f: A => B): Yoneda[F, B] = new Yoneda[F, B] {
    def transformation[C](g: B => C): F[C] = self.transformation(g compose f)
  }
}

object Yoneda {
  def apply[F[_]: Functor, A](fa: F[A]): Yoneda[F, A] = new Yoneda[F, A] {
    override def transformation[B](f: A => B): F[B] = Functor[F].fmap(fa)(f)
  }

  def extract[F[_], A](lf: Yoneda[F, A]): F[A] = lf.run
}

object YonedaUsage extends App {

  val xs: List[Int] = List(1, 2, 3, 4)

  // very inefficient as this implies multiple traversal of the whole list.
  xs.map(_ + 1).map(_ + 1).map(_ + 1)

  // Instead let's delay and compose these mappings
  import Functor.std._ // import some common functors (like for List)
  val y: Yoneda[List, Int] = Yoneda(xs).map(_ + 1).map(_ + 1).map(_ + 1)

  val result = y.run
  println(result) // prints List(4, 5, 6, 7)
}

/*
  Coyoneda works almost the same than Yoneda, but relax the requirement
  for F[A] to be a functor when initialized. The need for a Functor's instance
  is delayed until the very last moment, when the resulting structure has
  to be evaluated.

  https://medium.com/@olxc/yoneda-and-coyoneda-trick-f5a0321aeba4
 */
sealed trait Coyoneda[F[_], A] {

  import Coyoneda._

  type UnderlyingType

  val underlyingValue: F[UnderlyingType]
  val transformation: UnderlyingType => A

  def fmap[B](f: A => B): Coyoneda[F, B] =
    lift(underlyingValue)(f compose transformation)

  def run(implicit F: Functor[F]): F[A] = F.fmap(underlyingValue)(transformation)
}

object Coyoneda {

  def apply[F[_], A](fa: F[A]): Coyoneda[F, A]  = lift(fa)(identity)

  def lift[F[_], A, B](fa: F[A])(f: A => B) : Coyoneda[F, B]  =
    new Coyoneda[F, B] {
      type UnderlyingType = A

      override val transformation: UnderlyingType => B = f
      override val underlyingValue: F[UnderlyingType] = underlyingValue
  }
}

object CoyonedaUsage extends App {

  case class Foo[A](a: A)
  val foo = Foo(42)

  // Notice that foo is not a Functor, and nothing requires it to be one (for now)
  val c: Coyoneda[Foo, Int] = Coyoneda(foo).fmap(_ + 1).fmap(_ + 2)

  // Because we'd like to evaluate the whole structure now, we need to make sure `Foo`
  // is actually a Functor. So let's define one:
  implicit val fooF: Functor[Foo] = new Functor[Foo] {
    override def fmap[A, B](fa: Foo[A])(f: A => B): Foo[B] = Foo(f(fa.a))
  }

  val result: Foo[Int] = c.run
  println(result) // prints Foo(45)
}