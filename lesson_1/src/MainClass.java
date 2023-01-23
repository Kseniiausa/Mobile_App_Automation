//     1. Сделать класс MainClass.
//        В классе MainClass сделать метод, который возвращает число 14 (назвать: getLocalNumber).
//        Сделать класс MainClassTest.
//        В классе MainClassTest написать тест, проверяющий, что метод getLocalNumber возвращает число 14 (назвать: testGetLocalNumber).



public class MainClass {

    int getLocalNumber (){
        return 14;
    }

// 2. Сделать в классе MainClass приватное поле класса, которое равно 20 (назвать: class_number),
//    и публичный метод (getClassNumber), который эту переменную возвращает.
//    Сделать класс MainClassTest (если еще нет).
//    В классе MainClassTest написать тест (назвать testGetClassNumber), который проверяет,
//    что метод getClassNumber возвращает число больше 45.

    private int class_number = 20;
    public int getClassNumber (){
        return class_number;
    }

// 3. Сделать в классе MainClass приватное поле класса, которое равно строке “Hello, world” (назвать: class_string),
//    и публичный метод (назвать: getClassString), который возвращает строку.
//    Сделать класс MainClassTest (если еще нет).
//    В классе MainClassTest написать тест (назвать testGetClassString), который проверяет,
//    что метод getClassString возвращает строку, в которой есть подстрока “hello” или “Hello”, если нет ни одной из подстрок - тест падает.
    private String class_string = "Hello, world";
    public String getClassString ()
    {
        return class_string;
    }
}
