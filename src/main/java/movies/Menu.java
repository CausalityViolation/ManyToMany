package movies;

public class Menu {

    MovieDAO manage = new MovieDAO();

    public void MainMenu() {

        while (true) {

            System.out.println("\n=================================");
            System.out.println("              Menu               ");
            System.out.println("=================================");
            System.out.println("1. Show all");
            System.out.println("2. Show Heroes");
            System.out.println("3. Show Movies");
            System.out.println("4. New Hero");
            System.out.println("5. New Movie");
            System.out.println("6. Delete Movie");
            System.out.println("7. Delete Hero");
            System.out.println("8. Update");
            System.out.println("\n0. Exit");
            System.out.println("=================================");
            System.out.print("Input: ");

            int choice = manage.scanInt();

            switch (choice) {

                case 1:
                    manage.viewAll();
                    break;
                case 2:
                    manage.viewSuperHeroes();
                    break;
                case 3:
                    manage.viewMovies();
                    break;
                case 4:
                    manage.newHero();
                    break;
                case 5:
                    manage.newMovie();
                    break;
                case 6:
                    manage.deleteMovie();
                    break;
                case 7:
                    manage.deleteHero();
                    break;
                case 8:
                    subMenu();
                    break;
                case 0:
                    System.exit(0);
                    break;

                default:
                    System.out.println("<Invalid Input>");
            }
        }

    }

    public void subMenu() {

        System.out.println("=================================");
        System.out.println("1. Update Hero");
        System.out.println("2. Update Movie Title");
        System.out.println("3. Add Hero to an existing Movie");
        System.out.println("0. Return to Main Menu");
        System.out.println("=================================");
        System.out.print("Input: ");

        while (true) {

            int choice = manage.scanInt();

            switch (choice) {

                case 1:
                    manage.updateHero();
                    return;
                case 2:
                    manage.updateMovie();
                    return;
                case 3:
                    manage.addHeroToExistingMovie();
                    return;
                case 0:
                    return;
            }
        }

    }

}
