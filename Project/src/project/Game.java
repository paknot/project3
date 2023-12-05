package project;

import java.util.Scanner;
import java.util.Arrays;

public class Game {

    Dice diceClass = new Dice();
    Scanner input = new Scanner(System.in);
    String[][] scoreTable = {
        {"Category", "Player 1", "Player 2"},
        {"Ones", "", ""},
        {"Twos", "", ""},
        {"Threes", "", ""},
        {"Fours", "", ""},
        {"Fives", "", ""},
        {"Sixes", "", ""},
        {"Sequence", "", ""},
        {"Total", "", ""},};
    int[] dice = new int[5];

    public void welcome() {
        System.out.println("Hello and welcome to the simple dice game!");
        input.nextLine();
        System.out.println("The objective of a game is to score as much points as possible in each of a given categories.");
        input.nextLine();
        System.out.println("You do this by having as many dice of a number of your category, eg. you score more points in Sixes if you have 3 sixes not 2");
        input.nextLine();
        System.out.println("You get a initial throw as well as 3 additional ones");
        input.nextLine();
        System.out.println("After each throw you will have an option of setting aside some dice (1) or defering a throw (2)");
        input.nextLine();
        System.out.println("At the end of your round the updated version of a score table is going to be shown ");

        String choice = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("\nWould you like to enter your custom username?(Y/N) ");
            choice = input.next();

            if (choice.equals("y") || choice.equals("n")) {
                validInput = true;
            } else {
                System.out.println("Invalid input! Please enter y or n");
            }
        }

