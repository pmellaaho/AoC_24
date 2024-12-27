import java.time.zone.ZoneRules

data class Rule(val precedingPage: Int, val latterPage: Int)

lateinit var pageOrderingRules: Set<Rule>
lateinit var updates: List<List<Int>>

fun main() {

    fun initData(input: List<String>) {
        val (a, b) = input.partition { it.contains('|') }

        val regex = "\\D+".toRegex()
        pageOrderingRules = a.map { line ->
            line.split(regex).let {
                Rule(it.first().toInt(), it.last().toInt())
            }
        }.toSet()
//        "There are ${pageOrderingRules.size} rules alltogether".println()

        updates = b.drop(1).map {
            it.split(',').map { s -> s.toInt() }
        }
    }

    fun List<Int>.rulesThatApply(): List<Rule> = pageOrderingRules.filter { rule ->
        this.contains(rule.precedingPage) && this.contains(rule.latterPage)
    }

    val isUpdateInCorrectOrder: (List<Int>) -> Boolean = { update ->
        val rulesForUpdate = update.rulesThatApply()
        var correctOrder = true
        update.forEachIndexed { index, page ->
            val rulesForPage = rulesForUpdate.filter {
                page == it.precedingPage || page == it.latterPage
            }

            for (rule in rulesForPage) {
                if (rule.precedingPage == page) {
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

    fun part1(input: List<String>): Int {
        initData(input)
        val updatesWithCorrectOrder = updates.filter { isUpdateInCorrectOrder(it) }
        return updatesWithCorrectOrder.sumOf { it.get(it.lastIndex / 2) }
    }

    class RuleComparator(private val rules: List<Rule>) : Comparator<Int> {
        override fun compare(x: Int?, y: Int?): Int =
            when {
                rules.any { it.precedingPage == x && it.latterPage == y } -> 1
                rules.any { it.precedingPage == y && it.latterPage == x } -> -1
                else -> 0
            }
    }

    fun part2(input: List<String>): Int {
        initData(input)
        val updatesWithIncorrectOrder = updates.filterNot { isUpdateInCorrectOrder(it) }

        val fixedUpdates = updatesWithIncorrectOrder.map { update ->
            val rulesForUpdate = update.rulesThatApply()
            val comparator = RuleComparator(rulesForUpdate)
            update.sortedWith(comparator)
        }
        return fixedUpdates.sumOf { it.get(it.lastIndex / 2) }
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
//    check(part2(input) == 5180)
}
