# CodexNaturalis

## Team

Amina El Kharouai [@AminaElKharouai](https://github.com/AminaElKharouai)<br>
Jihad Founoun [@jihadfounoun](https://github.com/jihadfounoun)<br>
Mattia Locatelli [@MattiaLocatelli](https://github.com/MattiaLocatelli)<br>
Emanuele Laico [@emanuelelaico](https://github.com/emanuelelaico)<br>

## Progress

| Functionality    |                       State                       |
|:-----------------|:-------------------------------------------------:|
| Simplified rules | ![#c5f015](https://placehold.it/15/44bb44/44bb44) |
| Complete rules   | ![#c5f015](https://placehold.it/15/44bb44/44bb44) |
| Socket           | ![#c5f015](https://placehold.it/15/44bb44/44bb44) |
| RMI              | ![#c5f015](https://placehold.it/15/44bb44/44bb44) |
| CLI              | ![#c5f015](https://placehold.it/15/44bb44/44bb44) |
| GUI              | ![#c5f015](https://placehold.it/15/f03c15/f03c15) |
| Disconnections   | ![#c5f015](https://placehold.it/15/44bb44/44bb44) |
| Multiple games   | ![#c5f015](https://placehold.it/15/44bb44/44bb44) |
| Persistence      | ![#c5f015](https://placehold.it/15/f03c15/f03c15) |
| Chat             | ![#c5f015](https://placehold.it/15/f03c15/f03c15) |

## Setup

- In the [Jar](deliverables/jar) folder there are two jar files, one to set the Server up, and the
  other one to start the Client.
- The Server can be run with the following command:
    ```
    > java --enable-preview -jar Server.jar 
    ```
  This command must be followed by the desired hostname for the server as argument (usually the IP of the network
  interface), if you want to play with other devices in LAN.

- The Client can be run with the following commands:
    ```
  > chcp 65001
    ```
  to enable the UTF-8 charset on Windows, then:
    ```
    > java --enable-preview -jar Client.jar 
    ```
  The Server's IP to connect to can be specified during the execution.
  In case the emoji are not displayed correctly in the Windows PowerShell, we suggest to use the Command Prompt.  

## Utilized Software

* [Draw.io]: Sequence diagram and UML.
* [IntelliJ]: IDE for the project.
* [Maven]: Package and dependency management.

## Licences

**Cranio Creations**: https://www.craniocreations.it/

**Politecnico di Milano**: https://www.polimi.it/
