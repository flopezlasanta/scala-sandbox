package sandbox

import org.scalatest.{ FunSuite, BeforeAndAfter }
import scala.io.Source

class CommonAncestorTests extends FunSuite with BeforeAndAfter {

  var source: Source = _

  before {
    source = Source.fromString("5\nAdam\nZoe\nJohn Amy\nJohn Peter\nPeter Zoe\nPeter Phil\nPhil Adam")
  }
  after {
    source.close
  }

  test("test build use case structure (map, target1, target2)") {
    val useCase = CommonAncestor.Tree.build(source)

    assert(useCase != null)
    assert(useCase.map != null)
    assert(useCase.e1 == "Adam")
    assert(useCase.e2 == "Zoe")
  }

  test("test find common ancestor immediate relation") {
    val useCase = CommonAncestor.Tree.build(source)
    useCase.e1 = "Phil"
    useCase.e2 = "Adam"
    val ancestor = CommonAncestor.Tree.find(useCase.map, useCase.e1, useCase.e2)

    assert(ancestor != null)
    assert(ancestor == "Phil")
  }

  test("test find common ancestor immediate descendants") {
    val useCase = CommonAncestor.Tree.build(source)
    useCase.e1 = "Amy"
    useCase.e2 = "Peter"
    val ancestor = CommonAncestor.Tree.find(useCase.map, useCase.e1, useCase.e2)

    assert(ancestor != null)
    assert(ancestor == "John")
  }

  test("test find common ancestor different level descendants") {
    val useCase = CommonAncestor.Tree.build(source)
    useCase.e1 = "Adam"
    useCase.e2 = "Zoe"
    val ancestor = CommonAncestor.Tree.find(useCase.map, useCase.e1, useCase.e2)

    assert(ancestor != null)
    assert(ancestor == "Peter")
  }
}