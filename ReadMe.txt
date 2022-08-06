Server
	To start do "java ChatServer"
	To change startup port do "-csp xxxxx" afterwards
	If multiple ports are specified you will be asked to choose one
	"EXIT" will close all connections and shut down
	"/getIP" will print the IP address
Client
	To start do "java ChatClient"
	To change startup port do "-ccp xxxxx" afterwards
	To change startup port do "-cca xxx.xxx.xxx.xxx" afterwards
	These can be used together or singularly eg
	"java ChatClient"
	"java ChatClient -ccp 14070"
	"java ChatClient -cca 127.0.0.2"
	"java ChatClient -ccp 14070 -cca 127.0.0.2"
	"java ChatClient -cca 127.0.0.2 -ccp 14070" 
	These are all valid start up uses
	If multiple ports or multiple IPs are specified you will be asked to choose one
	Doing "/setuser xxx" will allow you to set a username 
	eg "/setuser jake", you cannot have the same username as someone else
	Typing "QUIT" will disconnect you from the server and is how to quit
	Typing "/pm" will allow you to send a personal message to another online user
	usage: "/pm nameofrecepient message" eg "/pm jake yoyo"
	Any other message written by the user will be sent to all other users + the server
