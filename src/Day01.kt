fun main() {

    fun List<String>.asIntLists() =
        fold(Pair(mutableListOf<Int>(), mutableListOf<Int>())) { pair, row ->
            pair.apply {
                row.split(" ").apply {
                    first += first().toInt()
                    second += last().toInt()
                }
            }
        }

    fun Pair<Int, Int>.distance(): Int =
        when {
            first == second -> 0
            first > second -> first - second
            else -> second - first
        }

    fun part1(input: List<String>): Int {
        val (left, right) = input.asIntLists()

        left.sort()
        right.sort()
        return left.zip(right).sumOf { it.distance() }
    }

    fun List<Int>.nbrOf(s: Int) = filter { it == s }.size

    fun part2(input: List<String>): Int {
        val (left, right) = input.asIntLists()

        return left.fold(0) { acc, s ->
            acc + right.nbrOf(s) * s
        }
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
//    part1(input).println()
    part2(input).println()
}
