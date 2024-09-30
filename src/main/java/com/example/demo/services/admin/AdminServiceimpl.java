package com.example.demo.services.admin;

import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.TaskStatus;
import com.example.demo.repos.TaskRepo;
import com.example.demo.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceimpl implements AdminService {

    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().filter(user -> user.getRole().name().equals("EMPLOYEE"))
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto postTask(TaskDto taskDto) {
        Optional<User> user = userRepo.findById(taskDto.getEmployeeId());
        System.out.println(user);
        if(user.isPresent()){
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setTaskStatus(TaskStatus.IN_PROGRESS);
            task.setDueDate(taskDto.getDueDate());
            task.setPriority(taskDto.getPriority());
            task.setUser(user.get());
            return taskRepo.save(task).getTaskDTO();
        }
        return null;
    }



}
