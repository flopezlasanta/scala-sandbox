package sandbox

import org.scalatest.{FunSuite, BeforeAndAfter}
import scala.io.Source

class MostPopularTests extends FunSuite with BeforeAndAfter {

	var source:Source = _

    before {
    	source = Source.fromString("2\nSun\nMoon\nRiver\nSun\nMountain\nSun\nSea\nMoon")
    }
    after {
    	source.close
    }

    test("test most popular word") {
    	val word = MostPopular.find(source)
    	assert("Sun" == word)
    }
    
    test("test most popular word with duplicates") (pending)
}