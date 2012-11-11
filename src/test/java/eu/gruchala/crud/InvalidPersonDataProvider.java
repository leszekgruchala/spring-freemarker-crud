package eu.gruchala.crud;

import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.DataProvider;

public class InvalidPersonDataProvider {

    @DataProvider
    public static Object[][] invalidNameDataProvider() {
        return new String[][]{
            {""},
            {null},
            {"as"},
            {"franek"}
        };
    }

    @DataProvider
    public static Object[][] invalidBirthDateDataProvider() {
        final Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 1);
        final Date tomorrow = new Date(today.getTimeInMillis());

        return new Date[][]{
            {null},
            {tomorrow}
        };
    }

    @DataProvider
    public static Object[][] invalidEmailDataProvider() {
        return new String[][]{
            {""},
            {null},
            {"123@.pl"},
            {"@.pl"},
            {"asd@"}
        };
    }
}
