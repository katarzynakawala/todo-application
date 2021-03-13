package io.github.application.todo;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoEntityServiceTest {
    @Test
    public void checkingIfServiceWorksTest(){
        //given
        TodoRepository repository= Mockito.mock(TodoRepository.class);
        List<TodoEntity> todoEntities = new ArrayList<>();
        todoEntities.add(new TodoEntity(1, "text", true));
        Mockito.when(repository.findAll()).thenReturn(todoEntities);

        TodoConverter todoConverter = new TodoConverter();

        TodoService todoService = new TodoService(repository, todoConverter);
        //when
        List<TodoDTO> all = todoService.findAll2();
        //then
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getText()).isEqualTo("text");
        assertThat(all.get(0).getId()).isEqualTo(1);
        assertThat(all.get(0).isDone()).isTrue();
    }

    @Test
    public void gettingTodoByIdTest(){
        //given
        TodoRepository repository= Mockito.mock(TodoRepository.class);

        TodoEntity todoEntity = new TodoEntity(3, "testtest", false);
        Mockito.when(repository.findById(3)).thenReturn(Optional.of(todoEntity));

        TodoConverter todoConverter = new TodoConverter();
        TodoService todoService = new TodoService(repository, todoConverter);
        //when
        Optional<TodoDTO> optionalTodo = todoService.findById(3);
        TodoDTO todo = optionalTodo.get();
        //then
        assertThat(todo.getText()).isEqualTo("testtest");
        assertThat(todo.getId()).isEqualTo(3);
        assertThat(todo.isDone()).isFalse();
    }
}
