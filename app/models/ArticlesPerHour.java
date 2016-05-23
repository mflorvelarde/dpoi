package models;

/**
 * Created by florenciavelarde on 22/5/16.
 */
public class ArticlesPerHour {
    public int hour;
    public int count;

    public ArticlesPerHour (int hour, int count) {
        this.hour = hour;
        this.count = count;
    }

    public void addToCount() {
        count += 1;
    }
}



