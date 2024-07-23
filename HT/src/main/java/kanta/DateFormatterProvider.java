package kanta;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface DateFormatterProvider {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    default String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    default LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
}
