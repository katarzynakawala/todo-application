package io.github.application.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
class TodoController {
    private final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final TodoService service;

    TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<TodoDTO>> findAllTodos() {
        //logger.info("Got request");
        List<TodoDTO> all = service.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    ResponseEntity<TodoDTO> findOneTodo(@PathVariable Integer id) {
        service.findById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<TodoId> toggleTodo(@PathVariable Integer id) {
        service.toggle(id);
        return ResponseEntity.ok(new TodoId(id));
    }

    @PostMapping
    ResponseEntity<TodoId> create(@Validated @RequestBody TodoDTO todo) {
        TodoDTO created = service.create(todo);
        return ResponseEntity.ok(new TodoId(created.getId()));
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteOneTodo(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
