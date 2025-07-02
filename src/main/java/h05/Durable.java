package h05;

import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
public interface Durable {

    double getDurability();

    void setDurability(double durability);

    void reduceDurability(double amount);
}
