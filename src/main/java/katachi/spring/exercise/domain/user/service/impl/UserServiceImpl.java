package katachi.spring.exercise.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import katachi.spring.exercise.domain.user.model.MTask;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.service.UserService;
import katachi.spring.exercise.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;

	@Override
	public void inputTask(MTask task) {
		mapper.insertOne(task);
	}

	@Override
	public List<MTask> getTasks(String taskName) {
		return mapper.findMany(taskName);
	}

	@Override
	public List<MUser> getUsers() {
		return mapper.findUser();
	}

	@Override
	public void updateTaskOne(MTask task) {
		mapper.updateOne(task);
	}

	@Override
	public MTask getTaskOne(String id) {
		return mapper.findTask(id);
	}

	@Override
	public void deleteOne(String id) {
		mapper.deleteOne(id);
	}

	@Override
	public void finshedTask(Model model) {
		mapper.finshedTask(model);
	}

	@Override
	public void removedTask(String id) {
		mapper.removedTask(id);
	}

}
