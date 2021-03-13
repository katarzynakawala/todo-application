package io.github.application.todo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TodoConverter implements Converter<TodoEntity, TodoDTO> {

    public TodoDTO convert(TodoEntity todoEntity){
        return TodoDTO.builder()
        .id(todoEntity.getId())
        .text(todoEntity.getText())
        .done(todoEntity.isDone())
                .build();
    }
}
