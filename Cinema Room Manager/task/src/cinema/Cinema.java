package cinema;

import java.util.Scanner;

public class Cinema {

    SystemState systemState;
    private final int rows;
    private final int seats;
    private final char[][] hallMap;
    boolean powerOn = true;
    int rowSold;
    int seatSold;

    public Cinema() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

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
            cinema.parceInput(scanner.nextInt());
        }

    }

    void parceInput(int input) {
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

    void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("0. Exit");
    }


    void mainMenu(int input) {
        switch (input) {
            case 1:
                printHallMap();
                printMenu();
                break;
            case 2:
                systemState = SystemState.BUY;
                buyTicket(input);
                break;
            case 0:
                exit();
                break;
            default:
                break;
        }

    }

    public void printHallMap() {
        System.out.println();
        System.out.println("Cinema:");
        for (char[] vector : hallMap) {
            for (char element : vector) {
                System.out.print(" " + element);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void buyTicket(int input) {
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
                ticketPricePrint(rowSold);
                systemState = SystemState.MENU;
                printMenu();
                break;
            default:
                break;
        }
    }

    void seatBooking (int rowSold, int seatSold) {
        hallMap[rowSold][seatSold] = 'B';
    }

    void ticketPricePrint(int rowSold) {
        if (rows * seats <= 60) {
            System.out.println("Ticket price: $10");
        } else {
            if (rowSold > rows / 2) {
                System.out.println("Ticket price: $8");
            } else {
                System.out.println("Ticket price: $10");
            }
        }
    }

    public void exit() {
        powerOn = false;
    }
}

enum SystemState {
    MENU,
    BUY,
    ROW_SELECTION,
    SEAT_SELECTION;
}

