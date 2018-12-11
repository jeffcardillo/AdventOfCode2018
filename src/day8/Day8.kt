package code.day8

import java.io.File
import java.io.FileInputStream

class Day8 {

    lateinit var values: MutableList<Int>
    lateinit var root: TreeNode

    var metaSum = 0

    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 8 - Part A")

        values = readInputFile()

        readNode(null)

        println("\t* Metadata sum: $metaSum")
    }

    fun partB() {
        println("Day 8 - Part B")

        values.clear()
        values = readInputFile()

        readNode(null)

        println("\t* Root Value: ${root.calculateValue()}")

    }

    private fun readNode(parent: TreeNode?) {
        val child = values.removeAt(0)
        val meta = values.removeAt(0)

        val node = TreeNode()

        if (parent == null) {
            root = node
        }

        parent?.children?.add(node)

        for (i in 1..child) {
            readNode(node)
        }

        for (i in 1..meta) {
            val m = values.removeAt(0)
            metaSum += m
            node.metadata.add(m)
        }
    }

    private fun readInputFile(): MutableList<Int> {
        val inputStream: FileInputStream = File("./src/day8/day8_input.txt").inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }

        return lineList[0].split(" ").map { Integer.parseInt(it) }.toMutableList()
    }
}