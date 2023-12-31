
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Pedido {

    private String comida;
    private double precio;

    public Pedido(String comida, double precio) {
        this.comida = comida;
        this.precio = precio;
    }

    public String getComida() {
        return comida;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return comida + " - Precio: $" + precio;
    }
}

public class MenuRestaurante {

    public static String nombreUsuario = "";
    public static double valorTotalCompra = 0;
    public static String direccion = "";
    public static double asado = 10;
    public static double milanesa = 20;
    public static double empanadas = 30;
    public static double pizza = 40;
    public static double pancho = 50;
    public static double hamburguesa = 60;
    public static double bebida = 70;
    public static ArrayList<String> opcionesSeleccionadas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pedido> pedidos = new ArrayList<>();
        while (true) {
            System.out.println(logo());

            System.out.println("MENÚ DEL RESTAURANTE");
            System.out.println("1) Agregar: Nombre del usuario - Dirección de vivienda - Datos de tarjeta");
            System.out.println("2) Seleccionar pedidos");
            System.out.println("3) Modificar pedidos (condimentos e ingredientes)");
            System.out.println("4) Ver pedidos cargados en el archivo");
            System.out.println("5) Borrar un pedido");
            System.out.println("6) Procesar compra");
            System.out.println("7) Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    datos();
                    break;
                case 2:
                    compra(pedidos);
                    break;

                case 3:

                    modificarPedido(pedidos);
                    break;
                case 4:
                    leerPedidosDesdeArchivo();
                    break;
                case 5:
                    eliminar(pedidos);
                    break;
                case 6:
                    System.out.println("Compra realizada con exito!");
                    eliminarArchivoPedidos();
                    break;
                case 7:
                    System.out.println("Gracias por su visita. ¡Hasta luego!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        }
    }

    // Datos del cliente.
    public static void datos(){
        Scanner scanner = new Scanner(System.in);
                    System.out.print("Ingrese su nombre: ");
                    nombreUsuario = scanner.nextLine();
                    System.out.print("Ingrese su dirección de vivienda: ");
                    direccion = scanner.nextLine();
                    System.out.print("Ingrese los datos de la tarjeta: ");
                    String datosTarjeta = scanner.nextLine();
                    guardarInformacionUsuario(nombreUsuario, direccion, datosTarjeta);
    }

    public static void eliminar(ArrayList<Pedido> pedidos) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número del pedido que desea borrar: ");
        int numeroPedidoABorrar = scanner.nextInt();
        if (numeroPedidoABorrar >= 1 && numeroPedidoABorrar <= pedidos.size()) {
            Pedido pedidoBorrado = pedidos.remove(numeroPedidoABorrar - 1);
            valorTotalCompra -= pedidoBorrado.getPrecio();
            System.out.println("Pedido eliminado: " + pedidoBorrado);
            guardarPedidosEnArchivo(pedidos, nombreUsuario, direccion, valorTotalCompra);
        } else {
            System.out.println("Número de pedido no válido.");
        }

    }

    public static void modificarPedido(ArrayList< Pedido> pedidos) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MODIFICAR UN PEDIDO:");
        System.out.print("Ingrese el número de pedido que desea modificar: \n");
        for (int i = 0; i < opcionesSeleccionadas.size(); i++) {
            System.out.println((i + 1) + ") " + opcionesSeleccionadas.get(i));
        }
        int numeroPedidoAModificar = scanner.nextInt();

        if (numeroPedidoAModificar >= 1 && numeroPedidoAModificar <= pedidos.size()) {
            Pedido pedidoAModificar = pedidos.get(numeroPedidoAModificar - 1);
            System.out.println("Pedido seleccionado: " + pedidoAModificar);
            realizarModificacion(pedidos, numeroPedidoAModificar, pedidoAModificar);
            guardarPedidosEnArchivo(pedidos, nombreUsuario, direccion, calcularNuevoTotal(pedidos));
        } else {
            System.out.println("Número de pedido no válido.");
        }
    }

    public static void realizarModificacion(ArrayList< Pedido> pedidos, int numeroPedidoAModificar, Pedido pedidoAModificar) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Agregar condimento (10% de aumento)");
        System.out.println("2) Agregar ingrediente (20% de aumento)");
        System.out.println("3) Agregar condimento e ingrediente (30% de aumento)");
        int opcionModificacion = scanner.nextInt();

