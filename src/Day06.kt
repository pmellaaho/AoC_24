data class CoordinatePoint(val x: Int, val y: Int) {
    operator fun plus(vector: Vector) = CoordinatePoint(this.x + vector.x, this.y + vector.y)
}

data class Vector(val x: Int, val y: Int)

val east = Vector(1, 0)
val south = Vector(0, 1)
val west = Vector(-1, 0)
val north = Vector(0, -1)


lateinit var theMap: List<CoordinatePoint>

lateinit var obstacles: List<CoordinatePoint>
var maxX: Int = 0
var maxY: Int = 0

fun main() {

    fun buildMap(input: List<String>): CoordinatePoint {
        var startPosition: CoordinatePoint? = null

        obstacles = buildList {
            theMap = input.mapIndexed { y, s ->
                s.mapIndexed { x, c ->
                    CoordinatePoint(x, y).also {
                        if (startPosition == null && c == '^') startPosition = it
                        else if (c == '#') add(it)
                    }
                }
            }.flatten()
        }

        require(startPosition != null)
        return startPosition as CoordinatePoint
    }


    fun init(input: List<String>): CoordinatePoint {
        val startPosition = buildMap(input)
        maxX = input.lastIndex
        maxY = theMap.last().y
        return startPosition
    }

    fun nextDirection(direction: Vector): Vector =
        when (direction) {
            west -> north
            north -> east
            east -> south
            south -> west
            else -> error("Not allowed direction")
        }

    data class Result(val visited: Set<CoordinatePoint>, val outOfMapReached: Boolean)

    fun goToDirection(startPosition: CoordinatePoint, direction: Vector): Result {
        val route = mutableSetOf(startPosition)
        var shouldProceed = true
        var exit = false

        while (shouldProceed) {
            val nextPos = route.last() + direction
            when {
                nextPos.x > maxX || nextPos.y > maxY -> {
                    shouldProceed = false
                    exit = true
                }

                obstacles.any { it.x == nextPos.x && it.y == nextPos.y } -> shouldProceed = false
                else -> route.add(nextPos)
            }
        }
        return Result(route, exit)
    }


    fun part1(input: List<String>): Int {
        val startPosition = init(input)
        val route = mutableSetOf(startPosition)

        var direction = north
        var shouldProceed = true

        while (shouldProceed) {
            val travelResult = goToDirection(startPosition = route.last(), direction = direction)
            route += travelResult.visited

            if (travelResult.outOfMapReached) {
                shouldProceed = false
            } else {
                direction = nextDirection(direction)
            }
        }

        route.size.println()
        return route.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
//    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
//    part2(input).println()
}
