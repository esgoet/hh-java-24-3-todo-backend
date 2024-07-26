package com.github.esgoet.hhjava243todobackend.service;

import com.github.esgoet.hhjava243todobackend.model.NewToDoDto;
import com.github.esgoet.hhjava243todobackend.model.Status;
import com.github.esgoet.hhjava243todobackend.model.ToDo;
import com.github.esgoet.hhjava243todobackend.repository.ToDoRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoServiceTest {
    ToDoRepo toDoRepo = mock(ToDoRepo.class);
    IdService idService = mock(IdService.class);
    ToDoService service = new ToDoService(toDoRepo, idService);

    @Test
    void findToDosTest_whenNoToDos() {
        //WHEN
        List<ToDo> actual = service.findToDos();

        //THEN
        List<ToDo> expected = new ArrayList<>();
        assertEquals(expected,actual);

    }

    @Test
    void findToDosTest_whenToDosExist() {
        //GIVEN
        when(toDoRepo.findAll()).thenReturn(List.of(new ToDo("1", "Clean", Status.OPEN)));

        //WHEN
        List<ToDo> actual = service.findToDos();

        //THEN
        List<ToDo> expected = List.of(new ToDo("1", "Clean", Status.OPEN));
        verify(toDoRepo).findAll();
        assertEquals(expected,actual);
    }


    @Test
    void findToDoTest_whenToDoExists() {
        //GIVEN
        when(toDoRepo.findById("1")).thenReturn(Optional.of(new ToDo("1", "Clean", Status.OPEN)));
        //WHEN
        ToDo actual = service.findToDo("1");
        //THEN
        ToDo expected = new ToDo("1", "Clean", Status.OPEN);
        verify(toDoRepo).findById("1");
        assertEquals(expected, actual);
    }

    @Test
    void findToDoTest_whenNoToDoExists() {
        //GIVEN
        when(toDoRepo.findById("1")).thenReturn(Optional.empty());
        //THEN
        assertThrows(NoSuchElementException.class,
                //WHEN
                () -> service.findToDo("1"));
        verify(toDoRepo).findById("1");
    }

    @Test
    void saveToDoTest() {
        //GIVEN
        when(idService.generateId()).thenReturn("1");
        NewToDoDto newToDoDto = new NewToDoDto("Clean");
        ToDo newToDo = new ToDo("1", "Clean", Status.OPEN);
        when(toDoRepo.save(newToDo)).thenReturn(newToDo);
        //WHEN
        ToDo actual = service.saveToDo(newToDoDto);
        //THEN
        ToDo expected = new ToDo("1", "Clean", Status.OPEN);
        verify(idService).generateId();
        verify(toDoRepo).save(newToDo);
        assertEquals(expected, actual);
    }

    @Test
    void updateToDoTest_whenToDoExists() {
        //GIVEN
        ToDo existingToDo = new ToDo("1", "Clean", Status.OPEN);
        ToDo updatedToDo = new ToDo("1","Clean", Status.IN_PROGRESS);
        when(toDoRepo.findById("1")).thenReturn(Optional.of(existingToDo));
        when(toDoRepo.save(updatedToDo)).thenReturn(updatedToDo);
        //WHEN
        ToDo actual = service.updateToDo("1", updatedToDo);
        //THEN
        ToDo expected = new ToDo("1", "Clean", Status.IN_PROGRESS);
        verify(toDoRepo).findById("1");
        verify(toDoRepo).save(updatedToDo);
        assertEquals(expected, actual);
    }

    @Test
    void updateToDoTest_whenNoToDoExists() {
        //GIVEN
        ToDo updatedToDo = new ToDo("1","Clean", Status.IN_PROGRESS);
        when(toDoRepo.findById("1")).thenReturn(Optional.empty());

        //THEN
        assertThrows(NoSuchElementException.class,
                //WHEN
                () -> service.updateToDo("1", updatedToDo));

        verify(toDoRepo).findById("1");
    }

    @Test
    void deleteToDoTest() {
        //GIVEN
        doNothing().when(toDoRepo).deleteById("1");
        //WHEN
        service.deleteToDo("1");
        //THEN
        verify(toDoRepo).deleteById("1");
    }
}