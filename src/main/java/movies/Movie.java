package movies;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Integer ID;
    private String title;

    @ManyToMany(mappedBy = "movies", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private final Set<SuperHero> superHeroList = new HashSet<>();

    public Movie() {

    }

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SuperHero> getSuperHeroList() {
        return superHeroList;
    }

    public void addSuperHero(SuperHero superHero) {
        superHeroList.add(superHero);
        superHero.getMovies().add(this);
    }

    public void removeSuperHero(SuperHero superHero) {
        superHeroList.remove(superHero);
        superHero.getMovies().remove(this);
    }

    @Override
    public String toString() {
        return "ID: " + ID +
                "\nTitle: " + title +
                "\nCast: " + superHeroList.stream().map(SuperHero::getName).collect(Collectors.toList()) + "\n";
    }
}
