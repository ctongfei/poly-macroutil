package poly.macroutil

import scala.language.experimental.macros
import scala.reflect.macros.whitebox._
import scala.annotation._

// Credit to Shiva Wu: https://coderwall.com/p/_cyziw/varargs-update-support-in-scala
class variadicUpdate extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro VariadicUpdateImpl.impl
}

object VariadicUpdateImpl {

  private val updateVariadic = "updateVariadic"

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    def renameMethod(m: DefDef, newName: String): DefDef =
      DefDef(m.mods, TermName(newName), m.tparams, m.vparamss, m.tpt, m.rhs)

    val m = annottees(0).tree match {
      case m: DefDef => m
      case _ => c.abort(c.enclosingPosition, "@updateVariadic can only be applied to an update method")
    }
    if (m.name.decodedName.toString != "update") {
      c.abort(c.enclosingPosition, "@updateVariadic can only be applied to an update method")
    }
    if (m.vparamss.size != 2) {
      c.abort(c.enclosingPosition, "@updateVariadic-applied method must have two argument list")
    }
    if (m.vparamss(0).size == 0) {
      c.abort(c.enclosingPosition, "The first argument list of an @updateVariadic-applied method must not be empty")
    }
    if (m.vparamss(1).size != 1) {
      c.abort(c.enclosingPosition, "The second argument list of an @updateVariadic-applied method must have only one element")
    }

    c.Expr[Any] {
      q"""
        import scala.language.experimental.macros

        ${renameMethod(m, updateVariadic)}
        def update(values: Any*): Unit = macro VariadicUpdateImpl.transform
      """
    }
  }

  def transform(c: Context)(values: c.Tree*): c.Tree = {
    import c.universe._
    Apply(
      Apply(Select(c.prefix.tree, TermName(updateVariadic)), values.init.toList),
      List(values.last)
    )
  }
}
