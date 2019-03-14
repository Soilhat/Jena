public class Movie {

    private String id;
    private String year;
    private String title;
    private String language;
    private String country;
    private String type;

    public Movie(String id){
        this.id = id;
        year = null;
        title = null;
        language = null;
        country = null;
        type = null;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setType(String type) {
        if(this.type == null )
            this.type = type;
        else {
            if(!this.type.contains(type))
                this.type += ", " + type;
        }
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        String def = title + ":\n\tType: " + type + "\n\tYear: " + year + "\n\tLanguage: " + language + "\n\tCountry: "+ country;
        return def;
    }
}
