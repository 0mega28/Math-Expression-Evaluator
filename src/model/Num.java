package model;

public sealed interface Num {
    record Int(Long value) implements Num {
    }

    record Float(Double value) implements Num {
    }
}
