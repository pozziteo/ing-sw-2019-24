# ing-sw-2019-24

## Contributors:
Matteo Pozzi - Luca Pomè - Sara Sacco

## Game launch
To launch the game navigate to /deliveries/jar; to run from command line you can just type "java -jar name_of_jar.jar", while to run with double-click we suggest to use the scripts on the /launchers root and then choosing your OS. Don't modify the roots structure, or move files, or the game will crash. Server must be launched before clients.

In config.properties there are some in-game property you may vary; in particular "ipAddress" is the ip address to which clients will try to connect, "socketPort" and "rmiPort" are the ports where the server will listen to, "skulls" is the number of skulls used for games, "lobbyTimeout" is the timeout of lobbys before starting a game when in a lobby there are at least 3 players. The other timeouts are not particularly relevant.

Accounts.ser file contains a list of accounts already registered on the server, and all new accounts will be registered here.

For a better experience for Windows CLI users, we suggest to launch the game with a Linux environment to permit the console to support ANSI characters.

### Completed requirements:
- Complete rules
- CLI
- GUI
- Socket
- RMI
- Multiple games (FA)
