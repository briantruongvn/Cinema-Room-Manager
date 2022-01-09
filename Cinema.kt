package cinema
import java.util.Scanner
const val TEN = 10
const val EIGHT = 8
const val SIXTY = 60
fun main() {
    do {
        var inputSeatAndRowCorrect = false
        try {
            val scanner = Scanner(System.`in`)
            println("Enter the number of rows:")
            val row: Int = scanner.nextInt()
            println("Enter the number of seats in each row:")
            val seat: Int = scanner.nextInt()
            if (row in 1..9 && seat in 1..9) {
                inputSeatAndRowCorrect = true
                val seats = MutableList(row) { MutableList(seat) { "S" } }
                var currentIncome = 0
                var numberOfPurchased = 0
                do {
                    showTheMenu()
                    val answer = readLine()!!.toInt()
                    when (answer) {
                        1 -> showTheSeats(seats)
                        2 -> {
                            var correctInput = false
                            while(!correctInput){
                                try {
                                    println("Enter a row number:")
                                    val rowSeat = scanner.nextInt()
                                    println("Enter a seat number in that row:")
                                    val numSeat = scanner.nextInt()
                                    if (rowSeat <= 0 || rowSeat > row || numSeat <= 0 || numSeat > seat) {
                                        println("Wrong input!")
                                    } else if (seats[rowSeat - 1][numSeat - 1] == "B") {
                                        println("That ticket has already been purchased!")
                                    } else {
                                        addToSeats(rowSeat, numSeat, seats)
                                        val currentPrice = calculatePrice(row, seat, rowSeat)
                                        println("Ticket price: $${currentPrice}")
                                        numberOfPurchased++
                                        currentIncome += calculatePrice(row, seat, rowSeat)
                                        correctInput = !correctInput
                                    }
                                } catch (e: Exception) {
                                    println("Wrong input")
                                }
                            }
                        }
                        3 -> {
                            giveStatistics(numberOfPurchased, row, seat, currentIncome)
                        }
                    }
                } while (answer != 0)
            } else {
                println("Wrong input!")
            }
        } catch(e: Exception) {
            println("Wrong input!")
        }
    } while(!inputSeatAndRowCorrect)
}

//Print the layout based on the seats list
fun showTheSeats(seats: MutableList<MutableList<String>>) {
    //Print the headings
    println("Cinema:")
    print("  ")
    for (i in 1..seats[0].size) {
        print("$i ")
    }
    println()
    // Print the seat layout
    for (i in seats.indices) {
        println("${i + 1} ${seats[i].joinToString(" ")}")
    }
}
/* Show the menu */
fun showTheMenu() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}
/*
Change the chosen seat to B
 */
fun addToSeats(rowSeat: Int, numSeat: Int, seats: MutableList<MutableList<String>>):
        MutableList<MutableList<String>> {
        seats[rowSeat - 1][numSeat - 1] = "B"
    return seats
}
/*
Calculate the price base on total row and seat number of the cinema.
The price changed according to the row of the seat.
 */
fun calculatePrice(row: Int, seat: Int, rowSeat: Int): Int {
    return if (row * seat <= SIXTY) {
        TEN
    } else if (rowSeat <= row / 2) {
        TEN
    } else {
        EIGHT
    }
}

fun giveStatistics(numberPurchased: Int, row: Int, seat: Int, currentIncome: Int) {
    println("Number of purchased tickets: $numberPurchased")
    val percentage: Double = if (numberPurchased == 0) { 0.00 } else {
        numberPurchased.toDouble() / (row * seat)
    }
    println("Percentage: ${String.format("%.2f", percentage * 100)}%")
    println("Current income: $$currentIncome")
    val totalIncome = if (row * seat <= SIXTY) {
        row * seat * TEN
    }
    else {
        row / 2 * TEN * seat + (row - row / 2) * EIGHT * seat
    }
    println("Total income: $$totalIncome")
}

