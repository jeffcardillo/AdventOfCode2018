package code.day8

class TreeNode {
    var children: MutableList<TreeNode> = mutableListOf()
    var metadata: MutableList<Int> = mutableListOf()

    fun calculateValue() : Int {
        return if (children.isEmpty()) {
            metadata.sum()
        } else {
            var sum = 0
            for (i in metadata) {
                var index = Math.max((i-1), 0)
                if (index < children.size) {
                    sum += children[index].calculateValue()
                }
            }

            sum
        }
    }
}