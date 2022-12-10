package katachi.spring.exercise.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import katachi.spring.exercise.application.UserApplicationService;
import katachi.spring.exercise.domain.user.model.MTask;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.service.UserService;
import katachi.spring.exercise.form.InputForm;
import katachi.spring.exercise.form.updateForm;

@Controller
public class TaskListController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserApplicationService userApplicationService;

	//作業一覧画面へ遷移

	//作業登録画面へ遷移
	@GetMapping("/task/input")
	public String getInput(Model model, @ModelAttribute InputForm form, @ModelAttribute MUser user) {
		//担当名のMapを作成する
		Map<String, Integer> userMap = userApplicationService.getUserMap();
		model.addAttribute("userMap", userMap);
		return "/task/input";
	}

	@PostMapping("/task/input")
	public String postInput(Model model, @ModelAttribute @Validated InputForm form, BindingResult bindingResult,
			@ModelAttribute MUser user) {

		MTask task = modelMapper.map(form, MTask.class);
		task.setTaskName(form.getTaskName());
		task.setUserId(form.getUserName());
		task.setExpireDate(form.getExpireDate());

		//入力チェック
		if (bindingResult.hasErrors()) {
			return getInput(model, form, user);
		}
		//登録処理
		//createDatetimeとupdateDateTimeがnullだからここで日付を入れるメソッドを使う
		int del = 0;
		task.setRegistrationDate(new Date());
		task.setCreateDateTime(new Date());
		task.setUpdateDateTime(new Date());

		task.setIsDeleted(del);
		if (form.getCheck() == null) {
			form.setCheck(0);
		}
		if (form.getCheck() == 1) {
			task.setFinshedDate(new Date());
			System.out.println("test182");
		}

		userService.inputTask(task);
		return "redirect:/task/list";
	}

	//作業更新画面へ遷移
	@GetMapping("/task/update/{id}")
	public String getUpdateTask(@PathVariable("id") String id, Model model, @ModelAttribute updateForm form) {
		Map<String, Integer> userMap = userApplicationService.getUserMap();
		MTask task = userService.getTaskOne(id);
		model.addAttribute("task", task);
		model.addAttribute("userMap", userMap);
		model.addAttribute("taskId", id);
		return "/task/update";
	}

	//作業更新
	@PostMapping("/task/update/{id}")
	public String postUpdateTask(Model model, @ModelAttribute @Validated updateForm form, BindingResult bindingResult) {

		String id = form.getId();
		MTask task = modelMapper.map(form, MTask.class);

		//入力チェック
		if (bindingResult.hasErrors()) {
			return getUpdateTask(id, model, form);
		}
		//登録処理
		//createDatetimeとupdateDateTimeがnullだからここで日付を入れるメソッドを使う
		int del = 0;
		task.setRegistrationDate(new Date());
		task.setUpdateDateTime(new Date());

		task.setIsDeleted(del);
		if (form.getCheck() == null) {
			form.setCheck(0);
		}
		if (form.getCheck() == 1) {
			task.setFinshedDate(new Date());
		}


		userService.updateTaskOne(task);
		return "redirect:/task/list";

	}

	//削除画面遷移
	@GetMapping("/task/deleted/{id}")
	public String getDeletedTask(@PathVariable("id") String id, Model model) {
		MTask task = userService.getTaskOne(id);
		model.addAttribute("task", task);
		return "/task/deleted";
	}

	//削除処理
	@GetMapping("/task/deleted/try/{id}")
	public String deletedTask(@PathVariable("id") String id) {
		userService.deleteOne(id);
		return "redirect:/task/list";
	}

	//完了処理
	@GetMapping("/task/finshed/{id}")
	public String finshedTask(@PathVariable("id") String id, Model model) {
		Date finshedDate = new Date();
		model.addAttribute("finshedDate", finshedDate);
		model.addAttribute("taskId", id);
		userService.finshedTask(model);
		return "redirect:/task/list";
	}

	//未完了処理
	@GetMapping("/task/remove/{id}")
	public String removedTask(@PathVariable("id") String id) {
		userService.removedTask(id);
		return "redirect:/task/list";
	}

	//検索処理と画面遷移
	@GetMapping(value = "task/list")
	public String searchTask(@RequestParam(required = false) String taskName, Model model) {
		List<MTask> taskList = userService.getTasks(taskName);
		model.addAttribute("searchword", taskName);
		model.addAttribute("taskList", taskList);
		return "/task/list";
	}

}
