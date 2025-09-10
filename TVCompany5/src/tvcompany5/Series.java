package tvcompany5;

import java.util.Scanner;

public class Series {
    // Using clear variable names
    private final String[][] allSeries;
    private int seriesCount;
    private final int maxSeries;
    private final Scanner scanner;

    public Series() {
        maxSeries = 100; // Reasonable limit
        allSeries = new String[maxSeries][4];
        seriesCount = 0;
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("*******************************************************");
        System.out.println("**LATEST SERIES - 2025**");
        System.out.println("*******************************************************");
        System.out.println("**Enter (1) to launch menu or any other key to exit**");
        System.out.println("*******************************************************");

        String input = scanner.nextLine();
        if (input.equals("1")) {
            displayMainMenu();
        } else {
            exitApp();
        }
    }

    private void displayMainMenu() {
        while (true) {
            System.out.println("\nPlease select one of the following menu items:");
            System.out.println("(1) Capture a new series.");
            System.out.println("(2) Search for a series.");
            System.out.println("(3) Update series.");
            System.out.println("(4) Delete a series.");
            System.out.println("(5) Print series report - 2025");
            System.out.println("(6) Exit Application.");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewSeries();
                    break;
                case "2":
                    findSeries();
                    break;
                case "3":
                    modifySeries();
                    break;
                case "4":
                    removeSeries();
                    break;
                case "5":
                    generateReport();
                    break;
                case "6":
                    exitApp();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            System.out.println("Enter (1) to launch menu or any other key to exit");
            String continueOption = scanner.nextLine();
            if (!continueOption.equals("1")) {
                exitApp();
                return;
            }
        }
    }

    public void addNewSeries() {
        if (seriesCount >= maxSeries) {
            System.out.println("Cannot add more series. Storage is full!");
            return;
        }

        System.out.print("Enter the series id: ");
        String id = scanner.nextLine();

        System.out.print("Enter the series name: ");
        String name = scanner.nextLine();

        String age = getValidAge();

        System.out.print("Enter the number of episodes for " + name + ": ");
        String episodes = scanner.nextLine();

        // Store the series data
        allSeries[seriesCount][SeriesModel.ID] = id;
        allSeries[seriesCount][SeriesModel.NAME] = name;
        allSeries[seriesCount][SeriesModel.AGE] = age;
        allSeries[seriesCount][SeriesModel.EPISODES] = episodes;

        seriesCount++;
        System.out.println("Series processed successfully!!!");
    }

    // Get a valid age with clear validation
    private String getValidAge() {
        while (true) {
            System.out.print("Enter the series age restriction: ");
            String ageInput = scanner.nextLine();

            if (isAgeValid(ageInput)) {
                return ageInput;
            } else {
                System.out.println("You have entered an incorrect series age!!!");
                System.out.print("Please re-enter the series age >> ");
            }
        }
    }

    // Simple age validation
    private boolean isAgeValid(String ageInput) {
        try {
            int age = Integer.parseInt(ageInput);
            return age >= 2 && age <= 18;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void findSeries() {
        System.out.print("Enter the series id to search: ");
        String searchId = scanner.nextLine();

        findSeriesById(searchId);
    }

    // Search for a series by ID
    private int findSeriesById(String seriesId) {
        for (int i = 0; i < seriesCount; i++) {
            if (allSeries[i][SeriesModel.ID].equals(seriesId)) {
                System.out.println("---");
                System.out.println("SERIES ID: " + allSeries[i][SeriesModel.ID]);
                System.out.println("SERIES NAME: " + allSeries[i][SeriesModel.NAME]);
                System.out.println("SERIES AGE RESTRICTION: " + allSeries[i][SeriesModel.AGE]);
                System.out.println("SERIES NUMBER OF EPISODES: " + allSeries[i][SeriesModel.EPISODES]);
                System.out.println("---");
                return i;
            }
        }

        System.out.println("---");
        System.out.println("Series with Series Id: " + seriesId + " was not found!");
        System.out.println("---");
        return -1;
    }

    public void modifySeries() {
        System.out.print("Enter the series id to update: ");
        String updateId = scanner.nextLine();

        int seriesIndex = findSeriesById(updateId);

        if (seriesIndex == -1) {
            System.out.println("Series with Series Id: " + updateId + " was not found!");
            return;
        }

        System.out.print("Enter the series name: ");
        allSeries[seriesIndex][SeriesModel.NAME] = scanner.nextLine();

        allSeries[seriesIndex][SeriesModel.AGE] = getValidAge();

        System.out.print("Enter the number of episodes: ");
        allSeries[seriesIndex][SeriesModel.EPISODES] = scanner.nextLine();

        System.out.println("Series updated successfully!");
    }

    public boolean removeSeries() {
        System.out.print("Enter the series id to delete: ");
        String deleteId = scanner.nextLine();

        int seriesIndex = -1;
        for (int i = 0; i < seriesCount; i++) {
            if (allSeries[i][SeriesModel.ID].equals(deleteId)) {
                seriesIndex = i;
                break;
            }
        }

        if (seriesIndex == -1) {
            System.out.println("Series with Series Id: " + deleteId + " was not found!");
            return false;
        }

        System.out.print("Are you sure you want to delete series " + deleteId + " from the system? Yes (y) to delete: ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            // Shift all series after the deleted one
            for (int i = seriesIndex; i < seriesCount - 1; i++) {
                allSeries[i] = allSeries[i + 1];
            }

            allSeries[seriesCount - 1] = new String[4];
            seriesCount--;

            System.out.println("---");
            System.out.println("Series with Series Id: " + deleteId + " WAS deleted!");
            System.out.println("---");
            return true;
        } else {
            System.out.println("Deletion cancelled.");
            return false;
        }
    }

    public void generateReport() {
        if (seriesCount == 0) {
            System.out.println("No series data available.");
            return;
        }

        for (int i = 0; i < seriesCount; i++) {
            System.out.println("Series " + (i + 1));
            System.out.println("---");
            System.out.println("SERIES ID: " + allSeries[i][SeriesModel.ID]);
            System.out.println("SERIES NAME: " + allSeries[i][SeriesModel.NAME]);
            System.out.println("SERIES AGE RESTRICTION: " + allSeries[i][SeriesModel.AGE]);
            System.out.println("NUMBER OF EPISODES: " + allSeries[i][SeriesModel.EPISODES]);
            System.out.println("---");
        }
    }

    private void exitApp() {
        System.out.println("Exiting application. Goodbye!");
        scanner.close();
        System.exit(0);
    }

    void launchMenu() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}   