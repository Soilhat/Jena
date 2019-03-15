import java.util.ArrayList;
import java.util.List;

public class Person {

    private String id;
    private String name;
    private String nationality;
    private String age;
    private String gender;
    private String type;
    private List<Movie> actIn;
    private List<Movie> write;
    private List<Movie> direct;

    public Person(String id){
        this.id = id;
        name = null;
        nationality = null;
        age = null;
        type = null;
        actIn = null;
        write = null;
        direct = null;
    }

    public Person(String id, String name, String nationality, String age, String gender, String type) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.age = age;
        this.gender = gender;
        this.type = type;
        actIn = null;
        write = null;
        direct = null;
    }

    public List<Movie> getActIn() {
        return actIn;
    }

    public List<Movie> getWrite() {
        return write;
    }

    public List<Movie> getDirect() {
        return direct;
    }

    String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    void setAge(String age) {
        this.age = age;
    }

    void setName(String name) {
        this.name = name;
    }

    void setNationality(String nationality) {
        this.nationality = nationality;
    }

    void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        if(type != null){
            if(this.type == null) this.type = type;
            else this.type += " " + type;
        }
    }

    void addActIn(Movie movie){
        if(actIn == null) actIn = new ArrayList<Movie>();
        if(!actIn.contains(movie)) actIn.add(movie);
        if(!type.contains("Actor")) type += " Actor";
    }

    void addWriterOf(Movie movie){
        if(write == null) write = new ArrayList<Movie>();
        if(!write.contains(movie)) write.add(movie);
        if(!type.contains("Writer")) type += " Writer";
    }

    void addDirectorOf(Movie movie){
        if(direct == null) direct = new ArrayList<Movie>();
        if(!direct.contains(movie)) direct.add(movie);
        if(!type.contains("Director")) type += " Director";
    }


    @Override
    public String toString() {
        return name + ":" +
                ((type!= null)? "\n\tType: " + type : "")+
                ((gender!= null)? "\n\tGender: " + gender : "") +
                ((age!= null)?"\n\tAge: " + age : "") +
                ((nationality!= null)?"\n\tNationality: " + nationality : "");
    }

    @Override
    public boolean equals(Object obj) {
        boolean retour = false;
        if(obj.getClass() == this . getClass()){
            if(((Person)obj).getId().equals(id))
                retour = true;
        }
        return retour;
    }
}
