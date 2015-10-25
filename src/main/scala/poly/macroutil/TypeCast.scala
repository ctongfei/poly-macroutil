package poly.macroutil

import scala.language.experimental.macros
import scala.reflect.macros.blackbox._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object TypeCast {

  implicit class withTypeCastOps[T](x: T) {

    /** An alias of `asInstanceOf`. */
    def as[U]: U = macro asImpl[U]

    /** An alias of `isInstanceOf`. */
    def is[U]: Boolean = macro isImpl[U]

  }

  def asImpl[U](c: Context): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$implicitConversion($x).as[$typ]" => q"$x.asInstanceOf[$typ]"
    }
  }

  def isImpl[U](c: Context): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$implicitConversion($x).is[$typ]" => q"$x.isInstanceOf[$typ]"
    }
  }

}
