package com.taskbuzz.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.taskbuzz.entities.CategoryFactory;
import com.taskbuzz.entities.Todo;
import com.taskbuzz.entities.User;
import com.taskbuzz.mediator.ServiceMediator;
import com.taskbuzz.mediator.ServiceMediatorImpl;
import com.taskbuzz.repository.TodoRepository;
import com.taskbuzz.repository.UserRepository;
import com.taskbuzz.todoapp.request.AddToDoRequest;
import com.taskbuzz.todoapp.request.UpdateToDoRequest;



@Service
public class ToDoService {

    @Autowired 
	private UserRepository userRepository;
    @Autowired 
	private TodoRepository todoRepository;
    @Autowired
	private UserService userService;

	
    @Autowired
    private PasswordEncoder passwordEncoder;

	public Todo getToDoById(Long todoId) {
		return todoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
	}

	public User addToDoList(Long userId, AddToDoRequest todoRequest) {
		
		ServiceMediator serviceMediator = new ServiceMediatorImpl(userService);
		User user = serviceMediator.getUser(userId);
		Todo todo = new Todo();
		todo.setTask(todoRequest.getTask());
		todo.setDueDate(todoRequest.getDueDate());
		todo.setPriority(todoRequest.getPriority());
		String category = new CategoryFactory().getCategoryType(todoRequest.getCategory());
		todo.setCategory(category);
		user.getTodoList().add(todo);
		
		return userRepository.save(user);
	}

	public Todo toggleToDoCompleted(Long todoId) {
		Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
		todo.setCompleted(!todo.getCompleted());
		return todoRepository.save(todo);
	}

	public void deleteToDo(@PathVariable Long userId, @PathVariable Long todoId) {
		ServiceMediator serviceMediator = new ServiceMediatorImpl(userService);
		User user = serviceMediator.getUser(userId);
		Todo todo = getToDoById(todoId);
		user.getTodoList().remove(todo);
		todoRepository.delete(todo);
	}

	public void updateToDoById(Long todoId, UpdateToDoRequest updatetodorequest) {
		Todo todo = getToDoById(todoId);
		if (todo != null) {
			if (updatetodorequest.getDueDate() != null && updatetodorequest.getTask() != null
					&& updatetodorequest.getPriority() != null) {
				todo.setDueDate(updatetodorequest.getDueDate());
				todo.setTask(updatetodorequest.getTask());
				todo.setPriority(updatetodorequest.getPriority());
			} else if (updatetodorequest.getDueDate() != null) {
				todo.setDueDate(updatetodorequest.getDueDate());
			} else if (updatetodorequest.getTask() != null) {
				todo.setTask(updatetodorequest.getTask());
			} else if (updatetodorequest.getPriority() != null) {
				todo.setPriority(updatetodorequest.getPriority());
			}
			todoRepository.save(todo);
		}
	}
}
