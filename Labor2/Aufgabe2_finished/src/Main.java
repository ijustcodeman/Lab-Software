/**
 * Diese Klasse Demonstriert anhand von Beispielen die Unterschiede zwischen Wert- und Verweistypen
 * sowie von Boxing und Unboxing.
 * Weitere Informationen finden Sie in der Textdatei "unterschiede.txt".
 * @author Max Gebert, 21513
 */
public class Main {

    public static void main(String[] args){

        int primitiveInt = 42;           // primitiver Werttyp
        Integer boxedInt = primitiveInt; // Autoboxing: int -> Integer

        int unboxedInt = boxedInt;       // Unboxing: Integer -> int

        System.out.println("Boxed Integer: " + boxedInt);
        System.out.println("Unboxed int: " + unboxedInt);
    }
}
