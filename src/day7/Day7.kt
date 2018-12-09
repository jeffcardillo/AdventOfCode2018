package code.day7

import java.io.File
import java.io.FileInputStream

class Day7 {

    private val nodeMap = LinkedHashMap<String, Node>()
    private var dependencies = mutableListOf<String>()

    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 7 - Part A")

        populateNodeMap()
        printNodeMap()

        addStartNodes()

        var traversedNodes: String = ""
        while (!dependencies.isEmpty()) {
            loop@ for (index in 0..(dependencies.size - 1)) {
                // get first dependency
                val depKey = dependencies[index]
                val node = nodeMap[depKey]

                // consume if no dependencies
                if (node?.dependencies!!.isEmpty()) {
                    for (n in allDependedOnMe(node)) {
                        if (!dependencies.contains(n.label)) {
                            dependencies.add(n.label)
                        }
                    }

                    dependencies.remove(node.label)
                    dependencies.sort()

                    traversedNodes += node.label
                    consumeNode(node)
                    break@loop
                }
            }
        }

        println("\t* Traversed node order: $traversedNodes")
    }

    private fun printNodeMap() {
        // output structure for sanity
        for (key in nodeMap.keys) {
            print("\t$key --> ( ")
            for (node in nodeMap[key]?.dependencies!!) {
                print("${node.label} ")
            }
            println(")")
        }
    }

    private fun populateNodeMap() {
        val fileValuesList = readInputFile()

        nodeMap.clear()

        fileValuesList.forEach {
            val stepLabel = getStep(it)
            val dependencyLabel = getDependency(it)
            val step = nodeMap[stepLabel] ?: Node(stepLabel)
            val dependency = nodeMap[dependencyLabel] ?: Node(dependencyLabel)

            step.addDependency(dependency)

            // if we created new objects, make sure they are in node map
            if (!nodeMap.containsKey(stepLabel)) {
                nodeMap[stepLabel] = step
            }

            if (!nodeMap.containsKey(dependencyLabel)) {
                nodeMap[dependencyLabel] = dependency
            }
        }
    }

    private fun consumeNode(node: Node) {
        // remove dependency from all remaining nodes
        for (n in nodeMap.values) {
            n.removeNodeFromDependencies(node.label)
        }

        nodeMap.remove(node.label)
    }

    private fun addStartNodes() {
        // no other node should depend on it. can be more than one
        for (node in nodeMap.values) {
            if (node.dependencies.isEmpty()) {
                dependencies.add(node.label)
            }
        }

        dependencies.sort()
    }

    private fun allDependedOnMe(node: Node) : List<Node> {
        val deps = mutableListOf<Node>()
        loop@ for (n in nodeMap.values) {
            if (n.dependsOnNode(node)) {
                deps.add(n)
            }
        }

        return deps
    }

    fun partB() {
        println("Day 7 - Part B")

        populateNodeMap()
        addStartNodes()
        var traversedNodes: String = ""

        val workers = listOf(Worker("worker1"), Worker("worker2"), Worker("worker3"), Worker("worker4"), Worker("worker5"))

        var time = 0

        fun workerAvailable(): Worker? {
            return workers.find { it.busyUntil <= time }
        }

        fun isBeingWorkedOn(label: String) : Boolean {
            return workers.find { it.workingOnLabel == label } != null
        }

        fun completeWork(label: String) {
            val node = nodeMap[label] ?: return

            // consume if no dependencies
            for (n in allDependedOnMe(node)) {
                if (!dependencies.contains(n.label)) {
                    dependencies.add(n.label)
                }
            }

            dependencies.remove(node.label)
            dependencies.sort()

            traversedNodes += node.label
            consumeNode(node)
        }

        while (!dependencies.isEmpty()) {
            for (worker in workers) {
                if (worker.busyUntil <= time) {
                    completeWork(worker.workingOnLabel)
                }
            }

            loop@ for (index in 0..(dependencies.size - 1)) {
                val worker = workerAvailable() ?: break@loop

                val depKey = dependencies[index]
                val node = nodeMap[depKey]
                if (node?.dependencies!!.isEmpty() && !isBeingWorkedOn(depKey)) {
                    worker?.workingOnLabel = depKey
                    worker?.busyUntil = time + 60 + (node.label.single() - 64).toInt() // use char minus constant to get to A=1
                }
            }

            time++
        }

        println("\t* Time to complete: ${ workers.map { it.busyUntil }.max() ?: 0} Traversed node order: $traversedNodes")
    }

    private fun getStep(line: String) : String {
        val start = line.indexOf("must be finished before step ") + "must be finished before step ".length
        return line.substring(start, start + 1)
    }


    private fun getDependency(line: String) : String {
        val start = line.indexOf("Step ") + "step ".length
        return line.substring(start, start + 1)
    }

    private fun readInputFile(): List<String> {
        val inputStream: FileInputStream = File("./src/day7/day7_input.txt").inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }

        return lineList
    }
}