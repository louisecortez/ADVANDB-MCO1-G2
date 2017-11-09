package resources;

import java.util.ArrayList;

public class DynamicSet {
    private ArrayList<String> headers;
    private ArrayList<ArrayList<String>> data;

    public DynamicSet(ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
        this.setHeaders(headers);
        this.setData(data);
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }
}
