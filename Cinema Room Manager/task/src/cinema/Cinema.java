package cinema;

import java.util.Scanner;

public class Cinema {

    private SystemState systemState;
    private final int rows;
    private final int seats;
    private final char[][] hallMap;
    private boolean powerOn = true;
    private boolean smallCinema = false;
    private int totalIncome;
    private int rowSold;
    private int seatSold;
    private int currentIncome = 0;
    private double hallOccupancy = 0.00;
    private int soldTickets = 0;

    public Cinema() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        totalIncome = 0;

        if (rows * seats <= 60) {
            totalIncome = rows * seats * 10;
            smallCinema = true;
        } else {
            totalIncome = ((rows / 2) * 10 * seats) + ((rows - (rows / 2)) * 8 * seats);
        }

        hallMap = new char[rows + 1][seats + 1];
        hallMap[0][0] = ' ';
        for (int i = 1; i <= seats; i++) {
            hallMap[0][i] = (char) (i + '0');
        }

        for (int j = 1; j <= rows; j++) {
            hallMap[j][0] = (char) (j + '0');
            for (int k = 1; k <= seats; k++) {
                hallMap[j][k] = 'S';
            }
        }
    }

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.systemState = SystemState.MENU;
        Scanner scanner = new Scanner(System.in);
        cinema.printMenu();

        while (cinema.powerOn) {
            cinema.parseInput(scanner.nextInt());
        }

    }

    private void parseInput(int input) {
        switch (systemState) {
            case MENU:
                mainMenu(input);
                break;
            case BUY:
                buyTicket(input);
                break;
            case ROW_SELECTION:
                buyTicket(input);
                break;
            case SEAT_SELECTION:
                buyTicket(input);
                break;
            default:
                break;
        }
    }

    private void mainMenu(int input) {
        switch (input) {
            case 1:
                printHallMap();
                printMenu();
                break;
            case 2:
                systemState = SystemState.BUY;
                buyTicket(input);
                break;
            case 3:
                printStatistic();
                printMenu();
                break;
            case 0:
                exit();
                break;
            default:
                break;
        }

    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private void printHallMap() {
        System.out.println();
        System.out.println("Cinema:");
        for (char[] row : hallMap) {
            for (char element : row) {
                System.out.print(" " + element);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printStatistic() {
        System.out.println();
        System.out.println("Number of purchased tickets: " + soldTickets);
        System.out.printf("Percentage: %.2f", hallOccupancy);
        System.out.println("%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    private void buyTicket(int input) {
        switch (systemState) {
            case BUY:
                systemState = SystemState.ROW_SELECTION;
                System.out.println("\nEnter a row number:");
                break;
            case ROW_SELECTION:
                rowSold = input;
                systemState = SystemState.SEAT_SELECTION;
                System.out.println("Enter a seat number in that row:");
                break;
            case SEAT_SELECTION:
                seatSold = input;
                seatBooking(rowSold, seatSold);
                break;
            default:
                break;
        }
    }

    private void seatBooking (int rowSold, int seatSold) {
        if (rowSold > rows || seatSold > seats) {
            System.out.println("\nWrong input!");
            systemState = SystemState.BUY;
            buyTicket(2);
        } else if (hallMap[rowSold][seatSold] == 'B') {
            System.out.println("\nThat ticket has already been purchased");
            systemState = SystemState.BUY;
            buyTicket(2);
        } else {
            hallMap[rowSold][seatSold] = 'B';
            currentIncome += calcTicketPrice(rowSold);
            soldTickets += 1;
            setHallOccupancy();
            ticketPricePrint(rowSold);
            systemState = SystemState.MENU;
            printMenu();
        }

    }

    private int calcTicketPrice (int rowSold) {
        if (smallCinema) {
            return 10;
        } else if (rowSold <= rows / 2) {
            return 10;
        } else {
            return 8;
        }
    }

    private void ticketPricePrint(int rowSold) {
        System.out.println("\nTicket price: $" + calcTicketPrice(rowSold));
    }

    private void setHallOccupancy () {
        if (soldTickets == 0) {
        } else {
            hallOccupancy = (soldTickets / ((double) rows * seats)) * 100.00;
        }
    }

    private void exit() {
        powerOn = false;
    }
}

enum SystemState {
    MENU,
    BUY,
    ROW_SELECTION,
    SEAT_SELECTION;
}

