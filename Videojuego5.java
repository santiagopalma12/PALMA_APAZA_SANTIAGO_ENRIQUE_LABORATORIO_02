package labFP2;
import java.util.*;

public class Videojuego5{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continuar;

        do {
            Soldado[][] tablero = new Soldado[10][10];
            HashMap<String, Soldado[]> ejercitos = new HashMap<>();
            ejercitos.put("Ejército 1", new Soldado[10]);
            ejercitos.put("Ejército 2", new Soldado[10]);

            int vidaTotalEjercito1 = 0, vidaTotalEjercito2 = 0;
            int numeroSoldados = (int) (Math.random() * 11);

            for (int i = 0; i < numeroSoldados; i++) {
                Soldado soldado;
                int vida, fila, columna;
                String ejercito = (i % 2 == 0) ? "Ejército 1" : "Ejército 2";

                do {
                    vida = (int) (Math.random() * 6) + 1;
                    fila = (int) (Math.random() * 10);
                    columna = (int) (Math.random() * 10);
                    soldado = new Soldado("Soldado" + i, vida, fila, columna, ejercito);
                } while (verificar(tablero, soldado));

                tablero[fila][columna] = soldado;
                agregarSoldado(ejercitos, ejercito, soldado);

                if (ejercito.equals("Ejército 1")) {
                    vidaTotalEjercito1 += vida;
                } else {
                    vidaTotalEjercito2 += vida;
                }
            }

            mostrar(tablero);
            soldadoMayorVida(ejercitos);
            mostrarDatosEjercito(vidaTotalEjercito1, vidaTotalEjercito2);
            rankingSoldadosBurbuja(ejercitos);
            rankingSoldadosSeleccion(ejercitos);
            determinarGanador(vidaTotalEjercito1, vidaTotalEjercito2);

            System.out.println("¿Desea seguir jugando? Ingrese 'si' para continuar o cualquier otra tecla para salir.");
            continuar = scanner.nextLine();

        } while (continuar.equalsIgnoreCase("si"));

        System.out.println("Saliendo, gracias por jugar.");
        scanner.close();
    }

    public static boolean verificar(Soldado[][] tablero, Soldado soldado) {
        return tablero[soldado.getFila()][soldado.getColumna()] != null;
    }

    public static void agregarSoldado(HashMap<String, Soldado[]> ejercitos, String ejercito, Soldado soldado) {
        Soldado[] soldados = ejercitos.get(ejercito);
        for (int i = 0; i < soldados.length; i++) {
            if (soldados[i] == null) {
                soldados[i] = soldado;
                break;
            }
        }
    }

    public static void mostrar(Soldado[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == null) {
                    System.out.print("_________| ");
                } else {
                    System.out.print(tablero[i][j].getNombre() + " | ");
                }
            }
            System.out.println();
        }
    }

    public static void soldadoMayorVida(HashMap<String, Soldado[]> ejercitos) {
        for (String ejercito : ejercitos.keySet()) {
            Soldado[] soldados = ejercitos.get(ejercito);
            Soldado soldadoMayor = null;

            for (Soldado soldado : soldados) {
                if (soldado != null && (soldadoMayor == null || soldado.getNivelVida() > soldadoMayor.getNivelVida())) {
                    soldadoMayor = soldado;
                }
            }

            if (soldadoMayor != null) {
                System.out.println("Soldado con mayor nivel de vida en " + ejercito + ": " + soldadoMayor.getNombre() +
                        " (Vida: " + soldadoMayor.getNivelVida() + ")");
            }
        }
    }

    public static void mostrarDatosEjercito(int vidaTotalEjercito1, int vidaTotalEjercito2) {
        System.out.println("Vida total del Ejército 1: " + vidaTotalEjercito1);
        System.out.println("Vida total del Ejército 2: " + vidaTotalEjercito2);
    }

    public static void rankingSoldadosBurbuja(HashMap<String, Soldado[]> ejercitos) {
        for (String ejercito : ejercitos.keySet()) {
            Soldado[] soldados = ejercitos.get(ejercito);
            for (int i = 0; i < soldados.length - 1; i++) {
                for (int j = 0; j < soldados.length - i - 1; j++) {
                    if (soldados[j] != null && soldados[j + 1] != null &&
                            soldados[j].getNivelVida() < soldados[j + 1].getNivelVida()) {
                        Soldado temp = soldados[j];
                        soldados[j] = soldados[j + 1];
                        soldados[j + 1] = temp;
                    }
                }
            }

            System.out.println("Ranking de poder de " + ejercito + " (Burbuja):");
            for (Soldado soldado : soldados) {
                if (soldado != null) {
                    System.out.println(soldado.getNombre() + " - Vida: " + soldado.getNivelVida());
                }
            }
        }
    }

    public static void rankingSoldadosSeleccion(HashMap<String, Soldado[]> ejercitos) {
        for (String ejercito : ejercitos.keySet()) {
            Soldado[] soldados = ejercitos.get(ejercito);
            for (int i = 0; i < soldados.length - 1; i++) {
                int maxIdx = i;
                for (int j = i + 1; j < soldados.length; j++) {
                    if (soldados[j] != null && soldados[maxIdx] != null &&
                            soldados[j].getNivelVida() > soldados[maxIdx].getNivelVida()) {
                        maxIdx = j;
                    }
                }
                if (soldados[i] != null && soldados[maxIdx] != null) {
                    Soldado temp = soldados[maxIdx];
                    soldados[maxIdx] = soldados[i];
                    soldados[i] = temp;
                }
            }

            System.out.println("Ranking de poder de " + ejercito + " (Selección):");
            for (Soldado soldado : soldados) {
                if (soldado != null) {
                    System.out.println(soldado.getNombre() + " - Vida: " + soldado.getNivelVida());
                }
            }
        }
    }

    public static void determinarGanador(int vidaTotalEjercito1, int vidaTotalEjercito2) {
        if (vidaTotalEjercito1 > vidaTotalEjercito2) {
            System.out.println("¡El Ejército 1 ha ganado!");
        } else if (vidaTotalEjercito1 < vidaTotalEjercito2) {
            System.out.println("¡El Ejército 2 ha ganado!");
        } else {
            System.out.println("¡Empate entre los ejércitos!");
        }
    }
}


