package io.github.application.todo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "todos")
class TodoEntity {
    @Id
    @GeneratedValue(generator = "inc", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "inc", strategy = "increment")


    private Integer id;
    private String text;
    private boolean done;

    public TodoEntity(Integer id, String text, boolean done) {
        this.id = id;
        this.text = text;
        this.done = done;
    }

    public TodoEntity() {
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
