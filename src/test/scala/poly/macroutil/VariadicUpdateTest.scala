package poly.macroutil

import org.scalatest._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
class VariadicUpdateTest extends FunSuite {


  class Test {
    @variadicUpdate def update(cs: Int*)(value: Double) = {
      println(s"update ${cs mkString " "} to $value")
    }
  }

  test("") {
    val a = new Test
    a(1, 2, 3, 4, 4, 4) = 0.2
    a(3, 4, 5, 6) = 0.2
  }
}
