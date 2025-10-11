# Taller 2 POO

Taller realizado en parejas por: Catalina Rojas y Benjamin Cortes

## Clases
Main.java ->> Es la clase principal, la cual tiene toda la logica de aplicacion y los menus correspondientes al sistema.

Usuario.java ->> Es la clase en donde se representa al usuario con sus credenciales y roles dentro del sistema.

PC.java ->> Es la clase en donde se encuentra la informacion de los Pcs y donde se calculan los niveles de riesgo de los mismos.

Puerto.java ->> Es la clase en donde se representa al puerto de red, con su estado y las vulnerabilidades asociadas al mismo.

Vulnerabilidad.java ->> Es la clase en donde se representa la vulnerabilidad de seguridad conocida asociada a un puerto especifico.

Escaner.java ->> Es la clase en donde se representa el formato del reporte final al hacer un escaneo de seguridad al pc.

## Descripcion formato archivos txt
usuarios.txt --> Formato: (usuario;contraseña;rol) ==> Ejemplo: juan;JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyk=;USER

pcs.txt --> Formato: (id|ip|sistema operativo) ==> Ejemplo: SRV-PROD-01|192.168.1.100|Windows Server 2022

puertos.txt --> Formato: (id pc|numero puerto |estado puerto) ==> Ejemplo: SRV-PROD-01|22|abierto

vulnerabilidades.txt --> Formato: (puerto|nombre|descripcion) ==> Ejemplo: 22|SSH Brute Force|Ataque de fuerza bruta contra servicio SSH

reportes.txt 
--> Formato:
(PC: id pc
IP: direccion ip
So: sistema operativo
Usuario: usuario que hizo el escaneo
Nivel de riesgo: categoria de riesgo
Fecha: fecha del escaneo
Puertos abiertos: 
-- puertos expuestos con vulnerabilidades
--> * nombre de la vulnerabilidad --> * descripcion de la vulnerabilidad
)

==> Ejemplo:
=== Reporte de Escaneo ===
PC: SERVER-01
IP: 192.168.1.100
So: Windows Server 2022
Usuario: admin
Nivel de Riesgo: ALTO
Fecha: Wed Nov 15 10:30:45 CLST 2023
Puertos abiertos:
 - Puerto 3389
--> * RDP Exploit --> * Vulnerabilidad en Remote Desktop

## Archivos txt empleados
--> pcs.txt

PC001|10.0.5.10|Windows 11 Pro
PC002|10.25.8.15|Ubuntu 22.04 LTS
PC003|172.16.1.20|Windows 10 Home
PC004|172.25.10.25|CentOS 8
PC005|192.168.1.30|macOS Ventura
PC006|192.168.50.35|Debian 11
PC007|8.45.60.40|Windows Server 2019
PC008|130.85.70.45|Fedora 38
PC009|198.51.100.50|Windows 11 Home
PC010|203.0.113.55|openSUSE Leap 15.4

--> puertos.txt

PC001|22|Cerrado
PC001|80|Abierto
PC001|443|Abierto
PC001|3389|Abierto
PC002|22|Abierto
PC002|80|Abierto
PC002|443|Cerrado
PC002|3306|Abierto
PC003|22|Cerrado
PC003|80|Cerrado
PC003|135|Abierto
PC003|445|Abierto
PC004|22|Abierto
PC004|80|Abierto
PC004|443|Abierto
PC004|8080|Abierto
PC005|22|Abierto
PC005|80|Cerrado
PC005|443|Cerrado
PC005|5900|Abierto
PC006|22|Abierto
PC006|80|Abierto
PC006|443|Abierto
PC006|993|Abierto
PC007|22|Cerrado
PC007|80|Abierto
PC007|135|Abierto
PC007|3389|Abierto
PC008|22|Abierto
PC008|25|Abierto
PC008|80|Abierto
PC008|443|Cerrado
PC009|80|Cerrado
PC009|135|Abierto
PC009|443|Cerrado
PC009|5357|Abierto
PC010|22|Abierto
PC010|80|Abierto
PC010|443|Abierto
PC010|631|Abierto

--> usuario.txt

admin;JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=;ADMIN  ==> Contraseña: admin123
alice;A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=;USER  ==> Contraseña: user123
bob;iNQmb9TmM40TuEX88olXnSCciXgjuSF9o+Fhk28DFYk=;USER  ==> Contraseña: bob123

--> Vulnerabilidades.txt

22|SSH Brute Force|Permite ataques de fuerza bruta contra el servicio SSH para obtener credenciales válidas
80|HTTP Directory Traversal|Vulnerabilidad que permite acceder a archivos fuera del directorio web mediante secuencias de navegación
135|RPC Buffer Overflow|Desbordamiento de buffer en el servicio RPC que puede permitir ejecución remota de código
443|SSL/TLS Weak Cipher|Uso de cifrados débiles en la conexión SSL que pueden ser comprometidos por atacantes
445|SMB Remote Code Execution|Vulnerabilidad en el protocolo SMB que permite la ejecución remota de código malicioso
3306|MySQL SQL Injection|Inyección SQL en bases de datos MySQL que permite acceso no autorizado a datos sensibles
3389|RDP BlueKeep|Vulnerabilidad crítica en Remote Desktop Protocol que permite ejecución remota de código sin autenticación
5900|VNC Authentication Bypass|Bypass de autenticación en servicios VNC que permite acceso remoto no autorizado
8080|HTTP Proxy Misconfiguration|Configuración incorrecta del proxy HTTP que puede exponer servicios internos
25|SMTP Open Relay|Servidor de correo configurado incorrectamente que puede ser usado para envío masivo de spam
993|IMAP SSL Certificate Validation|Validación incorrecta de certificados SSL en servicios IMAP que permite ataques man-in-the-middle
631|CUPS Remote Code Execution|Vulnerabilidad en el servicio de impresión CUPS que permite ejecución remota de código
5357|UPnP SSDP Amplification|Servicio UPnP mal configurado que puede ser usado para ataques de amplificación DDoS
