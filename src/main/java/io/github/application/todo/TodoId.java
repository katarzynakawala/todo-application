package io.github.application.todo;

public class TodoId {
    private String name = "Kasia";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TodoId(Integer id) {
        this.id = id;
    }
}
