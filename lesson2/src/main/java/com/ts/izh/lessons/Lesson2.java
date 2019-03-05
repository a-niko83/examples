package com.ts.izh.lessons;

import com.ts.izh.lessons.config.SpringConfig;
import com.ts.izh.lessons.domain.Auto;
import com.ts.izh.lessons.domain.User;
import com.ts.izh.lessons.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Slf4j
public class Lesson2 {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        ProjectService projectService = ctx.getBean(ProjectService.class);

        // Чистим БД
        projectService.removeAllAutos();
        projectService.removeAllUsers();
        // Добавляем водителей
        User user1 = User.builder().name("Иванов").build();
        User user2 = User.builder().name("Петров").build();
        projectService.createUser(user1);
        projectService.createUser(user2);
        log.info("Водители:");
        projectService.getAllUsers().forEach((entry) -> log.info(entry.toString()));
        // Добавляем автомобили
        Auto auto1 = Auto.builder().model("Бмв").build();
        projectService.createAuto(auto1);
        Auto auto2 = Auto.builder().model("ВАЗ2110").build();
        projectService.createAuto(auto2);
        Auto auto3 = Auto.builder().model("Нива").build();
        projectService.createAuto(auto3);
        log.info("Автомобили в гараже:");
        projectService.getAllAutos().forEach((entry) -> log.info(entry.toString()));
        // Назначаем водителей автомобилей
        projectService.addAtoToUser(user1, auto1);
        projectService.addAtoToUser(user1, auto2);
        projectService.addAtoToUser(user2, auto1);
        projectService.addAtoToUser(user2, auto3);
        log.info("Автомобили в гараже:");
        projectService.getAllAutos().forEach((entry) -> log.info(entry.toString()));
    }
}
