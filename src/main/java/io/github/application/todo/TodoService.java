package io.github.application.todo;

import io.github.application.exception.Http404Exception;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class TodoService {
    private final TodoRepository repository;
    private final TodoConverter todoConverter;

    TodoService(TodoRepository repository, TodoConverter todoConverter) {
        this.repository = repository;
        this.todoConverter = todoConverter;
    }


    List<TodoDTO> findAll() {
        return repository
                .findAll()
                .stream()
                .map(todoConverter::convert)
                .collect(toList());
    }

    public List<TodoDTO> findAll2() {
        List<TodoDTO> list = new ArrayList<>();
        for (TodoEntity todoEntity : repository
                .findAll()) {
            TodoDTO convert = todoConverter.convert(todoEntity);
            list.add(convert);
        }
        return list;
    }


    public void toggle(Integer id) {
        Optional<TodoEntity> todo = repository.findById(id);
        if (!todo.isPresent()) {
            throw new Http404Exception();
        }
        todo.ifPresent(t -> {
            t.setDone(!t.isDone());
            repository.save(t);
        });
    }

    public TodoDTO create(TodoDTO todo) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setText(todo.getText());

        TodoEntity saveEntity = repository.save(todoEntity);

        TodoDTO todoDTO = new TodoDTO(saveEntity.getId(), saveEntity.getText(), saveEntity.isDone());
        return todoDTO;

    }

    public Optional<TodoDTO> findById(Integer id) {
        Optional<TodoEntity> todo = repository.findById(id);
        if (!todo.isPresent()) {
            throw new Http404Exception();
        }
        return Optional.of(todoConverter.convert(todo.get()));
    }

    public void deleteById(Integer id) {
        Optional<TodoEntity> todo = repository.findById(id);
        if (!todo.isPresent()) {
            throw new Http404Exception();
        }
        todo.ifPresent(t -> {
            repository.deleteById(id);
        });
    }
}