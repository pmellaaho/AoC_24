fun main() {

    data class LetterPoint(val x: Int, val y: Int, val letter: Char)
    data class Point(val x: Int, val y: Int)

    fun LetterPoint.adjacentPoints(maxX: Int, maxY: Int): List<Point> {
        val adjacentPoints =
            buildList<Point> {
                add(Point(x, y - 1))
                add(Point(x + 1, y))
                add(Point(x, y + 1))
                add(Point(x - 1, y))
                add(Point(x + 1, y - 1))
                add(Point(x + 1, y + 1))
                add(Point(x - 1, y + 1))
                add(Point(x - 1, y - 1))
            }
                .filter { it.x in 0..maxX }
                .filter { it.y in 0..maxY }
//       "$this has ${adjacentPoints.size} adjacent points".println()
        return adjacentPoints
    }

    fun List<LetterPoint>.toLetterPoint(point: Point): LetterPoint =
        this.firstOrNull { it.x == point.x && it.y == point.y }?.letter?.let {
            LetterPoint(point.x, point.y, it)
        } ?: throw Error("No match found")

    fun nextPointInDirection(first: LetterPoint, second: LetterPoint, maxX: Int, maxY: Int): Point? {
        val x = when {
            first.x < second.x -> second.x + 1
            first.x > second.x -> second.x - 1
            else -> first.x
        }

        val y = when {
            first.y < second.y -> second.y + 1
            first.y > second.y -> second.y - 1
            else -> first.y
        }
        return if (
            x in 0..maxX && y in 0..maxY
        ) Point(x, y)
        else null
    }

    fun part1(input: List<String>): Int {

        val all = input.mapIndexed { y, s ->
            s.mapIndexed { x, c ->
                LetterPoint(x, y, c)
            }
        }.flatten()

        val maxX = input.lastIndex
        val maxY = all.last().y

        return all.filter { it.letter == 'X' }
            .sumOf { xPoint ->

                xPoint.adjacentPoints(maxX, maxY)
                    .map { all.toLetterPoint(it) }
                    .filter { it.letter == 'M' }
                    .map { mPoint ->

                        val aPoint: LetterPoint? =
                            nextPointInDirection(xPoint, mPoint, maxX, maxY)
                                ?.let { all.toLetterPoint(it) }
                                ?.takeIf { it.letter == 'A' }

                        val sPoint: LetterPoint? =
                            aPoint?.let {
                                nextPointInDirection(mPoint, aPoint, maxX, maxY)
                                    ?.let { all.toLetterPoint(it) }
                                    ?.takeIf { it.letter == 'S' }
                            }

                        if (sPoint != null) 1 else 0
                    }.sum()
            }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
//    check(part2(testInput) == 48)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
//    part2(input).println()
}
