package org.yourcompany.yourproject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Ficheros {

    // El método main es el punto de entrada. Desde aquí llamamos a las demás funciones.
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        System.out.println("Vols crear el fitxer o contar quantes vegades es repeteix un carácter dins del fitxer?");
        System.out.println("---ESCRIU CREAR (si el vols crear)--- ");
        System.out.println("---ESCRIU CONTAR (si el vols contar)---");
        System.out.print("Funció: ");
        String accio = lector.nextLine();

        if (accio.equals("CREAR")|| accio.equals("crear")){
            System.out.println("--- INICIANDO REGISTRO DE DATOS ---");
            crearMapa(); // Llamamos al primer bloque de código
        }
        else if (accio.equals("CONTAR") || accio.equals("contar")){
            System.out.println("\n--- INICIANDO CONTEO DE LETRAS ---");
            contarLletres(); // Llamamos al segundo bloque de código 
        }
        else {
            System.out.println("Posa una acció correcta.");
        }
    }

    // 2. Método sacado fuera del main y corregido a "public static void"
    public static void crearMapa() {
        Scanner lector = new Scanner(System.in);
        String nom;
        int numero;
        int edad;
        List<Integer> edades = new ArrayList<>();

        try (FileWriter f = new FileWriter("agendaNumeros.txt", true)) {
            for (int i = 0; i < 5; i++) {
                System.out.print("Escriu un nom: ");
                nom = lector.nextLine();

                System.out.print("Escriu un numero: ");
                numero = Integer.parseInt(lector.nextLine());

                System.out.print("Escriu una edat: "); // Corregido a print normal, no err
                edad = Integer.parseInt(lector.nextLine());
                edades.add(edad);

                f.write((i + 1) + ": Nom: " + nom + ", Numero: " + numero + ", Edat: " + edad + "\n");
                System.out.println("Registrat correctamente.");
            }
            
        } catch (Exception e) {
            System.out.println("Error treballant amb fitxers: " + e.getMessage());
        }
        
        // Leer el archivo y calcular estadísticas
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("agendaNumeros.txt")));
            
            // Contar palabras usando split
            String[] palabras = contenido.trim().split("\\s+|,|:");
            int totalPalabras = 0;
            for (String palabra : palabras) {
                if (!palabra.isEmpty()) {
                    totalPalabras++;
                }
            }
            
            // Calcular media de edades
            double mediaEdades = 0;
            if (!edades.isEmpty()) {
                int sumaEdades = 0;
                for (int e : edades) {
                    sumaEdades += e;
                }
                mediaEdades = (double) sumaEdades / edades.size();
            }
            
            // Mostrar resultados
            System.out.println("\n=== ESTADÍSTICAS DEL FICHERO ===");
            System.out.println("Total de palabras: " + totalPalabras);
            System.out.println("Media de edades: " + String.format("%.2f", mediaEdades));
            
        } catch (Exception e) {
            System.out.println("Error leyendo el fichero: " + e.getMessage());
        }
    }
    
    // 3. Método separado para contar las letras
    public static void contarLletres() {
        // Asegúrate de que este archivo exista o cambiará la ruta a "agendaNumeros.txt"
        // si quieres leer el archivo que acabas de crear.
        String rutaArchivo = "/home/alumne/Documents/Programació/FitxersJava/agendaNumeros.txt";
        Map<Character, Integer> contador = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            int caracterLeido;
            
            while ((caracterLeido = br.read()) != -1) {
                char letra = (char) caracterLeido;
                
                if (Character.isLetter(letra)) {
                    letra = Character.toLowerCase(letra);
                    contador.put(letra, contador.getOrDefault(letra, 0) + 1);
                }
            }

        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo: " + e.getMessage());
        }
        
        System.out.println("--- Conteo de Letras ---");
        char letraMasRepetida = ' ';
        int maxRepeticiones = 0;
        for (Map.Entry<Character, Integer> entrada : contador.entrySet()) {
            System.out.println("Lletra '" + entrada.getKey() + "': " + entrada.getValue() + " vegades");
            if (entrada.getValue() > maxRepeticiones) {
                maxRepeticiones = entrada.getValue(); // Actualizamos el nuevo récord de cantidad
                letraMasRepetida = entrada.getKey();  // Guardamos quién es la nueva letra ganadora
            }
        }
        System.out.println("-- Més repeticions "+letraMasRepetida);
        System.out.println("-- Es repeteix "+maxRepeticiones +" vegades.");
    }
}