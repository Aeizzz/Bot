package javaBean;

/**
 *
 */
public class Results {

    private Location location;
    private Now now;
    private String last_update;
    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }

    public void setNow(Now now) {
        this.now = now;
    }
    public Now getNow() {
        return now;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
    public String getLast_update() {
        return last_update;
    }

}
