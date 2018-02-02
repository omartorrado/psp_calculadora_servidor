/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp_calculadora_servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author otorradomiguez
 */
public class Psp_calculadora_servidor {

    private static boolean salir = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //System.out.println("Creando socket servidor");
            while (!salir) {
                ServerSocket serverSocket = new ServerSocket();

                //System.out.println("Realizando el bind");
                InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
                serverSocket.bind(addr);

                //System.out.println("Aceptando conexiones");
                Socket newSocket = serverSocket.accept();

                //System.out.println("Conexi�n recibida");
                InputStream is = newSocket.getInputStream();
                OutputStream os = newSocket.getOutputStream();

                //Esto es el numero de bytes que leemos? parece que si
                byte[] mensaje = new byte[30];
                //el metodo is.read() devuelve el numero de bytes leidos del stream
                int bytesLeidos = is.read(mensaje);

                //tras leer la string recibida, separamos sus parametros
                String[] valores = new String(mensaje).trim().split(",");

                System.out.println("Mensaje recibido: " + valores[0] + "," + valores[1] + "," + valores[2]);

                //Filtramos las operaciones y guardamos el resultado en respuesta
                String respuesta = "Respuesta: ";
                int valor1 = Integer.parseInt(valores[0]);
                int valor2 = Integer.parseInt(valores[1]);
                float resultado;
                if (valores[2].equals("suma")) {
                    resultado = valor1 + valor2;
                    respuesta = respuesta + resultado;
                } else if (valores[2].equals("resta")) {
                    resultado = valor1 - valor2;
                    respuesta = respuesta + resultado;
                } else if (valores[2].equals("multip")) {
                    resultado = valor1 * valor2;
                    respuesta = respuesta + resultado;
                } else if (valores[2].equals("division")) {
                    resultado = valor1 / valor2;
                    respuesta = respuesta + resultado;
                } else if (valores[2].equals("salir")) {
                    respuesta = "Gracias por utilizar la calculadora de Omar";
                }else{
                    respuesta = "Operacion no valida";
                }

                //enviamos la respuesta
                byte[] mens = new byte[30];
                byte[] bytesMensaje = respuesta.getBytes();
                for (int i = 0; i < respuesta.getBytes().length; i++) {
                    mens[i] = bytesMensaje[i];
                }

                os.write(mens);

                if (valores[2].equals("salir")) {
                    newSocket.close();
                    serverSocket.close();
                    salir = true;
                } else {
                    newSocket.close();
                    serverSocket.close();
                }

            }
            //System.out.println("Cerrando el nuevo socket");

            //newSocket.close();
            //System.out.println("Cerrando el socket servidor");
            //serverSocket.close();
            //System.out.println("Terminado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