        if (choice.equals("y")) {
            for (int i = 0; i < 2; i++) {
                System.out.println("Please enter " + (i + 1) + " username: ");
                scoreTable[0][i + 1] = input.next();
            }
        } else if (choice.equals("n")) {
            System.out.println("Okay, going directly to the game");
        }
    }

    /*Here is the major part of the code
    it takes care of choosing between deferring and setting aside,
    it checks every input and checks if it's correct
        
     */
    public void major() {
        for (int i = 1; i <= 14; i++) {

            int throwsLeft = 3;
            int category = 0;
            int currentPlayer = 1;
            if (i % 2 == 0) {
                currentPlayer = 2;
            }
            while (throwsLeft > 0) {
                if (throwsLeft == 3) {
                    System.out.println(scoreTable[0][currentPlayer] + " you threw: ");
                    rollFive();
                    showDice();
                    String chosen = null;
                    //check for a valid inout 1/2
                    boolean validInput = false;
                    while (!validInput) {
                        System.out.print("\nEnter '1' to choose a category or '2' to defer a throw: ");
                        chosen = input.next();

                        if (chosen.equals("1") || chosen.equals("2")) {
                            validInput = true;
                        } else {
                            System.out.println("Invalid input! Please enter '1' or '2'.");
                        }
                    }
                    //SETTING ASIDE FROM A START
                    if (chosen.equals("1")) {
                        boolean validCategoryChosen = false;
                        while (!validCategoryChosen) {
                            System.out.println("Please select a category (1-7): ");

                            if (input.hasNextInt()) {
                                category = input.nextInt();

                                try {
                                    if (category >= 1 && category <= 7 && scoreTable[category][currentPlayer].isEmpty()) {
                                        validCategoryChosen = true;
                                    } else {
                                        System.out.println("Invalid category! Please choose another category.");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Invalid input. Enter category 1-7.");
                                }
                            } else {
                                System.out.println("Invalid input. Enter a numeric category between 1-7.");
                                input.next(); // Consume the invalid input
                            }
                        }
                        System.out.println("You chose " + scoreTable[category][0]);
                        System.out.println("Enter how many numbers you are going to set aside: ");
                        int aside = 0;
                        boolean validAside = false;
                        while (!validAside) {
                            if (input.hasNextInt()) {
                                aside = input.nextInt();

                                try {
                                    if (aside >= 0 && aside <= 4) {
                                        validAside = true;
                                    } else {
                                        System.out.println("Invalid number! Please choose another one.");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Invalid input. Enter number between 0-5.");
                                }
                            } else {
                                System.out.println("Invalid input. Enter a numeric index between 0-4.");
                                input.next(); // Consume the invalid input
                            }
                        }

                        int[] save = new int[aside];
                        for (int j = 0; j < save.length; j++) {
                            boolean isValidInput = false;

                            while (!isValidInput) {
                                System.out.print("Enter an index of a number to save(0-4): ");

                                try {
                                    int temp = input.nextInt();

                                    if (temp >= 0 && temp <= 4) {
                                        save[j] = dice[temp];
                                        isValidInput = true;
                                    } else {
                                        System.out.println("Invalid input. Number must be between 0 and 4.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid input. Please enter a valid integer.");
                                    input.nextLine();
                                }
                            }
                        }

                        rollFive();
                        for (int x = 0; x < save.length; x++) {
                            dice[x] = save[x];
                        }
                        System.out.println("Your new set is:");
                        showDice();
                        throwsLeft--;
                    } //DEFERING A THROW FROM A START
                    else if (chosen.equals("2")) {
                        rollFive();
                        System.out.println("Your new dice are:");
                        showDice();
                        throwsLeft--;
                        boolean validCategoryDefer = false;
                        while (!validCategoryDefer) {
                            System.out.println("\nPlease select a category (1-7): ");

                            if (input.hasNextInt()) {
                                category = input.nextInt();

                                try {
                                    if (category >= 1 && category <= 7 && scoreTable[category][currentPlayer].isEmpty()) {
                                        validCategoryDefer = true;
                                    } else {
                                        System.out.println("Invalid category! Please choose another category.");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Invalid input. Enter category 1-7.");
                                }
                            } else {
                                System.out.println("Invalid input. Enter a numeric category between 1-7.");
                                input.next();
                            }
                        }

                        System.out.println("Category seclected: " + scoreTable[category][0]);
                    } else {
                        System.out.println("Wrong input, enter again");
                    }

                    //THROWS 2 and 3
                } else {

                    String chosen = null;
                    boolean validInput = false;
                    while (!validInput) {
                        System.out.println("\nOptions, enter a number corresponding to and option \n(1)set aside some dice \n(2)defer a throw ");
                        chosen = input.next();

                        if (chosen.equals("1") || chosen.equals("2")) {
                            validInput = true;
                        } else {
                            System.out.println("Invalid input! Please enter '1' or '2'.");
                        }
                    }
                    if (chosen.equals("1")) {

                        System.out.println("Your category " + scoreTable[category][0]);
                        System.out.println("Enter how many numbers you are going to set aside: ");
                        int aside = 0;
                        boolean validAside = false;
                        while (!validAside) {
                            if (input.hasNextInt()) {
                                aside = input.nextInt();

                                try {
                                    if (aside >= 0 && aside <= 4) {
                                        validAside = true;
                                    } else {
                                        System.out.println("Invalid number! Please choose another one.");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Invalid input. Enter number between 0-5.");
                                }
                            } else {
                                System.out.println("Invalid input. Enter a correct numeric value.");
                                input.next(); 
                            }
                        }

                        int[] save = new int[aside];
                        for (int j = 0; j < save.length; j++) {
                            boolean isValidInput = false;

                            while (!isValidInput) {
                                System.out.print("Enter an index of a number to save(0-4): ");

                                try {
                                    int temp = input.nextInt();

                                    if (temp >= 0 && temp <= 4) {
                                        save[j] = dice[temp];
                                        isValidInput = true;
                                    } else {
                                        System.out.println("Invalid input. Number must be between 0 and 4.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid input. Please enter a valid integer.");
                                    input.nextLine();
                                }
                            }
                        }
                        rollFive();
                        for (int x = 0; x < save.length; x++) {
                            dice[x] = save[x];
                        }
                        System.out.println("Your new set is:");
                        showDice();
                        throwsLeft--;
                    } else if (chosen.equals("2")) {
                        rollFive();
                        System.out.println("Your new dice are:");
                        showDice();
                        throwsLeft--;
                    }
                }

            }

            System.out.println("You used all your additional throws");
            System.out.println("Your final dice are: ");
            showDice();
            if (category == 7) {
                scoreTable[category][currentPlayer] = Integer.toString(sequence(dice));
            } else {
                scoreTable[category][currentPlayer] = Integer.toString(addDice(dice, category));
            }
            System.out.println();
            printScoreTable();
            if (i == 14) {
                calculateScore();
            }
        }
    }

    // this method prints the scoreTable
    public void printScoreTable() {
        System.out.println("------------------------------------");
        for (String[] scoreTable1 : scoreTable) {
            for (String scoreTable11 : scoreTable1) {
                System.out.printf("| %-9s ", scoreTable11);
            }
            System.out.println("|");
            System.out.println("------------------------------------");
        }

        System.out.println();
    }

    //this rolls 5 new dice
    public void rollFive() {
        for (int j = 0; j < 5; j++) {
            dice[j] = diceClass.Roll();
        }
    }

    //this shows the current dice 
    public void showDice() {
        for (int i = 0; i < dice.length; i++) {
            System.out.print(dice[i] + " ");
        }
    }
    //this adds the same dice, used for categories 1-6

    public static int addDice(int[] arr, int target) {
        int sum = 0;
        for (int i : arr) {
            if (i == target) {
                sum += target;
            }
        }
        return sum;
    }

    //This acculumates the entire score and adds it to Total Score
    public void calculateScore() {
        for (int player = 1; player <= 2; player++) {
            int totalPoints = 0;

            for (int row = 1; row <= 6; row++) {
                if (!scoreTable[row][player].isEmpty()) {
                    totalPoints += Integer.parseInt(scoreTable[row][player]);
                }
            }

            scoreTable[8][player] = String.valueOf(totalPoints);
        }

        printScoreTable();
    }

    //check for sequence
    public static int sequence(int[] dice) {
        Arrays.sort(dice);
        int sum = 0;
        for (int i = 0; i < dice.length - 1; i++) {
            if (dice[i] + 1 != dice[i + 1]) {
                return 0;
            }
        }
        return 20;
    }

    public void play() {
        welcome();
        major();
    }

}
