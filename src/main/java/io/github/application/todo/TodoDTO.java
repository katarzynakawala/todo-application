package io.github.application.todo;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
public class TodoDTO {
    private Integer id;
    @NotNull
    @Size(min = 2)
    private String text;
    private boolean done;


    public TodoDTO(Integer id, String text, boolean done) {
        this.id = id;
        this.text = text;
        this.done = done;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