        switch (opcionModificacion) {
            case 1:
                System.out.print("Ingrese el condimento a agregar (sal, oregano, provenzal, curry, pimienta): ");
                String condimento = scanner.next();
                double nuevoPrecio1 = pedidoAModificar.getPrecio() * 1.10;
                pedidoAModificar = new Pedido(pedidoAModificar.getComida() + " con " + condimento, nuevoPrecio1);
                pedidos.set(numeroPedidoAModificar - 1, pedidoAModificar);
                System.out.println("Pedido modificado: " + pedidoAModificar);

                break;

            case 2:
                System.out.print("Ingrese el ingrediente a agregar (lechuga, tomate, jamon, queso, palmito, sardina): ");
                String ingrediente = scanner.next();
                double nuevoPrecio2 = pedidoAModificar.getPrecio() * 1.20;
                pedidoAModificar = new Pedido(pedidoAModificar.getComida() + " con " + ingrediente, nuevoPrecio2);
                pedidos.set(numeroPedidoAModificar - 1, pedidoAModificar);
                System.out.println("Pedido modificado: " + pedidoAModificar);

                break;

            case 3:
                System.out.print("Ingrese el condimento a agregar (sal, oregano, provenzal, curry, pimienta): ");
                String nuevoCondimento = scanner.next();
                System.out.print("Ingrese el ingrediente a agregar (lechuga, tomate, jamon, queso, palmito, sardina): ");
                String nuevoIngrediente = scanner.next();
                double nuevoPrecio3 = pedidoAModificar.getPrecio() * 1.30;
                pedidoAModificar = new Pedido(pedidoAModificar.getComida() + " con " + nuevoCondimento + " y " + nuevoIngrediente, nuevoPrecio3);
                pedidos.set(numeroPedidoAModificar - 1, pedidoAModificar);
                System.out.println("Pedido modificado: " + pedidoAModificar);

                break;

            default:
                System.out.println("Opción no válida. No se realizó ninguna modificación.");
                break;
        }
    }

    public static void lista() {
        System.out.println("LISTA DE OPCIONES:");
        System.out.println("1) Asado con ensalada - Precio: $" + asado);
        System.out.println("2) Milanesa con puré mixto - Precio: $" + milanesa);
        System.out.println("3) Empanadas de carne y pollo - Precio: $" + empanadas);
        System.out.println("4) Pizza comun o a la piedra - Precio: $" + pizza);
        System.out.println("5) Pancho - Precio: $" + pancho);
        System.out.println("6) Hamburguesa - Precio: $" + hamburguesa);
        System.out.println("7) Bebida Coca-cola - Precio: $" + bebida);
        System.out.print("Seleccione el número de la comida que desea: ");
    }

    public static void compra(ArrayList< Pedido> pedidos) {
        Scanner scanner = new Scanner(System.in);
        lista();
        int numeroComida = scanner.nextInt();

        if (numeroComida >= 1 && numeroComida <= 7) {
            Pedido pedido = null;
            switch (numeroComida) {
                case 1:
                    pedido = new Pedido("Asado con ensalada", asado);
                    valorTotalCompra = valorTotalCompra + asado;
                    opcionesSeleccionadas.add("Asado con ensalada");
                    break;
                case 2:
                    pedido = new Pedido("Milanesa con puré mixto", milanesa);
                    valorTotalCompra = valorTotalCompra + milanesa;
                    opcionesSeleccionadas.add("Milanesa con puré mixto");
                    break;
                case 3:
                    pedido = new Pedido("Empanadas de carne y pollo", empanadas);
                    valorTotalCompra = valorTotalCompra + empanadas;
                    opcionesSeleccionadas.add("Empanadas de carne y pollo");
                    break;
                case 4:
                    pedido = new Pedido("Pizza común o a la piedra", pizza);
                    valorTotalCompra = valorTotalCompra + pizza;
                    opcionesSeleccionadas.add("Pizza común o a la piedra");
                    break;
                case 5:
                    pedido = new Pedido("Pancho", pancho);
                    valorTotalCompra = valorTotalCompra + pancho;
                    opcionesSeleccionadas.add("Pancho");
                    break;
                case 6:
                    pedido = new Pedido("Hamburguesa", hamburguesa);
                    valorTotalCompra = valorTotalCompra + hamburguesa;
                    opcionesSeleccionadas.add("Hamburguesa");
                    break;
                case 7:
                    pedido = new Pedido("Bebida Coca-cola", bebida);
                    valorTotalCompra = valorTotalCompra + bebida;
                    opcionesSeleccionadas.add("Bebida Coca-cola");
                    break;
            }
            pedidos.add(pedido);
            System.out.println("Pedido agregado: " + pedido);

            guardarPedidosEnArchivo(pedidos, nombreUsuario, direccion, valorTotalCompra);

            System.out.println("Valor total de la compra: $" + valorTotalCompra);
        } else {
            System.out.println("Número de comida no válido. Por favor, elija una opción válida.");
        }
    }
    private static void guardarInformacionUsuario(String nombre, String direccion, String tarjeta) {

        String rutaEscritorio = System.getProperty("user");
        String archivoPedidos = rutaEscritorio + "datos.txt";
        try ( PrintWriter writer = new PrintWriter(new FileWriter(archivoPedidos, true))) {

            writer.println("\n");
            writer.println("Nombre del usuario: " + nombre);
            writer.println("Dirección de vivienda: " + direccion);
            writer.println("Datos de tarjeta: " + tarjeta);
            writer.println("\n");
        } catch (IOException e) {
            System.out.println("Error al guardar la información del usuario.");
        }
    }

    public static String logo() {
           return "                                       /$$     /$$      / $$$$$$$$        /$$    /$$ \n"
                + "                                      | $$     |$$     | $$    | $$      | $$    |$$ \n"
                + "                                      | $$     |$$     | $$    | $$      | $$    |$$ \n"
                + "                                      | $$$$$$$$       | $$    | $$      | $$$$$$$$   \n"
                + "                                      |_______  $$     | $$    | $$      |______  $$  \n"
                + "                                              | $$     | $$    | $$             | $$ \n"
                + "                                              | $$     |  $$$$$$$$              | $$ \n"
                + "                                              |__/     |________/               |__/ \n"
                + ""
                + "   /$$$$$$$$|       /$$$$$$$$       /$$$$$$$|    /$$$$$$$$|    /$$$$      /$$   /$$   /$$$$$$$$     /$$$$     /$$$  /$$|  /$$$$$$$$|\n"
                + "  | $$    | $$|     |$$ ____/       |$$____/     |__ $$|___|  /$$  |$$|  | $$  | $$  | $$  | $$   /$$  | $$  | $$$ | $$|  |__ $$___|\n"
                + "  | $$    | $$|     |$$             |$$             |$$|     |$$   |$$|  | $$  | $$  | $$  | $$  | $$  | $$  | $$ $  $$|     |$$|   \n"
                + "  | $$ $$ $$|       |$$$$$$$$       |$$$$$$$|       |$$|     |$$$$$$$$|  | $$  | $$  | $$ $$ $$  | $$$$$$$$  | $$  $ $$|     |$$|   \n"
                + "  | $|     |$$|     |$$ ____/         |___$$|       |$$|     |$$ _  $$|  | $$  | $$  | $$  $$    | $$ _  $$  | $$ | $$$|     |$$|   \n"
                + "  | $|     | $|     |$$                   $$|       |$$|     |$$|  |$$|  | $$  | $$  | $$  | $$  | $$  | $$  | $$ |  $$|     |$$|   \n"
                + "  | $|     | $|     |$$$$$$$$       /$$$$$$$|       |$$|     |$$|  |$$|  | $$$$$$$$  | $$  | $$  | $$  | $$  | $$ | $$$|     |$$|   \n"
                + "  |__/     |__/     |________/     |________/       |__/     |__/  |__/  |________/  |__/  |__/  |__/  |__/  |__/ |___/      |__/   \n"
                + " ";

    }

    private static void guardarPedidosEnArchivo(ArrayList< Pedido> pedidos, String nombreUsuario, String direccion, double valorTotalCompra) {
        int largoCompra = 0;
        String rutaEscritorio = System.getProperty("user");
        String archivoPedidos = rutaEscritorio + "pedidos.txt";
        try ( PrintWriter writer = new PrintWriter(new FileWriter(archivoPedidos))) {
            writer.println("Nombre del cliente: " + nombreUsuario);
            writer.println("Direccion del cliente: " + direccion);
            writer.println("-------------------------------------");
            writer.println("Encargos: ");
            for (Pedido pedido : pedidos) {
                largoCompra += 1;
                writer.println(largoCompra + " " + pedido.getComida() + ": " + pedido.getPrecio());

            }
            writer.println("-------------------------------------");
            writer.println("Valor total de la compra: $" + valorTotalCompra);
            writer.println();
        } catch (IOException e) {
            System.out.println("Error al guardar los pedidos.");
        }
    }
    // Calcular el nuevo total.

    public static double calcularNuevoTotal(ArrayList<Pedido> pedidos) {
    double nuevoTotal = 0;
    for (Pedido pedido : pedidos) {
        nuevoTotal += pedido.getPrecio();
    }
    return nuevoTotal;
}

    private static void leerPedidosDesdeArchivo() {

        String rutaEscritorio = System.getProperty("user");
        String archivoPedidos = rutaEscritorio + "pedidos.txt";

        try ( BufferedReader reader = new BufferedReader(new FileReader(archivoPedidos))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    String comida = parts[0];
                    double precio = Double.parseDouble(parts[1]);
                    Pedido pedido = new Pedido(comida, precio);
                    System.out.println(pedido);
                }

                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los pedidos.");
        }
    }

    private static void eliminarArchivoPedidos() {
        String rutaEscritorio = System.getProperty("user");
        String archivoPedidos = rutaEscritorio + "pedidos.txt";
        File file = new File(archivoPedidos);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("\u001B[32;2m" + "****************************************************");
                System.out.println("*                                                  *");
                System.out.println("\u001B[32;2m" + "* ARCHIVO 'pedidos.txt' SE HA ELIMINADO CORRECTAMENTE.*");
                System.out.println("*                                                  *");
                System.out.println("\u001B[32;2m" + "****************************************************");
            } else {
                System.out.println("");
                System.out.println("\u001B[31m" + "ERROR AL ELIMINAR EL ARCHIVO 'pedidos.txt.");
                System.out.println("");
            }
        } else {
            System.out.println("");
            System.out.println("\u001B[31m" + "EARCHIVO 'pedidos.txt' NO EXISTE.");
            System.out.println("");
        }
    }
}
