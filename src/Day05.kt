fun main() {

    data class Rule(val precedingPage: Int, val latterPage: Int)

    fun part1(input: List<String>): Int {
        val (a, b) = input.partition { it.contains('|') }

        val regex = "\\D+".toRegex()
        val pageOrderingRules = a.map { line ->
            line.split(regex).let {
                Rule(it.first().toInt(), it.last().toInt())
            }
        }
        "There are ${pageOrderingRules.size} rules alltogether".println()

        val updates = b.drop(1).map {
            it.split(',').map { s -> s.toInt() }
        }

        val inCorrectOrder =
        updates.filter { update ->
            val rulesThatApply = pageOrderingRules.filter { rule ->
                update.contains(rule.precedingPage) && update.contains(rule.latterPage)
            }
//            "For update: $update, there are ${rulesThatApply.size} rules that apply".println()
            var correctOrder = true

            update.forEachIndexed { index, page ->
                val rulesForPage = rulesThatApply.filter {
                    page == it.precedingPage || page == it.latterPage }

//                "For page: $page, there are ${rulesForPage.size} rules that apply".println()
                for(rule in rulesForPage) {
                    if(rule.precedingPage == page) {
                        if (index > update.indexOf(rule.latterPage)) {
                            correctOrder = false
                            return@forEachIndexed
                        }
                    } else {
                        if (index < update.indexOf(rule.precedingPage)) {
                            correctOrder = false
                            return@forEachIndexed
                        }
                    }
                }
            }
            correctOrder
        }

        return inCorrectOrder.sumOf { it.get(it.lastIndex / 2) }

    }

    fun part2(input: List<String>): Int {
        return 0
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
//    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}
