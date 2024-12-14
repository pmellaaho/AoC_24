fun main() {

    fun part1(input: List<String>): Int {
        val mulRegex = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
        val numbersRegex = """\d{1,3}""".toRegex()
        return input.sumOf { line ->
            val matchResult = mulRegex.findAll(line)
            matchResult.toList().sumOf {
                val mR = numbersRegex.findAll(it.value)
                mR.toList().zipWithNext { a, b ->
                    a.value.toInt() * b.value.toInt()
                }.sum()
            }
        }
    }

    fun part2(input: List<String>): Int {
        val mulRegex = """mul\((\d{1,3},\d{1,3})\)|do(n't)?\(\)""".toRegex()

        var apply = true
        var sum = 0

        val memory = input.joinToString()
        val all = mulRegex.findAll(memory).map { it.value }

        for (cmd in all) {
            when {
                cmd == "do()" -> apply = true
                cmd == "don't()" -> apply = false
                apply && cmd.startsWith("mul(") -> {
                    val (a, b) = cmd.removeSurrounding("mul(", ")")
                        .split(",")
                    sum += a.toInt() * b.toInt()
                }
            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
