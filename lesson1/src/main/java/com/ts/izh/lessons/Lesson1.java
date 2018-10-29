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
        ProjectService userService = ctx.getBean(ProjectService.class);

        Auto autoMers = new Auto(1,"Бмв");
        userService.createAuto(autoMers);
        Auto autoVaz = new Auto(2,"Ваз2101");
        userService.createAuto(autoVaz);
        Auto autoNiva = new Auto(3,"Нива");
        userService.createAuto(autoNiva);

        User user1 = new User(1,"Иванов");
        userService.createUser(user1);
        User user2 = new User(2, "Петров");
        userService.createUser(user2);

        userService.getAllUsers().forEach((entry) -> System.out.println(entry.toString()));

        userService.addAtoToUser(user1, autoMers);
        userService.addAtoToUser(user1, autoVaz);


        userService.addAtoToUser(user2, autoMers);
        userService.addAtoToUser(user2, autoNiva);

        userService.getAllUsers().forEach((entry) -> System.out.println(entry.toString()));
    }
}
