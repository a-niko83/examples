package com.ts.izh.lessons;

import com.ts.izh.lessons.config.SpringConfig;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.service.ProjectService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Lesson1 {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        ProjectService projectService = ctx.getBean(ProjectService.class);

        // Чистим БД
        projectService.removeAllAutos();
        projectService.removeAllUsers();

        // Добавляем автомобили
        Auto autoMers = new Auto(1,"Бмв");
        projectService.createAuto(autoMers);
        Auto autoVaz = new Auto(2,"Ваз2101");
        projectService.createAuto(autoVaz);
        Auto autoNiva = new Auto(3,"Нива");
        projectService.createAuto(autoNiva);
        System.out.println("Автомобили в гараже:");
        projectService.getAllAutos(null).forEach((entry) -> System.out.println(entry.toString()));

        // Добавляем водителей
        User user1 = new User(1,"Иванов");
        projectService.createUser(user1);
        User user2 = new User(2, "Петров");
        projectService.createUser(user2);
        System.out.println("Водители:");
        projectService.getAllUsers().forEach((entry) -> System.out.println(entry.toString()));

        // Назначаем водителей автомобилей
        projectService.addAtoToUser(user1, autoMers);
        projectService.addAtoToUser(user1, autoVaz);
        projectService.addAtoToUser(user2, autoMers);
        projectService.addAtoToUser(user2, autoNiva);
        System.out.println("Водители:");
        projectService.getAllUsers().forEach((entry) -> System.out.println(entry.toString()));

        System.out.println("Автомобили в гараже:");
        projectService.getAllAutos(null).forEach((entry) -> System.out.println(entry.toString()));
    }
}
