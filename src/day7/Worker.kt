package code.day7

class Worker (val name: String, var workingOnLabel: String = "", var busyUntil: Int = 0) {
    override fun toString(): String = "$name $workingOnLabel $busyUntil"
}
