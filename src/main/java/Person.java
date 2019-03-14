public class Person {

    private String id;
    private String name;
    private String nationality;
    private String age;
    private String gender;

    public Person(String id){
        this.id = id;
        name = null;
        nationality = null;
        age = null;
    }

    String getId() {
        return id;
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

    @Override
    public String toString() {
        return name + ":\n\tGender: " + gender + "\n\tAge: " + age + "\n\tNationality: " + nationality;
    }
}
