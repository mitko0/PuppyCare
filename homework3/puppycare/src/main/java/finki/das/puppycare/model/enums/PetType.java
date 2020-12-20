package finki.das.puppycare.model.enums;

public enum PetType {
    Other("Друго"),
    Dog("Куче"),
    Cat("Мачка"),
    Rabbit("Зајак"),
    Mouse("Глушец");

    public final String value;

    PetType(String value) {
        this.value = value;
    }
}