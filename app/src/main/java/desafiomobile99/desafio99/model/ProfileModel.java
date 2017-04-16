package desafiomobile99.desafio99.model;

/**
 * Created by DT on 4/15/17.
 */

public class ProfileModel {

    private String id;
    private String name;
    private String image;
    private String birthday;
    private String bio;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass().equals(this.getClass())) {
            ProfileModel object = (ProfileModel) o;
            if(object.getId().equals(this.getId())) {
                return true;
            }
        }
        return false;
    }
}
