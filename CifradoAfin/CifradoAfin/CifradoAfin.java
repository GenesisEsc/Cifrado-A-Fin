public class CifradoAfin {  
    
    //variable para almacenar los 27 caracteres del alfabeto incluyendo la ñ
    private static final String ALFABETO = "abcdefghijklmnñopqrstuvwxyz";
    //variable para indicar el modulo de la operacion, que es 27 por los 27 caracteres del alfabetos
    private static final int MODULO = 27;

//1. normalizar residuos negativos 
    //como el operador % en Java puede dar resultados negativos, este método asegura que siempre obtengamos un resultado positivo dentro del rango de 0 a 26.
    public int mod27(int n) {
        return ((n % MODULO)+ MODULO) % MODULO;
    }

    //2. máximo común divisor
    //algoritmo de Euclides para calcular el máximo común divisor (MCD) de dos números enteros. Se utiliza para verificar si la clave 'a' es coprima con 27, lo cual es un requisito para que el cifrado y descifrado funcionen correctamente.
    public int mcd(int a, int b) {
        //math abs elimina el signo negativo de los numeros, para que no afecte el calculo del mcd
        a= Math.abs(a);
        b= Math.abs(b);

        //ciclo hasta que el residuo sea 0, lo que indica que hemos encontrado el MCD
        while (b!=0) {
            int residuo= a % b; //calcular el residuo de a entre b
            a=b; //el divisor, es decir, b, pasa a ser el nuevo a
            b=residuo; //el residuo pasa a ser el nuevo b para la siguiente iteración
        }
        return a;
    }

    //3. inverso modular para descifrar el texto cifrado
    public int inversoModular (int a) {
        //a tiene que estar en el rango de 0 a 26 para que el ciclo funcione correctamente
        a= mod27(a);

        //probamos numeros del 1 al 26
        for(int x=1; x < 27; x++) {
            //multiplicamos a por x y verificamos si el residuo de la division entre 27 es igual a 1
            if ((a * x) % 27 == 1) {
                return x; //almacena el valor de x que cumple la condición y lo retorna
            }
        }
        //si el ciclo termina y no encuentra un inverso modular de a, retorna -1 indicando que no existe un inverso modular para el valor dado de a.
        return -1; 
    }

//4. metodo para cifrar
    public String cifrar(String texto, int a, int b) {
        //stringbuilder me permite trabajar en la misma cadena de texto sin llenar los espacios de memoria
        StringBuilder resultado = new StringBuilder();

        //valida el rango de a y b y que a sea coprima de 27; si no, lanza una excepcion
        validarClaves(a, b);

        //recorrer el texto
        for(int i=0; i< texto.length(); i++) {
            //obtiene el caracter en la posicion i
            char c = texto.charAt(i);

            // Detectamos si es mayúscula y la pasamos a minúscula temporalmente
            boolean esMayuscula = Character.isUpperCase(c);
            char cMinuscula = Character.toLowerCase(c);

            //obtiene el indice del caracter
            int index = ALFABETO.indexOf(cMinuscula);

            if (index != -1) { //vemos que el indice este en el alfabeto de 27
                //FORMULA: c=(a*m+b) mod 27
                int nuevoIndice = mod27((a*index)+b);
                char nuevoChar = ALFABETO.charAt(nuevoIndice);

                // Si era mayúscula originalmente, la devolvemos como mayúscula
                resultado.append(esMayuscula ? Character.toUpperCase(nuevoChar) : nuevoChar);
                
            }else{
                //resultado .append(c) conserva los caracteres que no están en el alfabeto, como espacios, signos de puntuación y números, sin cambios.
                resultado.append(c);
            }
        }
        //convierte el StringBuilder a String y lo retorna como resultado final del cifrado
        return resultado.toString();
    }

// 5. metodo para descifrar
    public String descifrar(String texto, int a, int b) {
        //stringbuilder me permite trabajar en la misma cadena de texto sin llenar los espacios de memoria
        StringBuilder resultado = new StringBuilder();

        //valida el rango de a y b y que a sea coprima de 27; si no, lanza una excepcion
        validarClaves(a, b);

        //obtener inverso modular (a^-1)
        int aInverso = inversoModular(a);

        //recorrer el texto
        for (int i=0; i<texto.length(); i++) {
            char c = texto.charAt(i);
            boolean esMayuscula = Character.isUpperCase(c);
            char cMinuscula = Character.toLowerCase(c);
            int index = ALFABETO.indexOf(cMinuscula);

            if(index !=-1) {
                // formula: M = (a^-1 * (C - b)) mod 27
                //multiplicamos el inverso por la resta de la posición (C) menos el desplazamiento (b)
                int operacion = aInverso * (index-b);

                // mod27 arregla automáticamente si operacion da un numero negativo
                int nuevoIndice = mod27(operacion);
                char nuevoChar = ALFABETO.charAt(nuevoIndice);
        
                 resultado.append(esMayuscula ? Character.toUpperCase(nuevoChar) : nuevoChar);
            }else{
                //conservamos espacios, signos y numeros sin cambio
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    // 6. validar claves
    //a debe estar entre 1 y 26 y ser coprima de 27 (para que exista el inverso modular); b debe estar entre 0 y 26
    private void validarClaves(int a, int b) {
        if (a < 1 || a >= MODULO) {
            throw new IllegalArgumentException("La clave a debe estar entre 1 y 26.");
        }
        if (mcd(a, MODULO) != 1) {
            throw new IllegalArgumentException("La clave a no es coprima de 27.");
        }
        if (b < 0 || b >= MODULO) {
            throw new IllegalArgumentException("La clave b debe estar entre 0 y 26.");
        }
    }
}