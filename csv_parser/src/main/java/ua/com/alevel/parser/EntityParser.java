package ua.com.alevel.parser;

import ua.com.alevel.entity.BaseEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntityParser {

    private EntityParser() {

    }

    public static <E extends BaseEntity> List<String> entToString(E entity) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        Field[] parentFields = entity.getClass().getSuperclass().getDeclaredFields();
        List<String> values = new ArrayList<>();

        for (Field field : parentFields) {
            field.setAccessible(true);
            values.add(String.valueOf(field.get(entity)));
        }
        for (Field field : fields) {
            field.setAccessible(true);
            values.add(String.valueOf(field.get(entity)));
        }
        return values;
    }

    public static <E extends BaseEntity> List<String> getFields(Class<E> entClass) {
        //получаем поля класса сущности
        Field[] fields = entClass.getDeclaredFields();
        Field[] parentFields = entClass.getSuperclass().getDeclaredFields();
        List<String> titles = new ArrayList<>();

        for (Field field : parentFields) {
            titles.add(field.getName());
        }
        for (Field field : fields) {
            titles.add(field.getName());
        }
        return titles;
    }

    public static <E extends BaseEntity> List<E> toEntities(List<List<String>> file, Class<E> entity) {
        try {
            //получаем поля класса сущности
            Field[] parentFields = entity.getSuperclass().getDeclaredFields();
            Field[] fields = entity.getDeclaredFields();
            Field[] total = Arrays.copyOf(parentFields, parentFields.length + fields.length);
            System.arraycopy(fields, 0, total, parentFields.length, fields.length);

            List<E> result = new ArrayList<>();
            Constructor<?> construct = entity.getDeclaredConstructor();

            for (List<String> line : file) {
                List<String> ids = new ArrayList<>();
                E ent = (E) construct.newInstance();
                String current = line.get(0);
                //разбиваем на лексемы
                String[] lexems = current.split(",");
                for (int i = 0; i < lexems.length; ++i) {
                    if (i < 4) total[i].setAccessible(true);
                    if (isInteger(lexems[i])) {
                        total[i].set(ent, Integer.parseInt(lexems[i]));
                    }
                    //дальше идут ИД курсов/студентов
                    else if (i > 2) {
                        //удаляем символы свойственные csv и спискам + пробелы
                        lexems[i] = lexems[i].replaceAll("\\[", "");
                        lexems[i] = lexems[i].replaceAll("]", "");
                        lexems[i] = lexems[i].replaceAll("\"", "");
                        lexems[i] = lexems[i].replaceAll(" ", "");
                        ids.add(lexems[i]);
                    } else {
                        total[i].set(ent, lexems[i]);
                    }
                }
                total[total.length - 1].set(ent, ids);
                result.add(ent);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
