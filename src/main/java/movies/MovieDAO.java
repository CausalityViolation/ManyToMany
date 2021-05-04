package movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    Scanner input = new Scanner(System.in);

    public void viewAll() {

        viewMovies();
        viewSuperHeroes();

    }

    public void viewMovies() {

        EntityManager em = emf.createEntityManager();
        Query moviesQuery = em.createQuery("SELECT m FROM Movie m");

        @SuppressWarnings("unchecked")
        List<Movie> content = moviesQuery.getResultList();
        System.out.println("<Displaying all Movies>");

        for (Movie m : content) {
            System.out.println(m);
        }
        System.out.println("<End of list>\n");

    }

    public void viewSuperHeroes() {

        EntityManager em = emf.createEntityManager();
        Query heroQuery = em.createQuery("SELECT h FROM SuperHero h");

        @SuppressWarnings("unchecked")
        List<SuperHero> content = heroQuery.getResultList();
        System.out.println("<Displaying all Heroes and their Movies>");

        for (SuperHero h : content) {
            System.out.println(h);
        }
        System.out.println("<End of list>\n");

    }


    public void newHero() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Input Hero Name: ");
        String name = input.nextLine();

        SuperHero h1 = new SuperHero(name);

        System.out.println("Input Super Power: ");
        String power = input.nextLine();

        h1.setPower(power);

        em.getTransaction().begin();
        em.persist(h1);
        em.getTransaction().commit();
        em.close();

        System.out.println("<Hero successfully added to library>");

    }


    public void newMovie() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Input Title: ");
        String title = input.nextLine();

        System.out.println("Would you like to add an existing Hero to your game?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("0. Cancel");

        int choice = scanInt();

        if (choice == 1) {

            viewSuperHeroes();

            System.out.print("Enter the ID of the Hero you'd like to assign to the Movie: ");
            int heroID = scanInt();

            SuperHero hero = em.find(SuperHero.class, heroID);
            Movie newMovie = new Movie(title);

            newMovie.addSuperHero(hero);

            em.getTransaction().begin();
            em.persist(newMovie);
            em.getTransaction().commit();
            em.close();


        } else if (choice == 2) {

            Movie newMovie = new Movie(title);

            em.getTransaction().begin();
            em.persist(newMovie);
            em.getTransaction().commit();
            em.close();

        } else {
            return;
        }


        System.out.println("<Movie successfully added to library>");


    }

    public void deleteMovie() {

        viewMovies();
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter the ID of the Movie you'd like to DELETE: ");
        int movieID = scanInt();

        em.getTransaction().begin();
        Movie m = em.find(Movie.class, movieID);

        //Removing all Hero references from the selected Movie
        for (SuperHero hero : m.getSuperHeroList()) {
            m.removeSuperHero(hero);
        }
        em.remove(m);
        em.getTransaction().commit();
        em.close();

        System.out.println("<Movie successfully deleted>\n");

    }

    public void deleteHero() {

        viewSuperHeroes();
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter the ID of the Hero you'd like to DELETE: ");
        int heroID = scanInt();

        em.getTransaction().begin();
        SuperHero s = em.find(SuperHero.class, heroID);

        //Removing all Movie references from the selected Hero
        for (Movie movie : s.getMovies()) {
                s.removeMovie(movie);

        }
        em.remove(s);
        em.getTransaction().commit();
        em.close();

        System.out.println("<Hero successfully deleted>\n");

    }

    public void updateHero() {

        viewSuperHeroes();
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter the ID of the Hero you'd like to Update: ");
        int heroID = scanInt();

        em.getTransaction().begin();
        SuperHero s = em.find(SuperHero.class, heroID);

        while (true) {

            System.out.println("=================================");
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Power");
            System.out.println("=================================");
            System.out.print("Input: ");

            int choice = scanInt();

            if (choice == 1) {

                System.out.print("Enter a new Name for the Hero: ");
                String name = input.nextLine();
                s.setName(name);
                em.persist(s);
                em.getTransaction().commit();
                em.close();

                System.out.println("<Name successfully updated!>");

                return;

            } else if (choice == 2) {

                System.out.print("Enter a new Power for the Hero: ");
                String power = input.nextLine();
                s.setPower(power);
                em.persist(s);
                em.getTransaction().commit();
                em.close();

                System.out.println("<Super Power Successfully updated!>");

                return;

            } else {
                System.out.println("Invalid Input!");
            }

        }
    }

    public void updateMovie() {

        viewMovies();
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter the ID of the Movie you'd like to Update: ");
        int movieID = scanInt();

        em.getTransaction().begin();
        Movie m = em.find(Movie.class, movieID);

        System.out.print("Enter a new Title for the Movie: ");
        String title = input.nextLine();

        m.setTitle(title);
        em.persist(m);
        em.getTransaction().commit();
        em.close();

    }

    public void addHeroToExistingMovie() {

        EntityManager em = emf.createEntityManager();
        viewSuperHeroes();

        System.out.print("Enter the ID of the Hero you'd like to assign to a Movie: ");
        int heroID = scanInt();

        SuperHero hero = em.find(SuperHero.class, heroID);

        viewMovies();
        System.out.print("Enter the ID of the Movie you wish your hero to be assigned to: ");
        int movieID = scanInt();

        Movie updateMovie = em.find(Movie.class, movieID);
        updateMovie.addSuperHero(hero);

        em.getTransaction().begin();
        em.persist(hero);
        em.getTransaction().commit();
        em.close();

    }


    public int scanInt() {

        int input;

        while (true) {
            try {
                input = this.input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please input numerical data");
                this.input.nextLine();
            }
        }
        this.input.nextLine();
        return input;
    }

}
