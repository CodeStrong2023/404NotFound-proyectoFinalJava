import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuRestaurante{
    
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

            System.out.println("MENU DEL RESTAURANTE");
            System.out.println("1) Agregar nombre del usuario, dirección de vivienda, datos de tarjeta");
            System.out.println("2) Seleccionar pedidos");
            System.out.println("3) Modificar pedidos (condimentos e ingredientes)");
            System.out.println("4) Ver pedidos cargados en el archivo");
            System.out.println("5) Borrar un pedido");
            System.out.println("6) procesar compra");
            System.out.println("7) salir");

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
                    System.out.println("compra realizada con exito");
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
    public static void datos() {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Ingrese su nombre: ");
            nombreUsuario = entrada.nextLine();
            System.out.println("Ingrese la dirección de su casa: ");
            direccion = entrada.nextLine();
            System.out.println("Ingrese los datos de la tarjeta: ");
            String datosTarjeta = entrada.nextLine();
            guardarInformacionlUsuario(nombreUsuario, direccion, datosTarjeta);
    }
    
     public static void eliminar(ArrayList<Pedido> pedidos){
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
        
      public static void modificarPedido(ArrayList<Pedido> pedidos) {
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
            guardarPedidosEnArchivo(pedidos,nombreUsuario, direccion, calcularNuevoTotal(pedidos));
        } else {
            System.out.println("Número de pedido no válido.");
        }
    }
    // Calcular el nuevo total.
    public static double calcularNuevoTotal(ArrayList<Pedido> pedidos) {
            double nuevoTotal = 0;
            for (Pedido pedido : pedidos) {
                nuevoTotal += getPrecio();
            }
            return nuevoTotal;
    }
}