package movies;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class SuperHero {

    @Id
    @GeneratedValue
    private Integer ID;
    private String name;
    private String power;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable
    private final Set<Movie> movies = new HashSet<Movie>() {
    };

    public SuperHero() {

    }

    public SuperHero(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        movie.getSuperHeroList().add(this);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
        movie.getSuperHeroList().remove(this);
    }


    @Override
    public String toString() {
        return "ID: " + ID +
                "\nName: " + name +
                "\nSuper Power: " + power +
                "\nMovies: " + movies.stream().map(Movie::getTitle).collect(Collectors.toList()) + "\n";
    }
}
