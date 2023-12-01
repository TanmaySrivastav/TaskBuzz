package com.taskbuzz.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.taskbuzz.entities.Priority;
import com.taskbuzz.entities.Todo;
import com.taskbuzz.entities.User;
import com.taskbuzz.mediator.ServiceMediator;
import com.taskbuzz.mediator.ServiceMediatorImpl;
import com.taskbuzz.repository.TodoRepository;
import com.taskbuzz.repository.UserRepository;
import com.taskbuzz.request.AddToDoRequest;
import com.taskbuzz.request.UpdateToDoRequest;




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

	public ToDoService(UserRepository userRepository2, TodoRepository todoRepository2, UserService userService2) {

	}


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

	public User deleteToDo(@PathVariable Long userId, @PathVariable Long todoId) {
		ServiceMediator serviceMediator = new ServiceMediatorImpl(userService);
		User user = serviceMediator.getUser(userId);
		Todo todo = getToDoById(todoId);
		user.getTodoList().remove(todo);
		todoRepository.delete(todo);
		return user;
	}

	public void updateToDoById(Long todoId, UpdateToDoRequest updatetodorequest) {
		Todo todo = getToDoById(todoId);
		if (todo != null) {
			if (updatetodorequest.getDueDate() != null && updatetodorequest.getTask() != null
					&& updatetodorequest.getPriority() != null) {
				todo.setDueDate(updatetodorequest.getDueDate());
				todo.setTask(updatetodorequest.getTask());
				
				//setting the Priority with Decorator and Command
				Priority newPriority = getPriorityFromDecorators(todo, updatetodorequest.getPriority());
				setPriorityWithCommand(todo, newPriority);
			} else if (updatetodorequest.getDueDate() != null) {
				todo.setDueDate(updatetodorequest.getDueDate());
			} else if (updatetodorequest.getTask() != null) {
				todo.setTask(updatetodorequest.getTask());
			} else if (updatetodorequest.getPriority() != null) {
				//setting the Priority with Decorator and Command
				Priority newPriority = getPriorityFromDecorators(todo, updatetodorequest.getPriority());
				setPriorityWithCommand(todo, newPriority);
			}
			todoRepository.save(todo);
		}
	}
	
	// Get Priority Object from Decorator
	public Priority getPriorityFromDecorators(Todo todo, Priority priority) {
		switch (priority.getPriorityLevel()) {
		case 1: // Todo Object is Decorated with a High Priority
			return new WithHighPriority(todo).getPriority();
		case 2: // Todo Object is Decorated with a Medium Priority
			return new WithMediumPriority(todo).getPriority();
		case 3: // Todo Object is Decorated with a Low Priority
			return new WithLowPriority(todo).getPriority();
		default: // Todo Object is Decorated with a Low Priority By Default
			return new WithLowPriority(todo).getPriority();
		}
	}

	// Set Priority to Task with Command Pattern
	public void setPriorityWithCommand(Todo todo, Priority priority) {
		CommandInvoker invoker = new CommandInvoker();
		// Concrete Command Object Instance is created using Receiver - Todo Object
		SetHighPriorityToTodoCommand highPriorityToTodoCommand = new SetHighPriorityToTodoCommand(todo);
		SetMediumPriorityToTodoCommand mediumPriorityToTodoCommand = new SetMediumPriorityToTodoCommand(todo);
		SetLowPriorityToTodoCommand lowPriorityToTodoCommand = new SetLowPriorityToTodoCommand(todo);

		// Type of Command is set with CommandInvoker
		switch (priority.getPriorityLevel()) {
		case 1:
			invoker.setCommand(highPriorityToTodoCommand);
			break;
		case 2:
			invoker.setCommand(mediumPriorityToTodoCommand);
			break;
		case 3:
		default:
			invoker.setCommand(lowPriorityToTodoCommand);
		}
		// Command is invoked to set the priority to Todo
		invoker.invokeCommand();
	}
}
