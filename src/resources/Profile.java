package resources;

public class Profile {
    private int query_id;
    private double duration;
    private String query_string;

    public Profile(int query_id, double duration, String query_string) {
        this.setQuery_id(query_id);
        this.setDuration(duration);
        this.setQuery_string(query_string);
    }

    public int getQuery_id() {
        return query_id;
    }

    public void setQuery_id(int query_id) {
        this.query_id = query_id;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getQuery_string() {
        return query_string;
    }

    public void setQuery_string(String query_string) {
        this.query_string = query_string;
    }
}
