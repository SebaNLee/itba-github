public enum BasicOperation implements Operation {

    SUM("+", (x, y) -> x + y), // !! acá el lambda
    RES("-", (x, y) -> x - y),
    DIV("/", (x, y) -> x / y),
    MULT("*", (x, y) -> x * y);

    private String symbol;
    private Operation operation; // guardo la operación en instancia de interfaz

    BasicOperation(String symbol, Operation operation) { // guardo Operation interfaz, para usarlo en apply genérico
        this.symbol = symbol;
        this.operation = operation;
    }
    
    @Override
    public double apply(double x, double y) {
        return operation.apply(x, y); // !!! ejecuta apply() con el lambda guardado en var operation
    }

    @Override
    public String toString() {
        return symbol;
    }
}
