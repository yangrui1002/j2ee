package pojo;

/**
 * Created by YR on 2018/11/30.
 */
public class User {
    private String name;
    private String emil;
    private String password;
    private int is_admin;
    private String id;
    private double money;

    public String getId() {
        return id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(){}

    public User(String name, String emil, String password, int is_admin) {
        this.name = name;
        this.emil = emil;
        this.password = password;
        this.is_admin = is_admin;
    }

    public User(String name, String emil, String password) {
        this.name = name;
        this.emil = emil;
        this.password = password;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmil() {
        return emil;
    }

    public void setEmil(String emil) {
        this.emil = emil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", emil='" + emil + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
