package com.eztornado.tornadocorebase.config;

import com.eztornado.tornadocorebase.annotation.EveryDay;
import com.eztornado.tornadocorebase.annotation.EveryHour;
import com.eztornado.tornadocorebase.annotation.EveryMinute;
import com.eztornado.tornadocorebase.tasks.DeleteExpiredSessionsTask;
import com.eztornado.tornadocorebase.tasks.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.quartz.CronExpression;

import java.util.concurrent.TimeUnit;

@Component
public class CustomTaskScheduler {

    private final DeleteExpiredSessionsTask deleteExpiredSessionsTask;

    @Autowired
    public CustomTaskScheduler(
            DeleteExpiredSessionsTask deleteExpiredSessionsTask) {
        this.deleteExpiredSessionsTask = deleteExpiredSessionsTask;
    }

    @PostConstruct
    public void scheduleTasks() {
        scheduleTasksWithAnnotation(EveryMinute.class, deleteExpiredSessionsTask);
    }

    private void scheduleTasksWithAnnotation(Class<? extends Annotation> annotationClass, Task task) {
        Method[] methods = task.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String cronExpression = translateAnnotationToCron(method.getAnnotation(annotationClass));
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            try {

                long initialDelay = this.getDelayFromAnnotation(method.getAnnotation(annotationClass));

                scheduler.scheduleAtFixedRate(() -> {
                            try {
                                Date date = new Date();
                                System.out.println(date.toString() +  " TCScheduler - Executing task: " + task.getClass().getName() + " with annotation: " + method.getAnnotation(annotationClass).toString());
                                method.invoke(task);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, new CronExpression(cronExpression)
                                .getNextValidTimeAfter(new Date())
                                .getTime() - System.currentTimeMillis(),  // Aquí se pasa la diferencia de tiempo en milisegundos
                        initialDelay, TimeUnit.MILLISECONDS);  // Esto especifica la unidad de tiempo para la diferencia
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String translateAnnotationToCron(Annotation annotation) {
        // Aquí, traduce la anotación personalizada a una expresión cron válida
        // Implementa la lógica según tus anotaciones personalizadas
        if (annotation instanceof EveryDay) {
            EveryDay everyDay = (EveryDay) annotation;
            int interval = everyDay.value(); // Obtén el valor del intervalo de días
            if (interval > 0) {
                // La expresión cron para ejecutar cada X días sería "0 0 0 */X * ?"
                return "0 0 0 */" + interval + " * ?";
            }
        } else if (annotation instanceof EveryHour) {
            EveryHour everyHour = (EveryHour) annotation;
            int interval = everyHour.value(); // Obtén el valor del intervalo de horas
            if (interval > 0) {
                // La expresión cron para ejecutar cada X horas sería "0 0 */X * * ?"
                return "0 0 */" + interval + " * * ?";
            }
        } else if (annotation instanceof EveryMinute) {
            EveryMinute everyMinute = (EveryMinute) annotation;
            int interval = everyMinute.value(); // Obtén el valor del intervalo de minutos
            if (interval > 0) {
                // La expresión cron para ejecutar cada X minutos sería "0 */X * ? * *"
                return "0 */" + interval + " * ? * *";
            }
        }
        // Implementa más casos según tus necesidades
        return null;
    }

    private long getDelayFromAnnotation(Annotation annotation) {
        // Aquí, traduce la anotación personalizada a una expresión cron válida
        // Implementa la lógica según tus anotaciones personalizadas
        if (annotation instanceof EveryDay) {
            return 86400000 * ((EveryDay) annotation).value();
        } else if (annotation instanceof EveryHour) {
            return 3600000 * ((EveryHour) annotation).value();
        } else if (annotation instanceof EveryMinute) {
            return 60000 * ((EveryMinute) annotation).value();
        }
        // Implementa más casos según tus necesidades
        return 60000;
    }
}
