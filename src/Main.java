import java.util.Random;
import java.util.Scanner;

public class Main {


//    public enum Language {
//        En("English"),
//        Ru("Русский"),
//        Fr("Французский");
//
//        private String secondName;
//
//        Language(String secondName) {
//            this.secondName = secondName;
//        }
//
//        public String getSecondName() {
//            return secondName;
//        }
//    }

    public enum Player {
        COMPUTER,
        USER,
        INTIAL
    }


    public enum Cell {
        ALIVE_SHIP('Ж'),
        DEAD_SHIP('X'),
        MISS_SHOOT('0'),
        EMPTY('.');

        private char value;

        Cell(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }

    // <возвращаемый тип данных> <имя_функкции> (<параметры с типами данных, каждый параметр через запятую>) { <логика> }
    static int sum(int a, int b) {
        return a + b;
    }

    void printSum(int a, int b) {
        System.out.println(a + b);
    }

    static Cell[][][] initFieldsAndPlaceShips(int fieldSize) {
        Cell[][] computerField = new Cell[fieldSize][fieldSize];
        Cell[][] userField = new Cell[fieldSize][fieldSize];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                computerField[i][j] = Cell.EMPTY;
                userField[i][j] = Cell.EMPTY;
            }
        }
        return new Cell[][][]{computerField, userField};
    }

    static void placeShips(Cell[][] field, int countShips, int fieldSize, Random randGenerator) {
        for (int i = 0; i < countShips; i++) {
            int iShip, jShip;

            do {
                iShip = randGenerator.nextInt(fieldSize);
                jShip = randGenerator.nextInt(fieldSize);
            } while (field[iShip][jShip] != Cell.EMPTY);

            field[iShip][jShip] = Cell.ALIVE_SHIP;
        }
    }

    static Player firstPlayer(Random randGenerator) {
        if (randGenerator.nextBoolean()) {
            return Player.COMPUTER;
        } else {
            return Player.USER;
        }
    }

    static int[] shoot(int fieldSize, Random randGenerator, Cell[][] field, int ordinal, int countUserShips) {
        // 1ый индекс - новый игрок, 2ый индекс - колво кораблей
        int[] result = {-1, -1};

        int iShoot = randGenerator.nextInt(fieldSize);
        int jShoot = randGenerator.nextInt(fieldSize);

        if (field[iShoot][jShoot] != Cell.ALIVE_SHIP) {
            field[iShoot][jShoot] = Cell.MISS_SHOOT;
            result[0] = ordinal;
        } else {
            field[iShoot][jShoot] = Cell.DEAD_SHIP;
            result[1] = --countUserShips; // countUserShips--
        }

        return result;
    }


    public static void main(String[] args) {
        Random randGenerator = new Random();

        int fieldSize = 0;
        int countUserShips = 0;
        int countComputerShips = 0;

        Cell[][] computerField , userField ;
        Player activePlayer, winner = Player.INTIAL;

        Scanner scanner = new Scanner(System.in);

        boolean isPlayer = true;

        fieldSize = scanner.nextInt();

        countUserShips = countComputerShips = 10;

        Cell[][][] cells = initFieldsAndPlaceShips(fieldSize);

        computerField = cells[0];
        userField = cells[1];

        placeShips(userField, countUserShips, fieldSize, randGenerator);
        placeShips(computerField, countComputerShips, fieldSize, randGenerator);

        activePlayer = firstPlayer(randGenerator);

        while (isPlayer) {
            if (activePlayer == Player.COMPUTER) {
                int[] resultOfShoot = shoot(fieldSize, randGenerator, userField, Player.USER.ordinal(), countUserShips);

                if (resultOfShoot[0] != -1) {
                    activePlayer = Player.values()[resultOfShoot[0]];
                }
                if (resultOfShoot[1] != -1) {
                    countUserShips = resultOfShoot[1];
                }
            } else {
                int[] resultOfShoot = shoot(fieldSize, randGenerator, computerField, Player.COMPUTER.ordinal(), countComputerShips);

                if (resultOfShoot[0] != -1) {
                    activePlayer = Player.values()[resultOfShoot[0]];
                }
                if (resultOfShoot[1] != -1) {
                    countComputerShips = resultOfShoot[1];
                }
            }

            if (countUserShips == 0) {
                winner = Player.COMPUTER;
                isPlayer = false;
            } else if (countComputerShips == 0) {
                winner = Player.USER;
                isPlayer = false;
            }
        }

        System.out.println("Победитель: " + winner);
    }
}