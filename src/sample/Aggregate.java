package sample;

import java.util.UUID;

public class Aggregate {
    private UUID Id;

    protected Aggregate() {
        Id = UUID.randomUUID();
    }
}
