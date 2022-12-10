import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    public void test() {

        ArrayList<Field> lkwFields = makeFieldList(Lkw.class);
        System.out.println("Länge: " + lkwFields.size());
        for (Field feld : lkwFields) {
            System.out.println(feld.getName());
        }
    }
    public ArrayList<Field> makeFieldList(Class<?> Subclass) {
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(Subclass.getDeclaredFields()));
        fields.addAll(new ArrayList<>(Arrays.asList(Subclass.getSuperclass().getDeclaredFields())));
        return fields;
    }





}
