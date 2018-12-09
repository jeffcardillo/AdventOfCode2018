package code.day7

class Node (var label: String) {

    var dependencies: MutableList<Node> = mutableListOf()

    fun addDependency(dependency: Node) {
        dependencies.add(dependency)
        val cmp = compareBy<Node> { it.label }
        dependencies = dependencies.asSequence().sortedWith( cmp ).toMutableList()
    }

    fun removeNodeFromDependencies(label: String) {
        val node = dependencies.find { it.label == label }
        dependencies.remove(node)
    }

    fun dependsOnNode(node: Node) : Boolean {
        val node = dependencies.find { it.label == node.label }
        return node != null
    }
}