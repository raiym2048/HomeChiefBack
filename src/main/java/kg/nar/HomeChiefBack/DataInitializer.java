package kg.nar.HomeChiefBack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
/*

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void run(String... args) throws Exception {
        // Проверяем наличие записей в таблице RequestStatus
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM request_status", Long.class);

        if (count == 0) {
            // Если таблица пуста, выполняем SQL скрипт
            System.out.println("No records found in RequestStatus, inserting default values...");

            // Читаем SQL скрипт из файла
            Path scriptPath = new ClassPathResource("data.sql").getFile().toPath();
            String sqlScript = Files.readString(scriptPath);

            // Выполняем SQL скрипт
            jdbcTemplate.execute(sqlScript);

            System.out.println("Default values inserted into RequestStatus.");
        } else {
            System.out.println("Records found in RequestStatus. No action needed.");
        }
    }
}
*/
