package com.siestech.booksharing.Model;

public class User {
    private String name;
    private String profession;
    private String email;
    private String image;
    private String userId;

    public User() { }

    public User(String name, String profession, String email) {
        this.name = name; this.profession = profession; this.email = email;
    }
    // Kept only for source compatibility with older code; password is intentionally not stored.
    public User(String name, String profession, String email, String ignoredPassword) {
        this(name, profession, email);
    }
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getProfession(){return profession;}
    public void setProfession(String profession){this.profession=profession;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}
    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId=userId;}
}
