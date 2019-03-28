package com.fp

//import scala.language.{higherKinds, implicitConversions, reflectiveCalls}

//object Monad {
//  def apply[F[_] : Monad]: Monad[F] = implicitly[Monad[F]]
//
//  implicit class MonadOps[A, F[_] : Monad](fa: F[A]) {
//    def flatMap[B](f: A => F[B]): F[B] = Monad[F].flatMap(fa)(f)
//  }
//
//}

//trait Monad[F[_]] {
//
//  implicit val F: Functor[F]
//
//  def unit[A](a: A): F[A]
//
//  def join[A](fa: F[F[A]]): F[A]
//
//  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] =
//    join(F.fmap(fa)(f))
//}

//trait Functor[F[_]] {
//  def fmap[A, B](fa: F[A])(f: A => B): F[B]
//}
//
//object Functor {
//  def apply[F[_] : Functor]: Functor[F] = implicitly[Functor[F]]
//
//  implicit class Ops[A, F[_]](fa: F[A])(implicit F: Functor[F]) {
//    def fmap[B](f: A => B): F[B] = F.fmap(fa)(f)
//  }
//}
//
//case class Reader[R, A](run: R => A)
//
//object Reader {
//  def ask[R]: Reader[R, R] = Reader(r => r)
//
//  implicit def FunctorReader[E]: Functor[({type T[A] = Reader[E, A]})#T] =
//    new Functor[({type T[A] = Reader[E, A]})#T] {
//      override def fmap[A, B](fa: Reader[E, A])(f: A => B): Reader[E, B] =
//        Reader(r => f(fa.run(r)))
//    }
//}

//  implicit def MonadReader[E](implicit fr: Functor[Reader[E, _]]) =
//    new Monad[({type T[A] = Reader[E, A]})#T] {
//
//      override implicit val F = fr
//
//      override def unit[A](a: A): Reader[E, A] = Reader(_ => a)
//
//      override def join[A](fa: Reader[E, Reader[E, A]]): Reader[E, A] = Reader {
//        r => fa.run(r).run(r)
//      }
//    }
//}


//object T2 extends App {
//
//  trait Functor[F[_]] {
//    def map[A, B](x: F[A])(f: A => B): F[B]
//  }
//
//  implicit class Ops[F[_]: Functor, A](fa: F[A]) {
//    def map[B](f: A => B): F[B] = implicitly[Functor[F]].map(fa)(f)
//  }
//
////  type FF[A] = ({ type F[B] = A => B })
////  implicit def fff[A, B](f: FF[A]#F[B]): Ops[FF[A]#F, B] = new Ops[FF[A]#F, B](f)
//
//  trait Foo[A] {
//    type B
//    def value: B
//  }
//
//  object Foo {
//    type Aux[A0, B0] = Foo[A0] {type B = B0}
//  }
//
//  def foo[T, R](t: T)(implicit f: Foo.Aux[T, R]): R = m.zero
//
////  implicit def fff2[A, B](f: TwoL[A]): Ops[TwoL[A]#T, B] = new Ops[TwoL[A]#T, B](f#T)
////  implicit def ff2[A, B](f: FF[A]): Ops[FF, A] = new Ops(f)
//
//
//  implicit def ff[E]: Functor[FF[E]#F] = new Functor[FF[E]#F] {
//    override def map[A, B](x: E => A)(f: A => B): E => B = e => f(x(e))
//  }
//
//  val f: String => Int = _ => 42
//
//  val value: Functor[FF[String]#F] = ff[String]
//  val ops = new Ops[FF[String]#F, Int](f)(value)
//
//  ops.map(_ + 1)("")
//  value.map(f)(_ + 1)("")
//
//  println(f.map(_ + 1)(""))



//  type Two[A] = ({ type F[E] = (E, A) })#F[_]
//  implicit def Tuple2IsFunctor[X]: Functor[Two] = new Functor[Two] {
//    override def map[A, B](x: Two[A])(f: A => B): Two[B] = (x._1, f(x._2))
//  }

