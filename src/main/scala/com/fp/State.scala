package com.fp

object State {
  type State[S, A] = S => (A, S)

  def apply[S, A](r: State[S, A]): State[S, A] = (s: S) => r(s)

  def get[S]: State[S, S] = State(s => (s, s))
  def put[S](s: S): State[S, Unit] = State(_ => ((), s))

  type StateF[S] = { type L[A] = State[S, A] }

  implicit def stateFunctors[S]: Functor[StateF[S]#L] = new Functor[StateF[S]#L] {
    override def fmap[A, B](s: State[S, A])(f: A => B): State[S, B] = State { state =>
      val (a, newState) = s(state)
      (f(a), newState)
    }
  }

  implicit def stateMonads[S]: Monad[StateF[S]#L] = new Monad[StateF[S]#L] {
    override def unit[A](a: A): State[S, A] = State((a, _))

    override def join[A](ssa: State[S, State[S, A]]): State[S, A] = State { state =>
      val (sa, newState) = ssa(state)
      sa(newState)
    }
  }
}
