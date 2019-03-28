package com.fp

object YCombinator extends App {

  // TODO: Add Mu
  // TODO: Add explanations

  def factorial0(n: Int): Int =
    if(n == 0) 1 else n * factorial0(n - 1)

  def almostFactorial(f: Int => Int)(n: Int): Int =
    if(n == 0) 1 else n * f(n - 1)

  // lazy version
  // def Y[A](f: A => A): A = f(Y(f))

  // strict version
  // def Y[A](f: (A => A) => (A => A)): A => A = f(a => Y(f)(a))
  // def Y[A](f: (A => A) => (A => A)): A => A = a => f(Y(f))(a)

  case class H[A, B](h: H[A, B] => A => B) {
    def apply(h: H[A, B]): A => B = a => this.h(h)(a)
  }

  def partFactorial(h: H[Int, Int]): Int => Int = {
    n => if(n == 0) 1 else n * h(h)(n - 1)
  }

  def factorial1 = partFactorial(H(partFactorial))

  // Y combinator (applicative order)
  def Y[A, B](f: (A => B) => A => B): A => B = {
    val h = H[A, B](h => f(h(h)))
    h(h)
  }

  def factorial2: Int => Int = Y(almostFactorial)

  println(factorial2(0))
  println(factorial2(1))
  println(factorial2(3))

  /*

  FACT = (λfac (
    λn.IF (= n 0) 1
          (* n (fac (- n 1)))
    )
  )) FACT

   Y H = H (Y H)

   */
}
