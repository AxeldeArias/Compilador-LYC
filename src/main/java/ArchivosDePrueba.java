public enum ArchivosDePrueba {

    WHILE_WHILE("test-WhileWhile"),
    WHILE_IN_WHILE("test-WhileInWhile"),

    IF("test-If"),
    IF_CORTO("test-IfCorto"),
    IF_ELSE("test-IfElse"),
    IF_IF("test-IfIf"),
    IF_ELSE_IF("test-IfElseIf"),
    IF_IN_IF("test-IfInIf"),
    IF_WHILE("test-IfWhile"),
    IF_FACTOR("test-IfFactor"),
    PROGRAMA("..\\..\\prueba"),
    TEST_ASSEMBLER("test-Assembler"),
    TEST_ASSEMBLER_GET("test-Assembler-Get"),
    TEST_ASSEMBLER_DISPLAY("test-Assembler-Display"),

    DISPLAY("test-Display"),
    GET("test-Get");

    public final String nombre;

    private ArchivosDePrueba(String nombre) {
        String basepath = ".\\src\\resources\\";
        this.nombre = basepath + nombre;
    }
}