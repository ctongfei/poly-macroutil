package poly.macroutil

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object TypeCastTest extends App {

  import poly.macroutil.TypeCast._

  class A
  class B extends A
  trait C
  class D extends A with C

  val a = new A
  val b = new B
  val c = new C {}
  val d = new D

  assert(a.is[A])
  assert(!a.is[B])
  assert(!a.is[C])
  assert(!a.is[D])
  assert(b.is[A])
  assert(b.is[B])
  assert(!b.is[C])
  assert(!b.is[D])



}
