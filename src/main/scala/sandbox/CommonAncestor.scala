package sandbox

import com.typesafe.scalalogging._
import scala.io.Source

object CommonAncestor extends App with LazyLogging {

  case class Tree(name: String, parent: Option[Tree])
  case class UseCase(map: Map[String, Tree], var e1: String, var e2: String)

  object Tree {
    def build(source: Source): UseCase = {
      val lines = source.getLines
      val n = lines.take(1).toList(0).toInt // number of manager-employee pairs in the list	  	
      //logger.debug(s"Number of manager-employee: $n") // some logging

      val e1 = lines.take(1).toList(0) // first employee
      val e2 = lines.take(1).toList(0) // second employee
      //logger.debug(s"Target names: ($e1-$e2)") // some logging

      import scala.collection.mutable.{ HashMap => MutableMap }
      val map = MutableMap.empty[String, Tree] // map to speed up tree creation

      def link(m: String, e: String): Unit = {
        map(m) = map.getOrElse(m, Tree(m, None))
        map(e) = map.getOrElse(e, Tree(e, Some(map(m))))
        //logger.debug(s"$m -> $e") // some logging
      }
      lines.take(n).toList.map(_.split(" ")).map(x => link(x(0), x(1)))

      UseCase(map.toMap, e1, e2)
    }

    def find(map: Map[String, Tree], e1: String, e2: String) = {

      import scala.annotation.tailrec
      @tailrec
      def hierarchy(tree: Tree, list: List[String]): List[String] = tree.parent match {
        case None    => list
        case Some(x) => hierarchy(x, list ++ List(x.name))
      }

      val h1 = hierarchy(map(e1), List(e1))
      val h2 = hierarchy(map(e2), List(e2))
      h1.intersect(h2).head
    }
  }

	val source = io.Source.stdin
  val useCase = Tree.build(source)
  val ancestor = Tree.find(useCase.map, useCase.e1, useCase.e2)
  println(ancestor)
}
