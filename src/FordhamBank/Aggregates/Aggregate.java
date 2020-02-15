package FordhamBank.Aggregates;

import java.util.UUID;

public class Aggregate {
    private UUID Id;

    protected Aggregate() {
        Id = UUID.randomUUID();
    }

    public UUID GetId() {
        return Id;
    }
}
