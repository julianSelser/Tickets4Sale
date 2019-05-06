package tickets.core

object Genres extends Enumeration {
    protected case class Val(price: Int) extends super.Val
    implicit def valueToGenreVal(x: Value): Val = x.asInstanceOf[Val]

    val musical = Val(70)
    val comedy  = Val(50)
    val drama   = Val(40)
}
