package bot;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateTimeUtil {

    private static final String DAY_MONTH_YEAR_PATTERN = "dd-MM-yyyy";

    private DateTimeUtil() {
    }

    public static String convertToString(final LocalDate localDate) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_PATTERN);
        return localDate.format(formatter);
    }

    public static LocalDate convertToDate(final String dateAsText) {
        String[] mas = dateAsText.split(":");
        return LocalDate.parse(mas[mas.length - 1], DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_PATTERN));
    }
}