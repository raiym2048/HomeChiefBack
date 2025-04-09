package kg.nar.HomeChiefBack.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ObjectDto {
    private UUID id;
    String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
