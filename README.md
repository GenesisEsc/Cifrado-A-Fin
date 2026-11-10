# Cifrado Afín en Java (Swing)

**Estudiante:** Génesis Escobar  
**Asignatura:** Seguridad Informática — Desarrollo de Software, 5.º nivel  
**Docente:** Elvis Pachacama Cabezas

Aplicación de escritorio que cifra y descifra textos con el **cifrado afín**, implementado a mano (sin `javax.crypto`, sin `BigInteger.modInverse` y sin bibliotecas externas).

## Requisitos

- **JDK 17 o superior** (Java SE estándar; solo se usan `java.awt` y `javax.swing`).
- No necesita dependencias externas ni conexión a internet para ejecutarse.
- Un entorno de escritorio con pantalla, porque la interfaz es gráfica (Swing).

## Configuración en Visual Studio Code

### 1. Instalar el JDK

1. Descargue e instale un **JDK 17 o superior** (por ejemplo, Eclipse Temurin 17 o Oracle JDK 17).
2. Abra una terminal nueva y compruebe la instalación:

   ```bash
   java -version
   javac -version
   ```

   Ambos comandos deben mostrar la versión 17 o superior. Si `javac` no se reconoce, se instaló solo el JRE o falta agregar el JDK al `PATH`.
3. En Windows, si es necesario, defina la variable de entorno `JAVA_HOME` con la carpeta del JDK (por ejemplo, `C:\Program Files\Eclipse Adoptium\jdk-17...`) y agregue `%JAVA_HOME%\bin` al `PATH`. Después reinicie Visual Studio Code.

### 2. Instalar las extensiones

En la pestaña **Extensiones** (`Ctrl + Shift + X`) busque e instale:

| Extensión | Autor | Para qué sirve |
|---|---|---|
| **Extension Pack for Java** | Microsoft | Paquete recomendado: incluye las extensiones de la siguiente parte. |
| Language Support for Java™ | Red Hat | Reconoce el código Java, muestra errores y autocompleta. |
| Debugger for Java | Microsoft | Permite ejecutar y depurar el programa. |
| Project Manager for Java | Microsoft | Muestra los archivos del proyecto (vista *Java Projects*). |
| Test Runner for Java | Microsoft | Ejecuta pruebas (opcional para este proyecto). |

Instalando solo **Extension Pack for Java** se instalan las demás. Maven y Gradle vienen en el paquete, pero este proyecto no los necesita.

### 3. Abrir y ejecutar el proyecto

1. Cree una carpeta (por ejemplo, `CifradoAfin`) y copie dentro `CifradoAfin.java` y `CifradoAfinGUI.java`.
2. En Visual Studio Code use **Archivo → Abrir carpeta** y seleccione esa carpeta. Espere a que termine de cargar el soporte de Java (barra inferior).
3. Abra `CifradoAfinGUI.java` y pulse **Run** (el botón ▶ sobre `public static void main`) o use **Run → Run Without Debugging** (`Ctrl + F5`).
4. Debe abrirse la ventana «Sistema de Cifrado Afín».

Si Visual Studio Code no encuentra el JDK, abra la paleta de comandos (`Ctrl + Shift + P`), ejecute **Java: Configure Java Runtime** y seleccione el JDK 17 instalado.

## Clase principal

`CifradoAfinGUI` (contiene el método `main`).

## Archivos

| Archivo | Función |
|---|---|
| `CifradoAfin.java` | Lógica: `mod27`, `mcd`, `inversoModular`, `cifrar`, `descifrar`. |
| `CifradoAfinGUI.java` | Interfaz gráfica Swing y manejo de eventos. |

La lógica y la interfaz están separadas: la ventana solo llama a los métodos de `CifradoAfin`.

## Compilar y ejecutar

Desde la carpeta que contiene los dos archivos `.java`:

```bash
javac -encoding UTF-8 CifradoAfin.java CifradoAfinGUI.java
java CifradoAfinGUI
```

En un IDE (IntelliJ IDEA, NetBeans o Eclipse), cree un proyecto con los dos archivos y ejecute `CifradoAfinGUI`.

## Cómo se usa

1. Escriba el texto en **Texto de Entrada**.
2. Ingrese las claves **a** y **b** (enteros).
3. Pulse **Cifrar** o **Descifrar**; el resultado aparece en **Resultado Generado**.
4. **Usar Resultado** copia el resultado a la entrada para hacer la operación inversa.
5. **Limpiar** vacía las áreas de texto y conserva las claves.

Ejemplo: `HOLA MUNDO` con a = 5 y b = 8 → `PCJI ÑFSWC`.

## Reglas del algoritmo

- **Alfabeto de 27 letras:** `abcdefghijklmnñopqrstuvwxyz` (incluye la ñ), por lo tanto el módulo es **27**.
- Cifrar: `C = (a × M + b) mod 27`. Descifrar: `M = a⁻¹ × (C − b) mod 27`.
- **a** debe cumplir `MCD(a, 27) = 1`. Valores válidos: 1, 2, 4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 19, 20, 22, 23, 25 y 26. **b** va de 0 a 26.
- Las mayúsculas y minúsculas se conservan. Espacios, saltos de línea, números, signos y vocales con tilde no se modifican.
- `mod27` normaliza los residuos negativos (`((n % 27) + 27) % 27`).

## Validaciones

- Entrada vacía: advertencia.
- Clave vacía o no entera: mensaje de error.
- Clave `a` no coprima con 27 (por ejemplo, a = 3): mensaje «La clave a no es coprima de 27.».
- Claves fuera de rango (`a` fuera de 1–26 o `b` fuera de 0–26): mensaje indicando el rango válido.
- Si una operación falla, el área de resultado se borra para no mostrar un resultado anterior como actual.

## Diferencias respecto a la guía (26 letras)

Con 27 letras cambian las posiciones desde la ñ, el inverso de 5 es 11 (no 21) y los resultados de la guía (`RALI QEVXA`) pasan a ser `PCJI ÑFSWC`. Los valores a = 2 y a = 13 son válidos con módulo 27.

## Aviso

Es un proyecto académico. El cifrado afín solo tiene 486 claves válidas con 27 letras y es vulnerable al análisis de frecuencias; no debe usarse para proteger información real.
