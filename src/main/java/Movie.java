import java.util.List;

public class Movie {

    private String id;
    private String year;
    private String title;
    private String language;
    private String country;
    private String type;
    private List<Person> actors;
    private List<Person> directors;
    private List<Person> writers;

    public Movie(String id){
        this.id = id;
        year = null;
        title = null;
        language = null;
        country = null;
        type = null;
    }

    public Movie(String id, String title, String language, String year, String country, String type) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.language = language;
        this.country = country;
        this.type = type;
    }

    String getId() {
        return id;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setLanguage(String language) {
        this.language = language;
    }

    void setCountry(String country) {
        this.country = country;
    }

    void setType(String type) {
        if(type != null){
            if(this.type == null) this.type = type;
            else this.type += " " + type;
        }
    }

    void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        String def = title + ":\n\tType: " + type + "\n\tYear: " + year + "\n\tLanguage: " + language + "\n\tCountry: "+ country;
        return def;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retour = false;
        if(obj.getClass() == this . getClass()){
            if(((Movie)obj).getId().equals(id))
                retour = true;
        }
        return retour;
    }
}
