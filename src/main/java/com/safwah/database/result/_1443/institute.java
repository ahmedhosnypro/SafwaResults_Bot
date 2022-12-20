package com.safwah.database.result._1443;

import com.safwah.Main;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class institute {
    public static void main(String[] args) {
        var names = getUsers();
        updateFst(names);
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource instituteDataSource = new SQLiteDataSource();
    public static final Connection instituteConnection;

    static {
        instituteDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + "institute.db");
        try {
            instituteConnection = instituteDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static private Map<String, String> getUsers() {
        String selectQuery = """
                Select name, code1 from f1
                """;

        Map<String, String> names = new LinkedHashMap<>();

        try (Statement statement = instituteConnection.createStatement()) {
            var resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                names.put(resultSet.getString("name"), resultSet.getString("code1"));
            }
        } catch (SQLException e) {
            return new LinkedHashMap<>();
        }
        return names;
    }

    static private void updateFst(Map<String, String> names) {
        int count = 0;

        for (var entry : names.entrySet()) {
            String getResultQuery = String.format("""
                            SELECT *
                            FROM f2
                            where trim(
                                          replace(
                                                  replace(
                                                          replace(
                                                                  replace(
                                                                          replace(
                                                                                  replace(
                                                                                          replace(
                                                                                                  replace(name, 'إ', 'ا'),
                                                                                                  'أ', 'ا'),
                                                                                          'ى', 'ي'),
                                                                                  'ة', 'ه'),
                                                                          'ؤ', 'و'),
                                                                  'آ', 'ا'),
                                                          '  ', ' '),
                                                  '   ', ' ')) = '%s'
                                                  Or (code = '%s' And code != '0')
                               """, entry.getKey().replaceAll("[أإآ]", "ا").replaceAll("ؤ", "و").replaceAll("ى", "ي").replaceAll("ة", "ه"),
                    entry.getValue());

            try (Statement statement = instituteConnection.createStatement()) {
                var resultSet = statement.executeQuery(getResultQuery);
                if (resultSet.next()) {
                    String name = resultSet.getString(1);
                    String q = String.format("""
                                    UPDATE f1
                                    SET C20 =  '%s', C21 =  '%s', C22 =  '%s', C23 =  '%s', C24 =  '%s', C25 =  '%s', C26 =  '%s', C27 =  '%s', C28 =  '%s', C29 =  '%s', C30 =  '%s', C31 =  '%s', C32 =  '%s', C33 =  '%s', C34 =  '%s', C35 =  '%s', C36 =  '%s', C37 =  '%s', C38 =  '%s', C39 =  '%s', C40 =  '%s', C41 =  '%s', C42 =  '%s', C43 =  '%s'
                                    where name = '%s'
                                    or code1 = '%s'
                                    """, name, resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getString(15), resultSet.getString(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), resultSet.getString(21), resultSet.getString(22), resultSet.getString(23), resultSet.getString(24),
                            entry.getKey(), entry.getValue());
                    statement.executeUpdate(q);

                    if (entry.getKey().length() != name.length()) {
                        System.out.println(count + "- " + entry.getKey() + "   ||   " + name);
                    }
                    count++;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println(count);
    }
}
