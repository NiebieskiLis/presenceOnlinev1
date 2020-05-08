package database;

import lombok.Data;
import javax.persistence.*;
@Entity
@Data
public class Test{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private int pole1;
    public int getPole1() {
        return pole1;
    }
    public void setPole1(int pole1) {
        this.pole1 = pole1;
    }
}