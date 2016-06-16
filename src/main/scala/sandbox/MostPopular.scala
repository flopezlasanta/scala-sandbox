package sandbox

import com.typesafe.scalalogging._

// count words and then get the most popular; the big data approach is based on tuples (word, 1)
object MostPopular extends App with LazyLogging {

	import scala.io.Source
	def find(source: Source) = {
		val lines = source.getLines

	  	val n = lines.take(1).toList(0).toInt // number of items in the list	  	
	  	logger.debug(s"Number of lines: $n") // some logging

		import scala.collection.mutable.{HashMap => MutableMap}
		val mem = MutableMap.empty[String, Int].withDefaultValue(0) // map to store counter per word

		lines.take(n).toList.map(mem(_) += 1) // read word in the line, update mem
		val popular = mem.toList.map(_.swap).sorted.reverse // tuples (word, count) ordered by count (reversed)
		
		popular.foreach { case (k,v) => logger.debug(s"$k -> $v") } // some logging		
		popular.head._2 // return most popular (note: assumes no duplicates)
	}

	val source = io.Source.stdin
	print(find(source))
}