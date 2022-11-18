package katachi.spring.exercise.domain.user.service;

import java.util.List;

import org.springframework.ui.Model;

import katachi.spring.exercise.domain.user.model.MTask;
import katachi.spring.exercise.domain.user.model.MUser;

public interface UserService {

	public void inputTask(MTask task);

	public List<MTask> getTasks(String taskName);

	public List<MUser> getUsers();

	public void updateTaskOne(MTask task);

	public MTask getTaskOne(String id);

	public void deleteOne(String id);

	public void finshedTask(Model model);

	public void removedTask(String id);

}
