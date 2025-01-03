import kotlin.math.abs

fun main() {

    fun List<Int>.isAscending(): Boolean {
        var ok = true
        forEachIndexed { index, i ->
            if (index < lastIndex) {
                if (i > this[index + 1]) {
                    ok = false
                    return@forEachIndexed
                }
            }
        }
        return ok
    }

    fun List<Int>.isDescending(): Boolean {
        var ok = true
        forEachIndexed { index, i ->
            if (index < lastIndex) {
                if (i < this[index + 1]) {
                    ok = false
                    return@forEachIndexed
                }
            }
        }
        return ok
    }

    fun List<Int>.isLegal(): Boolean {
        var ok = true
        forEachIndexed { index, i ->
            if (index < lastIndex) {
                val diff = abs(i - this[index + 1])
                if (diff !in 1..3) {
                    ok = false
                    return@forEachIndexed
                }
            }
        }
//        "$this is Legal: $ok".println()
        return ok
    }

    fun List<Int>.isReportLegal(): Boolean {
        var ok = (isAscending() || isDescending()) && isLegal()

        if (!ok) { // Check if we can remove one and make it legal then
            for (i in this.indices) {
                val tmp = this.toMutableList().also { it.removeAt(i) }
                if ((tmp.isAscending() || tmp.isDescending()) && tmp.isLegal()) {
                    ok = true
                    break
                }
            }
        }

        "$this is Legal: $ok".println()
        return ok
    }

    fun part1(input: List<String>): Int {
        return input.map { report ->
            report.split(" ")
                .map { it.toInt() }
        }
            .filter { it.isAscending() || it.isDescending() }
            .count { it.isLegal() }
    }

    fun part2(input: List<String>): Int {
        return input.map { report ->
            report.split(" ")
                .map { it.toInt() }
        }.count { it.isReportLegal() }
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
//    part1(input).println()
    part2(input).println()
}

