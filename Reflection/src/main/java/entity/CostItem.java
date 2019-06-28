package entity;

import lombok.Data;
import lombok.Getter;

@Data
public class CostItem {

    private String type;
    private int code;
    private int amount;

    @Override
    public String toString() {
        return "CostItem{" +
                "type='" + type + '\'' +
                ", code=" + code +
                ", amount=" + amount +
                '}';
    }
}