//  case class Reader[E, A](run: E => A)
////  type ReaderF[E] = ({ type F[A] = Reader[E, A] })
//  type ReaderF[A] = Reader[_, A]
//
//  implicit def readerF[E]: Functor[ReaderF] = new Functor[ReaderF] {
//    override def map[A, B](fa: ReaderF[A])(f: A => B): ReaderF[B] = {
////      f.compose[E](fa.run)
//      Reader[E, B](e => f(fa.run(e)))
//    }
//  }

//  val a: Two[String] = ("a", "")
//  println(a.map(_ + 1))

//  println(ff[String].map(f)(_ + 1)(""))


//  f.map(_ + 1)

//  val r: ReaderF[Int] = Reader((_: String) => 42)
//  r.map(_ + 1)

//  new Ops2(r)(readerF[String])
//}

//object Scratchpad extends App {
//  trait Functor[F[_]] {
//    def fmap[A, B](fa: F[A])(f: A => B): F[B]
//  }
//
//  implicit class FunctorOps[F[_]: Functor, A](fa: F[A]) {
//    def fmap[B](f: A => B): F[B] = implicitly[Functor[F]].fmap(fa)(f)
//  }
//
//  case class Reader[E, A](run: E => A)
////  type ReaderF[A] = Reader[_, A]
//  type ReaderF[E] = ({ type F[A] = Reader[E, A] })
//
//  implicit def readerF[E]: Functor[ReaderF[E]#F] = new Functor[ReaderF[E]#F] {
////    override def fmap[A, B](fa: ReaderF[E]#F[A])(f: A => B): ReaderF[E]#F[B] =
////      throw new IllegalArgumentException("")
//    /*Reader[E, B](e => f(fa.run(e)))*/
//    override def fmap[A, B](fa: ReaderF[A]#F[A])(f: A => B): ReaderF[B]#F[B] = ???
//  }
//
////  val foo: ReaderF[Int] = Reader[Int, Int](_ => 42)
////  println(foo.fmap(_ + 1))
//
//  def bar[E, A, B](r: ReaderF[A])(f: A => B)(implicit F: Functor[ReaderF[A]]): ReaderF[B] =
//    F.fmap[A, B](r)(f)
//
////  println(bar(foo)(_ + 1))
//
////  println(foo.fmap)
//
//  //readerF[String].fmap(foo)(_ + 1)
////  new FunctorOps[ReaderF, Int](
////    Reader[String, Int](_ => 42)
////  ).fmap(_ + 1)
//
//  //foo.fmap(_ + 1) // does not compile
//}

//object Test extends App {
//  val reader = Reader[String, Int](_ => 42)
//  reader.fmap2(_ + 1)
//  FunctorReader.fmap(reader)(_ + 1)

//  reader.fmap2(_ + 1)

//  println(Functor[({type T[A] = Reader[String, A]})#T].fmap(reader)(_ + 1).run(""))

//  implicit val x = Functor[({type T[A] = Reader[String, A]})#T]
//  Ops(reader).fmap2(_ + 1)

//  val x = Ops[Int, ({type T[A] = Reader[String, A]})#T](reader).fmap(_ + 1)

//  println(x.run(""))

//  Reader[String, Int](_ => 42).map(_ + 1)
//}



object TwoParametersFunctor extends App{
  trait Functor[F[_]] {
    def fmap[A, B](fa: F[A])(f: A => B): F[B]
  }

  implicit class FunctorOps[F[_]: Functor, A](self: F[A]) {
    def fmap[B](f: A => B): F[B] = implicitly[Functor[F]].fmap(self)(f)
  }

  case class Reader[A, B](run: A => B)
  type ReaderF[X] = ({ type L[A] = Reader[X, A] })

  implicit def readerFunctors[E]: Functor[ReaderF[E]#L] =
    new Functor[ReaderF[E]#L] {
      override def fmap[A, B](fa: Reader[E, A])(f: A => B): Reader[E, B] =
        Reader(e => f(fa.run(e)))
    }

  val foo: Reader[String, Int] = Reader[String, Int](s => s.length)

  val i = foo.fmap(_ + 1)

  println(i.run("Test"))
  println(i.run("Hello World"))
}