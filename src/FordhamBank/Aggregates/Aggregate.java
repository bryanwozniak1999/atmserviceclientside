package FordhamBank.Aggregates;


import java.util.UUID;

public class Aggregate {
    protected UUID Id;

    protected Aggregate() {
        Id = UUID.randomUUID();
    }

    protected Aggregate(UUID Id) {
        this.Id = Id;
    }

    public UUID GetId() {
        return Id;
    }
}
