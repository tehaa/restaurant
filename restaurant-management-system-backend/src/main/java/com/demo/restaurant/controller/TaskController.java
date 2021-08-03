package com.demo.restaurant.controller;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * this class don't belong to the restaurant task
 * @author ahmed
 *
 */

@Entity
@Table(name = "task")
@JsonIgnoreProperties(ignoreUnknown = true)
class Task {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;

	@Column(name = "description")
	private String description;

	@Column(name = "priority")
	private long priority;

	public Task() {
		super();
	}

	public Task(long id, String description, long priority) {
		super();
		this.id = id;
		this.description = description;
		this.priority = priority;
	}

	public Task(String description, long priority) {
		super();
		this.description = description;
		this.priority = priority;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class Response {

	String message;
	int status;

	public Response() {
		super();
	}

	public Response(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + ", status=" + status + "]";
	}

}

@RestController
@RequestMapping("/api/tasks")
class TaskController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private TaskRepository taskRepository;
	
	public static final String TASK_NOT_FOUND="Cannot find task with given id" ;
	
	public static final String TASK_DESCRIPTION_NOT_FOUND="Task description is required" ;


	@RequestMapping(value = "{taskId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTask(@PathVariable long taskId, @RequestBody Task task) {
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if (!optionalTask.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Response(TASK_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
		}
		
		if(Objects.isNull(task.getDescription())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(TASK_DESCRIPTION_NOT_FOUND, HttpStatus.BAD_REQUEST.value()));
		}  else {
			Task updatedTask=optionalTask.get();
			updatedTask.setDescription(task.getDescription());
			updatedTask.setPriority(task.getPriority());
			taskRepository.save(updatedTask);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Task(updatedTask.getDescription(), updatedTask.getPriority()));
		}

	}

}

@Repository
interface TaskRepository extends JpaRepository<Task, Long> {

}
